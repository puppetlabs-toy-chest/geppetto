package com.puppetlabs.geppetto.ruby.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

import com.puppetlabs.geppetto.pp.pptp.AbstractType;
import com.puppetlabs.geppetto.pp.pptp.Function;
import com.puppetlabs.geppetto.pp.pptp.Parameter;
import com.puppetlabs.geppetto.pp.pptp.Property;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeFragment;

public class PptpResourceTests extends AbstractRubyTests {
	private void doFunctionResource(String path, String functionName) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		File aRubyFile = testDataProvider.getTestFile(new Path(path));
		URI uri = URI.createFileURI(aRubyFile.getAbsolutePath());
		Resource r = resourceSet.getResource(uri, true);

		assertEquals("Should have loaded one thing", 1, r.getContents().size());
		EObject f = r.getContents().get(0);
		assertTrue("Should have loaded a function", f instanceof Function);
		Function func = (Function) f;
		assertEquals("Name should be the expected name", functionName, func.getName());
		assertTrue("Should be an rValue", func.isRValue());
	}

	private TypeFragment doTypeFragmentResource(String path, String typeName) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		File aRubyFile = testDataProvider.getTestFile(new Path(path));
		URI uri = URI.createFileURI(aRubyFile.getAbsolutePath());
		Resource r = resourceSet.getResource(uri, true);

		assertEquals("Should have loaded one thing", 1, r.getContents().size());
		EObject t = r.getContents().get(0);
		assertTrue("Should have loaded a typeFragment", t instanceof TypeFragment);
		TypeFragment type = (TypeFragment) t;
		assertEquals("Name should be the expected name", typeName, type.getName());
		return type;
	}

	private Type doTypeResource(String path, String typeName) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		File aRubyFile = testDataProvider.getTestFile(new Path(path));
		URI uri = URI.createFileURI(aRubyFile.getAbsolutePath());
		Resource r = resourceSet.getResource(uri, true);

		assertEquals("Should have loaded one thing", 1, r.getContents().size());
		EObject t = r.getContents().get(0);
		assertTrue("Should have loaded a type", t instanceof Type);
		Type type = (Type) t;
		assertEquals("Name should be the expected name", typeName, type.getName());
		return type;
	}

	private Parameter findParameter(AbstractType t, String name) {
		for(Parameter p : t.getParameters())
			if(name.equals(p.getName()))
				return p;
		return null;
	}

	private Property findProperty(AbstractType t, String name) {
		for(Property p : t.getProperties())
			if(name.equals(p.getName()))
				return p;
		return null;
	}

	@Test
	public void testFunctionResource() throws IOException {
		doFunctionResource("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest.rb", "echotest");
	}

	@Test
	public void testFunctionResource2() throws IOException {
		doFunctionResource("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest2.rb", "echotest2");
	}

	@Test
	public void testFunctionResource3() throws IOException {
		doFunctionResource("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest3.rb", "echotest3");
	}

	@Test
	public void testLoadConcatenatedDoc() throws Exception {
		String expectedDoc = "An optional SQL command to execute prior to the main :command; "
			+ "this is generally intended to be used for idempotency, to check "
			+ "for the existence of an object in the database to determine whether "
			+ "or not the main SQL command needs to be executed at all.";

		Type type = doTypeResource("testData/pp-modules-ruby/module-z/lib/puppet/type/postgresql_psql.rb", "postgresql_psql");

		assertEquals("Should have found 7 parameters", 7, type.getParameters().size());
		assertEquals("Should have found one property", 1, type.getProperties().size());

		Parameter param = findParameter(type, "unless");
		assertNotNull("Should have found a parameter called 'unless'", param);
		assertEquals("Should have found expected description of 'unless'", expectedDoc, param.getDocumentation());
	}

	@Test
	public void testLoadType() throws Exception {
		Type type = doTypeResource("testData/pp-modules-ruby/module-x/lib/puppet/type/thing.rb", "thing");

		assertEquals("Should have found two parameter", 2, type.getParameters().size());
		assertEquals("Should have found two properties", 2, type.getProperties().size());

		Parameter nameEntry = findParameter(type, "name");
		assertNotNull("Should have found a parameter called 'name'", nameEntry);
		assertEquals("Should have found a description of 'name'", "Description of name", nameEntry.getDocumentation());

		Parameter ensure = findParameter(type, "ensure");
		assertNotNull("Should have found a parameter called 'ensure'", ensure);

		Property weightEntry = findProperty(type, "weight");
		assertNotNull("Should have found a property called 'weight'", weightEntry);
		assertEquals("Should have found a description of 'weight'", "Description of weight", weightEntry.getDocumentation());

		Property emptyEntry = findProperty(type, "empty");
		assertNotNull("Should have found a property called 'weight'", emptyEntry);
		assertNull("Should have found a missing description of 'empty'", emptyEntry.getDocumentation());
	}

	@Test
	public void testTypeFragment() throws IOException {
		TypeFragment tf = doTypeFragmentResource(
			"testData/mock-puppet-distro/puppet-2.6.2_0/lib/puppet/type/mocktype/extra1.rb", "mocktype");
		assertEquals("Should have found one property", 1, tf.getProperties().size());
		Property extra1 = findProperty(tf, "extra1");
		assertNotNull("Should have found extra1", extra1);

		tf = doTypeFragmentResource("testData/mock-puppet-distro/puppet-2.6.2_0/lib/puppet/type/mocktype/extra2.rb", "mocktype");
		assertEquals("Should have found one property", 1, tf.getProperties().size());
		Property extra2 = findProperty(tf, "extra2");
		assertNotNull("Should have found extra2", extra2);

	}
}
