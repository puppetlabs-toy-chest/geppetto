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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.HashEntry;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator.PPType;
import com.puppetlabs.geppetto.pp.dsl.linking.IMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.pptp.PptpPrinter;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

/**
 * Context used when validating an expression against a type.
 */
public interface ITypeValidationContext {
	/**
	 * @return the acceptor used for diagnostic messages
	 */
	IMessageAcceptor acceptor();

	/**
	 * @return The container of validation advices.
	 */
	IValidationAdvisor advisor();

	boolean checkExpression(PuppetTypeParameter param, String type, List<ParameterValue> parameters, Expression expr,
			IMessageAcceptor acceptor);

	/**
	 * @return The description of the object referenced by the expression or <code>null</code> if not applicable.
	 */
	IEObjectDescription getReferencedObject();

	/**
	 * @return The resolved object referenced by the expression or <code>null</code> if not applicable.
	 */
	EObject getReferencedTypeOrClass();

	/**
	 * Attempts to extract the string that the expression represents. This method will return <code>null</code> unless
	 * the <code>expression</code> represent a String constant. Literals like <code>undef</code>, <code>default</code>, <code>true</code> or
	 * <code>false</code> will yield a <code>null</code> return.
	 *
	 * @param expression
	 *            The expression that might represent a constant string.
	 * @return The constant expression represented as a String or <code>null</code> if not applicable.
	 */
	String getStringConstant(Expression expression);

	/**
	 * Returns the type validator for the given <code>typeName</code>.
	 *
	 * @param typeName
	 *            The name for which we want a type validator.
	 * @return The type validator instance or <code>null</code> when no instance is found.
	 */
	ITypeValidator getTypeValidator(String typeName);

	/**
	 * @return the PP-type of the contained expression
	 * @see PPTypeEvaluator
	 */
	PPType ppType();

	/**
	 * @param expression
	 *            The expression to classify
	 * @return the PP-type of the provided <code>expression</code>
	 * @see PPTypeEvaluator
	 */
	PPType ppType(Expression expression);

	/**
	 * @return The puppet type of the contained expression.
	 */
	PuppetType type();

	/**
	 * @return A printer that can serialize puppet types and parameters onto a {@link StringBuilder}.
	 */
	PptpPrinter typePrinter();

	/**
	 * @return The puppet type of the contained expression.
	 */
	TypeValue typeValue();

	/**
	 * Will do nothing, emit a warning, or emit an error that validity cannot be asserted until runtime using the {@link #acceptor()}
	 * depending on the {@link IValidationAdvisor#getValidityAssertedAtRuntime()} preference obtained from the {@link #advisor()}.
	 */
	void validityAssertedAtRuntime();

	/**
	 * @return The expression to be validated.
	 */
	Expression value();

	/**
	 * The name of the value (if passed in a {@link HashEntry}
	 *
	 * @return The expression representing the name of the value or <code>null</code> if not applicable.
	 */
	Expression valueName();
}
