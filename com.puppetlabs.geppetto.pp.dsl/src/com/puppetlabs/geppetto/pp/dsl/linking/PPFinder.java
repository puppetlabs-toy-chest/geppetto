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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.puppetlabs.geppetto.common.tracer.ITracer;
import com.puppetlabs.geppetto.pp.AppendExpression;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.BinaryExpression;
import com.puppetlabs.geppetto.pp.Definition;
import com.puppetlabs.geppetto.pp.DefinitionArgument;
import com.puppetlabs.geppetto.pp.DefinitionArgumentList;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.HostClassDefinition;
import com.puppetlabs.geppetto.pp.Lambda;
import com.puppetlabs.geppetto.pp.LiteralExpression;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.NodeDefinition;
import com.puppetlabs.geppetto.pp.PPPackage;
import com.puppetlabs.geppetto.pp.ResourceBody;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.StringUtils;
import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import com.puppetlabs.geppetto.pp.dsl.linking.NameInScopeFilter.Match;
import com.puppetlabs.geppetto.pp.dsl.linking.NameInScopeFilter.SearchStrategy;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import com.puppetlabs.geppetto.pp.dsl.validation.PuppetTypeValidator;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;

/**
 * Utility class for finding references.
 */
public class PPFinder {
	public static class SearchResult {
		private List<IEObjectDescription> adjusted;

		private List<IEObjectDescription> raw;

		private SearchResult() {
			this.adjusted = Collections.emptyList();
			this.raw = this.adjusted;
		}

		private SearchResult(List<IEObjectDescription> rawAndAdjusted) {
			this.adjusted = this.raw = rawAndAdjusted;
		}

		private SearchResult(List<IEObjectDescription> pathAdjusted, List<IEObjectDescription> raw) {
			this.adjusted = pathAdjusted;
			this.raw = raw;
		}

		private SearchResult addAll(SearchResult other) {
			this.adjusted.addAll(other.adjusted);
			this.raw.addAll(other.raw);
			return this;
		}

		public List<IEObjectDescription> getAdjusted() {
			return adjusted;
		}

		public List<IEObjectDescription> getRaw() {
			return raw;
		}
	}

	static String getNameString(LiteralExpression expr) {
		if(expr == null)
			return null;

		if(expr.eClass() == PPPackage.Literals.LITERAL_DEFAULT)
			return "default";

		if(expr.eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE) {
			String parentString = ((LiteralNameOrReference) expr).getValue();
			if(parentString.isEmpty())
				parentString = null;
			return parentString;
		}
		return null;
	}

	private final static EClass[] CLASSES_FOR_VARIABLES = { //
	PPPackage.Literals.DEFINITION_ARGUMENT, //
		PPTPPackage.Literals.TP_VARIABLE, //
		// PPTPPackage.Literals.TYPE_ARGUMENT, //
		PPPackage.Literals.VARIABLE_EXPRESSION };

	private final static EClass[] DEF_AND_TYPE_ARGUMENTS = { PPPackage.Literals.DEFINITION_ARGUMENT, PPTPPackage.Literals.TYPE_ARGUMENT };

	// Note that order is important
	static final EClass[] DEF_AND_TYPE = { PPTPPackage.Literals.PUPPET_TYPE, PPTPPackage.Literals.TYPE, PPPackage.Literals.DEFINITION };

	static final EClass[] FUNC = { PPTPPackage.Literals.FUNCTION };

	final static EClass[] CLASS_AND_TYPE = { PPPackage.Literals.HOST_CLASS_DEFINITION,
	// PPTPPackage.Literals.TYPE
	};

	private Resource resource;

	@Inject
	private ISearchPathProvider searchPathProvider;

	/**
	 * Access to container manager for PP language
	 */
	@Inject
	private IContainer.Manager manager;

	private PPSearchPath searchPath;

	private final Multimap<String, IEObjectDescription> exportedPerLastSegment = ArrayListMultimap.create();

	/**
	 * Access to the 'pp' services (container management and more).
	 */
	@Inject
	private IResourceServiceProvider resourceServiceProvider;

	/**
	 * Access to naming of model elements.
	 */
	@Inject
	private IQualifiedNameProvider fqnProvider;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	private IQualifiedNameConverter converter;

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider
	 * that is aware of the context (builder, dirty editors, etc.). It is used to obtain the
	 * index for a particular resource. This special provider is obtained here.
	 */
	@Inject
	private ResourceDescriptionsProvider indexProvider;

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(PPDSLConstants.PP_DEBUG_LINKER)
	private ITracer tracer;

	@Inject
	private PuppetTypeValidator typeValidator;

	private Map<String, IEObjectDescription> metaCache;

	private Map<String, IEObjectDescription> metaVarCache;

	private final List<IEObjectDescription> exportedPatternVariables = Lists.newArrayList();

	private void buildExportedObjectsIndex(IResourceDescription descr, IResourceDescriptions descriptionIndex) {
		// The current (possibly dirty) exported resources
		IResourceDescription dirty = resourceServiceProvider.getResourceDescriptionManager().getResourceDescription(resource);
		String pathToCurrent = resource.getURI().path();

		exportedPerLastSegment.clear();
		exportedPatternVariables.clear();

		// add all (possibly dirty in global index)
		// check for empty qualified names which may be present in case of syntax errors / while editing etc.
		// empty names are simply skipped (they can not be found anyway).
		//
		for(IEObjectDescription d : dirty.getExportedObjects())
			if(d.getQualifiedName().getSegmentCount() >= 1)
				exportedPerLastSegment.put(d.getQualifiedName().getLastSegment(), d);

		// add all from global index, except those for current resource
		EClass builtInTypeClass = PPTPPackage.Literals.PUPPET_TYPE;
		for(IEObjectDescription d : getExportedObjects(descr, descriptionIndex))
			if(!d.getEObjectURI().path().equals(pathToCurrent) && d.getQualifiedName().getSegmentCount() >= 1) {
				// patterned based names are exceptional
				if(d.getUserData(PPDSLConstants.VARIABLE_PATTERN) != null) {
					exportedPatternVariables.add(d);
				}
				else {
					String name = d.getQualifiedName().getLastSegment();
					exportedPerLastSegment.put(name, d);
					if(d.getEClass() == builtInTypeClass)
						typeValidator.configureValidator(name, d);
				}
			}
	}

	private void cacheMetaParameters(EObject scopeDetermeningObject) {
		metaCache = Maps.newHashMap();
		metaVarCache = Maps.newHashMap();

		Resource scopeDetermeningResource = scopeDetermeningObject.eResource();

		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(scopeDetermeningResource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());
		if(descr == null)
			return; // give up - some sort of clean build
		EClass wantedType = PPTPPackage.Literals.TYPE_ARGUMENT;
		for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			for(IEObjectDescription objDesc : visibleContainer.getExportedObjects()) {
				QualifiedName q = objDesc.getQualifiedName();
				if("Type".equals(q.getFirstSegment())) {
					if(wantedType == objDesc.getEClass() || wantedType.isSuperTypeOf(objDesc.getEClass()))
						metaCache.put(q.getLastSegment(), objDesc);
				}
				else if(objDesc.getEClass() == PPTPPackage.Literals.META_VARIABLE) {
					metaVarCache.put(q.getLastSegment(), objDesc);
				}
			}
		}
	}

	public void configure(EObject o) {
		configure(o.eResource());
	}

	public void configure(Resource r) {
		resource = r;
		manager = resourceServiceProvider.getContainerManager();
		searchPath = searchPathProvider.get(r);

		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(resource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(resource.getURI());

		// Happens during start/clean in some state
		if(descr != null)
			buildExportedObjectsIndex(descr, descriptionIndex);
	}

	/**
	 * Find an attribute being a DefinitionArgument, Property, or Parameter for the given type, or a
	 * meta Property or Parameter defined for the type 'Type'.
	 *
	 * @param scopeDetermeningObject
	 * @param fqn
	 * @return
	 */
	public SearchResult findAttributes(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames) {
		SearchResult result = null;

		// do meta lookup first as this is made fast via a cache and these are used more frequent
		// than other parameters (measured).
		if(metaCache == null)
			cacheMetaParameters(scopeDetermeningObject);
		IEObjectDescription d = metaCache.get(fqn.getLastSegment());
		if(d == null)
			result = findInherited(
				scopeDetermeningObject, fqn, importedNames, Lists.<QualifiedName> newArrayList(), Match.EQUALS, DEF_AND_TYPE_ARGUMENTS);
		else
			result = new SearchResult(Lists.newArrayList(d));
		return result;
	}

	/**
	 * @param resourceBody
	 * @param fqn
	 * @return
	 */
	public SearchResult findAttributesWithPrefix(ResourceBody resourceBody, QualifiedName fqn) {
		// Must be configured for the resource containing resourceBody
		List<IEObjectDescription> result = Lists.newArrayList();

		// do meta lookup first as this is made fast via a cache and these are used more frequent
		// than other parameters (measured).
		// TODO: VERIFY that empty last segment matches ok
		// TODO: Make sure that length of match is same number of segments
		if(metaCache == null)
			cacheMetaParameters(resourceBody);
		String fqnLast = fqn.getLastSegment();
		for(String name : metaCache.keySet())
			if(name.startsWith(fqnLast))
				result.add(metaCache.get(name));

		result.addAll(findInherited(
			resourceBody, fqn, null, Lists.<QualifiedName> newArrayList(), Match.STARTS_WITH, DEF_AND_TYPE_ARGUMENTS).getAdjusted());
		return new SearchResult(result);

	}

	/**
	 * Find an argument declaration with the given name in the list.
	 *
	 * @param args
	 *            The argument list
	 * @param name
	 *            The name to search for
	 * @return A description of the found variable or <code>null</code> if it could not be found.
	 */
	private IEObjectDescription findDefinitionArgument(DefinitionArgumentList args, String name) {
		if(args != null)
			for(DefinitionArgument arg : args.getArguments()) {
				String argName = arg.getArgName();
				if(argName != null && argName.regionMatches(1, name, 0, name.length()))
					return EObjectDescription.create(argName, arg);
			}
		return null;
	}

	public SearchResult findDefinitions(EObject scopeDetermeningResource, PPImportedNamesAdapter importedNames) {
		QualifiedName fqn2 = QualifiedName.EMPTY;

		// TODO: Note that order is important, TYPE has higher precedence and should be used for linking
		// This used to work when list was iterated per type, not it is iterated once with type check
		// first - thus if a definition is found before a type, it is earlier in the list.
		return findExternal(scopeDetermeningResource, fqn2, importedNames, Match.STARTS_WITH, DEF_AND_TYPE);
	}

	public SearchResult findDefinitions(EObject scopeDetermeningResource, String name, PPImportedNamesAdapter importedNames) {
		return findDefinitions(scopeDetermeningResource, name, importedNames, DEF_AND_TYPE);
	}

	public SearchResult findDefinitions(EObject scopeDetermeningResource, String name, PPImportedNamesAdapter importedNames,
			EClass... validClasses) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		// make all segments initial char lower case (if references is to the type itself - eg. 'File' instead of
		// 'file', or 'Aa::Bb' instead of 'aa::bb'
		QualifiedName fqn2 = QualifiedName.EMPTY;
		for(int i = 0; i < fqn.getSegmentCount(); i++)
			fqn2 = fqn2.append(StringUtils.toInitialLowerCase(fqn.getSegment(i)));
		// fqn2 = fqn.skipLast(1).append(toInitialLowerCase(fqn.getLastSegment()));

		// TODO: Note that order is important, TYPE has higher precedence and should be used for linking
		// This used to work when list was iterated per type, not it is iterated once with type check
		// first - thus if a definition is found before a type, it is earlier in the list.
		return findExternal(scopeDetermeningResource, fqn2, importedNames, Match.EQUALS, validClasses);
	}

	private SearchResult findExternal(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames,
			SearchStrategy matchingStrategy, EClass... eClasses) {
		if(scopeDetermeningObject == null)
			throw new IllegalArgumentException("scope determening object is null");
		if(fqn == null)
			throw new IllegalArgumentException("name is null");
		if(eClasses == null || eClasses.length < 1)
			throw new IllegalArgumentException("eClass is null or empty");

		// if(fqn.getSegmentCount() == 1 && "".equals(fqn.getSegment(0)))
		// throw new IllegalArgumentException("FQN has one empty segment");

		List<IEObjectDescription> targets = new UniqueEList<IEObjectDescription>();
		Resource scopeDetermeningResource = scopeDetermeningObject.eResource();

		// Not meaningful to continue if the name is empty (looking up nothing)
		if(fqn.getSegmentCount() == 0)
			return new SearchResult(targets, targets);

		// Not meaningful to record the fact that an Absolute reference was used as nothing
		// is named with an absolute FQN (i.e. it is only used to do lookup).
		final boolean absoluteFQN = fqn.getSegmentCount() > 0 && "".equals(fqn.getSegment(0));
		if(importedNames != null)
			importedNames.add(absoluteFQN
				? fqn.skipFirst(1)
				: fqn);

		if(scopeDetermeningResource != resource) {
			// This is a lookup in the perspective of some other resource
			// GIVE UP (the system is cleaning / is in bad state).
			if(resource == null || scopeDetermeningResource == null)
				return new SearchResult(targets, targets);

			IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(scopeDetermeningResource);
			IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());

			// GIVE UP (the system is performing a build clean).
			if(descr == null)
				return new SearchResult(targets, targets);
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);

			// for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			// for(EClass aClass : eClasses)
			for(IEObjectDescription objDesc : new NameInScopeFilter(matchingStrategy, getExportedObjects(descr, descriptionIndex),
			// visibleContainer.getExportedObjects(),
			fqn, nameOfScope, eClasses))
				targets.add(objDesc);
		}
		else {
			// This is lookup from the main resource perspective
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);
			for(IEObjectDescription objDesc : new NameInScopeFilter(matchingStrategy, //
				matchingStrategy.matchStartsWith()
					? exportedPerLastSegment.values()
					: exportedPerLastSegment.get(fqn.getLastSegment()), //
				fqn, nameOfScope, eClasses))
				targets.add(objDesc);

			if(targets.size() == 0) {
				// check the pattern variables
				for(IEObjectDescription objDesc : exportedPatternVariables) {
					String n = fqn.getLastSegment();
					String on = objDesc.getName().getLastSegment();
					if(n.startsWith(on) && Pattern.matches(objDesc.getUserData(PPDSLConstants.VARIABLE_PATTERN), n.substring(on.length())))
						targets.add(objDesc);

				}
			}
		}
		if(tracer.isTracing()) {
			for(IEObjectDescription d : targets)
				tracer.trace("    : ", converter.toString(d.getName()), " in: ", d.getEObjectURI().path());
		}
		return new SearchResult(searchPathAdjusted(scopeDetermeningObject, targets), targets);
	}

	public SearchResult findFunction(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames) {
		return findExternal(scopeDetermeningObject, fqn, importedNames, Match.EQUALS, FUNC);
	}

	public SearchResult findFunction(EObject scopeDetermeningObject, String name, PPImportedNamesAdapter importedNames) {
		return findFunction(scopeDetermeningObject, converter.toQualifiedName(name), importedNames);
	}

	public SearchResult findHostClasses(EObject scopeDetermeningResource, String name, PPImportedNamesAdapter importedNames) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		// make last segments initial char lower case (for references to the type itself - eg. 'File' instead of
		// 'file'.
		if(fqn.getSegmentCount() == 0)
			return new SearchResult(); // can happen while editing
		fqn = fqn.skipLast(1).append(StringUtils.toInitialLowerCase(fqn.getLastSegment()));
		return findExternal(scopeDetermeningResource, fqn, importedNames, Match.EQUALS, CLASS_AND_TYPE);
	}

	private SearchResult findInherited(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames,
			List<QualifiedName> stack, SearchStrategy matchingStrategy, EClass[] classes) {
		// Protect against circular inheritance
		QualifiedName containerName = fqn.skipLast(1);
		if(stack.contains(containerName))
			return new SearchResult();
		stack.add(containerName);

		// find using the given name
		SearchResult searchResult = findExternal(scopeDetermeningObject, fqn, importedNames, matchingStrategy, classes);
		final List<IEObjectDescription> result = searchResult.getAdjusted();
		// Collect raw results to enable better error reporting on path errors
		List<IEObjectDescription> rawResult = Lists.newArrayList();
		rawResult.addAll(searchResult.getRaw());

		// Search up the inheritance chain if no match (on exact match), or if a prefix search
		if(result.isEmpty() || !matchingStrategy.isExists()) {
			// find the parent type
			if(containerName.getSegmentCount() > 0) {
				// there was a parent
				List<IEObjectDescription> parentResult = findExternal(
					scopeDetermeningObject, containerName, importedNames, Match.EQUALS, DEF_AND_TYPE).getAdjusted();
				if(!parentResult.isEmpty()) {
					IEObjectDescription firstFound = parentResult.get(0);
					String parentName = firstFound.getUserData(PPDSLConstants.PARENT_NAME_DATA);
					if(parentName != null && parentName.length() > 0) {
						// find attributes for parent

						QualifiedName attributeFqn = converter.toQualifiedName(parentName);
						attributeFqn = attributeFqn.append(fqn.getLastSegment());
						SearchResult inheritedSearchResult = findInherited(
							scopeDetermeningObject, attributeFqn, importedNames, stack, matchingStrategy, classes);
						result.addAll(inheritedSearchResult.getAdjusted());
						rawResult.addAll(inheritedSearchResult.getRaw());
					}
				}
			}
		}
		return new SearchResult(result, rawResult);
	}

	public IEObjectDescription findLocalVariableInLambda(Lambda lambda, String name, PPImportedNamesAdapter importedNames,
			SearchStrategy matchingStrategy) {
		TreeIterator<EObject> itor = lambda.eAllContents();
		while(itor.hasNext()) {
			EObject o = itor.next();
			if(o instanceof AssignmentExpression || o instanceof AppendExpression) {
				Expression lhs = ((BinaryExpression) o).getLeftExpr();
				if(lhs instanceof VariableExpression) {
					String vname = ((VariableExpression) lhs).getVarName();
					if(vname.startsWith("$"))
						if(vname.length() > 1)
							vname = vname.substring(1);
						else
							vname = "";
					if(name.equals(vname))
						return EObjectDescription.create(name, lhs);
				}
			}
			else if(o instanceof DefinitionArgument) {
				DefinitionArgument arg = (DefinitionArgument) o;
				String argName = arg.getArgName();
				if(argName.startsWith("$") && name.equals(argName.substring(1)) || name.equals(argName))
					return EObjectDescription.create(name, o);
			}
			else if(o instanceof Lambda) {
				itor.prune(); // do not visit nested Lambdas
			}
		}
		return null;
	}

	public IEObjectDescription findLocalVariables(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames,
			SearchStrategy matchingStrategy) {
		if(fqn.getSegmentCount() != 1)
			return null;
		String name = fqn.getFirstSegment();
		if(name == null || name.length() < 1)
			return null;
		// find enclosing lambda
		for(EObject container = scopeDetermeningObject; container != null; container = container.eContainer()) {
			if(container instanceof Lambda) {
				IEObjectDescription desc = findLocalVariableInLambda((Lambda) container, name, importedNames, matchingStrategy);
				if(desc != null)
					return desc;
			}
			else if(container instanceof DefinitionArgumentList) {
				IEObjectDescription desc = findDefinitionArgument((DefinitionArgumentList) container, name);
				if(desc != null)
					return desc;
				if(container.eContainer() instanceof HostClassDefinition) {
					// The argument list belongs to a class definition. Check if the name references
					// a variable defined by an inherited class.
					LiteralExpression parent = ((HostClassDefinition) container.eContainer()).getParent();
					String parentString = getNameString(parent);
					if(parentString != null) {
						SearchResult parentAttrs = findInherited(
							scopeDetermeningObject, QualifiedName.create(parentString, name), importedNames,
							Lists.<QualifiedName> newArrayList(), matchingStrategy, CLASSES_FOR_VARIABLES);

						// Return first result with preference for adjusted matches.
						List<IEObjectDescription> result = parentAttrs.getAdjusted();
						if(result.isEmpty())
							result = parentAttrs.getRaw();
						if(!result.isEmpty())
							return result.get(0);
					}
				}
			}
			if(container instanceof HostClassDefinition) {
				IEObjectDescription desc = findDefinitionArgument(((HostClassDefinition) container).getArguments(), name);
				if(desc != null)
					return desc;
				break;
			}
			if(container instanceof Definition) {
				IEObjectDescription desc = findDefinitionArgument(((Definition) container).getArguments(), name);
				if(desc != null)
					return desc;
				break;
			}
			if(container instanceof NodeDefinition)
				break;
		}
		return null;
	}

	/**
	 * Finds a parameter or variable with the given name. More than one may be returned if the definition
	 * is ambiguous.
	 *
	 * @param scopeDetermeningResource
	 * @param name
	 * @param importedNames
	 * @return
	 */
	public SearchResult findVariable(EObject scopeDetermeningResource, QualifiedName fqn, PPImportedNamesAdapter importedNames) {
		if(fqn == null)
			throw new IllegalArgumentException("fqn is null");
		return findVariables(scopeDetermeningResource, fqn, importedNames, Match.NO_OUTER);
	}

	/**
	 * Finds a parameter or variable with the given name. More than one may be returned if the definition
	 * is ambiguous.
	 *
	 * @param scopeDetermeningResource
	 * @param name
	 * @param importedNames
	 * @return
	 */
	public SearchResult findVariable(EObject scopeDetermeningResource, String name, PPImportedNamesAdapter importedNames) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		return findVariables(scopeDetermeningResource, fqn, importedNames, Match.NO_OUTER_EXISTS);
	}

	/**
	 * Finds all matching variables in current and inherited scopes.
	 *
	 * @param scopeDetermeningResource
	 * @param name
	 * @param importedNames
	 * @return
	 */
	public SearchResult findVariables(EObject scopeDetermeningResource, QualifiedName fqn, PPImportedNamesAdapter importedNames) {
		if(fqn == null)
			throw new IllegalArgumentException("name is null");
		return findVariables(scopeDetermeningResource, fqn, importedNames, Match.NO_OUTER);
	}

	/**
	 * Fins parameters and/or variables with matching name using the given matching/search strategy.
	 *
	 * @param scopeDetermeningObject
	 * @param fqn
	 * @param importedNames
	 * @param matchingStrategy
	 * @return
	 */
	public SearchResult findVariables(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames,
			SearchStrategy matchingStrategy) {
		if(metaCache == null)
			cacheMetaParameters(scopeDetermeningObject);

		final boolean singleSegment = fqn.getSegmentCount() == 1;

		// If variable is a meta var, it is always found
		if(singleSegment) {
			IEObjectDescription metaVar = metaVarCache.get(fqn.getLastSegment());
			if(metaVar != null)
				return new SearchResult(Lists.newArrayList(metaVar), Lists.newArrayList(metaVar)); // what a waste...

			// if inside a define, all meta parameters are available
			if(isContainedInDefinition(scopeDetermeningObject)) {
				IEObjectDescription metaParam = metaCache.get(fqn.getLastSegment());
				if(metaParam != null)
					return new SearchResult(Lists.newArrayList(metaParam), Lists.newArrayList(metaParam)); // what a waste...
			}
			IEObjectDescription desc = findLocalVariables(scopeDetermeningObject, fqn, importedNames, matchingStrategy);
			if(desc != null)
				return new SearchResult(Lists.newArrayList(desc));

		}
		SearchResult result = findExternal(scopeDetermeningObject, fqn, importedNames, matchingStrategy, CLASSES_FOR_VARIABLES);
		if(result.getAdjusted().size() > 0 && matchingStrategy.isExists())
			return result;
		QualifiedName scopeName = getNameOfScope(scopeDetermeningObject);
		if(!scopeName.isEmpty()) {
			fqn = scopeName.append(fqn);
			return result.addAll(findInherited(
				scopeDetermeningObject, fqn, importedNames, Lists.<QualifiedName> newArrayList(), matchingStrategy, CLASSES_FOR_VARIABLES));
		}
		return result;
	}

	/**
	 * Finds all matching variables in current and inherited scopes.
	 *
	 * @param scopeDetermeningResource
	 * @param name
	 * @param importedNames
	 * @return
	 */
	public SearchResult findVariables(EObject scopeDetermeningResource, String name, PPImportedNamesAdapter importedNames) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		return findVariables(scopeDetermeningResource, fqn, importedNames, Match.NO_OUTER);
	}

	public SearchResult findVariablesPrefixed(EObject scopeDetermeningObject, QualifiedName fqn, PPImportedNamesAdapter importedNames) {
		return findVariables(scopeDetermeningObject, fqn, importedNames, Match.NO_OUTER_STARTS_WITH);
	}

	/**
	 * Produces an unmodifiable list of everything visible to the resource.
	 *
	 * @return
	 */
	public Collection<IEObjectDescription> getExportedDescriptions() {
		return Collections.unmodifiableCollection(exportedPerLastSegment.values());
	}

	/**
	 * Produces iterable over all visible exports from all visible containers.
	 *
	 * @param descr
	 * @param descriptionIndex
	 * @return
	 */
	private Iterable<IEObjectDescription> getExportedObjects(IResourceDescription descr, IResourceDescriptions descriptionIndex) {
		return Iterables.concat(Iterables.transform(
			manager.getVisibleContainers(descr, descriptionIndex), new Function<IContainer, Iterable<IEObjectDescription>>() {

				@Override
				public Iterable<IEObjectDescription> apply(IContainer from) {
					return from.getExportedObjects();
				}

			}));
	}

	public Collection<IEObjectDescription> getExportedPatternVariableDescriptions() {
		return Collections.unmodifiableCollection(exportedPatternVariables);
	}

	/**
	 * Produces an unmodifiable Multimap mapping from last segment to list of visible exports
	 * ending with that value.
	 *
	 * @return
	 */
	public Multimap<String, IEObjectDescription> getExportedPerLastSegement() {
		return Multimaps.unmodifiableMultimap(exportedPerLastSegment);
	}

	public Collection<IEObjectDescription> getExportedPerLastSegment(String name) {
		return name == null
			? Collections.<IEObjectDescription> emptyList()
			: exportedPerLastSegment.get(StringUtils.toInitialLowerCase(name));
	}

	/**
	 * Produces the name of the scope where the given object 'o' is contained.
	 *
	 * @param o
	 * @return
	 */
	public QualifiedName getNameOfScope(EObject o) {
		QualifiedName result = null;
		for(; o != null; o = o.eContainer()) {
			result = fqnProvider.getFullyQualifiedName(o);
			if(result != null)
				return result;
		}
		return QualifiedName.EMPTY;
	}

	public IEObjectDescription getPuppetTypeByName(String name) {
		for(IEObjectDescription export : getExportedPerLastSegment(name))
			if(PPTPPackage.Literals.PUPPET_TYPE.isSuperTypeOf(export.getEClass()))
				return export;
		return null;
	}

	private boolean isContainedInDefinition(EObject scoped) {
		for(EObject o = scoped; o != null; o = o.eContainer())
			if(o.eClass() == PPPackage.Literals.DEFINITION)
				return true;
		return false;
	}

	public boolean isPuppetTypeName(String name) {
		return getPuppetTypeByName(name) != null;
	}

	/**
	 * Adjusts the list of found targets in accordance with the search path for the resource being
	 * linked. This potentially resolves ambiguities (if found result is further away on the path).
	 * May return more than one result, if more than one resolution exist with the same path index.
	 *
	 * @param targets
	 * @return list of descriptions with lowest index.
	 */
	private List<IEObjectDescription> searchPathAdjusted(EObject scopeDeterminingObject, List<IEObjectDescription> targets) {
		int minTypesAndDefinesIdx = Integer.MAX_VALUE;
		int minClassIdx = Integer.MAX_VALUE;
		List<IEObjectDescription> result = new UniqueEList<IEObjectDescription>();
		List<IEObjectDescription> classResult = null;
		List<IEObjectDescription> typesAndDefinesResult = null;
		for(IEObjectDescription d : targets) {
			int idx = searchPath.searchIndexOf(d);
			if(idx == PPSearchPath.NOT_FOUND) {
				// We consider targets found in the same root container as the scope determining object to be OK
				// although they are not strictly on the search path.
				if(EcoreUtil.getRootContainer(scopeDeterminingObject) != EcoreUtil.getRootContainer(d.getEObjectOrProxy()))
					continue; // not found, skip
			}
			else if(idx == PPSearchPath.FOUND_IN_TPTP) {
				// Always found but not on search path so it might cause ambiguities
				result.add(d);
				continue;
			}

			if(PPPackage.Literals.HOST_CLASS_DEFINITION == d.getEClass()) {
				if(idx < minClassIdx) {
					minClassIdx = idx;
					if(classResult != null)
						classResult.clear(); // forget worse result
				}
				// only remember if equal to best found so far
				if(idx <= minClassIdx) {
					if(classResult == null)
						classResult = Lists.newArrayList();
					classResult.add(d);
				}
			}
			else {
				if(idx < minTypesAndDefinesIdx) {
					minTypesAndDefinesIdx = idx;
					if(typesAndDefinesResult != null)
						typesAndDefinesResult.clear(); // forget worse result
				}
				// only remember if equal to best found so far
				if(idx <= minTypesAndDefinesIdx) {
					if(typesAndDefinesResult == null)
						typesAndDefinesResult = Lists.newArrayList();
					typesAndDefinesResult.add(d);
				}
			}
		}
		if(typesAndDefinesResult != null)
			result.addAll(typesAndDefinesResult);
		if(classResult != null)
			result.addAll(classResult);
		return result;
	}
}
