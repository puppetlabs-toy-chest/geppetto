package com.puppetlabs.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.client.HttpResponseException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class PublishTestMojo extends AbstractForgeTestMojo {

	@Test
	public void publishOK() throws Exception {
		Package pkg = getPackage(ForgeIT.testModuleC);
		try {
			pkg.execute();
		}
		catch(MojoFailureException e) {
			fail("Packaging of module failed: " + e.getMessage());
		}
		Publish publish = getPublish(ForgeIT.testModuleC);
		try {
			publish.execute();
		}
		catch(MojoFailureException e) {
			fail("Publishing of OK module failed: " + e.getMessage());
		}
	}

	@Test
	public void publishWrongOwner() throws Exception {
		Package pkg = getPackage("test_module_wrong_owner");
		try {
			pkg.execute();
		}
		catch(MojoFailureException e) {
			fail("Packaging of module failed: " + e.getMessage());
		}
		Publish publish = getPublish("test_module_wrong_owner");
		try {
			publish.execute();
			fail("Publishing succeeded with wrong owner");
		}
		catch(MojoFailureException e) {
			Throwable t = e.getCause();
			assertTrue("Exception cause is not the right class", t instanceof HttpResponseException);
			assertContains("Wrong owner not detected correctly", "Forbidden", t);
		}
	}
}
