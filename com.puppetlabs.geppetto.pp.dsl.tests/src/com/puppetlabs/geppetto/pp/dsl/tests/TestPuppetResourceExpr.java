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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Test;

import com.puppetlabs.geppetto.pp.AdditiveExpression;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.LiteralList;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.PPFactory;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.SelectorEntry;
import com.puppetlabs.geppetto.pp.SelectorExpression;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;

/**
 * Tests Puppet ResourceExpression
 * - regular ResourceExpression
 * - VirtualResourceExpression
 * ResourceExpression used as:
 * - default resource
 * - resource override
 * Tests serialization and validation.
 */
public class TestPuppetResourceExpr extends AbstractPuppetTests {
	public static final String NOTITLE = null;

	// IMPORTANT - MAKE SURE THESE ARE NOT SCREWED UP ON CHECKIN - MAKES IT VERY DIFFICULT TO READ
	// @formatter:off
	static final String Sample_ResourceOneAttribute = "file { 'a resource':\n" + //
		"  owner => 'fred',\n" + //
		"}\n";

	static final String Sample_ResourceNoAttributes = "class { 'myclass':\n" + //
		"}\n";

	static final String Sample_TwoResources = "file { 'r1':\n" + //
		"  owner => 'fred',\n" + //
		"}\n\n" + //
		"file { 'r2':\n" + //
		"  owner => 'fred',\n" + //
		"}\n";

	static final String Sample_VirtualResource = "@file { 'a resource':\n" + //
		"  owner => 'fred',\n" + //
		"}\n";

	static final String Sample_ResourceWithRequire = "file { 'x':\n" + //
		"  require => Package['a', 'b', 'c'],\n" + //
		"}\n";

	static final String Sample_VirtualResourceExported = "@@file { 'a resource':\n" + //
		"  owner => 'fred',\n" + //
		"}\n";

	static final String Sample_ResourceOneAddAttribute = "File { 'a resource':\n" + //
		"  owner +> 'fred',\n" //
		+ "}\n";

	static final String Sample_DefaultResource = "File {\n" + //
		"  owner => 0777,\n" + //
		"  group => 0666,\n" + //
		"  other => 0555,\n" + //
		"}\n";

	static final String Sample_ResourceMAttributes = "file { 'a resource':\n" + //
		"  owner => 0777,\n" + //
		"  group => 0666,\n" + //
		"  other => 0555,\n" + //
		"}\n";

	static final String Sample_MResourcesMAttributes = "file {\n" + //
		"  'a resource':\n" + //
		"    owner => 0777,\n" + //
		"    group => 0666,\n" + //
		"    other => 0555;\n" + //
		"\n" + //
		"  'another resource':\n" + //
		"    a   => 1,\n" + //
		"    bb  => 2,\n" + //
		"    ccc => 3;\n" + //
		"}\n";

	// @formatter:on

	@Override
	protected boolean shouldTestSerializer(XtextResource resource) {
		// Do not test the serializer the way the JUnit test does it - it will barf on
		// magic insertion of text not in the node model.
		return false;
	}

	@Test
	public void test_Serialize_1() throws Exception {
		String code = "file { 'afile': owner => 'foo'}";
		String fmt = "file { 'afile':\n  owner => 'foo'\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);

	}

	@Test
	public void test_Serialize_assignArray() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		AssignmentExpression assignment = PPFactory.eINSTANCE.createAssignmentExpression();
		assignment.setLeftExpr(createVariable("a"));
		LiteralList pplist = PPFactory.eINSTANCE.createLiteralList();
		assignment.setRightExpr(pplist);
		pplist.getElements().add(createSqString("10"));
		pplist.getElements().add(createSqString("20"));
		statements.add(assignment);
		String fmt = "$a = ['10', '20',]\n";
		String s = serialize(pp);
		assertEquals("serialization should produce same result", fmt, s);
	}

	@Test
	public void test_Serialize_DefaultResource() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		statements.add(createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555")));
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_DefaultResource, s);
	}

	@Test
	public void test_Serialize_fromModel() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		VariableExpression var = pf.createVariableExpression();
		var.setVarName("$a");
		statements.add(var);

		String s = serialize(pp);
		assertEquals("serialization should produce same result", "$a\n", s);
	}

	@Test
	public void test_Serialize_MResourcesMAttributes() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(
			createResourceBody("another resource", "a", createValue("1"), "bb", createValue("2"), "ccc", createValue("3")));
		statements.add(re);
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_MResourcesMAttributes, s);
	}

	@Test
	public void test_Serialize_ResourceMAttributes() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		statements.add(createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555")));
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceMAttributes, s);
	}

	/**
	 * Test serialization of Resource with one attribute
	 * - attribute definition
	 * - attribute additions
	 *
	 * @throws Exception
	 */
	@Test
	public void test_Serialize_ResourceNoAttributes() throws Exception {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		statements.add(createResourceExpression("class", "myclass"));
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceNoAttributes, s);

	}

	/**
	 * Test serialization of Resource with one attribute
	 * - attribute definition
	 * - attribute additions
	 *
	 * @throws Exception
	 */
	@Test
	public void test_Serialize_ResourceOneAttribute() throws Exception {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		statements.add(createResourceExpression("file", "a resource", "owner", "fred"));
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceOneAttribute, s);

		// --with attribute addition
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		statements.add(createResourceExpression(true, "File", "a resource", "owner", "fred"));
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceOneAddAttribute, s);
	}

	@Test
	public void test_Serialize_ResourceWithRequires() {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		LiteralNameOrReference reqType = pf.createLiteralNameOrReference();
		reqType.setValue("Package");
		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(reqType);
		at.getParameters().add(createSqString("a"));
		at.getParameters().add(createSqString("b"));
		at.getParameters().add(createSqString("c"));

		ResourceExpression vr = createResourceExpression("file", "x", "require", at);
		statements.add(vr);
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceWithRequire, s);

	}

	@Test
	public void test_Serialize_TwoResources() throws Exception {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		statements.add(createResourceExpression("file", "r1", "owner", "fred"));
		statements.add(createResourceExpression("file", "r2", "owner", "fred"));
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_TwoResources, s);

		// --with attribute addition
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		statements.add(createResourceExpression(true, "File", "a resource", "owner", "fred"));
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ResourceOneAddAttribute, s);
	}

	@Test
	public void test_Serialize_VirtualResource() {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression vr = createVirtualResourceExpression("file", "a resource", "owner", "fred");
		statements.add(vr);
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_VirtualResource, s);

		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		vr = createVirtualExportedResourceExpression("file", "a resource", "owner", "fred");
		statements.add(vr);
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_VirtualResourceExported, s);
	}

	@Test
	public void test_SmokeTest_Simple() throws Exception {
		String code = "file { 'afile': owner => 'foo'}";
		EObject m = getModel(code);
		assertTrue("Should have been a PuppetManifest", m instanceof PuppetManifest);
	}

	@Test
	public void test_Valdate_UnknownProperty() throws Exception {
		String code = "file { 'afile': donor => 'A donor'}";
		XtextResource r = getResourceFromStringAndExpect(code, UNKNOWN_EXPECTATION);
		resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY_PROP);

	}

	/**
	 * Test a DefaultResource's invalid states:
	 * - multiple bodies
	 * - bodies with titles
	 */
	@Test
	public void test_Validate_DefaultResourceNotOk() {

		// -- multiple bodies
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		ResourceExpression re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody(null, "a", "1", "b", "2", "c", "3"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_MULTIPLE_BODIES);

		// -- bodies with titles
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"File", "title in error", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody(null, "a", "1", "b", "2", "c", "3"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertAll(
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__RESOURCE_WITH_TITLE),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__RESOURCE_MULTIPLE_BODIES));
	}

	@Test
	public void test_Validate_DefaultResourceOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		ResourceExpression re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();
	}

	/**
	 * Validate Resource Override not ok states:
	 * - multiple bodies
	 * - bodies with titles
	 * - at expression with leftexpr not being a NameOrReference
	 * - at expression with no parameters
	 * - at expression left is name == warning
	 */
	@Test
	public void test_Validate_Override_NotOk() {
		// -- states where the at expression is ok

		// -- multiple bodies
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		ResourceExpression re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody(null, "a", "1", "b", "2", "c", "3"));
		// swap the created LiteralNameOrReference for an AtExpression
		AtExpression reRef = pf.createAtExpression();
		reRef.setLeftExpr(re.getResourceExpr());
		re.setResourceExpr(reRef);
		// add a parameter (use of a default here is just because it requires no further value setting :)
		reRef.getParameters().add(pf.createLiteralDefault());

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_MULTIPLE_BODIES);

		// -- bodies with titles
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"File", "title in error", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody(null, "a", "1", "b", "2", "c", "3"));
		// swap the created LiteralNameOrReference for an AtExpression
		reRef = pf.createAtExpression();
		reRef.setLeftExpr(re.getResourceExpr());
		re.setResourceExpr(reRef);
		// add a parameter (use of a default here is just because it requires no further value setting :)
		reRef.getParameters().add(pf.createLiteralDefault());

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertAll(
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__RESOURCE_WITH_TITLE),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__RESOURCE_MULTIPLE_BODIES));

		// -- states where the at expression is wrong

		// -- at expression has no parameters
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		// swap the created LiteralNameOrReference for an AtExpression
		reRef = pf.createAtExpression();
		reRef.setLeftExpr(re.getResourceExpr());
		re.setResourceExpr(reRef);

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.validator().checkAtExpression(reRef);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_REFERENCE_NO_PARAMETERS);

		// -- at expression left is not a NameOrReference
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		// swap the created LiteralNameOrReference for an AtExpression with faulty leftExpr
		reRef = pf.createAtExpression();
		LiteralNameOrReference noRef = pf.createLiteralNameOrReference();
		noRef.setValue("::foo");
		reRef.setLeftExpr(noRef);
		re.setResourceExpr(reRef);
		// add a parameter (use of a default here is just because it requires no further value setting :)
		reRef.getParameters().add(pf.createLiteralDefault());

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.validator().checkAtExpression(reRef);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_CLASSREF);

		// -- at expression left is null
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		// swap the created LiteralNameOrReference for an AtExpression with faulty leftExpr
		reRef = pf.createAtExpression();
		reRef.setLeftExpr(null);
		re.setResourceExpr(reRef);
		// add a parameter (use of a default here is just because it requires no further value setting :)
		reRef.getParameters().add(pf.createLiteralDefault());

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.validator().checkAtExpression(reRef);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_REFERENCE_NO_REFERENCE);

	}

	/**
	 * Validate Resource Override ok states:
	 * - single untitled body
	 * - at expression with NameOrReference as left expression
	 * - at least one parameter in the at expression
	 */
	@Test
	public void test_Validate_Override_ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		// swap the created LiteralNameOrReference for an AtExpression
		AtExpression reRef = pf.createAtExpression();
		reRef.setLeftExpr(re.getResourceExpr());
		re.setResourceExpr(reRef);
		// add a parameter (use of a default here is just because it requires no further value setting :)
		reRef.getParameters().add(pf.createLiteralDefault());

		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

		// add a second parameter and revalidate
		reRef.getParameters().add(pf.createLiteralDefault());
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

	}

	/**
	 * Validate RegularResource non ok states:
	 * - missing title
	 * - additive attributes
	 */
	@Test
	public void test_Validate_RegularResourceNotOk() {

		// -- missing title
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"file", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_WITHOUT_TITLE);

		// -- additive statements
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			true, "file", "title", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		// error will occur as many times as there are additions,so check using "any"
		tester.diagnose().assertAny(AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__RESOURCE_WITH_ADDITIONS));

		// -- non conforming resource expression
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			false, "file", "title", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		re.setResourceExpr(pf.createLiteralDefault());
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_BAD_TYPE_FORMAT);

	}

	/**
	 * Test Regular Resource ok states:
	 * - Resource with some attributes
	 * - Resource with empty body
	 * - Resource with multiple bodies
	 * - Resource with multiple bodies - one being empty
	 */
	@Test
	public void test_Validate_RegularResourceOk() {

		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

		// --Resource with empty body
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression("file", "a resource");
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

		// --Resource with multiple bodies
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody("another resource", "a", "1", "b", "2", "c", "3"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

		// --Resource with multiple bodies, one (in the middle) being empty
		pp = pf.createPuppetManifest();
		statements = pp.getStatements();
		re = createResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		re.getResourceData().add(createResourceBody("another resource1"));
		re.getResourceData().add(createResourceBody("another resource2", "a", "1", "b", "2", "c", "3"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();

	}

	@Test
	public void test_Validate_ResourceWithRequires() {
		// --with attribute definition
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		LiteralNameOrReference reqType = pf.createLiteralNameOrReference();
		reqType.setValue("Package");
		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(reqType);
		at.getParameters().add(createNameOrReference("a"));
		at.getParameters().add(createNameOrReference("b"));
		at.getParameters().add(createNameOrReference("c"));

		ResourceExpression re = createResourceExpression("file", "x", "require", at);
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.validator().checkAtExpression(at);
		tester.diagnose().assertOK();

	}

	@Test
	public void test_Validate_VirtualResource_NotOk() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createVirtualResourceExpression(
			"File", NOTITLE, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_NOT_VIRTUALIZEABLE);
	}

	@Test
	public void test_Validate_VirtualResource_Ok() {
		// -- Resource with a couple of attribute definitions
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createVirtualResourceExpression(
			"file", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.diagnose().assertOK();
	}

	@Test
	public void test_ValidateComplexName() {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();
		ResourceExpression re = createVirtualResourceExpression(
			"monitor::foo", "a resource", "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		// should only report unknown type - but the spec of this unknown type should be allowed
		// this is now done in linking, so this error does not show
		// tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
		tester.diagnose().assertOK();

	}

	@Test
	public void test_ValidateExpressionTitles_NotOk() {
		// just testing one
		{ // -- literal string
			AdditiveExpression titleExpr = pf.createAdditiveExpression();
			titleExpr.setLeftExpr(createVariable("a"));
			titleExpr.setRightExpr(createVariable("b"));
			PuppetManifest pp = pf.createPuppetManifest();
			EList<Expression> statements = pp.getStatements();

			ResourceExpression re = createVirtualResourceExpression(
				"monitor::foo", titleExpr, "owner", createValue("0777"), "group", createValue("0666"), "other", createValue("0555"));
			statements.add(re);
			tester.validator().checkResourceExpression(re);
			tester.validator().checkResourceBody(re.getResourceData().get(0));
			tester.diagnose().assertAny(AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION_STRING_OK));
		}
	}

	@Test
	public void test_ValidateExpressionTitles_Ok() {

		{ // -- literal string
			SingleQuotedString titleExpr = pf.createSingleQuotedString();
			titleExpr.setText("test");
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
		{ // -- name
			LiteralNameOrReference titleExpr = pf.createLiteralNameOrReference();
			titleExpr.setValue("test");
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
		{ // -- variable
			VariableExpression titleExpr = pf.createVariableExpression();
			titleExpr.setVarName("$test");
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
		{ // -- literal list
			LiteralList titleExpr = pf.createLiteralList();
			titleExpr.getElements().add(this.createNameOrReference("a"));
			titleExpr.getElements().add(this.createNameOrReference("b"));
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
		{ // -- hasharray access
			AtExpression titleExpr = pf.createAtExpression();
			titleExpr.setLeftExpr(createNameOrReference("Foo"));
			titleExpr.getParameters().add(createNameOrReference("a"));
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
		{ // -- selector
			SelectorExpression titleExpr = pf.createSelectorExpression();
			SelectorEntry entry = pf.createSelectorEntry();
			titleExpr.getParameters().add(entry);

			SingleQuotedString slhs = pf.createSingleQuotedString();
			slhs.setText("\'x\'");
			LiteralName entrylhs = pf.createLiteralName();
			entrylhs.setValue("a");

			titleExpr.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());
			subTestValidateExpressionTitles(titleExpr).assertOK();
		}
	}
}
