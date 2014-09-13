/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.linking;

import static com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter.COLLECTOR_IS_REGULAR;
import static com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;
import static com.puppetlabs.geppetto.pp.dsl.linking.PPFinder.CLASS_AND_TYPE;
import static com.puppetlabs.geppetto.pp.dsl.linking.PPFinder.DEF_AND_TYPE;
import static com.puppetlabs.geppetto.pp.dsl.linking.PPFinder.FUNC;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.puppetlabs.geppetto.common.tracer.ITracer;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.AttributeOperation;
import com.puppetlabs.geppetto.pp.AttributeOperations;
import com.puppetlabs.geppetto.pp.BinaryExpression;
import com.puppetlabs.geppetto.pp.Case;
import com.puppetlabs.geppetto.pp.CaseExpression;
import com.puppetlabs.geppetto.pp.CollectExpression;
import com.puppetlabs.geppetto.pp.Definition;
import com.puppetlabs.geppetto.pp.DefinitionArgument;
import com.puppetlabs.geppetto.pp.ElseExpression;
import com.puppetlabs.geppetto.pp.ElseIfExpression;
import com.puppetlabs.geppetto.pp.ExprList;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.ExpressionTE;
import com.puppetlabs.geppetto.pp.FunctionCall;
import com.puppetlabs.geppetto.pp.HostClassDefinition;
import com.puppetlabs.geppetto.pp.IfExpression;
import com.puppetlabs.geppetto.pp.Lambda;
import com.puppetlabs.geppetto.pp.LiteralExpression;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.MethodCall;
import com.puppetlabs.geppetto.pp.NodeDefinition;
import com.puppetlabs.geppetto.pp.PPPackage;
import com.puppetlabs.geppetto.pp.ParameterizedExpression;
import com.puppetlabs.geppetto.pp.ParenthesisedExpression;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.ResourceBody;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.SelectorEntry;
import com.puppetlabs.geppetto.pp.UnlessExpression;
import com.puppetlabs.geppetto.pp.UnquotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.VariableTE;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.adapters.CrossReferenceAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.contentassist.PPProposalsGenerator;
import com.puppetlabs.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import com.puppetlabs.geppetto.pp.dsl.linking.PPFinder.SearchResult;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.PPPatternHelper;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;

/**
 * Handles special linking of ResourceExpression, ResourceBody and Function references.
 */
public class PPResourceLinker implements IPPDiagnostics {

	private static String proposalIssue(String issue, String[] proposals) {
		if(proposals == null || proposals.length == 0)
			return issue;
		return issue + ISSUE_PROPOSAL_SUFFIX;
	}

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(PPDSLConstants.PP_DEBUG_LINKER)
	private ITracer tracer;

	/**
	 * Access to precompiled regular expressions
	 */
	@Inject
	private PPPatternHelper patternHelper;

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider
	 * that is aware of the context (builder, dirty editors, etc.). It is used to obtain the
	 * index for a particular resource. This special provider is obtained here.
	 */
	@Inject
	private ResourceDescriptionsProvider indexProvider;

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	private IQualifiedNameConverter converter;

	@Inject
	private PPProposalsGenerator proposer;

	@Inject
	private PPFinder ppFinder;

	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	@Inject
	private ISearchPathProvider searchPathProvider;

	@Inject
	private Provider<IValidationAdvisor> validationAdvisorProvider;

	private void _link(CollectExpression o, LinkContext ctx) {
		classifier.classify(o);
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null || resourceType != COLLECTOR_IS_REGULAR)
			return;

		internalLinkResourceTypeExpression(o, o.getClassReference(), true, ctx);
		IEObjectDescription desc = adapter.getTargetObjectDescription(IEObjectDescription.class);
		if(desc != null)
			internalLinkAttributeOperations(o.getAttributes(), desc, ctx);
	}

	/**
	 * Links an arbitrary interpolation expression. Handles the special case of a literal name expression e.g.
	 * "${literalName}" as
	 * if it was "${$literalname}"
	 *
	 * @param o
	 * @param importedNames
	 * @param acceptor
	 */
	private void _link(ExpressionTE o, LinkContext ctx) {
		Expression expr = o.getExpression();
		if(expr instanceof ParenthesisedExpression)
			expr = ((ParenthesisedExpression) expr).getExpr();
		String varName = null;
		if(expr instanceof LiteralNameOrReference)
			varName = ((LiteralNameOrReference) expr).getValue();
		if(varName == null)
			return; // it is some other type of expression - it is validated as expression
		String varNameWithDollar = varName.startsWith("$")
			? varName
			: ('$' + varName);
		if(patternHelper.isVARIABLE(varNameWithDollar) || patternHelper.isDECIMALVAR(varNameWithDollar))
			internalLinkVariable(expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, varName, ctx);
		else
			ctx.acceptError("Not a valid variable name", expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, ISSUE__NOT_VARNAME);
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(FunctionCall o, LinkContext ctx) {

		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		LiteralNameOrReference name = (LiteralNameOrReference) o.getLeftExpr();
		checkValidName(name, ctx);
		internalLinkFunctionCall(o, o.getLeftExpr(), name.getValue(), ctx);
	}

	private void _link(HostClassDefinition o, LinkContext ctx) {
		final LiteralExpression parent = o.getParent();
		String parentString = PPFinder.getNameString(parent);
		if(parentString == null)
			return;

		checkValidName(parent, parentString, ctx);
		PPImportedNamesAdapter importedNames = ctx.getImportedNames();
		SearchResult searchResult = ppFinder.findHostClasses(o, parentString, importedNames);
		List<IEObjectDescription> descs = searchResult.getAdjusted();
		if(descs.size() > 0) {
			CrossReferenceAdapter.set(parent, descs);
			// record resolution at resource level
			importedNames.addResolved(descs);

			if(descs.size() > 1) {
				// this is an ambiguous link - multiple targets available and order depends on the
				// order at runtime (may not be the same).
				importedNames.addAmbiguous(descs);
				ctx.acceptWarning(
					"Ambiguous reference to: '" + parentString + "' found in: " + visibleResourceList(o.eResource(), descs), o,
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, ISSUE__AMBIGUOUS_REFERENCE,
					proposer.computeDistinctProposals(parentString, descs));
			}
			// must check for circularity
			List<QualifiedName> visited = Lists.newArrayList();
			visited.add(converter.toQualifiedName(o.getClassName()));
			checkCircularInheritence(o, descs, visited, ctx);
		}
		else if(searchResult.getRaw().size() > 0) {
			List<IEObjectDescription> raw = searchResult.getRaw();
			CrossReferenceAdapter.set(parent, raw);

			// Sort of ok, it is not on the current path
			// record resolution at resource level, so recompile knows about the dependencies
			importedNames.addResolved(raw);
			ctx.acceptWarning(
				"Found outside current search path: '" + parentString + "'", o, PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
				ISSUE__NOT_ON_PATH);

		}
		else {
			// record unresolved name at resource level
			addUnresolved(importedNames, converter.toQualifiedName(parentString), NodeModelUtils.findActualNodeFor(parent));
			// importedNames.addUnresolved(converter.toQualifiedName(parentString));
			CrossReferenceAdapter.clear(parent);

			// ... and finally, if there was neither a type nor a definition reference
			String[] proposals = proposer.computeProposals(
				parentString, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), CLASS_AND_TYPE);
			ctx.acceptError("Unknown class: '" + parentString + "'", o, //
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, proposalIssue(ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
				proposals);
		}

		if(!advisor().allowInheritanceFromParameterizedClass()) {
			List<IEObjectDescription> targets = descs.size() > 0
				? descs
				: searchResult.getRaw();
			if(targets.size() > 0) {
				IEObjectDescription target = targets.get(0);
				if(target.getUserData(PPDSLConstants.CLASS_ARG_COUNT) != null)
					ctx.acceptError("Can not inherit from a parameterized class in Puppet versions < 3.0.", o, //
						PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, ISSUE__INHERITANCE_WITH_PARAMETERS);
			}
		}
	}

	/**
	 * Deal with LiteralNameOrReference expression that are not dealt with elsewhere but needs
	 * to be linked nevertheless.
	 *
	 * @param o
	 * @param ctx
	 */
	private void _link(LiteralNameOrReference o, LinkContext ctx) {
		if(!advisor().allowTypeDefinitions())
			return;

		EObject c = o.eContainer();
		// @fmtOff
		boolean possibleTypeRef =
			   c instanceof BinaryExpression
			|| c instanceof CaseExpression
			|| c instanceof ParenthesisedExpression
			|| c instanceof DefinitionArgument;
		// @fmtOn

		if(!possibleTypeRef && c instanceof ParameterizedExpression)
			possibleTypeRef = !(c instanceof FunctionCall || c instanceof MethodCall);

		if(!possibleTypeRef)
			return;

		String name = o.getValue();
		if(!patternHelper.isCLASSREF(name))
			return;

		PPImportedNamesAdapter importedNames = ctx.getImportedNames();
		SearchResult result = ppFinder.findDefinitions(o, name, importedNames);
		List<IEObjectDescription> refs = result.getAdjusted();
		boolean existsAdjusted = !refs.isEmpty();
		boolean existsRaw = existsAdjusted;
		if(existsAdjusted) {
			if(refs.size() > 1) {
				importedNames.addAmbiguous(refs);
				ctx.acceptWarning(
					"Ambiguous reference to: '" + name + "' found in: " + visibleResourceList(o.eResource(), refs), o,
					ISSUE__AMBIGUOUS_REFERENCE, proposer.computeDistinctProposals(name, refs, true));
			}
		}
		else {
			refs = result.getRaw();
			existsRaw = !refs.isEmpty();
			if(existsRaw)
				ctx.acceptWarning("Found outside search path: '" + name + "'", o, ISSUE__NOT_ON_PATH);
			else {
				String[] proposals = proposer.computeProposals(
					name, ppFinder.getExportedDescriptions(), true, ctx.getSearchPath(), PPFinder.DEF_AND_TYPE);
				ctx.acceptError("Unknown type: '" + name + "'", o, proposalIssue(ISSUE__UNKNOWN_TYPE, proposals), proposals);
			}
		}

		if(refs.size() == 1 && advisor().allowTypeDefinitions()) {
			IEObjectDescription ref = refs.get(0);
			int cls = ClassifierAdapter.UNKNOWN;
			if(PPTPPackage.Literals.PUPPET_TYPE.isSuperTypeOf(ref.getEClass()))
				cls = ClassifierAdapter.PUPPET_TYPE;
			else if(PPPackage.Literals.DEFINITION.isSuperTypeOf(ref.getEClass()))
				cls = PPPackage.Literals.HOST_CLASS_DEFINITION.isSuperTypeOf(ref.getEClass())
					? ClassifierAdapter.CLASS_REFERENCE
					: ClassifierAdapter.USER_DEFINED_RESOURCE_TYPE;
			else if(PPTPPackage.Literals.TYPE.isSuperTypeOf(ref.getEClass()))
				cls = ClassifierAdapter.RESOURCE_TYPE;

			classifier.classify(o);
			ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
			adapter.setTargetObject(ref);
			adapter.setClassifier(cls);
		}

		if(refs.size() > 0) {
			importedNames.addResolved(refs);
			CrossReferenceAdapter.set(o, refs);
		}
		else {
			CrossReferenceAdapter.clear(o);
			addUnresolved(ctx.getImportedNames(), name, NodeModelUtils.findActualNodeFor(o));
		}
	}

	private void _link(MethodCall o, LinkContext ctx) {
		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		Expression methodExpr = o.getMethodExpr();
		if(!(methodExpr instanceof LiteralName))
			return;
		LiteralName name = (LiteralName) methodExpr;
		checkValidName(name, ctx);
		internalLinkFunctionCall(o, methodExpr, name.getValue(), ctx);
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(ResourceBody o, LinkContext ctx, boolean profileThis) {

		ResourceExpression resource = (ResourceExpression) o.eContainer();
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		if(adapter.getClassifier() == ClassifierAdapter.UNKNOWN) {
			classifier.classify(resource);
			adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		}
		CLASSPARAMS: if(adapter.getClassifier() == RESOURCE_IS_CLASSPARAMS) {
			// pp: class { classname : parameter => value ... }

			final String className = stringConstantEvaluator.doToString(o.getNameExpr());
			if(className == null) {
				if(canBeAClassReference(o.getNameExpr())) {
					ctx.acceptWarning("Can not determine until runtime if this is a valid class reference (parameters not validated).", //
						o, // Flag entire body
							// PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, //
						ISSUE__RESOURCE_UNKNOWN_TYPE);
				}
				else {
					ctx.acceptError("Not a valid class reference", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, ISSUE__NOT_CLASSREF);
				}
				CrossReferenceAdapter.clear(o.getNameExpr());
				break CLASSPARAMS;
			}
			PPImportedNamesAdapter importedNames = ctx.getImportedNames();
			SearchResult searchResult = ppFinder.findHostClasses(o, className, importedNames);
			List<IEObjectDescription> descs = searchResult.getAdjusted();
			if(descs.size() < 1) {
				if(searchResult.getRaw().size() > 0) {
					// Sort of ok
					importedNames.addResolved(searchResult.getRaw());
					CrossReferenceAdapter.set(o.getNameExpr(), searchResult.getRaw());
					ctx.acceptWarning(
						"Found outside current search path (parameters not validated): '" + className + "'", o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, ISSUE__NOT_ON_PATH);
					return; // skip validating parameters

				}

				// Add unresolved info at resource level
				addUnresolved(importedNames, converter.toQualifiedName(className), NodeModelUtils.findActualNodeFor(o.getNameExpr()));
				// importedNames.addUnresolved(converter.toQualifiedName(className));
				CrossReferenceAdapter.clear(o.getNameExpr());

				String[] proposals = proposer.computeProposals(
					className, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), CLASS_AND_TYPE);
				ctx.acceptError("Unknown class: '" + className + "'", o, //
					PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, proposalIssue(ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
					proposals);
				return; // not meaningful to continue (do not report errors for each "inner name")
			}
			if(descs.size() > 0) {
				// Report resolution at resource level
				importedNames.addResolved(descs);
				CrossReferenceAdapter.set(o.getNameExpr(), descs);

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same). ISSUE: o can be a ResourceBody
					importedNames.addAmbiguous(descs);
					ctx.acceptWarning(
						"Ambiguous reference to: '" + className + "' found in: " + visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, ISSUE__AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(className, descs));
				}
				// use the first description found to find attributes
				internalLinkAttributeOperations(o.getAttributes(), descs.get(0), ctx);
			}

		}
		else if(adapter.getClassifier() != RESOURCE_IS_OVERRIDE || resource.getResourceExpr() instanceof AtExpression) {
			// normal resource or override file{} or File[x] { }
			IEObjectDescription desc = adapter.getTargetObjectDescription();
			// do not flag undefined parameters as errors if type is unknown
			if(desc != null) {
				internalLinkAttributeOperations(o.getAttributes(), desc, ctx);
			}
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(ResourceExpression o, LinkContext ctx) {
		classifier.classify(o);

		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return;

		// If resource is good, and not 'class', then it must have a known reference type.
		// the resource type - also requires getting the type name from the override's expression).
		if(resourceType == RESOURCE_IS_CLASSPARAMS) {
			// resource is pp: class { classname : parameter => value }
			// do nothing
		}
		else if(resourceType == RESOURCE_IS_OVERRIDE) {
			// TODO: possibly check a resource override if the expression is constant (or it is impossible to lookup
			// do nothing
			if(o.getResourceExpr() instanceof AtExpression) {
				internalLinkResourceTypeExpression(o, ((AtExpression) o.getResourceExpr()).getLeftExpr(), true, ctx);
			}
		}
		else {
			internalLinkResourceTypeExpression(o, o.getResourceExpr(), false, ctx);
		}
	}

	private void _link(VariableExpression o, LinkContext ctx) {
		// a definition of a variable (as opposed to a reference) is a leftExpr in an assignment expression
		if(o.eContainer().eClass() == PPPackage.Literals.ASSIGNMENT_EXPRESSION &&
			PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR == o.eContainingFeature())
			return; // is a definition
		internalLinkVariable(o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME, o.getVarName(), ctx);

	}

	private void _link(VariableTE o, LinkContext ctx) {
		internalLinkVariable(o, PPPackage.Literals.VARIABLE_TE__VAR_NAME, o.getVarName(), ctx);
	}

	private void addUnresolved(PPImportedNamesAdapter importedNames, QualifiedName name, INode node) {
		importedNames.addUnresolved(name, node.getTotalStartLine(), node.getTotalOffset(), node.getTotalLength());
	}

	private void addUnresolved(PPImportedNamesAdapter importedNames, String name, INode node) {
		addUnresolved(importedNames, converter.toQualifiedName(name), node);
	}

	private IValidationAdvisor advisor() {
		return validationAdvisorProvider.get();
	}

	/**
	 * Returns false if it is impossible that the given expression can result in a valid class
	 * reference at runtime.
	 * TODO: this is a really stupid way of doing "type inference", but better than nothing.
	 *
	 * @param e
	 * @return
	 */
	private boolean canBeAClassReference(Expression e) {
		if(e == null)
			return false; // can happen while editing
		switch(e.eClass().getClassifierID()) {
			case PPPackage.HOST_CLASS_DEFINITION:
			case PPPackage.ASSIGNMENT_EXPRESSION:
			case PPPackage.NODE_DEFINITION:
			case PPPackage.DEFINITION:
			case PPPackage.IMPORT_EXPRESSION:
			case PPPackage.RELATIONAL_EXPRESSION:
			case PPPackage.RESOURCE_EXPRESSION:
			case PPPackage.IF_EXPRESSION:
			case PPPackage.SELECTOR_EXPRESSION:
			case PPPackage.AND_EXPRESSION:
			case PPPackage.OR_EXPRESSION:
			case PPPackage.CASE_EXPRESSION:
			case PPPackage.EQUALITY_EXPRESSION:
			case PPPackage.RELATIONSHIP_EXPRESSION:
				return false;
		}
		return true;
	}

	private void checkCircularInheritence(HostClassDefinition o, Collection<IEObjectDescription> descs, List<QualifiedName> stack,
			LinkContext ctx) {
		for(IEObjectDescription d : descs) {
			QualifiedName name = d.getName();
			if(stack.contains(name)) {
				// Gotcha!
				ctx.acceptError( //
					"Circular inheritence", o, //
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, //
					ISSUE__CIRCULAR_INHERITENCE);
				return; // no use continuing
			}
			stack.add(name);
			String parentName = d.getUserData(PPDSLConstants.PARENT_NAME_DATA);
			if(parentName == null || parentName.length() == 0)
				continue;
			SearchResult searchResult = ppFinder.findHostClasses(d.getEObjectOrProxy(), parentName, ctx.getImportedNames());
			List<IEObjectDescription> parents = searchResult.getAdjusted(); // findHostClasses(d.getEObjectOrProxy(), parentName, importedNames);
			checkCircularInheritence(o, parents, stack, ctx);
		}
	}

	private void checkValidName(Definition definition, LinkContext ctx) {
		checkValidName(
			definition, PPPackage.Literals.DEFINITION__CLASS_NAME, IMessageAcceptor.INSIGNIFICANT_INDEX, definition.getClassName(), ctx);
	}

	private void checkValidName(EObject scopeDeterminingObject, EStructuralFeature feature, int idx, String name, LinkContext ctx) {
		if(advisor().allowTypeDefinitions())
			if(ppFinder.isPuppetTypeName(name))
				ctx.acceptError(
					"The name '" + name + "' is already defined by Puppet.", scopeDeterminingObject, feature, idx,
					ISSUE__RESERVED_TYPE_NAME);
	}

	private void checkValidName(Expression expr, String name, LinkContext ctx) {
		checkValidName(expr.eContainer(), expr.eContainingFeature(), AbstractMessageAcceptor.indexOfSourceInParent(expr), name, ctx);
	}

	private void checkValidName(LiteralName name, LinkContext ctx) {
		checkValidName(name, name.getValue(), ctx);
	}

	private void checkValidName(LiteralNameOrReference name, LinkContext ctx) {
		checkValidName(name, name.getValue(), ctx);
	}

	private boolean containsNameVar(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription d : descriptions)
			if("true".equals(d.getUserData(PPDSLConstants.PARAMETER_NAMEVAR)))
				return true;
		return false;
	}

	private boolean containsRegularExpression(EObject o) {
		if(o.eClass().getClassifierID() == PPPackage.LITERAL_REGEX)
			return true;
		TreeIterator<EObject> itor = o.eAllContents();
		while(itor.hasNext())
			if(itor.next().eClass().getClassifierID() == PPPackage.LITERAL_REGEX)
				return true;
		return false;
	}

	/**
	 * Returns the first type description. If none is found, the first description is returned.
	 *
	 * @param descriptions
	 * @return
	 */
	private IEObjectDescription getFirstTypeDescription(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription e : descriptions) {
			if(e.getEClass() == PPTPPackage.Literals.TYPE)
				return e;
		}
		return descriptions.get(0);
	}

	private void internalLinkAttributeOperations(AttributeOperations aos, IEObjectDescription desc, LinkContext ctx) {
		List<AttributeOperation> nameVariables = Lists.newArrayList();
		// Multimap<String, AttributeOperation> seen = ArrayListMultimap.create();

		if(aos != null && desc != null)
			for(AttributeOperation ao : aos.getAttributes()) {
				String key = ao.getKey();
				if("*".equals(key))
					// Linking not applicable
					continue;

				QualifiedName fqn = desc.getQualifiedName().append(key);
				// Accept name if there is at least one type/definition that lists the key
				// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
				// the property could be ok in one, but not in another instance.
				// finding that A'::x exists but not A''::x requires a lot more work
				List<IEObjectDescription> foundAttributes = ppFinder.findAttributes(aos, fqn, ctx.getImportedNames()).getAdjusted();
				// if the ao is a namevar reference, remember it so uniqueness can be validated
				if(foundAttributes.size() > 0) {
					ctx.getImportedNames().addResolved(foundAttributes);
					CrossReferenceAdapter.set(ao, foundAttributes);
					// seen.put(ao.getKey(), ao);

					if(containsNameVar(foundAttributes))
						nameVariables.add(ao);
					continue; // found one such parameter == ok
				}
				// if the reference is to "name" (and it was not found), then this is a deprecated
				// reference to the namevar
				if("name".equals(ao.getKey())) {
					nameVariables.add(ao);
					ctx.acceptWarning(
						"Deprecated use of the alias 'name'. Use the type's real name attribute.", ao,
						PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, ISSUE__RESOURCE_DEPRECATED_NAME_ALIAS);
					continue;
				}
				String[] proposals = proposer.computeAttributeProposals(fqn, ppFinder.getExportedDescriptions(), ctx.getSearchPath());
				ctx.acceptError(
					"Unknown attribute: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
					PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, proposalIssue(ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
			}
		if(nameVariables.size() > 1) {
			for(AttributeOperation ao : nameVariables) {
				ctx.acceptError(
					"Duplicate resource name attribute", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
					ISSUE__RESOURCE_DUPLICATE_NAME_PARAMETER);
				// // do not also flag as a general duplicate name
				// seen.removeAll(ao.getKey());
			}
		}
	}

	private void internalLinkFunctionArguments(String name, LiteralNameOrReference s, EList<Expression> statements, int idx, LinkContext ctx) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {

			// there should have been at least one argument
			if(idx >= statements.size()) {
				ctx.acceptError("Call to '" + name + "' must have at least one argument.", s, //
					PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, //
					ISSUE__REQUIRED_EXPRESSION);
				return;
			}

			Expression param = statements.get(idx);
			List<Expression> parameterList = null;
			if(param instanceof ExprList)
				parameterList = ((ExprList) param).getExpressions();
			else
				parameterList = Lists.newArrayList(param);

			int parameterIndex = -1;
			for(Expression pe : parameterList) {
				parameterIndex++;
				final String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					PPImportedNamesAdapter importedNames = ctx.getImportedNames();
					SearchResult searchResult = ppFinder.findHostClasses(s, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						if(param instanceof ExprList)
							ctx.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " + visibleResourceList(s.eResource(), foundClasses), //
								param, //
								PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
								ISSUE__AMBIGUOUS_REFERENCE, proposer.computeDistinctProposals(className, foundClasses));
						else
							ctx.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " + visibleResourceList(s.eResource(), foundClasses), //
								param.eContainer(), param.eContainingFeature(), idx, //
								ISSUE__AMBIGUOUS_REFERENCE, proposer.computeDistinctProposals(className, foundClasses));

					}
					else if(foundClasses.size() < 1) {
						if(searchResult.getRaw().size() > 0) {
							// sort of ok
							importedNames.addResolved(searchResult.getRaw());
							CrossReferenceAdapter.set(pe, searchResult.getRaw());

							if(param instanceof ExprList)
								ctx.acceptWarning(
									"Found outside current search path: '" + className + "'", param,
									PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, ISSUE__NOT_ON_PATH);
							else
								ctx.acceptWarning("Found outside current search path: '" + className + "'", //
									param.eContainer(), param.eContainingFeature(), idx, // ISSUE__NOT_ON_PATH);
									ISSUE__NOT_ON_PATH);

						}
						else {
							// not found
							// record unresolved name at resource level
							addUnresolved(importedNames, className, NodeModelUtils.findActualNodeFor(pe));
							// importedNames.addUnresolved(converter.toQualifiedName(className));
							CrossReferenceAdapter.clear(pe);

							String[] proposals = proposer.computeProposals(
								className, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), CLASS_AND_TYPE);
							String issueCode = proposalIssue(ISSUE__RESOURCE_UNKNOWN_TYPE, proposals);
							if(param instanceof ExprList) {
								ctx.acceptError("Unknown class: '" + className + "'", //
									param, //
									PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
									issueCode, //
									proposals);
							}
							else {
								ctx.acceptError("Unknown class: '" + className + "'", //
									param.eContainer(), param.eContainingFeature(), idx, //
									issueCode, //
									proposals);
							}
						}
					}
					else {
						// found
						importedNames.addResolved(foundClasses);
						CrossReferenceAdapter.set(pe, foundClasses);
					}
				}
				else {
					CrossReferenceAdapter.clear(pe);

					// warning or error depending on if this is a reasonable class reference expr or not
					String msg = null;
					boolean error = false;
					if(canBeAClassReference(pe)) {
						msg = "Can not determine until runtime if this is a valid class reference";
					}
					else {
						msg = "Not an acceptable parameter. Function '" + name + "' requires a class reference.";
						error = true;
					}
					if(param instanceof ExprList)
						ctx.accept(error
							? Severity.ERROR
							: Severity.WARNING, msg, //
							param, //
							PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
							ISSUE__RESOURCE_UNKNOWN_TYPE);

					else
						ctx.accept(error
							? Severity.ERROR
							: Severity.WARNING, msg, //
							param.eContainer(), param.eContainingFeature(), idx, //
							ISSUE__RESOURCE_UNKNOWN_TYPE);
				}
			}
		}
	}

	/**
	 * Link well known functions that must have references to defined things.
	 *
	 * @param o
	 * @param importedNames
	 * @param acceptor
	 */
	private void internalLinkFunctionArguments(String name, ParameterizedExpression o, LinkContext ctx) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {
			int parameterIndex = -1;
			for(Expression pe : o.getParameters()) {
				parameterIndex++;
				final String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					PPImportedNamesAdapter importedNames = ctx.getImportedNames();
					SearchResult searchResult = ppFinder.findHostClasses(o, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						ctx.acceptWarning(
							"Ambiguous reference to: '" + className + "' found in: " + visibleResourceList(o.eResource(), foundClasses), o,
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex, ISSUE__AMBIGUOUS_REFERENCE,
							proposer.computeDistinctProposals(className, foundClasses));
					}
					else if(foundClasses.size() < 1) {
						if(searchResult.getRaw().size() > 0) {
							// sort of ok
							importedNames.addResolved(searchResult.getRaw());
							CrossReferenceAdapter.set(pe, searchResult.getRaw());
							ctx.acceptWarning(
								"Found outside current search path: '" + className + "'", o,
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex, ISSUE__NOT_ON_PATH);
						}
						else {
							// not found
							// record unresolved name at resource level
							addUnresolved(importedNames, className, NodeModelUtils.findActualNodeFor(pe));
							// importedNames.addUnresolved(converter.toQualifiedName(className));
							CrossReferenceAdapter.clear(pe);

							String[] p = proposer.computeProposals(
								className, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), CLASS_AND_TYPE);
							ctx.acceptError(
								"Unknown class: '" + className + "'", o, //
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								proposalIssue(ISSUE__RESOURCE_UNKNOWN_TYPE, p), //
								p);
						}
					}
					else {
						// found
						importedNames.addResolved(foundClasses);
						CrossReferenceAdapter.set(pe, foundClasses);
					}
				}
				else {
					CrossReferenceAdapter.clear(pe);
					// warning or error depending on if this is a reasonable class reference expr or not
					if(canBeAClassReference(pe)) {
						ctx.acceptWarning("Can not determine until runtime if this is a valid class reference", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex, ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
					else {
						ctx.acceptError("Not an acceptable parameter. Function '" + name + "' requires a class reference.", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex, ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
				}

			}
			// there should have been at least one argument
			if(parameterIndex < 0) {
				ctx.acceptError("Call to '" + name + "' must have at least one argument.", o, //
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, //
					ISSUE__REQUIRED_EXPRESSION);

			}
		}
	}

	private void internalLinkFunctionCall(ParameterizedExpression o, EObject nameExpr, String name, LinkContext ctx) {
		final PPImportedNamesAdapter importedNames = ctx.getImportedNames();
		final SearchResult searchResult = ppFinder.findFunction(o, name, importedNames);
		final List<IEObjectDescription> found = searchResult.getAdjusted(); // findFunction(o, name, importedNames);
		if(found.size() > 0) {
			// record resolution at resource level
			importedNames.addResolved(found);
			CrossReferenceAdapter.set(nameExpr, found);
			internalLinkFunctionArguments(name, o, ctx);
			return; // ok, found
		}
		if(searchResult.getRaw().size() > 0) {
			// Not a hard error, it may be valid with a different path
			// not found on path, but exists somewhere in what is visible
			// record resolution at resource level
			importedNames.addResolved(searchResult.getRaw());
			CrossReferenceAdapter.set(nameExpr, searchResult.getRaw());
			internalLinkFunctionArguments(name, o, ctx);
			ctx.acceptWarning("Found outside current path: '" + name + "'", nameExpr, //
				ISSUE__NOT_ON_PATH //
			);
			return; // sort of ok
		}
		String[] proposals = proposer.computeProposals(name, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), FUNC);
		ctx.acceptError("Unknown function: '" + name + "'", nameExpr, //
			proposalIssue(ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
			proposals);
		// record failure at resource level
		addUnresolved(importedNames, name, NodeModelUtils.findActualNodeFor(nameExpr));
		CrossReferenceAdapter.clear(nameExpr);
	}

	/**
	 * Produces an error if the given EObject o is not contained (nested) in an expression that injects
	 * the result of a regular expression evaluation (i.e. $0 - $n).
	 * The injecting expressions are unless, if, elseif, case (entry), case expression, and selector entry.
	 * TODO: Check if there are (less obvious) expressions
	 *
	 * @param o
	 * @param varName
	 * @param attr
	 * @param acceptor
	 */
	private void internalLinkRegexpVariable(EObject o, String varName, EAttribute attr, LinkContext ctx) {
		// upp the containment chain
		for(EObject p = o.eContainer() /* , contained = o */; p != null; /* contained = p, */p = p.eContainer()) {
			switch(p.eClass().getClassifierID()) {
				case PPPackage.UNLESS_EXPRESSION:
					// o is either in cond, or then part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((UnlessExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.IF_EXPRESSION:
					// o is either in cond, then or else part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((IfExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.ELSE_IF_EXPRESSION:
					// o is either in cond, then or else part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((ElseIfExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.SELECTOR_ENTRY:
					// TODO: pedantic, check position in leftExpr, must have regexp to the left.
					if(containsRegularExpression(((SelectorEntry) p).getLeftExpr()))
						return;
					break;
				// TODO: CHECK IF THIS ISOTHERIC CASE IS SUPPORTED
				// case PPPackage.SELECTOR_EXPRESSION:
				// if(containsRegularExpression(((SelectorExpression)p).get))
				// return;
				// break;
				case PPPackage.CASE:
					// i.e. case expr { v0, v1, v2 : statements }
					for(EObject v : ((Case) p).getValues())
						if(containsRegularExpression(v))
							return;
					break;
				case PPPackage.CASE_EXPRESSION:
					// TODO: Investigate if this is allowed i.e.:
					// case $α =~ /regexp { true : $a = $0; false : $a = $0 }
					if(containsRegularExpression(((CaseExpression) p).getSwitchExpr()))
						return;
					break;
			}
		}
		ctx.acceptWarning("Corresponding regular expression not found. Value of '" + varName + "' can only be undefined at this point: '" +
			varName + "'", o, attr, ISSUE__UNKNOWN_REGEXP);

	}

	private ClassifierAdapter internalLinkResourceTypeExpression(EObject o, EObject reference, boolean upperCaseProposals, LinkContext ctx) {
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		if(adapter.getTargetObjectDescription() != null)
			// Already linked (Puppet Type expression)
			return adapter;

		// int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return null;

		// normal resource
		final PPImportedNamesAdapter importedNames = ctx.getImportedNames();
		SearchResult searchResult = ppFinder.findDefinitions(o, resourceTypeName, importedNames);
		List<IEObjectDescription> descs = searchResult.getAdjusted(); // findDefinitions(o, resourceTypeName, importedNames);

		// A resource type cannot reference a class but the HostClassDefinition inherits Definition
		// so we must purge them from the list.
		purgeHostClassResult(descs);

		if(descs.size() > 0) {
			removeDisqualifiedContainers(descs, o);
			// if any remain, pick the first type (or the first if there are no types)
			IEObjectDescription usedResolution = null;
			if(descs.size() > 0) {
				usedResolution = getFirstTypeDescription(descs);
				adapter.setTargetObject(usedResolution); // Resource expression's resolution of type
				CrossReferenceAdapter.set(reference, descs); // the actual reference

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same).
					importedNames.addAmbiguous(descs);
					ctx.acceptWarning(
						"Ambiguous reference to: '" + resourceTypeName + "' found in: " + visibleResourceList(o.eResource(), descs),
						reference, ISSUE__AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(resourceTypeName, descs, upperCaseProposals));
				}
			}
			// Add resolved information at resource level
			if(usedResolution != null)
				importedNames.addResolved(usedResolution);
			else
				importedNames.addResolved(descs);
		}
		else {
			descs = searchResult.getRaw();
			purgeHostClassResult(descs);
			if(descs.size() > 0) {
				// sort of ok
				importedNames.addResolved(descs);
				CrossReferenceAdapter.set(reference, descs);

				// do not record the type
				ctx.acceptWarning("Found outside search path: '" + resourceTypeName + "'", reference, ISSUE__NOT_ON_PATH);
			}
		}

		// only report unresolved if no raw (since if not found adjusted, error is reported as warning)
		if(descs.isEmpty()) {
			CrossReferenceAdapter.clear(reference);
			// ... and finally, if there was neither a type nor a definition reference
			if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null) {
				// Add unresolved info at resource level
				addUnresolved(importedNames, resourceTypeName, NodeModelUtils.findActualNodeFor(reference));
				String[] proposals = proposer.computeProposals(
					resourceTypeName, ppFinder.getExportedDescriptions(), upperCaseProposals, ctx.getSearchPath(), DEF_AND_TYPE);
				ctx.acceptError("Unknown resource type: '" + resourceTypeName + "'", reference,
				// PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, //
				proposalIssue(ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
					proposals);
			}
		}
		return adapter;
	}

	/**
	 * Links/validates unparenthesized function calls.
	 *
	 * @param statements
	 * @param acceptor
	 */
	private void internalLinkUnparenthesisedCall(EList<Expression> statements, LinkContext ctx) {
		if(statements == null || statements.size() == 0)
			return;

		each_top: for(int i = 0; i < statements.size(); i++) {
			Expression s = statements.get(i);

			// -- may be a non parenthesized function call
			if(s instanceof LiteralNameOrReference) {
				LiteralNameOrReference ref = (LiteralNameOrReference) s;
				checkValidName(ref, ctx);

				// there must be one more expression in the list (a single argument, or
				// an Expression list
				// TODO: different issue, can be fixed by adding "()" if this is a function call without
				// parameters, but difficult as validator does not know if function exists (would need
				// an adapter to be able to pick this up in validation).
				if((i + 1) >= statements.size()) {
					continue each_top; // error reported by validation.
				}
				// the next expression is consumed as a single arg, or an expr list
				// TODO: if there are expressions that can not be used as arguments check them here
				i++;
				// Expression arg = statements.get(i); // not used yet...
				String name = ref.getValue();
				final PPImportedNamesAdapter importedNames = ctx.getImportedNames();
				SearchResult searchResult = ppFinder.findFunction(s, name, importedNames);
				final boolean existsAdjusted = searchResult.getAdjusted().size() > 0;
				final boolean existsOutside = searchResult.getRaw().size() > 0;

				recordCrossReference(converter.toQualifiedName(name), searchResult, existsAdjusted, existsOutside, true, ctx, s);
				if(existsAdjusted || existsOutside) {
					internalLinkFunctionArguments(name, ref, statements, i, ctx);

					if(!existsAdjusted)
						ctx.acceptWarning("Found outside current path: '" + name + "'", s, //
							ISSUE__NOT_ON_PATH);
					continue each_top; // ok, found
				}
				String[] proposals = proposer.computeProposals(name, ppFinder.getExportedDescriptions(), ctx.getSearchPath(), FUNC);
				ctx.acceptError(
					"Unknown function: '" + name + "'", s, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
					proposalIssue(ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
					proposals);

				continue each_top;
			}
		}
	}

	private void internalLinkVariable(EObject o, EAttribute attr, String varName, LinkContext ctx) {
		boolean qualified = false;
		boolean global = false;
		boolean disqualified = false;
		QualifiedName qName = null;
		SearchResult searchResult = null;
		boolean existsAdjusted = false; // variable found as stated
		boolean existsOutside = false; // if not found, reflects if found outside search path
		final PPImportedNamesAdapter importedNames = ctx.getImportedNames();
		try {
			qName = converter.toQualifiedName(varName);
			if(patternHelper.isDECIMALVAR(varName)) {
				internalLinkRegexpVariable(o, varName, attr, ctx);
				return;
			}

			qualified = qName.getSegmentCount() > 1;
			global = qName.getFirstSegment().length() == 0;
			searchResult = ppFinder.findVariable(o, qName, importedNames);

			// remove all references to not yet initialized variables
			disqualified = (0 != removeDisqualifiedVariables(searchResult.getRaw(), o, varName));
			if(disqualified) // adjusted can not have disqualified entries if raw did not have them
				removeDisqualifiedVariables(searchResult.getAdjusted(), o, varName);
			existsAdjusted = searchResult.getAdjusted().size() > 0;
			existsOutside = existsAdjusted
				? false // we are not interested in that it may be both adjusted and raw
				: searchResult.getRaw().size() > 0;
		}
		catch(IllegalArgumentException iae) {
			// Can happen if there is something seriously wrong with the qualified name, should be caught by
			// validation - just ignore it here
			return;
		}
		IValidationAdvisor advisor = advisor();

		boolean mustExist = true;
		if(qualified && global) {
			// TODO: Possible future improvement when more global variables are known.
			// if reported as error now, almost all global variables would be flagged as errors.
			// Future enhancement could warn about those that are not found (resolved at runtime).
			mustExist = false;
		}

		// Record facts at resource and model levels about where variable was found
		recordCrossReference(qName, searchResult, existsAdjusted, existsOutside, mustExist, ctx, o);

		if(mustExist) {
			if(!(existsAdjusted || existsOutside)) {
				// importedNames.addUnresolved(qName);

				// found nowhere
				if(qualified || advisor.unqualifiedVariables().isWarningOrError()) {
					StringBuilder message = new StringBuilder();
					if(disqualified)
						message.append("Reference to not yet initialized variable: '");
					else
						message.append(qualified
							? "Unknown variable: '"
							: "Unqualified and Unknown variable: '");
					message.append(varName);
					message.append("'");

					String issue = disqualified
						? ISSUE__UNINITIALIZED_VARIABLE
						: ISSUE__UNKNOWN_VARIABLE;
					if(disqualified || advisor.unqualifiedVariables() == ValidationPreference.ERROR)
						ctx.acceptError(message.toString(), o, attr, issue);
					else
						ctx.acceptWarning(message.toString(), o, attr, issue);
				}
			}
			else if(!existsAdjusted && existsOutside) {
				// found outside
				if(qualified || advisor.unqualifiedVariables().isWarningOrError())
					ctx.acceptWarning("Found outside current search path variable: '" + varName + "'", o, attr, ISSUE__NOT_ON_PATH);
			}
		}

	}

	/**
	 * Returns true if the descriptions resource path is the same as for the given object and
	 * the fragment path of the given object starts with the fragment path of the description.
	 * (An alternative impl would be to first check if they are from the same resource - if so,
	 * it is know that this resource is loaded (since we have the given o) and it should
	 * be possible to search up the containment chain.
	 *
	 * @param desc
	 * @param o
	 * @return
	 */
	private boolean isParent(IEObjectDescription desc, EObject o) {
		URI descUri = desc.getEObjectURI();
		URI oUri = o.eResource().getURI();
		if(!descUri.path().equals(oUri.path()))
			return false;
		// same resource, if desc's fragment is in at the start of the path, then o is contained
		boolean result = o.eResource().getURIFragment(o).startsWith(descUri.fragment());
		return result;
	}

	/**
	 * Link all resources in the model
	 *
	 * @param model
	 * @param acceptor
	 */
	public void link(EObject model, IMessageAcceptor acceptor, boolean profileThis) {
		ppFinder.configure(model);
		Resource resource = model.eResource();

		// clear names remembered in the past
		PPImportedNamesAdapter importedNames = PPImportedNamesAdapterFactory.eINSTANCE.adapt(resource);
		importedNames.clear();

		LinkContext ctx = new LinkContext(importedNames, acceptor, searchPathProvider.get(resource), resource);
		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(resource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(resource.getURI());

		if(descr == null) {
			if(tracer.isTracing()) {
				tracer.trace("Cleaning resource: " + resource.getURI().path());
			}
			return;
		}

		if(tracer.isTracing())
			tracer.trace("Linking resource: ", resource.getURI().path(), "{");

		// Need to get everything in the resource, not just the content of the PuppetManifest (as the manifest has top level
		// expressions that need linking).
		TreeIterator<EObject> everything = resource.getAllContents();
		// it is important that ResourceExpresion are linked before ResourceBodyExpression (but that should
		// be ok with the tree iterator as the bodies are contained).

		while(everything.hasNext()) {
			EObject o = everything.next();
			EClass clazz = o.eClass();
			switch(clazz.getClassifierID()) {
				case PPPackage.EXPRESSION_TE:
					_link((ExpressionTE) o, ctx);
					break;

				case PPPackage.VARIABLE_TE:
					_link((VariableTE) o, ctx);
					break;

				case PPPackage.VARIABLE_EXPRESSION:
					_link((VariableExpression) o, ctx);
					break;

				case PPPackage.RESOURCE_EXPRESSION:
					_link((ResourceExpression) o, ctx);
					break;

				case PPPackage.RESOURCE_BODY:
					_link((ResourceBody) o, ctx, profileThis);
					break;

				case PPPackage.FUNCTION_CALL:
					_link((FunctionCall) o, ctx);
					break;

				// these are needed to link un-parenthesised function calls
				case PPPackage.PUPPET_MANIFEST:
					internalLinkUnparenthesisedCall(((PuppetManifest) o).getStatements(), ctx);
					break;

				case PPPackage.IF_EXPRESSION:
					internalLinkUnparenthesisedCall(((IfExpression) o).getStatements(), ctx);
					break;

				case PPPackage.UNLESS_EXPRESSION:
					internalLinkUnparenthesisedCall(((UnlessExpression) o).getStatements(), ctx);
					break;

				case PPPackage.ELSE_EXPRESSION:
					internalLinkUnparenthesisedCall(((ElseExpression) o).getStatements(), ctx);
					break;

				case PPPackage.ELSE_IF_EXPRESSION:
					internalLinkUnparenthesisedCall(((ElseIfExpression) o).getStatements(), ctx);
					break;

				case PPPackage.NODE_DEFINITION:
					internalLinkUnparenthesisedCall(((NodeDefinition) o).getStatements(), ctx);
					break;

				case PPPackage.DEFINITION:
					checkValidName((Definition) o, ctx);
					internalLinkUnparenthesisedCall(((Definition) o).getStatements(), ctx);
					break;

				case PPPackage.CASE:
					internalLinkUnparenthesisedCall(((Case) o).getStatements(), ctx);
					break;

				case PPPackage.HOST_CLASS_DEFINITION:
					checkValidName((HostClassDefinition) o, ctx);
					_link((HostClassDefinition) o, ctx);
					internalLinkUnparenthesisedCall(((HostClassDefinition) o).getStatements(), ctx);
					;
					break;

				case PPPackage.COLLECT_EXPRESSION:
					_link((CollectExpression) o, ctx);
					break;

				case PPPackage.METHOD_CALL:
					_link((MethodCall) o, ctx);
					break;

				case PPPackage.JAVA_LAMBDA:
				case PPPackage.RUBY_LAMBDA:
					internalLinkUnparenthesisedCall(((Lambda) o).getStatements(), ctx);
					break;

				case PPPackage.UNQUOTED_STRING:
					Expression expr = ((UnquotedString) o).getExpression();
					if(expr != null && expr instanceof LiteralNameOrReference) {
						//
						String varName = ((LiteralNameOrReference) expr).getValue();
						StringBuilder varName2 = new StringBuilder();
						if(!varName.startsWith("$"))
							varName2.append("$");
						varName2.append(varName);
						if(patternHelper.isVARIABLE(varName2.toString()))
							internalLinkVariable(expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, varName, ctx);
						else
							acceptor.acceptError(
								"Not a valid variable name", expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, ISSUE__NOT_VARNAME);

					}
					break;
				case PPPackage.LITERAL_NAME_OR_REFERENCE:
					_link((LiteralNameOrReference) o, ctx);
					break;
			}
		}
		if(tracer.isTracing())
			tracer.trace("}");

	}

	private void purgeHostClassResult(List<IEObjectDescription> result) {
		if(!result.isEmpty()) {
			Iterator<IEObjectDescription> iter = result.iterator();
			while(iter.hasNext())
				if(iter.next().getEClass() == PPPackage.Literals.HOST_CLASS_DEFINITION)
					iter.remove();
		}
	}

	/**
	 * Record facts about resolution of qName at model and resource level
	 *
	 * @param qName
	 *            - the name being resolved
	 * @param searchResult
	 *            - the result
	 * @param existsAdjusted
	 *            - if the adjusted result should be used
	 * @param existsOutside
	 *            - if the raw result should be used
	 * @param mustExists
	 *            - if non existence should be recored as unresolved
	 * @param importedNames
	 *            - resource level fact recorder
	 * @param o
	 *            - the model element where positive result is (also) recorded.
	 */
	private void recordCrossReference(QualifiedName qName, SearchResult searchResult, boolean existsAdjusted, boolean existsOutside,
			boolean mustExists, LinkContext ctx, EObject o) {
		List<IEObjectDescription> descriptions = null;

		if(existsAdjusted)
			descriptions = searchResult.getAdjusted();
		else if(existsOutside)
			descriptions = searchResult.getRaw();

		if(descriptions != null) {
			ctx.getImportedNames().addResolved(descriptions);
			CrossReferenceAdapter.set(o, descriptions);
		}
		else {
			CrossReferenceAdapter.clear(o);
		}
		if(mustExists && !(existsAdjusted || existsOutside)) {
			addUnresolved(ctx.getImportedNames(), qName, NodeModelUtils.findActualNodeFor(o));
			// importedNames.addUnresolved(qName);
		}

	}

	/**
	 * Surgically remove all disqualified descriptions (those that are HostClass and a container
	 * of the given object 'o'.
	 *
	 * @param descs
	 * @param o
	 */
	private void removeDisqualifiedContainers(List<IEObjectDescription> descs, EObject o) {
		if(descs == null)
			return;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			if(x.getEClass() == PPPackage.Literals.DEFINITION || !isParent(x, o))
				continue;
			litor.remove();
		}
	}

	private int removeDisqualifiedVariables(List<IEObjectDescription> descs, EObject o, String varName) {
		return removeDisqualifiedVariablesDefinitionArg(descs, o, varName) + removeDisqualifiedVariablesAssignment(descs, o);
	}

	/**
	 * Disqualifies variables that appear on the lhs of an assignment
	 *
	 * @param descs
	 * @param o
	 * @return
	 */
	private int removeDisqualifiedVariablesAssignment(List<IEObjectDescription> descs, EObject o) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = null;
		for(p = o; p != null; p = p.eContainer()) {
			int classifierId = p.eClass().getClassifierID();
			if(classifierId == PPPackage.JAVA_LAMBDA || classifierId == PPPackage.RUBY_LAMBDA)
				// Assignment expression can not pass block boundary
				return 0;

			if(classifierId == PPPackage.ASSIGNMENT_EXPRESSION || classifierId == PPPackage.APPEND_EXPRESSION)
				break;
		}
		if(p == null)
			return 0; // not in an assignment expression

		// p is a BinaryExpression at this point, we want it's parent being an abstract Definition
		final String definitionFragment = p.eResource().getURIFragment(p);
		final String definitionURI = p.eResource().getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;

	}

	/**
	 * Remove variables/entries that are not yet initialized. These are the values
	 * defined in the same name and type if the variable is contained in a definition argument
	 * <p>
	 * e.g. in define selfref($selfa = $selfref::selfa, $selfb=$selfa::x) { $x=10 } none of the references to selfa, or x are disqualified.
	 *
	 * @param descs
	 * @param o
	 * @return the number of disqualified variables removed from the list
	 */
	private int removeDisqualifiedVariablesDefinitionArg(List<IEObjectDescription> descs, EObject o, String varName) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = o;
		while(p != null && p.eClass().getClassifierID() != PPPackage.DEFINITION_ARGUMENT)
			p = p.eContainer();
		if(p == null)
			return 0; // not in a definition argument value tree

		EObject d = p.eContainer();
		if(d == null)
			return 0; // broken model

		// Check if variable references an argument declaration made previously in the definition argument list
		for(EObject arg : d.eContents()) {
			if(arg == p)
				// Can't include or go beyond current argument
				break;
			if(((DefinitionArgument) arg).getArgName().regionMatches(1, varName, 0, varName.length()))
				// No need to disqualify.
				return 0;
		}

		// d is a DefinitionArgumentList at this point, we want it's parent being an abstract Definition
		d = d.eContainer();
		final Resource resource = d.eResource();
		final String definitionFragment = resource.getURIFragment(d);
		final String definitionURI = resource.getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;
	}

	/**
	 * Collects the (unique) set of resource paths and returns a message with <=5 (+ ... and x others).
	 *
	 * @param descriptors
	 * @return
	 */
	private String visibleResourceList(Resource r, List<IEObjectDescription> descriptors) {
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		URI root = (URI) adapter.get(PPDSLConstants.RESOURCE_PROPERTY__ROOT_URI);

		// collect the (unique) resource paths
		List<String> resources = Lists.newArrayList();
		for(IEObjectDescription d : descriptors) {
			URI uri = EcoreUtil.getURI(d.getEObjectOrProxy());
			if(root != null) {
				uri = uri.deresolve(root.appendSegment(""));
			}
			boolean isPptpResource = "pptp".equals(uri.fileExtension());
			String path = isPptpResource
				? uri.lastSegment().replace(".pptp", "")
				: uri.devicePath();
			if(!resources.contains(path))
				resources.add(path);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(resources.size());
		buf.append(" resource");
		buf.append(resources.size() > 1
			? "s ["
			: " [");

		int count = 0;

		// if there are 4 include all, else limit to 3 - typically 2 (fresh user mistake) or is *many*
		final int countCap = resources.size() == 4
			? 4
			: 3;
		for(String s : resources) {
			if(count > 0)
				buf.append(", ");
			buf.append(s);
			if(count++ > countCap) {
				buf.append("and ");
				buf.append(resources.size() - countCap);
				buf.append(" other files...");
				break;
			}
		}
		buf.append("]");
		return buf.toString();
	}
}
