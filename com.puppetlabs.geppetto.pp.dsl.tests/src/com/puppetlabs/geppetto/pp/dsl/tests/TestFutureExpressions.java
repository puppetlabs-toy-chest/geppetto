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

import com.puppetlabs.geppetto.pp.AppendExpression;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.LiteralBoolean;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.MatchingExpression;
import com.puppetlabs.geppetto.pp.NodeDefinition;
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

	@SuppressWarnings("serial")
	@Override
	protected IPotentialProblemsAdvisor getPotentialProblemsAdvisor() {
		return new DefaultPotentialProblemsAdvisor() {
			@Override
			public ValidationPreference getValidityAssertedAtRuntime() {
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

	/**
	 * Test that append is an error.
	 * - $x += expr
	 */
	@Test
	public void test_Validate_AppendExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__DEPRECATED_PLUS_EQUALS);
	}

	/**
	 * Tests that chained assignemt is ok
	 * - $x = $y = expr
	 */
	@Test
	public void test_Validate_AssignmentExpression_NotOk_Chained() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ax = pf.createAssignmentExpression();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ax.setLeftExpr(v);

		AssignmentExpression ay = pf.createAssignmentExpression();
		VariableExpression y = pf.createVariableExpression();
		y.setVarName("$y");
		ay.setLeftExpr(y);

		LiteralBoolean b = pf.createLiteralBoolean();
		ay.setRightExpr(b);
		ax.setRightExpr(ay);
		pp.getStatements().add(ax);

		tester.validate(pp).assertOK();
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

	@Test
	public void test_Validate_NodeDefinitionExpression_NotOk_Inheritance() {
		PuppetManifest pp = pf.createPuppetManifest();
		NodeDefinition nd = pf.createNodeDefinition();
		nd.getHostNames().add(createNameOrReference("common"));
		nd.setParentName(pf.createLiteralDefault());
		pp.getStatements().add(nd);
		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__DEPRECATED_NODE_INHERITANCE);
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

	@Test
	public void test_Validate_VariableExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(v);

		v.setVarName("Abc");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		v.setVarName("3b");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		v.setVarName("01");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		v.setVarName("foo::bar::Fee");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);
	}
}
