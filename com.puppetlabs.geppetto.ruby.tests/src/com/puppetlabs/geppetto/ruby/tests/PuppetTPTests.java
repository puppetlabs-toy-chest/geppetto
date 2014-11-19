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
package com.puppetlabs.geppetto.ruby.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.Test;

import com.puppetlabs.geppetto.common.os.OsUtil;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.util.TarUtils;
import com.puppetlabs.geppetto.pp.dsl.PPStandaloneSetup;
import com.puppetlabs.geppetto.pp.pptp.AbstractType;
import com.puppetlabs.geppetto.pp.pptp.Function;
import com.puppetlabs.geppetto.pp.pptp.Parameter;
import com.puppetlabs.geppetto.pp.pptp.Property;
import com.puppetlabs.geppetto.pp.pptp.TargetEntry;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeFragment;
import com.puppetlabs.geppetto.ruby.tests.Facter.Facter1_6;

public class PuppetTPTests extends AbstractRubyTests {
	private static final String PUPPET_DOWNLOADS = "http://downloads.puppetlabs.com/";

	private Diagnostic diag;

	/* uncomment and modify path to test load of puppet distribution and creating an xml version */

	private void assertNoError() {
		assertTrue(diag.toString(), diag.getSeverity() < Diagnostic.ERROR);
	}

	private File download(String type, String tgzFileName) throws Exception {
		File downloadedFile = new File(getDownloadsDir(), tgzFileName);
		if(downloadedFile.exists())
			return downloadedFile;

		URL ascURL = new URL(PUPPET_DOWNLOADS + type + '/' + tgzFileName);
		InputStream input = ascURL.openStream();
		try {
			OutputStream output = new FileOutputStream(downloadedFile);
			StreamUtil.copy(input, output);
			output.close();
		}
		catch(Exception e) {
			downloadedFile.delete();
			throw e;
		}
		finally {
			input.close();
		}
		return downloadedFile;
	}

	private File getDownloadsDir() {
		File downloads = new File(testDataProvider.getBasedir(), "downloads");
		if(!(downloads.isDirectory() || downloads.mkdirs()))
			fail("Unable to create directory: " + downloads.getAbsolutePath());
		return downloads;
	}

	private Function getFunction(String name, TargetEntry target) {
		for(Function f : target.getFunctions())
			if(name.equals(f.getName()))
				return f;
		return null;
	}

	private Parameter getParameter(String name, Type type) {
		for(Parameter p : type.getParameters())
			if(name.equals(p.getName()))
				return p;
		return null;
	}

	private Property getProperty(String name, AbstractType type) {
		for(Property p : type.getProperties())
			if(name.equals(p.getName()))
				return p;
		return null;
	}

	private File getPuppetDistribution(String version) throws Exception {
		return getUnpackedDownload(getDownloadsDir(), "puppet", version);
	}

	private File getPuppetPlugins(String version) throws Exception {
		String pluginsName = "plugins-" + version;
		File downloads = new File(getDownloadsDir(), pluginsName);
		return getUnpackedDownload(downloads, "hiera", "1.3.0");
	}

	private File getUnpackedDownload(File destDir, String type, String version) throws Exception {
		String distroName = type + '-' + version;
		File distroDir = new File(destDir, distroName);
		if(distroDir.isDirectory())
			return distroDir;

		InputStream input = new GZIPInputStream(new FileInputStream(download(type, distroName + ".tar.gz")));
		try {
			TarUtils.unpack(input, destDir, false, null);
			input.close();
			return distroDir;
		}
		catch(Exception e) {
			OsUtil.deleteRecursive(distroDir);
			throw e;
		}
		finally {
			input.close();
		}
	}

	private void performLoad(File distroDir, File pluginsDir, File tptpFile) throws Exception {
		TargetEntry target = helper.loadDistroTarget(distroDir, diag);

		// Load the variables in the settings:: namespace
		helper.loadSettings(target);

		// Load the default meta variables (available as local in every scope).
		helper.loadMetaVariables(target);
		helper.loadPuppetVariables(target);

		// Load (optional) any plugins
		List<TargetEntry> plugins = helper.loadPluginsTarget(pluginsDir, diag);

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(tptpFile.getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);

		// Add all (optional) plugins
		targetResource.getContents().add(target);
		for(TargetEntry entry : plugins)
			targetResource.getContents().add(entry);
		targetResource.save(null);
	}

	private void performLoad(String version) throws Exception {
		performLoad(version, version);
	}

	private void performLoad(String version, String pptpVersion) throws Exception {
		performLoad(new File(getPuppetDistribution(version), "lib/puppet"), //
			getPuppetPlugins(version), //
			new File(testDataProvider.getTestOutputDir(), "puppet-" + pptpVersion + ".pptp"));
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		new PPStandaloneSetup().createInjectorAndDoEMFRegistration();
		diag = new Diagnostic();
	}

	/**
	 * This is a really odd place to do this, but since the other generators of pptp modesl
	 * are here...
	 *
	 * @throws Exception
	 */
	@Test
	public void testLoad_Facter1_6() throws Exception {
		File pptpFile = new File(testDataProvider.getTestOutputDir(), "facter-1.6.pptp");
		Facter1_6 facter = new Facter1_6();

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(pptpFile.getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);

		// Add all (optional) plugins
		targetResource.getContents().add(facter.asPPTP());
		targetResource.save(null);
	}

	@Test
	public void testLoad_Facter1_7() throws Exception {
		File pptpFile = new File(testDataProvider.getTestOutputDir(), "facter-1.7.pptp");
		Facter1_6 facter = new Facter1_6();

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(pptpFile.getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);

		// Add all (optional) plugins
		targetResource.getContents().add(facter.asPPTP());
		targetResource.save(null);
	}

	@Test
	public void testLoad2_6_18() throws Exception {
		performLoad("2.6.18");
	}

	@Test
	public void testLoad2_7_19() throws Exception {
		performLoad("2.7.19");
		assertNoError();
	}

	@Test
	public void testLoad2_7_26() throws Exception {
		performLoad("2.7.26");
		assertNoError();
	}

	@Test
	public void testLoad3_0_2() throws Exception {
		performLoad("3.0.2");
		assertNoError();
	}

	@Test
	public void testLoad3_1_1() throws Exception {
		performLoad("3.1.1");
		assertNoError();
	}

	@Test
	public void testLoad3_2_4() throws Exception {
		performLoad("3.2.4");
		assertNoError();
	}

	@Test
	public void testLoad3_3_2() throws Exception {
		performLoad("3.3.2");
		assertNoError();
	}

	@Test
	public void testLoad3_4_3() throws Exception {
		performLoad("3.4.3");
		assertNoError();
	}

	@Test
	public void testLoad3_5_1() throws Exception {
		performLoad("3.5.1");
		assertNoError();
	}

	@Test
	public void testLoad3_6_2() throws Exception {
		performLoad("3.6.2");
		assertNoError();
	}

	@Test
	public void testLoad3_7_3() throws Exception {
		performLoad("3.7.3");
		assertNoError();
	}

	@Test
	public void testLoadEMFTP() throws Exception {
		File pptpFile = testDataProvider.getTestFile(new Path("testData/pptp/puppet-3.7.3.pptp"));

		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(pptpFile.getAbsolutePath());
		Resource targetResource = resourceSet.getResource(fileURI, true);
		TargetEntry target = (TargetEntry) targetResource.getContents().get(0);
		assertEquals("Should have found 50 types", 50, target.getTypes().size());
		assertEquals("Should have found 47 functions", 47, target.getFunctions().size());
		assertEquals("Should have found 93 providers", 93, target.getProviders().size());
		assertEquals("Should have found 0 puppet types", 0, target.getPuppetTypes().size());
	}

	@Test
	public void testLoadMockDistro() throws Exception {
		File distroDir = testDataProvider.getTestFile(new Path("testData/mock-puppet-distro/puppet-2.6.2_0/lib/puppet"));
		TargetEntry target = helper.loadDistroTarget(distroDir, diag);

		// check the target itself
		assertNotNull("Should have resultet in a TargetEntry", target);
		assertEquals("Should have defined description", "Puppet Distribution", target.getDescription());
		assertEquals("Should have defined name", "puppet", target.getLabel());
		assertEquals("Should have defined version", "2.6.2_0", target.getVersion());

		// should have found one type "mocktype"
		assertEquals("Should have found one type", 1, target.getTypes().size());
		Type type = target.getTypes().get(0);
		assertEquals("Should have found 'mocktype'", "mocktype", type.getName());
		assertEquals("Should have found documentation", "This is a mock type", type.getDocumentation());

		assertEquals("Should have one property", 1, type.getProperties().size());
		{
			Property prop = getProperty("prop1", type);
			assertNotNull("Should have a property 'prop1", prop);
			assertEquals("Should have defined documentation", "This is property1", prop.getDocumentation());
		}
		{
			assertEquals("Should have one parameter", 1, type.getParameters().size());
			Parameter param = getParameter("param1", type);
			assertNotNull("Should have a parameter 'param1", param);
			assertEquals("Should have defined documentation", "This is parameter1", param.getDocumentation());
		}

		// There should be two type fragments, with a contribution each
		List<TypeFragment> typeFragments = target.getTypeFragments();
		assertEquals("Should have found two fragments", 2, typeFragments.size());

		TypeFragment fragment1 = typeFragments.get(0);
		TypeFragment fragment2 = typeFragments.get(1);
		boolean fragment1HasExtra1 = getProperty("extra1", fragment1) != null;
		{
			Property prop = getProperty("extra1", fragment1HasExtra1
				? fragment1
				: fragment2);
			assertNotNull("Should have a property 'extra1", prop);
			assertEquals("Should have defined documentation", "An extra property called extra1", prop.getDocumentation());
		}
		{
			Property prop = getProperty("extra2", fragment1HasExtra1
				? fragment2
				: fragment1);
			assertNotNull("Should have a property 'extra2", prop);
			assertEquals("Should have defined documentation", "An extra property called extra2", prop.getDocumentation());
		}

		// should have found two functions "echotest" and "echotest2"
		// and the log functions (8)
		assertEquals("Should have found two functions", 10, target.getFunctions().size());
		{
			Function f = getFunction("echotest", target);
			assertNotNull("Should have found function 'echotest'", f);
			assertTrue("echotest should be an rValue", f.isRValue());
		}
		{
			Function f = getFunction("echotest2", target);
			assertNotNull("Should have found function 'echotest2'", f);
			assertFalse("echotest2 should not be an rValue", f.isRValue());
		}

	}
}
