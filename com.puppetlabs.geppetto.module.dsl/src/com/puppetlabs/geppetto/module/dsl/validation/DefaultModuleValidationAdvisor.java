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
package com.puppetlabs.geppetto.module.dsl.validation;

import java.io.Serializable;

import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

public class DefaultModuleValidationAdvisor implements IModuleValidationAdvisor, Serializable {
	private static final long serialVersionUID = 1L;

	public static final DefaultModuleValidationAdvisor INSTANCE = new DefaultModuleValidationAdvisor();

	@Override
	public ValidationPreference getCircularDependency() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDependencyVersionMismatch() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDeprecatedKey() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getMissingForgeRequiredFields() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getModuleClassNotInInitPP() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getModulefileExists() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getModulefileExistsAndIsUsed() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getModuleNameNotStrict() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getModuleRedefinition() {
		return ValidationPreference.ERROR;
	}

	@Override
	public ValidationPreference getUnexpectedSubmodule() {
		return ValidationPreference.ERROR;
	}

	@Override
	public ValidationPreference getUnrecognizedKey() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getUnresolvedReference() {
		return ValidationPreference.ERROR;
	}

	@Override
	public ValidationPreference getWhitespaceInTag() {
		return ValidationPreference.WARNING;
	}
}
