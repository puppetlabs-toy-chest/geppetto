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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.puppetlabs.geppetto.pp.Definition;
import com.puppetlabs.geppetto.pp.HostClassDefinition;
import com.puppetlabs.geppetto.pp.dsl.StringUtils;
import com.puppetlabs.geppetto.pp.dsl.pptp.PptpPrinter;
import com.puppetlabs.geppetto.pp.pptp.FloatValue;
import com.puppetlabs.geppetto.pp.pptp.IntegerValue;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;
import com.puppetlabs.geppetto.pp.pptp.StringValue;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

public abstract class AbstractTypeValidator implements ITypeValidator {

	protected static void appendConstraintActual(ITypeValidationContext ctx, StringBuilder bld) {
		EObject target = ctx.getReferencedTypeOrClass();
		if(target instanceof PuppetType)
			ctx.typePrinter().appendTypedTypeName((PuppetType) target, bld);
		else if(target instanceof HostClassDefinition) {
			bld.append("Class[");
			StringUtils.appendInitialUpperCase(((Definition) target).getClassName(), bld);
			bld.append(']');
		}
		else if(target instanceof Definition) {
			bld.append("Resource[");
			StringUtils.appendInitialUpperCase(((Definition) target).getClassName(), bld);
			bld.append(']');
		}
		else if(target instanceof Type) {
			bld.append("Resource[");
			StringUtils.appendInitialUpperCase(((Type) target).getName(), bld);
			bld.append(']');
		}
		else
			bld.append(ctx.ppType().getPuppetTypeName());
	}

	protected static void appendConstraintExpectedVariant(List<TypeValue> values, PptpPrinter pptpPrinter, StringBuilder bld) {
		int nArgs = values.size();
		for(int argNo = 0; argNo < nArgs; ++argNo) {
			if(argNo > 0) {
				if(nArgs > 2)
					bld.append(',');
				bld.append(' ');
				if(argNo == nArgs - 1)
					bld.append("or ");
			}
			pptpPrinter.append(values.get(argNo), bld);
		}
	}

	protected static double getDoubleParam(List<ParameterValue> declArgs, int idx, double defaultValue) {
		ParameterValue value = getParam(declArgs, idx);
		return value instanceof FloatValue
			? ((FloatValue) value).getValue()
			: defaultValue;
	}

	protected static long getLongParam(List<ParameterValue> declArgs, int idx, long defaultValue) {
		ParameterValue value = getParam(declArgs, idx);
		return value instanceof IntegerValue
			? ((IntegerValue) value).getValue()
			: defaultValue;
	}

	protected static ParameterValue getParam(List<ParameterValue> declArgs, int idx) {
		if(idx >= declArgs.size())
			return null;
		return declArgs.get(idx);
	}

	protected static String getStringParam(List<ParameterValue> declArgs, int idx, String defaultValue) {
		ParameterValue value = getParam(declArgs, idx);
		return value instanceof StringValue
			? ((StringValue) value).getValue()
			: defaultValue;
	}

	protected static TypeValue getTypeParam(List<ParameterValue> declArgs, int idx) {
		ParameterValue value = getParam(declArgs, idx);
		return value instanceof TypeValue
			? (TypeValue) value
			: null;
	}

	protected static boolean isSuperTypeOf(PuppetType t1, PuppetType t2) {
		if(t1 == t2)
			return true;
		for(PuppetType st : t2.getSuperTypes())
			if(isSuperTypeOf(t1, st))
				return true;
		return false;
	}

	private final IEObjectDescription puppetTypeDesc;

	public AbstractTypeValidator(IEObjectDescription puppetTypeDesc) {
		this.puppetTypeDesc = puppetTypeDesc;
	}

	protected void appendConstraintStart(PuppetTypeParameter param, StringBuilder bld, ITypeValidationContext ctx) {
		PuppetType type = param == null
			? getType(ctx)
			: (PuppetType) param.eContainer();
		ctx.typePrinter().append(type, true, bld);
		bld.append(" argument ");
		if(param != null && type.getParameters().size() > 1) {
			bld.append('\'');
			bld.append(param.getName());
			bld.append("' ");
		}
	}

	protected boolean checkCollectionSize(int size, List<ParameterValue> declArgs, int min, int max, PuppetType puppetType,
			ITypeValidationContext ctx) {
		boolean success = true;
		boolean unbounded = max == Integer.MAX_VALUE;
		if(size < min || size > max) {
			StringBuilder bld = new StringBuilder();
			int wTop = size > max
				? size
				: max;
			ctx.typePrinter().append(puppetType, true, bld);
			bld.append(" accepts ");
			appendCount(min, wTop, bld);
			if(unbounded)
				bld.append(" or more arguments");
			else {
				if(min != max) {
					if(min == 1 && max == 2)
						bld.append(" or ");
					else
						bld.append(" to ");
					appendCount(max, wTop, bld);
				}
				bld.append(" argument");
				if(max > 1)
					bld.append('s');
			}
			bld.append(". Got ");
			appendCount(size, wTop, bld);
			ctx.acceptor().acceptError(bld.toString(), ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
			success = false;
		}
		return success;
	}

	protected boolean checkCollectionSize(int size, List<ParameterValue> declArgs, int minPos, PuppetType puppetType,
			ITypeValidationContext ctx) {
		int min = (int) getLongParam(declArgs, minPos, 0);
		int max = (int) getLongParam(declArgs, minPos + 1, Integer.MAX_VALUE);
		return checkCollectionSize(size, declArgs, min, max, puppetType, ctx);
	}

	@Override
	public PuppetType getType(EObject objectContext) {
		EObject puppetType = puppetTypeDesc.getEObjectOrProxy();
		if(puppetType.eIsProxy())
			puppetType = EcoreUtil.resolve(puppetType, objectContext);
		return (PuppetType) puppetType;
	}

	protected PuppetType getType(ITypeValidationContext ctx) {
		return getType(ctx.value());
	}

	@Override
	public String getTypeName() {
		return puppetTypeDesc.getName().getLastSegment();
	}

	public void unfulfilledConstraint(PuppetTypeParameter param, ITypeValidationContext ctx) {
		StringBuilder bld = new StringBuilder();
		String name = getTypeName();
		if(param != null) {
			appendConstraintStart(param, bld, ctx);
			bld.append("must be ");
			if("type".equals(name))
				bld.append("a ");
			else
				bld.append("of type ");
			StringUtils.appendInitialUpperCase(name, bld);
			bld.append(". Got ");
			appendConstraintActual(ctx, bld);
		}
		else {
			bld.append("Cannot use ");
			appendConstraintActual(ctx, bld);
			bld.append(" where ");
			StringUtils.appendInitialUpperCase(name, bld);
			bld.append(" is expected");
		}
		ctx.acceptor().acceptError(bld.toString(), ctx.value(), IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}
}
