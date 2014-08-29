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

import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.MatchingExpression;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

/**
 * Expression tests using PUPPET_FUTURE as compliance level.
 */
public class TestFutureExpressions extends AbstractPuppetTests {
	@Override
	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_4_0;
	}

	@Override
	protected IPotentialProblemsAdvisor getPotentialProblemsAdvisor() {
		return new DefaultPotentialProblemsAdvisor() {
			@Override
			public ValidationPreference validityAssertedAtRuntime() {
				return ValidationPreference.WARNING;
			}
			// TODO: Add more
		};
	}

	@Test
	public void test_Parse_MatchingExpression() throws Exception {
		String code = "$::a =~ Array[Integer]\n";
		Resource r = loadAndLinkSingleResource(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}

	@Test
	public void test_Validate_MatchingWithRHSName_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		VariableExpression v = pf.createVariableExpression();
		SingleQuotedString s = createSqString("a sq string");
		v.setVarName("$x");
		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(s);
		pp.getStatements().add(me);

		tester.validate(me).assertOK();
	}

	@Test
	public void test_Validate_MatchingWithRHSString_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		LiteralNameOrReference n = createNameOrReference("Array");
		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(n);
		pp.getStatements().add(me);

		tester.validate(me).assertOK();
	}

	@Test
	public void test_Validate_MatchingWithRHSVariable_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		VariableExpression v2 = pf.createVariableExpression();
		v2.setVarName("$y");
		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(v2);
		pp.getStatements().add(me);

		tester.validate(me).assertWarning(IPPDiagnostics.ISSUE__VALIDITY_ASSERTED_AT_RUNTIME);
	}

	/**
	 * Check that an non top-level expression declared last is OK even if it is
	 * at top level.
	 */
	@Test
	public void test_Validate_NotTopLevelOkIfLast() {
		PuppetManifest pp = pf.createPuppetManifest();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(v);
		v.setVarName("$x");
		tester.validate(pp).assertOK();
	}
}
