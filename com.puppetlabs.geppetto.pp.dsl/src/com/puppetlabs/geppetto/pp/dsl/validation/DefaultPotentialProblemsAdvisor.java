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
package com.puppetlabs.geppetto.pp.dsl.validation;

import java.io.Serializable;

/**
 * A default implementation of IPotentialProblemsAdvisor that returns Warnings for all potential problems, and
 * Ignore for all stylistic problems
 */
public class DefaultPotentialProblemsAdvisor implements IPotentialProblemsAdvisor, IStylisticProblemsAdvisor, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ValidationPreference getAssignmentToVarNamedString() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getAssignmentToVarNamedTrusted() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getAttributeIsNotString() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getBooleansInStringForm() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getCaseDefaultShouldAppearLast() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getDeprecatedImport() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDeprecatedNodeInheritance() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDeprecatedPlusEquals() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDeprecatedVariableName() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getDqStringNotRequired() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getDqStringNotRequiredVariable() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getEnsureShouldAppearFirstInResource() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getInterpolatedNonBraceEnclosedHyphens() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getMissingDefaultInSelector() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference getMlComments() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getRightToLeftRelationships() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getSelectorDefaultShouldAppearLast() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getUnbracedInterpolation() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getUnquotedResourceTitles() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference getValidityAssertedAtRuntime() {
		return ValidationPreference.IGNORE;
	}

}
