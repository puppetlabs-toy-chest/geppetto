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
package com.puppetlabs.geppetto.pp.dsl.tests;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

/**
 * Tests specific to reported issues using Puppet 3.5 and 3.5 validation.
 */
public class TestIssues3_5 extends AbstractPuppetTests {

	@Override
	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_3_5;
	}

	@Override
	protected IPotentialProblemsAdvisor getPotentialProblemsAdvisor() {
		return new DefaultPotentialProblemsAdvisor() {
			@Override
			public ValidationPreference getDeprecatedImport() {
				return ValidationPreference.WARNING;
			}
			// TODO: Add more
		};
	}

	@Test
	public void test_deprecatedImport() throws Exception {
		String code = "import \"foo\"\n";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertWarning(IPPDiagnostics.ISSUE__DEPRECATED_IMPORT);
	}
}
