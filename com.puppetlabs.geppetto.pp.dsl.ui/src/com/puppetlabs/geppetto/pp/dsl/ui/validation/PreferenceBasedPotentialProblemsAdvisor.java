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
package com.puppetlabs.geppetto.pp.dsl.ui.validation;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

/**
 * A potential problems advisor based on preference settings.
 * Note, that the preferences are read when this advisor is instantiated. Get a new instance when a preference has
 * changed.
 */
public class PreferenceBasedPotentialProblemsAdvisor implements IPotentialProblemsAdvisor {

	private PPPreferencesHelper preferences;

	@Inject
	public PreferenceBasedPotentialProblemsAdvisor(PPPreferencesHelper preferences) {
		this.preferences = preferences;
	}

	@Override
	public ValidationPreference getAssignmentToVarNamedString() {
		return preferences.getAssignmentToVariableNamedString();
	}

	@Override
	public ValidationPreference getAssignmentToVarNamedTrusted() {
		return preferences.getAssignmentToVariableNamedTrusted();
	}

	@Override
	public ValidationPreference getAttributeIsNotString() {
		return preferences.getAttributeIsNotString();
	}

	@Override
	public ValidationPreference getBooleansInStringForm() {
		return preferences.getBooleansInStringForm();
	}

	@Override
	public ValidationPreference getCaseDefaultShouldAppearLast() {
		return preferences.getCaseDefaultShouldAppearLast();
	}

	@Override
	public ValidationPreference getDeprecatedImport() {
		return preferences.getDeprecatedImport();
	}

	@Override
	public ValidationPreference getDeprecatedNodeInheritance() {
		return preferences.getDeprecatedNodeInheritance();
	}

	@Override
	public ValidationPreference getDeprecatedPlusEquals() {
		return preferences.getDeprecatedPlusEquals();
	}

	@Override
	public ValidationPreference getDeprecatedVariableName() {
		return preferences.getDeprecatedVariableName();
	}

	@Override
	public ValidationPreference getDqStringNotRequired() {
		return preferences.getDqStringNotRequired();
	}

	@Override
	public ValidationPreference getDqStringNotRequiredVariable() {
		return preferences.getDqStringNotRequiredVar();
	}

	/**
	 * @see com.puppetlabs.geppetto.pp.dsl.validation.IStylisticProblemsAdvisor#getEnsureShouldAppearFirstInResource()
	 */
	@Override
	public ValidationPreference getEnsureShouldAppearFirstInResource() {
		return preferences.getEnsureShouldAppearFirst();
	}

	@Override
	public ValidationPreference getInterpolatedNonBraceEnclosedHyphens() {
		return preferences.getInterpolatedNonBraceEnclosedHypens();
	}

	@Override
	public ValidationPreference getMissingDefaultInSelector() {
		return preferences.getMissingDefaultInSwitch();
	}

	@Override
	public ValidationPreference getMlComments() {
		return preferences.getMLCommentsValidationPreference();
	}

	@Override
	public ValidationPreference getRightToLeftRelationships() {
		return preferences.getRightToLeftRelationships();
	}

	@Override
	public ValidationPreference getSelectorDefaultShouldAppearLast() {
		return preferences.getSelectorDefaultShouldAppearLast();
	}

	@Override
	public ValidationPreference getUnbracedInterpolation() {
		return preferences.getUnbracedInterpolation();
	}

	@Override
	public ValidationPreference getUnquotedResourceTitles() {
		return preferences.getUnquotedResourceTitles();
	}

	@Override
	public ValidationPreference getValidityAssertedAtRuntime() {
		return preferences.getValidityAssertedAtRuntime();
	}

}
