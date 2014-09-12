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

import static org.eclipse.xtext.junit4.validation.AssertableDiagnostics.errorCode;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.HashEntry;
import com.puppetlabs.geppetto.pp.LiteralHash;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;

public class TestFutureResourceExpr extends AbstractPuppetTests {
	protected LiteralHash createHash(String... keyValuePairs) {
		int len = keyValuePairs.length;
		assertTrue("Key value pair array must have even number of elements", len % 2 == 0);
		LiteralHash hash = pf.createLiteralHash();
		List<HashEntry> elems = hash.getElements();
		for(int idx = 0; idx < len;) {
			HashEntry he = pf.createHashEntry();
			he.setKey(createNameOrReference(keyValuePairs[idx++]));
			he.setValue(createValue(keyValuePairs[idx++]));
			elems.add(he);
		}
		return hash;
	}

	@Override
	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_4_0;
	}

	@Test
	public void test_Validate_RegularResourceNotOk_SplashDuplicate() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "*", createHash("group", "0666", "other", "0555"), "*",
			createHash("group", "0666", "other", "0555"));
		statements.add(re);

		// Will occur twice. Once on each duplicate.
		tester.validate(pp).assertAll(
			errorCode(IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_ATTRIBUTE), errorCode(IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_ATTRIBUTE));
	}

	@Test
	public void test_Validate_RegularResourceNotOk_SplashNotHash() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression("file", "a resource", "owner", createValue("0777"), "*", createValue("0555"));
		statements.add(re);
		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__SPLASH_VALUE_MUST_BE_HASH);
	}

	@Test
	public void test_Validate_RegularResourceOk_Splash() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "*", createHash("group", "0666", "other", "0555"));
		statements.add(re);
		tester.validate(pp).assertOK();
	}

	@Test
	public void test_Validate_RegularResourceOk_SplashVariable() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression("file", "a resource", "owner", createValue("0777"), "*", createVariable("h"));
		statements.add(re);
		tester.validate(pp).assertOK();
	}
}
