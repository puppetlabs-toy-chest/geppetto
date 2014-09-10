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
package com.puppetlabs.geppetto.pp.dsl.eval;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.AdditiveExpression;
import com.puppetlabs.geppetto.pp.AndExpression;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.BinaryExpression;
import com.puppetlabs.geppetto.pp.DoubleQuotedString;
import com.puppetlabs.geppetto.pp.EqualityExpression;
import com.puppetlabs.geppetto.pp.FunctionCall;
import com.puppetlabs.geppetto.pp.LiteralBoolean;
import com.puppetlabs.geppetto.pp.LiteralDefault;
import com.puppetlabs.geppetto.pp.LiteralHash;
import com.puppetlabs.geppetto.pp.LiteralList;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.LiteralRegex;
import com.puppetlabs.geppetto.pp.LiteralUndef;
import com.puppetlabs.geppetto.pp.MatchingExpression;
import com.puppetlabs.geppetto.pp.MultiplicativeExpression;
import com.puppetlabs.geppetto.pp.OrExpression;
import com.puppetlabs.geppetto.pp.ParenthesisedExpression;
import com.puppetlabs.geppetto.pp.RelationalExpression;
import com.puppetlabs.geppetto.pp.SelectorExpression;
import com.puppetlabs.geppetto.pp.ShiftExpression;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.TextExpression;
import com.puppetlabs.geppetto.pp.UnaryMinusExpression;
import com.puppetlabs.geppetto.pp.UnaryNotExpression;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.VerbatimTE;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ClassifierAdapterFactory;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;

/**
 * Evaluates Type of an expression.
 */
@Singleton
public class PPTypeEvaluator {
	public enum PPType {
		/** type can not be determined statically */
		DYNAMIC(null),

		/** expression results in a string that is known to have integer content */
		INTEGER_STRING("String"),

		/** expression results in a string that is known to have float content */
		FLOAT_STRING("String"),

		/** expression results in a numberic value */
		NUMERIC("Numeric"),

		/** expression results in an integer */
		INTEGER("Integer"),

		/** expression results in an float */
		FLOAT("Float"),

		/** expression results in a string that is known to be not numeric */
		STRING("String"),

		/** expression results in a string that is known to be not numeric */
		UNQUOTED_STRING("String"),

		/** expression results in a string that may or may not be numeric */
		DYNAMIC_STRING("String"),

		/** expression results in boolean */
		BOOLEAN("Boolean"),

		/** expression result is a regexp */
		REGEXP("Regexp"),

		/** expression results in an array */
		ARRAY("Array"),

		/** expression results in a hash */
		HASH("Hash"),

		/** expression results in an puppet type */
		TYPE("Type"),

		/** expression results in an class */
		CLASS("Class"),

		/** resource type */
		RESOURCE_TYPE("Resource"),

		/** user defined resource type */
		USER_DEFINED_RESOURCE_TYPE("Resource"),

		/** expression is literal undefined */
		UNDEF("Undef"),

		/** keyword default */
		DEFAULT("Default"),

		/** non value producing expression (procedure, or statement) */
		VOID(null);

		private final String puppetTypeName;

		PPType(String puppetTypeName) {
			this.puppetTypeName = puppetTypeName;
		}

		public String getPuppetTypeName() {
			return puppetTypeName;
		}
	}

	public enum TypeConformant {
		YES, NO, INCONCLUSIVE;

		public boolean maybeConformant(TypeConformant tc) {
			return tc == YES || tc == INCONCLUSIVE;
		}
	}

	private static PPType forClassifierOf(EObject o, PPType defaultType) {
		PPType type = defaultType;
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adaptOrNull(o);
		if(adapter != null)
			switch(adapter.getClassifier()) {
				case ClassifierAdapter.PUPPET_TYPE:
					type = PPType.TYPE;
					break;
				case ClassifierAdapter.CLASS_REFERENCE:
					type = PPType.CLASS;
					break;
				case ClassifierAdapter.RESOURCE_TYPE:
					type = PPType.RESOURCE_TYPE;
					break;
				case ClassifierAdapter.USER_DEFINED_RESOURCE_TYPE:
					type = PPType.USER_DEFINED_RESOURCE_TYPE;
			}
		return type;
	}

	private PolymorphicDispatcher<PPType> typeDispatcher = new PolymorphicDispatcher<PPType>(
		"_type", 1, 1, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<PPType> get()) {
		@Override
		protected PPType handleNoSuchMethod(Object... params) {
			return PPType.VOID;
		}
	};

	protected PPType _type(AdditiveExpression o) {
		return binaryNumericType(o);
	}

	protected PPType _type(AndExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(AssignmentExpression o) {
		return type(o.getRightExpr());
	}

	protected PPType _type(AtExpression o) {
		return o.getLeftExpr() instanceof LiteralNameOrReference
			? forClassifierOf(o.getLeftExpr(), PPType.DYNAMIC)
			: PPType.DYNAMIC;
	}

	protected PPType _type(DoubleQuotedString o) {
		String s = TextExpressionHelper.getNonInterpolated(o);
		if(s != null)
			return getStringType(s);
		// there is interpolation
		// Can check if impossible that it is numeric
		// A simplistic check can replace each interpolated expression with 0 and see if the result is numeric.
		// This leaves a coupel of esotric cases where someone tries to compose a hex or floating point number and
		// the interpolated values are for the 'x' 'X', 'E', or '+'/'-'
		//
		StringBuilder builder = new StringBuilder();
		for(TextExpression te : o.getStringPart()) {
			if(te instanceof VerbatimTE)
				builder.append(((VerbatimTE) te).getText());
			else
				builder.append("0");
		}
		// i.e. either dynamic string that *may* be a number, or a non numeric string otherwise
		return isNumericString(builder.toString())
			? PPType.DYNAMIC_STRING
			: PPType.STRING;
	}

	protected PPType _type(EqualityExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(FunctionCall o) {
		return PPType.DYNAMIC;
	}

	protected PPType _type(LiteralBoolean o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(LiteralDefault o) {
		return PPType.DEFAULT;
	}

	protected PPType _type(LiteralHash o) {
		return PPType.HASH;
	}

	protected PPType _type(LiteralList o) {
		return PPType.ARRAY;
	}

	protected PPType _type(LiteralName o) {
		PPType type = getNumberType(o.getValue());
		if(type == null)
			type = PPType.UNQUOTED_STRING;
		return type;
	}

	protected PPType _type(LiteralNameOrReference o) {
		PPType type = getNumberType(o.getValue());
		if(type == null)
			type = forClassifierOf(o, PPType.UNQUOTED_STRING);
		return type;
	}

	protected PPType _type(LiteralRegex o) {
		return PPType.REGEXP;
	}

	protected PPType _type(LiteralUndef o) {
		return PPType.UNDEF;
	}

	protected PPType _type(MatchingExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(MultiplicativeExpression o) {
		return binaryNumericType(o);
	}

	protected PPType _type(OrExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(ParenthesisedExpression o) {
		return type(o.getExpr());
	}

	protected PPType _type(RelationalExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(SelectorExpression o) {
		return PPType.DYNAMIC;
	}

	protected PPType _type(ShiftExpression o) {
		return binaryNumericType(o);
	}

	protected PPType _type(SingleQuotedString o) {
		return getStringType(o.getText());
	}

	protected PPType _type(UnaryMinusExpression o) {
		PPType exprType = type(o.getExpr());
		if(!(exprType == PPType.INTEGER || exprType == PPType.FLOAT))
			exprType = PPType.NUMERIC;
		return exprType;
	}

	protected PPType _type(UnaryNotExpression o) {
		return PPType.BOOLEAN;
	}

	protected PPType _type(VariableExpression o) {
		return PPType.DYNAMIC;
	}

	protected PPType binaryNumericType(BinaryExpression o) {
		PPType lhsType = type(o.getLeftExpr());
		if(lhsType == PPType.FLOAT)
			// Doesn't matter what rhs is, this is always a FLOAT
			return lhsType;

		if(lhsType == PPType.INTEGER) {
			PPType rhsType = type(o.getRightExpr());
			if(rhsType == PPType.INTEGER || rhsType == PPType.FLOAT)
				return rhsType;
		}

		// No info. Return NUMERIC since this may evaluate to INTEGER
		return PPType.NUMERIC;
	}

	private PPType getNumberType(String s) {
		if(s == null || s.isEmpty())
			return null;
		char c = s.charAt(0);
		if(c == '+' || c == '-' || Character.isDigit(c)) {
			try {
				Long.decode(s);
				return PPType.INTEGER;
			}
			catch(NumberFormatException e) {
				try {
					Double.parseDouble(s);
					return PPType.FLOAT;
				}
				catch(NumberFormatException e2) {
				}
			}
		}
		return null;
	}

	public String getStringConstant(EObject o) {
		if(o instanceof SingleQuotedString)
			return ((SingleQuotedString) o).getText();
		if(o instanceof LiteralNameOrReference)
			return ((LiteralNameOrReference) o).getValue();
		if(o instanceof LiteralName)
			return ((LiteralName) o).getValue();
		if(o instanceof DoubleQuotedString)
			return TextExpressionHelper.getNonInterpolated((DoubleQuotedString) o);
		return null;
	}

	protected PPType getStringType(String s) {
		PPType st = getNumberType(s);
		if(st == null)
			st = PPType.STRING;
		else if(st == PPType.INTEGER)
			st = PPType.INTEGER_STRING;
		else if(st == PPType.FLOAT)
			st = PPType.FLOAT_STRING;
		return st;

	}

	private boolean isNumericString(String s) {
		PPType t = getNumberType(s);
		return t == PPType.INTEGER || t == PPType.FLOAT;
	}

	public TypeConformant numeric(PPType t) {
		switch(t) {
			case NUMERIC:
			case INTEGER:
			case FLOAT:
			case INTEGER_STRING:
			case FLOAT_STRING:
				return TypeConformant.YES;
			case DYNAMIC:
			case DYNAMIC_STRING:
				return TypeConformant.INCONCLUSIVE;
		}
		return TypeConformant.NO;
	}

	public TypeConformant numericType(Object o) {
		return numeric(type(o));
	}

	public PuppetType puppetType(String name, EObject scope) {
		return null;
	}

	public TypeConformant quotedString(PPType t) {
		switch(t) {
			case STRING:
			case DYNAMIC_STRING:
			case INTEGER_STRING:
			case FLOAT_STRING:
				return TypeConformant.YES;
			case DYNAMIC:
				return TypeConformant.INCONCLUSIVE;
		}
		return TypeConformant.NO;
	}

	public PPType type(Object o) {
		if(o == null)
			return PPType.VOID;
		return typeDispatcher.invoke(o);
	}
}
