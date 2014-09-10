/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.validation;

import static com.puppetlabs.geppetto.pp.dsl.StringUtils.appendCount;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.Definition;
import com.puppetlabs.geppetto.pp.DoubleQuotedString;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.HashEntry;
import com.puppetlabs.geppetto.pp.HostClassDefinition;
import com.puppetlabs.geppetto.pp.LiteralHash;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.LiteralRegex;
import com.puppetlabs.geppetto.pp.PPPackage;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.TextExpression;
import com.puppetlabs.geppetto.pp.VerbatimTE;
import com.puppetlabs.geppetto.pp.dsl.StringUtils;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator.PPType;
import com.puppetlabs.geppetto.pp.dsl.linking.IMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.linking.RecordingMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.pptp.PptpPrinter;
import com.puppetlabs.geppetto.pp.pptp.FloatValue;
import com.puppetlabs.geppetto.pp.pptp.IntegerValue;
import com.puppetlabs.geppetto.pp.pptp.NamedTypeValue;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;
import com.puppetlabs.geppetto.pp.pptp.RegexpValue;
import com.puppetlabs.geppetto.pp.pptp.StringValue;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

@Singleton
public class PuppetTypeValidator {

	private class InternalValidationContext implements ITypeValidationContext {
		private final IMessageAcceptor acceptor;

		private final EObject value;

		private final PPType valueType;

		InternalValidationContext(EObject value, PPType valueType, IMessageAcceptor acceptor) {
			this.value = value;
			this.valueType = valueType;
			this.acceptor = acceptor;
		}

		@Override
		public IMessageAcceptor acceptor() {
			return acceptor;
		}

		@Override
		public IValidationAdvisor advisor() {
			return PuppetTypeValidator.this.advisor();
		}

		@Override
		public boolean checkExpression(PuppetTypeParameter param, String puppetType, List<ParameterValue> parameters, Expression value,
				IMessageAcceptor acceptor) {
			return PuppetTypeValidator.this.checkExpression(param, puppetType, parameters, value, acceptor);
		}

		public IEObjectDescription getReferencedObject() {
			return PuppetTypeValidator.this.getReferencedObject(value());
		}

		public EObject getReferencedTypeOrClass() {
			return valueType == PPType.TYPE || valueType == PPType.RESOURCE_TYPE || valueType == PPType.USER_DEFINED_RESOURCE_TYPE ||
				valueType == PPType.CLASS
				? getResolvedObject(getReferencedObject())
				: null;
		}

		public EObject getResolvedObject(IEObjectDescription desc) {
			EObject target = null;
			if(desc != null) {
				target = desc.getEObjectOrProxy();
				if(target.eIsProxy())
					target = EcoreUtil.resolve(target, value());
			}
			return target;
		}

		@Override
		public String getStringConstant(Expression expr) {
			return typeEvaluator.getStringConstant(expr);
		}

		@Override
		public ITypeValidator getTypeValidator(String typeName) {
			return PuppetTypeValidator.this.getTypeValidator(typeName);
		}

		@Override
		public PPType ppType() {
			return valueType;
		}

		@Override
		public PPType ppType(Expression expr) {
			return typeEvaluator.type(expr);
		}

		@Override
		public PuppetType type() {
			return getTypeValidator(valueType.getPuppetTypeName()).getType(value());
		}

		@Override
		public PptpPrinter typePrinter() {
			return pptpPrinter;
		}

		@Override
		public TypeValue typeValue() {
			try {
				return PuppetTypeValidator.this.getTypeValue(value());
			}
			catch(ParameterizedTypeCreationException e) {
				return null;
			}
		}

		@Override
		public void validityAssertedAtRuntime() {
			PuppetTypeValidator.this.validityAssertedAtRuntime(value(), acceptor);
		}

		@Override
		public Expression value() {
			return value instanceof HashEntry
				? ((HashEntry) value).getValue()
				: (Expression) value;
		}

		public Expression valueName() {
			return value instanceof HashEntry
				? ((HashEntry) value).getKey()
				: null;
		}
	}

	@SuppressWarnings("serial")
	public static class ParameterizedTypeCreationException extends Exception {
		ParameterizedTypeCreationException(String message) {
			super(message);
		}
	}

	protected static int getMaxAllowedArgs(List<PuppetTypeParameter> paramDecls) {
		for(PuppetTypeParameter paramDecl : paramDecls)
			if(paramDecl.isVarargs())
				return Integer.MAX_VALUE;
		return paramDecls.size();
	}

	protected static int getMinRequiredArgs(List<PuppetTypeParameter> paramDecls) {
		int min = 0;
		for(PuppetTypeParameter paramDecl : paramDecls)
			if(paramDecl.isRequired())
				++min;
		return min;
	}

	private static EObject getResolvedEObject(IEObjectDescription desc, EObject scope) {
		EObject o = desc.getEObjectOrProxy();
		if(o.eIsProxy())
			o = EcoreUtil.resolve(o, scope);
		return o;
	}

	@Inject
	protected PPTypeEvaluator typeEvaluator;

	@Inject
	private Provider<IValidationAdvisor> validationAdvisorProvider;

	@Inject
	private PptpPrinter pptpPrinter;

	@Inject
	private Map<String, ITypeValidatorFactory> typeValidatorFactories;

	private final Map<String, ITypeValidator> typeValidators = Maps.newHashMap();

	private IValidationAdvisor advisor() {
		return validationAdvisorProvider.get();
	}

	/**
	 * <p>
	 * Validate the given <code>value</code> expression using the given <code>puppetType</code> and link according to type.
	 * </p>
	 * <p>
	 * The method will return <code>false</code> when validation is performed but fails. If the validation must be deferred until runtime
	 * (because the <code>value</code> expression is dynamic), then this method returns <code>true</code>. A warning might have been
	 * produced (controlled by preferences).
	 * </p>
	 *
	 * @param param
	 *            The parameter when checking a type parameter. Might be <code>null</code>. Only used in diagnostics
	 * @param puppetType
	 *            The type used for validating the <code>value</code> expression.
	 * @param declArgs
	 *            The optional parameter values for the <code>puppetType</code>. Can be <code>null</code>.
	 * @param entryOrValue
	 *            The {@link Expression} or {@link HashEntry} that will be checked against the given <code>puppetType</code>.
	 * @param acceptor
	 *            Message acceptor.
	 * @return <code>true</code> if the validation was a success or if it was deferred until runtime.
	 */
	public boolean checkExpression(PuppetTypeParameter param, String puppetType, List<ParameterValue> declArgs, EObject entryOrValue,
			IMessageAcceptor acceptor) {

		if("any".equals(puppetType))
			return true;

		Expression value = entryOrValue instanceof HashEntry
			? ((HashEntry) entryOrValue).getValue()
			: (Expression) entryOrValue;

		PPType ppType = typeEvaluator.type(value);
		if(ppType == PPType.DYNAMIC) {
			// We have no idea what this is at this point
			validityAssertedAtRuntime(value, acceptor);
			return true;
		}

		if(declArgs == null)
			declArgs = Collections.emptyList();

		return getTypeValidator(puppetType).validate(param, declArgs, new InternalValidationContext(entryOrValue, ppType, acceptor));
	}

	/**
	 * Validates the parameters of the <code>parameterizedType</code> against the type named <code>typeName</code>.
	 *
	 * @param typeName
	 *            The name of the puppet type.
	 * @param parameterizedType
	 *            The holder of the parameters to be validated.
	 * @param skipFirst
	 *            Set to <code>true</code> if the first parameter declaration of the type should be skipped over. This is useful
	 *            when we validate expressions like <code>File['/foo/bar']</code> since what we really use then is
	 *            <code>Resource[File, '/foo/bar']</code> and the fact that <code>File</code> is a <code>Resource</code> has already been
	 *            established.
	 * @param acceptor
	 *            Message acceptor.
	 */
	public void checkTypeParameters(String typeName, AtExpression parameterizedType, boolean skipFirst, IMessageAcceptor acceptor) {
		ITypeValidator validator = getTypeValidator(typeName);
		if(validator == null) {
			acceptor.acceptError("No such type: '" + typeName + '\'', parameterizedType, IPPDiagnostics.ISSUE__UNKNOWN_TYPE);
			return;
		}

		PuppetType pType = validator.getType(parameterizedType);
		List<PuppetTypeParameter> paramDecls = pType.getParameters();
		int paramDeclCount = paramDecls.size();

		int declIdx = 0;
		if(skipFirst)
			++declIdx;

		PuppetTypeParameter currentDecl = null;
		boolean currentRequired = false;
		if(paramDeclCount > declIdx) {
			currentDecl = paramDecls.get(declIdx++);
			currentRequired = currentDecl.isRequired();
		}

		List<? extends EObject> paramValues = parameterizedType.getParameters();
		int valueCount = paramValues.size();

		List<HashEntry> namedParamValues = null;
		if(pType.isNamedParameters()) {
			// Value count must be exactly 1 and the parameter must be a LiteralHash
			if(valueCount == 1) {
				EObject hash = paramValues.get(0);
				if(hash instanceof LiteralHash)
					namedParamValues = ((LiteralHash) hash).getElements();
			}

			if(namedParamValues == null) {
				StringBuilder bld = new StringBuilder();
				pptpPrinter.append(pType, true, bld);
				bld.append(" accepts exactly one argument which must be a hash");
				acceptor.accept(Severity.ERROR, bld.toString(), parameterizedType, IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

				// This is fatal
				return;
			}
			valueCount = namedParamValues.size();
			paramValues = namedParamValues;
		}

		for(int valueIdx = 0; valueIdx < valueCount;) {
			EObject paramValue = paramValues.get(valueIdx);
			if(currentDecl == null) {
				// Declaration list exhausted. We have nothing to match the given value to.
				StringBuilder bld = new StringBuilder();
				pptpPrinter.append(pType, true, bld);
				int min = getMinRequiredArgs(paramDecls);
				if(skipFirst)
					--min;

				int max = getMaxAllowedArgs(paramDecls);
				if(max != Integer.MAX_VALUE) {
					if(skipFirst)
						--max;

					bld.append(" accepts ");
					appendCount(min, max, bld);
					if(min != max) {
						if(min == 1 && max == 2)
							bld.append(" or ");
						else
							bld.append(" to ");
						appendCount(max, min, bld);
					}
					bld.append(" argument");
					if(max > 1)
						bld.append('s');
					bld.append(". Got ");
					appendCount(paramValues.size(), max, bld);
				}
				else {
					// There are varags in the list so reporting min/max is
					// not appropriate.
					bld.append(" all arguments were fulfilled by the first ");
					bld.append(valueIdx);
					bld.append(" arguments. The remaining ");
					bld.append(valueCount - valueIdx);
					bld.append(" are superfluous");
				}

				acceptor.accept(
					Severity.ERROR, bld.toString(), parameterizedType, PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, valueIdx,
					IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
				++valueIdx;
				break; // One error is enough. Don't mark other superfluous expressions
			}

			RecordingMessageAcceptor tmpAcceptor = new RecordingMessageAcceptor();
			if(checkExpression(currentDecl, currentDecl.getType().getName(), currentDecl.getParameters(), paramValue, tmpAcceptor)) {
				++valueIdx;
				if(currentDecl.isVarargs())
					currentRequired = false;
				else {
					if(declIdx < paramDecls.size()) {
						currentDecl = paramDecls.get(declIdx++);
						currentRequired = currentDecl.isRequired();
					}
					else
						currentDecl = null;
				}
				tmpAcceptor.playBack(acceptor);
			}
			else {
				if(currentRequired || !currentDecl.isVarargs() || declIdx >= paramDecls.size()) {
					// Required (or optional but provided) parameter was not matched
					// (varags and not provided means that it's OK with zero matches)
					tmpAcceptor.playBack(acceptor);
					++valueIdx;
				}

				if(declIdx < paramDecls.size()) {
					currentDecl = paramDecls.get(declIdx++);
					currentRequired = currentDecl.isRequired();
				}
				else
					currentDecl = null;
			}
		}
	}

	/**
	 * Configure a {@link ITypeValidator} for a type with the given <code>name</code> and <code>description</code>. This
	 * method does nothing if the type has already been configured. The {@link IEObjectDescription#getEClass() description.getEClass()} must
	 * return {@link EClass} {@link PPTPPackage.Literals#PUPPET_TYPE PUPPET_TYPE}.
	 *
	 * @param name
	 *            The name of the type.
	 * @param description
	 *            The type description.
	 */
	public void configureValidator(String name, IEObjectDescription description) {
		synchronized(typeValidators) {
			if(!typeValidators.containsKey(name))
				typeValidators.put(name, getTypeValidatorFactory(name).create(description));
		}
	}

	private ParameterValue getParameter(EObject expr) throws ParameterizedTypeCreationException {
		if(expr instanceof SingleQuotedString) {
			StringValue param = PPTPFactory.eINSTANCE.createStringValue();
			param.setValue(((SingleQuotedString) expr).getText());
			return param;
		}
		else if(expr instanceof DoubleQuotedString) {
			EList<TextExpression> parts = ((DoubleQuotedString) expr).getStringPart();
			if(parts.size() == 1) {
				TextExpression part = parts.get(0);
				if(part instanceof VerbatimTE) {
					StringValue param = PPTPFactory.eINSTANCE.createStringValue();
					param.setValue(((VerbatimTE) part).getText());
					return param;
				}

			}
			throw new ParameterizedTypeCreationException("Not a string constant");
		}
		else if(expr instanceof LiteralName) {
			StringValue param = PPTPFactory.eINSTANCE.createStringValue();
			param.setValue(((LiteralName) expr).getValue());
			return param;
		}
		if(expr instanceof LiteralRegex) {
			String r = ((LiteralRegex) expr).getValue();
			int l = r.length();
			if(l >= 2 && r.charAt(0) == '/' && r.charAt(l - 1) == '/') {
				RegexpValue param = PPTPFactory.eINSTANCE.createRegexpValue();
				param.setValue(r.substring(1, l - 1));
				return param;
			}
		}
		else if(expr instanceof LiteralNameOrReference) {
			LiteralNameOrReference ref = (LiteralNameOrReference) expr;
			String s = ref.getValue();
			char c = s.charAt(0);
			if(c == '+' || c == '-' || Character.isDigit(c)) {
				try {
					Long v = Long.decode(s);
					IntegerValue param = PPTPFactory.eINSTANCE.createIntegerValue();
					param.setValue(v);
					return param;
				}
				catch(NumberFormatException e) {
					try {
						double d = Double.parseDouble(s);
						FloatValue param = PPTPFactory.eINSTANCE.createFloatValue();
						param.setValue(d);
						return param;
					}
					catch(NumberFormatException e2) {
					}
				}
			}
			try {
				return getTypeValue(ref);
			}
			catch(ParameterizedTypeCreationException e) {
				StringValue param = PPTPFactory.eINSTANCE.createStringValue();
				param.setValue(s);
				return param;
			}
		}
		else if(expr instanceof AtExpression) {
			return getTypeValue((Expression) expr);
		}
		else if(expr instanceof HashEntry) {
			HashEntry entry = (HashEntry) expr;
			ParameterValue key = getParameter(entry.getKey());
			if(!(key instanceof StringValue))
				throw new ParameterizedTypeCreationException("Expected hash key to be of String type");
			ParameterValue value = getParameter(entry.getValue());
			if(!(value instanceof TypeValue))
				throw new ParameterizedTypeCreationException("Expected hash value to be a Type");
			NamedTypeValue param = PPTPFactory.eINSTANCE.createNamedTypeValue();
			param.setName(((StringValue) key).getValue());
			param.setValue(((TypeValue) value).getValue());
			param.getParameters().addAll(((TypeValue) value).getParameters());
			return param;
		}
		throw new ParameterizedTypeCreationException("Unable to evaluate type parameter");
	}

	public void getParameters(AtExpression expr, List<ParameterValue> paramValues, boolean useNamedParameters)
			throws ParameterizedTypeCreationException {
		List<? extends EObject> params = expr.getParameters();
		if(useNamedParameters) {
			if(params.size() != 1)
				throw new ParameterizedTypeCreationException("Expected exactly one parameter");
			EObject hash = params.get(0);
			if(!(hash instanceof LiteralHash))
				throw new ParameterizedTypeCreationException("Expected parameter to be a hash literal");
			params = ((LiteralHash) hash).getElements();
		}
		for(EObject param : params)
			paramValues.add(getParameter(param));
	}

	public IEObjectDescription getReferencedObject(EObject expr) {
		if(expr instanceof AtExpression)
			expr = ((AtExpression) expr).getLeftExpr();
		if(expr instanceof LiteralNameOrReference) {
			ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adaptOrNull(expr);
			if(adapter != null)
				return adapter.getTargetObjectDescription();
		}
		return null;
	}

	/**
	 * Returns the type validator for the given <code>typeName</code>.
	 *
	 * @param typeName
	 *            The name for which we want a type validator.
	 * @return The type validator instance or <code>null</code> when no instance is found.
	 */
	public ITypeValidator getTypeValidator(String typeName) {
		return typeValidators.get(StringUtils.toInitialLowerCase(typeName));
	}

	private ITypeValidatorFactory getTypeValidatorFactory(String name) {
		ITypeValidatorFactory factory = typeValidatorFactories.get(name);
		if(factory == null)
			throw new IllegalArgumentException("No type validator has been registered for type " + name);
		return factory;
	}

	public TypeValue getTypeValue(Expression typeExpr) throws ParameterizedTypeCreationException {
		PPTPFactory pf = PPTPFactory.eINSTANCE;
		Expression typeName = typeExpr instanceof AtExpression
			? ((AtExpression) typeExpr).getLeftExpr()
			: typeExpr;
		ClassifierAdapter classifier = ClassifierAdapterFactory.eINSTANCE.adapt(typeName);
		TypeValue param = pf.createTypeValue();
		IEObjectDescription typeDesc = classifier.getTargetObjectDescription();
		if(typeDesc == null)
			throw new ParameterizedTypeCreationException("Unable to resolve type");

		EObject type = getResolvedEObject(typeDesc, typeExpr);
		boolean useNamedParameters = false;
		List<ParameterValue> params = param.getParameters();
		boolean ok = false;
		if(type instanceof PuppetType) {
			ok = true;
			PuppetType pt = (PuppetType) type;
			param.setValue(pt);
			useNamedParameters = pt.isNamedParameters();
		}
		else if(type instanceof HostClassDefinition) {
			ok = true;
			param.setValue(puppetType("class", typeExpr));
			StringValue sv = pf.createStringValue();
			sv.setValue(((HostClassDefinition) type).getClassName());
			params.add(sv);
		}
		else if(type instanceof Definition) {
			ok = true;
			param.setValue(puppetType("resource", typeExpr));
			StringValue sv = pf.createStringValue();
			sv.setValue(((Definition) type).getClassName());
			params.add(sv);
		}
		else if(type instanceof Type) {
			ok = true;
			param.setValue(puppetType("resource", typeExpr));
			StringValue sv = pf.createStringValue();
			sv.setValue(((Type) type).getName());
			params.add(sv);
		}

		if(!ok)
			throw new ParameterizedTypeCreationException("Unable to evaluate type parameter value");

		if(typeExpr instanceof AtExpression)
			getParameters((AtExpression) typeExpr, params, useNamedParameters);
		return param;
	}

	public PuppetType puppetType(String typeName, EObject typeExpr) throws ParameterizedTypeCreationException {
		ITypeValidator typeValidator = getTypeValidator(typeName);
		if(typeValidator == null)
			throw new ParameterizedTypeCreationException("Unable to resolve type");
		return typeValidator.getType(typeExpr);
	}

	private void validityAssertedAtRuntime(EObject value, IMessageAcceptor acceptor) {
		Severity severity;
		switch(advisor().validityAssertedAtRuntime()) {
			case IGNORE:
				return;
			case WARNING:
				severity = Severity.WARNING;
				break;
			default:
				severity = Severity.ERROR;
		}
		acceptor.accept(severity, "Validity cannot be asserted until runtime", value, IPPDiagnostics.ISSUE__VALIDITY_ASSERTED_AT_RUNTIME);
	}
}
