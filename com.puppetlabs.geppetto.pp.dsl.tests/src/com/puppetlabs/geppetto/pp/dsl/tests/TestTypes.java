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
package com.puppetlabs.geppetto.pp.dsl.tests;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.dsl.eval.PPTypeEvaluator;
import com.puppetlabs.geppetto.pp.dsl.linking.DiagnosticConsumerBasedMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.ITypeValidator;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.PuppetTypeValidator;
import com.puppetlabs.geppetto.pp.dsl.validation.PuppetTypeValidator.ParameterizedTypeCreationException;
import com.puppetlabs.geppetto.pp.pptp.PuppetType;
import com.puppetlabs.geppetto.pp.pptp.TypeValue;

/**
 * @author thhal
 */
public class TestTypes extends AbstractPuppetTests {
	public static Map<String, String> fromStrings(String[] pairedStrings) {
		int top = pairedStrings.length;
		assertTrue("Expected an even number of strings", top % 2 == 0);
		Map<String, String> result = Maps.newHashMapWithExpectedSize(top / 2);
		for(int idx = 0; idx < pairedStrings.length; idx += 2)
			result.put(pairedStrings[idx], pairedStrings[idx + 1]);
		return result;
	}

	public final static String[] typeNames = {
		"type", "any", "runtime", "scalar", "boolean", "numeric", "integer", "float", "collection", "array", "hash", "tuple", "struct",
		"variant", "optional", "enum", "regexp", "pattern" };

	PuppetTypeValidator puppetTypeValidator;

	PPTypeEvaluator typeEvaluator;

	@Test
	public void asLHSInMatch() throws Exception {
		String code = "$my_array = [1,3,4]\n" + //
			"if $my_array =~ Array {\n" + //
			"notice('yes')" + //
			"}\n"; //
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	@Test
	public void asParameterInDefine() throws Exception {
		String code = "define call_foo(Pattern[Regexp[/foo/]] $x, $y) { }\n";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	@Test
	public void classTypeNamesNotOK() throws Exception {
		String code = "class %s { }";
		for(String typeName : typeNames) {
			Resource r = loadAndLinkSingleResource(format(code, typeName));
			resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__RESERVED_TYPE_NAME);
		}
	}

	@Test
	public void defineTypeNamesNotOK() throws Exception {
		String code = "define %s { }";
		for(String typeName : typeNames) {
			Resource r = loadAndLinkSingleResource(format(code, typeName));
			resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__RESERVED_TYPE_NAME);
		}
	}

	@Test
	public void errorsWhenWrongNumberOrType() throws Exception {
		// @fmtOff
		for(Map.Entry<String, String> x : fromStrings(new String[]
	    {
	        "Array[0]"                    , "Array[V,from,to] argument 'V' must be a Type. Got Integer",
	        "Hash[0]"                     , "Hash[K,V,from,to] argument 'K' must be a Type. Got Integer",
	        "Hash[Integer, 0]"            , "Hash[K,V,from,to] argument 'V' must be a Type. Got Integer",
	        "Array[Integer,1,2,3]"        , "Array[V,from,to] accepts 1 to 3 arguments. Got 4",
	        "Array[Integer,String]"       , "Array[V,from,to] argument 'from' must be of type Integer. Got Type[String]",
	        "Hash[Integer,String, 1,2,3]" , "Hash[K,V,from,to] accepts 1 to 4 arguments. Got 5",
	        "Resource[0]"                 , "Resource[type_name,title] argument 'type_name' must be of type String or Type[Resource]. Got Integer",
	        "Resource[a, 0]"              , "Resource[type_name,title] argument 'title' must be of type String. Got Integer",
	        "File[0]"                     , "Resource[type_name,title] argument 'title' must be of type String. Got Integer",
	        "String[a]"                   , "String[from,to] argument 'from' must be of type Integer. Got String",
	        "Pattern[0]"                  , "Pattern[patterns] argument must be of type String, Regexp, Type[Pattern], or Type[Regexp]. Got Integer",
	        "Regexp[0]"                   , "Regexp[pattern] argument must be of type String or Regexp. Got Integer",
	        "Regexp[a,b]"                 , "Regexp[pattern] accepts one argument. Got two",
	      }).entrySet()) {
			Resource r = loadAndLinkSingleResource(x.getKey());
			resourceWarningDiagnostics(r).assertOK();
			resourceErrorDiagnostics(r).assertOK();
			tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED, x.getValue());
		}
		// @fmtOn
	}

	@Test
	public void errorsWhenWrongNumberOrTypeOnLiterals() throws Exception {
		// @fmtOff
		for(Map.Entry<String, String> x : fromStrings(new String[]
	    {
	        "'abc'[x]"                    , "A String[] cannot use String where Integer is expected",
	        "'abc'[1.0]"                  , "A String[] cannot use Float where Integer is expected",
	        "'abc'[1,2,3]"                , "String supports [] with one or two arguments. Got 3",
	        "true[0]"                     , "Operator '[]' is not applicable to a Boolean",
	        "1[0]"                        , "Operator '[]' is not applicable to an Integer",
	        "3.14[0]"                     , "Operator '[]' is not applicable to a Float",
	        "/.*/[0]"                     , "Operator '[]' is not applicable to a Regexp",
	        "[1][a]"                      , "An Array[] cannot use String where Integer is expected",
	        "[1][0.0]"                    , "An Array[] cannot use Float where Integer is expected",
	        "[1]['0.0']"                  , "An Array[] cannot use Float where Integer is expected",
	        "[1,2][1, 0.0]"               , "An Array[] cannot use Float where Integer is expected",
	        "[1,2][1.0, -1]"              , "An Array[] cannot use Float where Integer is expected",
	        "[1,2][1, -1.0]"              , "An Array[] cannot use Float where Integer is expected"
	      }).entrySet()) {
			Resource r = loadAndLinkSingleResource(x.getKey());
			resourceWarningDiagnostics(r).assertOK();
			resourceErrorDiagnostics(r).assertOK();
			tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION, x.getValue());
		}
		// @fmtOn
	}

	private Expression getAssignmentRHS(Expression expr) {
		assertTrue("Expected assignement expression", expr instanceof AssignmentExpression);
		return ((AssignmentExpression) expr).getRightExpr();
	}

	@Override
	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_4_0;
	}

	@Before
	public void init() {
		Injector injector = getInjector();
		puppetTypeValidator = injector.getInstance(PuppetTypeValidator.class);
		typeEvaluator = injector.getInstance(PPTypeEvaluator.class);
	}

	public PuppetType puppetType(String typeName, EObject scope) {
		ITypeValidator typeValidator = puppetTypeValidator.getTypeValidator(typeName);
		assertNotNull("Unable to find validator for type " + typeName, typeValidator);
		return typeValidator.getType(scope);
	}

	@Test
	public void validateEnum() throws Exception {
		String t = "$t = Enum['foo', 'fee', 'fum']\n";

		validateTypeExpression(t + "$e = 'foo'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'fee'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'fum'\n", 0).assertOK();
		validateTypeExpression(t + "$e = \"foo\"\n", 0).assertOK();
		validateTypeExpression(t + "$e = \"fee\"\n", 0).assertOK();
		validateTypeExpression(t + "$e = \"fum\"\n", 0).assertOK();
		validateTypeExpression(t + "$e = foo\n", 0).assertOK();
		validateTypeExpression(t + "$e = fee\n", 0).assertOK();
		validateTypeExpression(t + "$e = fum\n", 0).assertOK();
		validateTypeExpression(t + "$e = $::a\n", 0).assertOK();
		validateTypeExpression(t + "$e = \"f${::a}\"\n", 0).assertOK();
		validateTypeExpression(t + "$e = 23\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = default\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 'Foo'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 'bar'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateFileResource() throws Exception {
		// @fmtOff
		validateTypeExpression(
			"$t = Resource[File]\n" +
			"$e = File['/tmp/foo']\n",
			0).assertOK();
		// @fmtOn
	}

	@Test
	public void validateFloat() throws Exception {
		String t = "$t = Float\n";

		// Float literal
		validateTypeExpression(t + "$e = 1.2\n", 0).assertOK();

		// Float binary expression
		validateTypeExpression(t + "$e = 1.2 + 3\n", 0).assertOK();

		// Unknown Numeric binary expression
		validateTypeExpression(t + "$e = $::a * $::b\n", 0).assertOK();

		// Integer literal
		validateTypeExpression(t + "$e = 2\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Integer binary expression
		validateTypeExpression(t + "$e = 1 + 2\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateFloatRange() throws Exception {
		String t = "$t = Float[5.0,10.0]\n";
		validateTypeExpression(t + "$e = 5.0\n", 0).assertOK();
		validateTypeExpression(t + "$e = 10.0\n", 0).assertOK();
		validateTypeExpression(t + "$e = 4.999\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 10.001\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateHash() throws Exception {
		String t = "$t = Hash\n";
		validateTypeExpression(t + "$e = { a => 10, b => 20 }\n", 0).assertOK();
		validateTypeExpression(t + "$e = { 10 => 'a', 20 => 'b' }\n", 0).assertOK();
		validateTypeExpression(t + "$e = $::a\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'not a hash'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = [1,2,3]\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateHashTyped() throws Exception {
		String t = "$t = Hash[String,Integer]\n";
		validateTypeExpression(t + "$e = { a => 10, b => 20 }\n", 0).assertOK();
		validateTypeExpression(t + "$e = { 'a' => 10, 'b' => 20 }\n", 0).assertOK();
		validateTypeExpression(t + "$e = $::a\n", 0).assertOK();
		validateTypeExpression(t + "$e = { 10 => 20 }\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		t = "$t = Hash[String,Hash[String,Integer]]\n";
		validateTypeExpression(t + "$e = { 'a' => { 'a' => 10, 'b' => 20 }, 'b' => { 'a' => 10, 'b' => 20 } }\n", 0).assertOK();
		validateTypeExpression(t + "$e = { 'a' => { 'a' => 10, 'b' => 20 }, 'b' => { 20 => 10, 'b' => 20 } }\n", 0).assertDiagnostic(
			IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateInteger() throws Exception {
		String t = "$t = Integer\n";
		// Integer literal
		validateTypeExpression(t + "$e = 2\n", 0).assertOK();

		// Integer binary expression
		validateTypeExpression(t + "$e = 1 + 2\n", 0).assertOK();

		// Unknown Numeric binary expression
		validateTypeExpression(t + "$e = $::a * $::b\n", 0).assertOK();

		// Float literal
		validateTypeExpression(t + "$e = 1.2\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Float binary expression
		validateTypeExpression(t + "$e = 1.2 + 3\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateIntegerRange() throws Exception {
		String t = "$t = Integer[5,10]\n";
		validateTypeExpression(t + "$e = 5\n", 0).assertOK();
		validateTypeExpression(t + "$e = 10\n", 0).assertOK();
		validateTypeExpression(t + "$e = 4\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 11\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateNumeric() throws Exception {
		String t = "$t = Numeric\n";

		// Integer
		validateTypeExpression(t + "$e = 2\n", 0).assertOK();

		// Integer binary expression
		validateTypeExpression(t + "$e = 1 + 2\n", 0).assertOK();

		// Float
		validateTypeExpression(t + "$e = 1.2\n", 0).assertOK();

		// Float binary expression
		validateTypeExpression(t + "$e = 1.2 + $::a\n", 0).assertOK();

		// Unknown Numeric binary expression
		validateTypeExpression(t + "$e = $::a * $::b\n", 0).assertOK();

		// Single-quoted integer string
		validateTypeExpression(t + "$e = '32'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Double-quoted integer string
		validateTypeExpression(t + "$e = \"32\"\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Single-quoted float string
		validateTypeExpression(t + "$e = '3.4'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Double-quoted float string
		validateTypeExpression(t + "$e = \"4.5\"\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Interpolated, perhaps a number (still not Number type)
		validateTypeExpression(t + "$e = \"32${::a}\"\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validatePattern() throws Exception {
		String t = "$t = Pattern['foo', /fee/, Regexp['^fum$'], Pattern[/^a$/, /^b$/, /^c$/]]\n";

		validateTypeExpression(t + "$e = 'matchfoobar'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'matchfeebar'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'matchfumbar'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 'fum'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'a'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'b'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'c'\n", 0).assertOK();
	}

	@Test
	public void validateString() throws Exception {
		String t = "$t = String\n";

		// Single-quoted string
		validateTypeExpression(t + "$e = 'hello'\n", 0).assertOK();

		// Double-quoted string
		validateTypeExpression(t + "$e = \"hello\"\n", 0).assertOK();

		// Interpolated string
		validateTypeExpression(t + "$e = \"hello${::a}\"\n", 0).assertOK();

		// Interpolated, perhaps a number
		validateTypeExpression(t + "$e = \"32${::a}\"\n", 0).assertOK();

		// String without quotes
		validateTypeExpression(t + "$e = foo\n", 0).assertOK();

		// Unknown Numeric binary expression
		validateTypeExpression(t + "$e = $::a * $::b\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Float literal
		validateTypeExpression(t + "$e = 1.2\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Integer literal
		validateTypeExpression(t + "$e = 2\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Default literal
		validateTypeExpression(t + "$e = default\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Undef literal
		validateTypeExpression(t + "$e = undef\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Boolean literal
		validateTypeExpression(t + "$e = true\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateStringSize() throws Exception {
		String t = "$t = String[4,9]\n";
		validateTypeExpression(t + "$e = 'bad'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = 'good'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'confident'\n", 0).assertOK();
		validateTypeExpression(t + "$e = 'presumptuous'\n", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateStruct() throws Exception {
		String t = "$t = Struct[{mode=>Enum[read,write,update], path=>Optional[String[1]]}]";
		validateTypeExpression(t + "$e = { mode=>write, path=>'/foo/bar'}", 0).assertOK();

		// Missing optional value
		validateTypeExpression(t + "$e = { mode=>write }", 0).assertOK();

		// Missing required value
		validateTypeExpression(t + "$e = { path=>'/foo/bar' }", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Key that isn't declared
		validateTypeExpression(t + "$e = { mode=>write, path=>'/foo/bar', size=>300 }", 0).assertDiagnostic(
			IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Misspelled key
		validateTypeExpression(t + "$e = { mode=>write, paht=>'/foo/bar' }", 0).assertDiagnostic(
			IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);

		// Misspelled enum in value type
		validateTypeExpression(t + "$e = { mode=>writ, path=>'/foo/bar' }", 0).assertDiagnostic(
			IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	@Test
	public void validateType() throws Exception {
		String t = "$t = Type[Resource[File['/foo/bar']]]";
		validateTypeExpression(t + "$e = Resource[File['/foo/bar']]", 0).assertOK();
		validateTypeExpression(t + "$e = File['/foo/bar']", 0).assertOK();
		validateTypeExpression(t + "$e = File['/foo/bar', '/bar/foo']", 0).assertDiagnostic(
			IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		validateTypeExpression(t + "$e = File['/foo/fee']", 0).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
	}

	private AssertableResourceDiagnostics validateTypeExpression(String code, int preamble) throws Exception {
		Resource r = loadAndLinkSingleResource(code);
		PuppetManifest manifest = (PuppetManifest) r.getContents().get(0);

		tester.validate(manifest).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		List<Expression> statements = manifest.getStatements();
		assertEquals("Unexpected number of expressions", preamble + 2, statements.size());
		Expression typeExpr = getAssignmentRHS(statements.get(preamble));
		ListBasedDiagnosticConsumer c = new ListBasedDiagnosticConsumer();
		try {
			TypeValue tv = puppetTypeValidator.getTypeValue(typeExpr);
			puppetTypeValidator.checkExpression(
				null, tv.getValue().getName(), tv.getParameters(), getAssignmentRHS(statements.get(preamble + 1)),
				new DiagnosticConsumerBasedMessageAcceptor(c));
		}
		catch(ParameterizedTypeCreationException e) {
			fail(e.getMessage());
		}

		return diagnostics(c);
	}

	@Test
	public void validateUserDefinedResource() throws Exception {
		// @fmtOff
		validateTypeExpression(
			"define foo($x) { notice $x }\n" +
			"$t = Type[Foo['/tmp/foo']]\n" +
			"$e = Foo['/tmp/foo']\n",
			1).assertOK();

		validateTypeExpression(
			"define foo($x) { notice $x }\n" +
			"$t = Type[Foo['/tmp/foo']]\n" +
			"$e = Foo['/tmp/bar']\n",
			1).assertDiagnostic(IPPDiagnostics.ISSUE__TYPE_CONSTRAINT_NOT_FULFILLED);
		// @fmtOn
	}
}
