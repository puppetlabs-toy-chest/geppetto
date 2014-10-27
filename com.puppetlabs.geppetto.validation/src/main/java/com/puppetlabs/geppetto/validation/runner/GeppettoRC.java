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
package com.puppetlabs.geppetto.validation.runner;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleValidationAdvisorBean;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.PotentialProblemsAdvisorBean;

/**
 * Class that represents the geppetto-rc.json file
 */
public class GeppettoRC {
	public static class Advices {
		private final PotentialProblemsAdvisorBean manifest;

		private final ModuleValidationAdvisorBean module;

		@JsonCreator
		public Advices(@JsonProperty("manifest") PotentialProblemsAdvisorBean manifest,
				@JsonProperty("module") ModuleValidationAdvisorBean module) {
			this.manifest = manifest;
			this.module = module;
		}

		/**
		 * @return the manifest problems advisor
		 */
		public PotentialProblemsAdvisorBean getManifest() {
			return manifest;
		}

		/**
		 * @return the module validation advisor
		 */
		public ModuleValidationAdvisorBean getModule() {
			return module;
		}
	}

	private final Set<String> excludes;

	private final ComplianceLevel complianceLevel;

	private final Advices advice;

	/**
	 * @param excludes
	 *            glob patterns for file/folder exclusions
	 * @param complianceLevel
	 *            Puppet version
	 * @param advice
	 *            Validation advices
	 * @throws IllegalArgumentException
	 */
	@JsonCreator
	public GeppettoRC(@JsonProperty("excludes") Set<String> excludes, @JsonProperty("compliance_level") ComplianceLevel complianceLevel,
			@JsonProperty("advice") Advices advice) {
		this.excludes = excludes;
		this.complianceLevel = complianceLevel;
		this.advice = advice;
	}

	/**
	 * @return the advice
	 */
	public Advices getAdvice() {
		return advice;
	}

	/**
	 * @return the complianceLevel
	 */
	public ComplianceLevel getComplianceLevel() {
		return complianceLevel;
	}

	/**
	 * @return the excludes
	 */
	public Set<String> getExcludes() {
		return excludes == null
			? Collections.<String> emptySet()
			: Collections.unmodifiableSet(excludes);
	}
}
