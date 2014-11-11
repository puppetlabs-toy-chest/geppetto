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

import com.google.inject.Provider;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisorWrapper;

/**
 * A parameterized provider of validation advisor.
 */
public class ValidationAdvisorProvider implements Provider<IValidationAdvisor> {
	private ComplianceLevel level;

	private IPotentialProblemsAdvisor problemsAdvisor;

	private final ValidationAdvisorWrapper instance;

	public ValidationAdvisorProvider() {
		this.level = PuppetTarget.getDefault().getComplianceLevel();
		this.problemsAdvisor = DefaultPotentialProblemsAdvisor.INSTANCE;
		this.instance = new ValidationAdvisorWrapper(level.createValidationAdvisor(problemsAdvisor));
	}

	/**
	 * Provides an IValidatioNAdvisor configured from the ComplianceLevel and (optional) ProblemsAdvisor
	 * If the problems advisor is null, an instance of {@link DefaultPotentialProblemsAdvisor} is used.
	 */
	@Override
	public synchronized IValidationAdvisor get() {
		return instance;
	}

	private void reset() {
		instance.setProblemsAdvisor(level.createValidationAdvisor(problemsAdvisor));
	}

	public synchronized void setComplianceLevel(ComplianceLevel level) {
		if(this.level != level) {
			this.level = level;
			reset();
		}
	}

	public synchronized void setProblemsAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
		if(this.problemsAdvisor != problemsAdvisor) {
			this.problemsAdvisor = problemsAdvisor;
			reset();
		}
	}
}
