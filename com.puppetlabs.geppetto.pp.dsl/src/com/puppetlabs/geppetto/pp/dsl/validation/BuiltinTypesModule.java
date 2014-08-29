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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.HashEntry;
import com.puppetlabs.geppetto.pp.LiteralHash;
import com.puppetlabs.geppetto.pp.LiteralList;
import com.puppetlabs.geppetto.pp.dsl.StringUtils;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator.PPType;
import com.puppetlabs.geppetto.pp.dsl.eval.TextExpressionHelper;
import com.puppetlabs.geppetto.pp.dsl.linking.RecordingMessageAcceptor;
import com.puppetlabs.geppetto.pp.pptp.FloatValue;
import com.puppetlabs.geppetto.pp.pptp.IntegerValue;
import com.puppetlabs.geppetto.pp.pptp.NamedTypeValue;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;
import com.puppetlabs.geppetto.pp.pptp.StringValue;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

public class BuiltinTypesModule extends AbstractModule {
	public static class AnyValidator extends AbstractTypeValidator {
		@Inject
		public AnyValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			return true;
		}
	}

	public static class ArrayValidator extends AbstractTypeValidator {
		@Inject
		public ArrayValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		@Override
		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.ARRAY) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			if(declArgs.isEmpty())
				// Unbounded array, so nothing more to check.
				return true;

			PuppetType puppetType = getType(ctx);
			List<Expression> elems = ((LiteralList) ctx.value()).getElements();
			boolean success = checkCollectionSize(elems.size(), declArgs, 1, puppetType, ctx);

			int top = elems.size();
			if(top > 0) {
				// Validate that all elements have a correct type
				TypeValue tv = getTypeParam(declArgs, 0);
				if(tv != null) {
					PuppetType elemType = tv.getValue();
					List<ParameterValue> elemTypeArgs = tv.getParameters();
					for(int idx = 0; idx < top; ++idx)
						if(!ctx.checkExpression(
							puppetType.getParameters().get(0), elemType.getName(), elemTypeArgs, elems.get(idx), ctx.acceptor()))
							success = false;
				}
			}
			return success;
		}
	}

	public static class BooleanValidator extends AbstractTypeValidator {
		@Inject
		public BooleanValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() == PPType.BOOLEAN)
				return true;
			unfulfilledConstraint(param, ctx);
			return false;
		}
	}

	public static class CallableValidator extends AbstractTypeValidator {
		@Inject
		public CallableValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			// TODO:
			return true;
		}
	}

	public static class ClassValidator extends AbstractTypeValidator {
		@Inject
		public ClassValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.CLASS) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			int paramCount = declArgs.size();
			if(paramCount == 0)
				return true;

			boolean success = false;
			IEObjectDescription ref = ctx.getReferencedObject();
			for(int idx = 0; idx < paramCount;) {
				ParameterValue pv = getParam(declArgs, idx++);

				// The className paramameters can be a type or a string
				if(pv instanceof TypeValue) {
					PuppetTypeParameter tvParam = getType(ctx).getParameters().get(0);
					TypeValue tv = (TypeValue) pv;
					if(idx < paramCount && paramCount > 1) {
						RecordingMessageAcceptor tmpAcceptor = new RecordingMessageAcceptor();
						success = ctx.checkExpression(tvParam, tv.getValue().getName(), tv.getParameters(), ctx.value(), ctx.acceptor());
						if(success)
							tmpAcceptor.playBack(ctx.acceptor());
					}
					else
						success = ctx.checkExpression(tvParam, tv.getValue().getName(), tv.getParameters(), ctx.value(), ctx.acceptor());
				}
				else if(pv instanceof StringValue && ref != null)
					// Name of resource must conform with type parameter
					success = StringUtils.equalsIgnoreInitialCase(ref.getName().getLastSegment(), ((StringValue) pv).getValue());

				if(success)
					return true;
			}
			unfulfilledConstraint(param, ctx);
			return false;
		}
	}

	public static class DefaultValidator extends AbstractTypeValidator {
		@Inject
		public DefaultValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.DEFAULT) {
				unfulfilledConstraint(param, ctx);
				return false;
			}
			return true;
		}
	}

	public static class EnumValidator extends StringValidator {
		@Inject
		public EnumValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		@Override
		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(!super.validate(param, Collections.<ParameterValue> emptyList(), ctx))
				return false; // String validation failed

			String s = ctx.getStringConstant(ctx.value());
			if(s == null)
				return true;

			for(ParameterValue arg : declArgs)
				if(arg instanceof StringValue && s.equals(((StringValue) arg).getValue()))
					return true;

			StringBuilder bld = new StringBuilder();
			int top = declArgs.size();
			appendConstraintStart(param, bld, ctx);
			if(top == 0)
				bld.append("is an empty enum. It does not match anything");
			else {
				bld.append("expects ");
				if(top > 2)
					bld.append("one of ");
				ctx.typePrinter().append(declArgs.get(0), bld);
				for(int idx = 1; idx < top; ++idx) {
					if(top == 2)
						bld.append(" or ");
					else {
						bld.append(", ");
						if(idx == top - 1)
							bld.append("or ");
					}
					ctx.typePrinter().append(declArgs.get(idx), bld);
				}
			}
			bld.append(". Got '");
			bld.append(s);
			bld.append('\'');
			ctx.acceptor().acceptError(bld.toString(), ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
			return false;
		}
	}

	public static class FloatValidator extends AbstractTypeValidator {
		@Inject
		public FloatValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			boolean success = true;
			switch(ctx.ppType()) {
				case FLOAT:
					if(!declArgs.isEmpty()) {
						// Bounded. Validate if value is constant
						String s = TextExpressionHelper.getLiteralString(ctx.value());
						if(s != null)
							try {
								double n = Double.parseDouble(s);
								if(!(n >= getDoubleParam(declArgs, 0, Double.MIN_VALUE) && n <= getDoubleParam(
									declArgs, 1, Double.MAX_VALUE)))
									success = false;
							}
							catch(NumberFormatException e2) {
								success = false;
							}
					}
					break;
				case NUMERIC: // This means that we don't know if it's a float or integer.
					break;
				default:
					success = false;
			}
			if(success == false)
				unfulfilledConstraint(param, ctx);
			return success;
		}
	}

	public static class HashValidator extends AbstractTypeValidator {
		@Inject
		public HashValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.HASH) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			if(declArgs.isEmpty())
				// Unbounded hash, so nothing more to check.
				return true;

			PuppetType puppetType = getType(ctx);
			List<HashEntry> elems = ((LiteralHash) ctx.value()).getElements();
			boolean success = checkCollectionSize(elems.size(), declArgs, 2, puppetType, ctx);
			int top = elems.size();
			if(top > 0) {
				// Validate that all elements have a correct type
				TypeValue kt = getTypeParam(declArgs, 0);
				if(kt != null) {
					PuppetType elemType = kt.getValue();
					List<ParameterValue> elemTypeArgs = kt.getParameters();
					for(int idx = 0; idx < top; ++idx)
						if(!ctx.checkExpression(
							puppetType.getParameters().get(0), elemType.getName(), elemTypeArgs, elems.get(idx).getKey(), ctx.acceptor()))
							success = false;
				}
				TypeValue vt = getTypeParam(declArgs, 1);
				if(vt != null) {
					PuppetType elemType = vt.getValue();
					List<ParameterValue> elemTypeArgs = vt.getParameters();
					for(int idx = 0; idx < top; ++idx)
						if(!ctx.checkExpression(
							puppetType.getParameters().get(1), elemType.getName(), elemTypeArgs, elems.get(idx).getValue(), ctx.acceptor()))
							success = false;
				}
			}
			return success;
		}
	}

	public static class InheritanceValidator extends AbstractTypeValidator {
		@Inject
		public InheritanceValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(isSuperTypeOf(getType(ctx), ctx.type()))
				return true;
			unfulfilledConstraint(param, ctx);
			return false;
		}
	}

	public static class IntegerValidator extends AbstractTypeValidator {
		@Inject
		public IntegerValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			boolean success = true;
			switch(ctx.ppType()) {
				case INTEGER:
					if(!declArgs.isEmpty()) {
						// Bounded. Validate if value is constant
						String s = TextExpressionHelper.getLiteralString(ctx.value());
						if(s != null)
							try {
								long n = Long.decode(s);
								if(!(n >= getLongParam(declArgs, 0, Long.MIN_VALUE) && n <= getLongParam(declArgs, 1, Long.MAX_VALUE)))
									success = false;
							}
							catch(NumberFormatException e) {
								success = false;
							}
					}
					break;
				case NUMERIC: // This means that we don't know if it's a float or integer.
					break;
				default:
					success = false;
			}
			if(success == false)
				unfulfilledConstraint(param, ctx);
			return success;
		}
	}

	public static class NumericValidator extends AbstractTypeValidator {
		@Inject
		public NumericValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			boolean success = true;
			switch(ctx.ppType()) {
				case INTEGER:
				case FLOAT:
				case NUMERIC:
					break;
				default:
					unfulfilledConstraint(param, ctx);
					success = false;
			}
			return success;
		}
	}

	public static class OptionalValidator extends AbstractTypeValidator {
		@Inject
		public OptionalValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		@Override
		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() == PPType.UNDEF)
				return true;

			TypeValue tv = getTypeParam(declArgs, 0);
			if(tv == null)
				return true;

			return ctx.checkExpression(
				ctx.type().getParameters().get(0), tv.getValue().getName(), tv.getParameters(), ctx.value(), ctx.acceptor());
		}
	}

	public static class PatternValidator extends StringValidator {
		@Inject
		public PatternValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		@Override
		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(!super.validate(param, Collections.<ParameterValue> emptyList(), ctx))
				return false; // String validation failed

			String s = ctx.getStringConstant(ctx.value());
			if(s == null)
				return true;

			for(ParameterValue arg : declArgs)
				if(arg instanceof TypeValue) {
					TypeValue tv = (TypeValue) arg;
					String tvName = tv.getValue().getName();
					if("pattern".equals(tvName)) {
						if(validate(param, tv.getParameters(), ctx))
							return true;
					}
					else if("regexp".equals(tvName)) {
						List<ParameterValue> tvp = tv.getParameters();
						if(tvp.size() > 0 && matchRexexpOrString(tvp.get(0), s))
							return true;
					}
				}
				else if(matchRexexpOrString(arg, s))
					return true;

			unfulfilledConstraint(param, ctx);
			return false;
		}
	}

	public static class RegexpValidator extends AbstractTypeValidator {
		@Inject
		public RegexpValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.REGEXP) {
				unfulfilledConstraint(param, ctx);
				return false;
			}
			return true;
		}
	}

	public static class ResourceValidator extends AbstractTypeValidator {
		@Inject
		public ResourceValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			PPType ppType = ctx.ppType();
			if(ppType != PPType.RESOURCE_TYPE && ppType != PPType.USER_DEFINED_RESOURCE_TYPE) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			int paramCount = declArgs.size();
			if(paramCount == 0)
				return true;

			ParameterValue pv = getParam(declArgs, 0);
			boolean success = true;

			// The first argument can be a type or a string
			PuppetType puppetType = getType(ctx);
			if(pv instanceof TypeValue) {
				TypeValue tv = (TypeValue) pv;
				success = ctx.checkExpression(
					puppetType.getParameters().get(0), tv.getValue().getName(), tv.getParameters(), ctx.value(), ctx.acceptor());
			}
			else if(pv instanceof StringValue) {
				IEObjectDescription ref = ctx.getReferencedObject();
				if(ref != null)
					// Name of resource must conform with type parameter
					success = StringUtils.equalsIgnoreInitialCase(ref.getName().getLastSegment(), ((StringValue) pv).getValue());
			}

			if(success) {
				if(paramCount == 1 || !(ctx.value() instanceof AtExpression))
					return true;

				PuppetTypeParameter paramDecl = puppetType.getParameters().get(1);
				List<Expression> vparms = ((AtExpression) ctx.value()).getParameters();
				for(Expression vparm : vparms) {
					switch(ctx.ppType(vparm)) {
						case STRING:
						case INTEGER_STRING:
						case FLOAT_STRING:
						case UNQUOTED_STRING:
							String s = ctx.getStringConstant(vparm);
							if(s != null) {
								// Must be present in the list of titles
								int paramIdx = 1;
								for(; paramIdx < paramCount; ++paramIdx)
									if(s.equals(getStringParam(declArgs, paramIdx, null)))
										break;

								if(paramIdx == paramCount) {
									success = false;
									break;
								}
							}
							break;
						default:
							// Validate using String type. Will emit validity asserted at runtime etc.
							return ctx.checkExpression(
								paramDecl, paramDecl.getType().getName(), Collections.<ParameterValue> emptyList(), vparm, ctx.acceptor());
					}
				}
			}
			if(!success)
				unfulfilledConstraint(param, ctx);
			return success;
		}
	}

	public static class RuntimeValidator extends AbstractTypeValidator {
		@Inject
		public RuntimeValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			// TODO:
			return true;
		}
	}

	public static class StringValidator extends AbstractTypeValidator {
		@Inject
		public StringValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			switch(ctx.ppType()) {
				case DYNAMIC_STRING:
				case STRING:
				case FLOAT_STRING:
				case INTEGER_STRING:
				case UNQUOTED_STRING:
					if(declArgs.isEmpty())
						return true;
					String s = ctx.getStringConstant(ctx.value());
					return s == null
						? true
						: checkCollectionSize(s.length(), declArgs, 0, ctx.type(), ctx);
				default:
					unfulfilledConstraint(param, ctx);
					return false;
			}
		}
	}

	public static class StructValidator extends HashValidator {
		@Inject
		public StructValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		@Override
		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.HASH) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			boolean success = true;
			EObject value = ctx.value();
			if(!(value instanceof LiteralHash)) {
				ctx.validityAssertedAtRuntime();
				return true;
			}

			Map<String, NamedTypeValue> unmatchedKeys = Maps.newHashMap();
			for(ParameterValue arg : declArgs)
				if(arg instanceof NamedTypeValue) {
					NamedTypeValue ntArg = (NamedTypeValue) arg;
					unmatchedKeys.put(ntArg.getName(), ntArg);
				}

			boolean runtimeKeyValidity = false;
			PuppetTypeParameter hashParam = ctx.type().getParameters().get(0);
			for(HashEntry entry : ((LiteralHash) value).getElements()) {
				// Each parameter represents a value type. The key types must all be strings
				if(!ctx.checkExpression(hashParam, "string", Collections.<ParameterValue> emptyList(), entry.getKey(), ctx.acceptor())) {
					// String validation failed for the key
					success = false;
					continue;
				}

				String key = ctx.getStringConstant(entry.getKey());
				if(key == null) {
					// Struct member does not have a constant string as its key.
					ctx.validityAssertedAtRuntime();
					runtimeKeyValidity = true;
					continue;
				}

				NamedTypeValue found = unmatchedKeys.remove(key);
				if(found == null) {
					// Struct member with the provided name was not found in type declaration
					StringBuilder bld = new StringBuilder();
					appendConstraintStart(hashParam, bld, ctx);
					bld.append("does not allow a key named '");
					bld.append(key);
					bld.append('\'');
					ctx.acceptor().acceptError(bld.toString(), entry.getKey(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
					success = false;
				}
				else {
					if(!ctx.checkExpression(hashParam, found.getValue().getName(), found.getParameters(), entry.getValue(), ctx.acceptor()))
						success = false;
				}
			}

			if(runtimeKeyValidity)
				// Could not determine all keys, so don't report them missing
				return success;

			if(!unmatchedKeys.isEmpty()) {
				// Unmatched keys are OK provided the type is Optional.
				Iterator<NamedTypeValue> iter = unmatchedKeys.values().iterator();
				while(iter.hasNext())
					if("optional".equals(iter.next().getValue().getName()))
						iter.remove();

				if(!unmatchedKeys.isEmpty()) {
					// Still not empty? Too bad.
					StringBuilder bld = new StringBuilder();
					appendConstraintStart(hashParam, bld, ctx);
					bld.append("is missing required key");
					if(unmatchedKeys.size() > 1)
						bld.append('s');
					boolean first = true;
					for(String key : unmatchedKeys.keySet()) {
						if(first)
							first = false;
						else
							bld.append(", ");
						bld.append('\'');
						bld.append(key);
						bld.append('\'');
					}
					ctx.acceptor().acceptError(bld.toString(), ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
					success = false;
				}
			}
			return success;
		}
	}

	public static class TupleValidator extends AbstractTypeValidator {
		@Inject
		public TupleValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.ARRAY) {
				unfulfilledConstraint(param, ctx);
				return false;
			}

			int nArgs = declArgs.size();
			if(nArgs == 0)
				// Unbounded tuple, so nothing more to check.
				return true;

			// We can expect a variable number of types first, followed by a size constraint
			int idx;
			int minPos = -1;
			for(idx = 0; idx < nArgs; ++idx)
				if(declArgs.get(idx) instanceof IntegerValue) {
					minPos = -1;
					nArgs = idx;
					break;
				}

			int min;
			int max;
			PuppetType puppetType = getType(ctx);
			List<Expression> elems = ((LiteralList) ctx.value()).getElements();
			if(minPos >= 0) {
				min = (int) getLongParam(declArgs, minPos, nArgs);
				max = (int) getLongParam(declArgs, minPos + 1, nArgs);
			}
			else
				min = max = nArgs;

			int nElems = elems.size();
			boolean success = checkCollectionSize(nElems, declArgs, min, max, puppetType, ctx);
			if(nElems > nArgs)
				nArgs = nElems;

			// Perform type check on elements that we have types for
			PuppetTypeParameter typeParameter = ctx.type().getParameters().get(0);
			for(idx = 0; idx < nArgs; ++idx) {
				ParameterValue pv = declArgs.get(idx);
				if(pv instanceof TypeValue) {
					TypeValue tv = (TypeValue) pv;
					if(!ctx.checkExpression(typeParameter, tv.getValue().getName(), tv.getParameters(), elems.get(idx), ctx.acceptor()))
						success = false;
				}
			}
			return success;
		}
	}

	public static class TypeValidator extends AbstractTypeValidator {
		@Inject
		public TypeValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		private boolean compareParams(ParameterValue a, ParameterValue b) {
			if(a instanceof StringValue)
				return b instanceof StringValue
					? ((StringValue) a).getValue().equals(((StringValue) b).getValue())
					: false;
			if(a instanceof IntegerValue)
				return b instanceof IntegerValue
					? ((IntegerValue) a).getValue() == ((IntegerValue) b).getValue()
					: false;
			if(a instanceof FloatValue)
				return b instanceof FloatValue
					? ((FloatValue) a).getValue() == ((FloatValue) b).getValue()
					: false;
			if(a instanceof TypeValue)
				return b instanceof TypeValue
					? compareTypes((TypeValue) a, (TypeValue) b)
					: false;
			return false;
		}

		private boolean compareTypes(TypeValue a, TypeValue b) {
			if(!a.getValue().getName().equals(b.getValue().getName()))
				return false;

			List<ParameterValue> argsA = a.getParameters();
			List<ParameterValue> argsB = b.getParameters();
			if(argsA.size() != argsB.size())
				return false;

			nextA: for(ParameterValue argA : argsA) {
				for(ParameterValue argB : argsB)
					if(compareParams(argA, argB))
						continue nextA;
				return false;
			}
			return true;
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			PPType ppType = ctx.ppType();
			if(ppType != PPType.TYPE && ppType != PPType.RESOURCE_TYPE && ppType != PPType.USER_DEFINED_RESOURCE_TYPE) {
				unfulfilledConstraint(param, ctx);
				return false;
			}
			TypeValue tv = getTypeParam(declArgs, 0);
			if(tv == null)
				// Any type will do
				return true;

			String typeName = tv.getValue().getName();
			if(ppType != PPType.TYPE) {
				// This is a short-form, i.e. File rather than Resource[File]. This is
				// OK if we're a Type[Resource]
				if("resource".equals(typeName))
					return ctx.checkExpression(
						getType(ctx).getParameters().get(0), typeName, tv.getParameters(), ctx.value(), ctx.acceptor());

				// We don't recognize any other short form
				unfulfilledConstraint(param, ctx);
				return false;
			}

			TypeValue referencedType = ctx.typeValue();
			if(referencedType == null) {
				// The referenced type contains dynamic stuff.
				ctx.validityAssertedAtRuntime();
				return true;
			}
			return compareTypes(tv, referencedType);
		}
	}

	public static class UndefValidator extends AbstractTypeValidator {
		@Inject
		public UndefValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			if(ctx.ppType() != PPType.UNDEF) {
				// A bit weird, but if anyone restricts a parameter to type Undef, then
				// only undef is allowed.
				unfulfilledConstraint(param, ctx);
				return false;
			}
			return true;
		}
	}

	public static class VariantValidator extends AbstractTypeValidator {
		@Inject
		public VariantValidator(@Assisted IEObjectDescription puppetTypeDesc) {
			super(puppetTypeDesc);
		}

		public boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx) {
			// Just delegate this down to the individual types. We don't switch the owner to
			// be the actual variant here.
			int nArgs = declArgs.size();
			boolean success = false; // One must succeed
			List<TypeValue> values = Lists.newArrayList();
			PuppetType puppetType = getType(ctx);
			for(int argNo = 0; argNo < nArgs; ++argNo) {
				TypeValue tv = getTypeParam(declArgs, argNo);
				if(tv != null) {
					values.add(tv);
					RecordingMessageAcceptor tmpAcceptor = new RecordingMessageAcceptor();
					if(ctx.checkExpression(
						puppetType.getParameters().get(0), tv.getValue().getName(), tv.getParameters(), ctx.value(), tmpAcceptor)) {
						tmpAcceptor.playBack(ctx.acceptor()); // In case of warnings
						success = true;
						break;
					}
				}
			}
			if(success == false) {
				// None of the variants were succesful and since we've thrown away all errors to that fact, we need to
				// produce one here.
				if(values.isEmpty()) {
					ctx.acceptor().acceptError(
						"Unable to to fulfil empty variant constraint", ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
					return false;
				}
				StringBuilder bld = new StringBuilder();
				if(param != null) {
					appendConstraintStart(param, bld, ctx);
					bld.append("must be of type ");
					appendConstraintExpectedVariant(values, ctx.typePrinter(), bld);
					bld.append(". Got ");
					appendConstraintActual(ctx, bld);
				}
				else {
					bld.append("Cannot use ");
					appendConstraintActual(ctx, bld);
					bld.append(" where ");
					appendConstraintExpectedVariant(values, ctx.typePrinter(), bld);
					bld.append(" is expected");
				}
				ctx.acceptor().acceptError(bld.toString(), ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
			}
			return success;
		}
	}

	private static boolean matchRexexpOrString(ParameterValue arg, String s) {
		// Covers both string and literal regexp
		return arg instanceof StringValue && Pattern.compile(((StringValue) arg).getValue()).matcher(s).find();
	}

	@Override
	protected void configure() {
		MapBinder<String, ITypeValidatorFactory> mapBinder = MapBinder.newMapBinder(binder(), String.class, ITypeValidatorFactory.class);
		installType("any", AnyValidator.class, mapBinder);
		installType("array", ArrayValidator.class, mapBinder);
		installType("boolean", BooleanValidator.class, mapBinder);
		installType("callable", CallableValidator.class, mapBinder);
		installType("catalogEntry", InheritanceValidator.class, mapBinder);
		installType("class", ClassValidator.class, mapBinder);
		installType("collection", InheritanceValidator.class, mapBinder);
		installType("data", InheritanceValidator.class, mapBinder);
		installType("default", DefaultValidator.class, mapBinder);
		installType("enum", EnumValidator.class, mapBinder);
		installType("float", FloatValidator.class, mapBinder);
		installType("hash", HashValidator.class, mapBinder);
		installType("integer", IntegerValidator.class, mapBinder);
		installType("numeric", NumericValidator.class, mapBinder);
		installType("optional", OptionalValidator.class, mapBinder);
		installType("pattern", PatternValidator.class, mapBinder);
		installType("regexp", RegexpValidator.class, mapBinder);
		installType("resource", ResourceValidator.class, mapBinder);
		installType("runtime", RuntimeValidator.class, mapBinder);
		installType("scalar", InheritanceValidator.class, mapBinder);
		installType("string", StringValidator.class, mapBinder);
		installType("struct", StructValidator.class, mapBinder);
		installType("tuple", TupleValidator.class, mapBinder);
		installType("type", TypeValidator.class, mapBinder);
		installType("undef", UndefValidator.class, mapBinder);
		installType("variant", VariantValidator.class, mapBinder);
	}

	protected void installType(String typeName, Class<? extends ITypeValidator> validatorClass,
			MapBinder<String, ITypeValidatorFactory> mapBinder) {
		Key<ITypeValidatorFactory> key = Key.get(ITypeValidatorFactory.class, Names.named(typeName));
		install(new FactoryModuleBuilder().implement(ITypeValidator.class, validatorClass).build(key));
		mapBinder.addBinding(typeName).toProvider(getProvider(key));
	}
}
