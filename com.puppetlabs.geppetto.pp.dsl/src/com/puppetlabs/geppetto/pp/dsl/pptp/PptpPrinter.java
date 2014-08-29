/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.pptp;

import java.util.List;

import com.puppetlabs.geppetto.pp.dsl.StringUtils;
import com.puppetlabs.geppetto.pp.pptp.IntegerValue;
import com.puppetlabs.geppetto.pp.pptp.NamedTypeValue;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;
import com.puppetlabs.geppetto.pp.pptp.RegexpValue;
import com.puppetlabs.geppetto.pp.pptp.StringValue;
import com.puppetlabs.geppetto.pp.pptp.TypeReferenceValue;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

/**
 * A class that can produce Puppet Types in string form.
 */
public class PptpPrinter {
	public void append(ParameterValue param, StringBuilder bld) {
		if(param == null)
			return;

		if(param instanceof TypeValue) {
			TypeValue tv = (TypeValue) param;
			if(tv instanceof NamedTypeValue) {
				bld.append(((NamedTypeValue) tv).getName());
				bld.append("=>");
			}
			appendTypeName(tv.getValue(), bld);
			appendParameters(tv.getParameters(), bld);
		}
		else if(param instanceof TypeReferenceValue)
			bld.append(((TypeReferenceValue) param).getValue());
		else if(param instanceof StringValue) {
			bld.append('\'');
			bld.append(((StringValue) param).getValue());
			bld.append('\'');
		}
		else if(param instanceof RegexpValue) {
			bld.append('/');
			bld.append(((StringValue) param).getValue());
			bld.append('/');
		}
		else if(param instanceof IntegerValue)
			bld.append(((IntegerValue) param).getValue());
		else
			bld.append("<unknown value type>");
	}

	public void append(PuppetType puppetType, boolean condensed, StringBuilder bld) {
		if(puppetType == null)
			return;

		appendTypeName(puppetType, bld);
		List<PuppetTypeParameter> parameters = puppetType.getParameters();
		int top = parameters.size();
		if(top == 0)
			return;

		bld.append('[');
		PuppetTypeParameter param = parameters.get(0);
		if(condensed)
			bld.append(param.getName());
		else
			append(param, bld);
		for(int idx = 1; idx < top; ++idx) {
			bld.append(',');
			param = parameters.get(idx);
			if(condensed)
				bld.append(param.getName());
			else {
				bld.append(' ');
				append(param, bld);
			}
		}
		bld.append(']');
	}

	public void append(PuppetTypeParameter param, StringBuilder bld) {
		if(param == null)
			return;

		if(param.isNamevar())
			bld.append("{String => ");
		appendTypeName(param.getType(), bld);
		appendParameters(param.getParameters(), bld);
		if(param.isNamevar())
			bld.append('}');
		bld.append(' ');
		String label = param.getName();
		if(label == null)
			label = "<unnamed parameter>";
		bld.append(label);
		if(param.isVarargs())
			if(param.isRequired())
				bld.append('+');
			else
				bld.append('*');
		else if(!param.isRequired())
			bld.append('?');
	}

	private void appendParameters(List<ParameterValue> values, StringBuilder bld) {
		int top = values.size();
		if(top == 0)
			return;

		bld.append('[');
		ParameterValue first = values.get(0);
		boolean hash = first instanceof NamedTypeValue;
		if(hash)
			bld.append('{');
		append(first, bld);
		for(int idx = 1; idx < top; ++idx) {
			bld.append(", ");
			append(values.get(idx), bld);
		}
		if(hash)
			bld.append('}');
		bld.append(']');
	}

	public void appendTypedTypeName(PuppetType type, StringBuilder bld) {
		bld.append("Type");
		String tn = type.getName();
		if(!"type".equals(tn)) { // We don't print Type[Type] since that's a Type
			bld.append('[');
			StringUtils.appendInitialUpperCase(tn, bld);
			bld.append(']');
		}
	}

	public void appendTypeName(PuppetType type, StringBuilder bld) {
		if(type == null)
			return;

		String name = type.getName();
		if(name == null || name.length() == 0)
			bld.append("<unnamed type>");
		else
			StringUtils.appendInitialUpperCase(name, bld);
	}

	public String toString(PuppetType puppetType) {
		StringBuilder bld = new StringBuilder();
		append(puppetType, false, bld);
		return bld.toString();
	}
}
