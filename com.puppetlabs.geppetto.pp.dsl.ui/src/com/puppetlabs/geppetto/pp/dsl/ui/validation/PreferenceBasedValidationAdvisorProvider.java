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
import com.google.inject.Provider;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelperProvider;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;

/**
 * A provider of validation advisor controlled by user preferences.
 *
 */
public class PreferenceBasedValidationAdvisorProvider<T extends IValidationAdvisor> implements
Provider<IValidationAdvisor> {
	public static <T extends IValidationAdvisor> PreferenceBasedValidationAdvisorProvider<T> create() {
		return new PreferenceBasedValidationAdvisorProvider<T>();
	}

	@Inject
	private PPPreferencesHelperProvider preferencesProvider;

	@Inject
	private Provider<IPotentialProblemsAdvisor> potentialProblemsAdvisorProvider;

	@Override
	public IValidationAdvisor get() {
		return preferencesProvider.get().getValidationComplianceLevel().createValidationAdvisor(
			potentialProblemsAdvisorProvider.get());
	}
}
