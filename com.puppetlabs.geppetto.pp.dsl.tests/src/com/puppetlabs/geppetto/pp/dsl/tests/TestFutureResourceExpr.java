/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.tests;

import org.junit.Test;

import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;

public class TestFutureResourceExpr extends AbstractPuppetTests {
	@Override
	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_4_0;
	}

	@Test
	public void test_ValidateExpressionTitles_Ok_Default() {
		subTestValidateExpressionTitles(pf.createLiteralDefault());
	}
}
