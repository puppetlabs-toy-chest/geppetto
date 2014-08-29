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
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.inject.multibindings.MapBinder;
import com.puppetlabs.geppetto.pp.pptp.ParameterValue;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;

/**
 * Implemeted by all types in the built-in type system. Designed to allow future user defined
 * types.
 * It's recommended that implementations are based on the {@link AbstractTypeValidator}. They must
 * have a constructor with the following syntax:
 *
 * <pre>
 * &#064;Inject
 * public OptionalValidator(@Assisted IEObjectDescription puppetTypeDesc) {
 * 	super(puppetTypeDesc);
 * }
 * </pre>
 *
 * An implementation must be registered with a {@link MapBinder MapBinder&lt;String,ITypeValidatorFactory&gt;} and have a corresponding
 * {@link PuppetType} declaration present in the target platform (in a .pptp file). The name of the type must start with a lowercase
 * letter.
 *
 * @see BuiltinTypesModule
 */
public interface ITypeValidator {
	/**
	 * Returns the {@link PuppetType} instance resolved using the provided <code>scope</code>.
	 *
	 * @param objectContext
	 *            The scope used when resolving a ecore proxy.
	 * @return The resolved type.
	 * @see EcoreUtil#resolve(EObject, EObject)
	 */
	PuppetType getType(EObject objectContext);

	/**
	 * @return the name of the type with initial lowercase.
	 */
	String getTypeName();

	/**
	 * Valitates the value contained in the <code>ctx</code> using the contained type constraint. The method will return <code>true</code>
	 * if the type of the provided value cannot be infered.
	 *
	 * @param param
	 *            A parameter from an owner type that contains the same type as this instance. This is used for
	 *            diagnostic messages during validation of parameterized types and can be <code>null</code>.
	 * @param declArgs
	 *            Arguments for the parameters of the contained type. Can be <code>null</code>.
	 * @param ctx
	 *            The context to use fo the validation. The context contains the actual value that should be validated
	 *            along with an acceptor for diagnostic messages and access to other type validators.
	 * @return <code>true</code> unless the type could be validated and the validation failed.
	 */
	boolean validate(PuppetTypeParameter param, List<ParameterValue> declArgs, ITypeValidationContext ctx);
}
