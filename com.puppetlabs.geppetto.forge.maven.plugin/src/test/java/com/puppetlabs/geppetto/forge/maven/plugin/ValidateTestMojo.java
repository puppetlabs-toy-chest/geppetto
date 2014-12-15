package com.puppetlabs.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class ValidateTestMojo extends AbstractForgeTestMojo {
	@Test
	public void moduleWithComplexRuby() throws Exception {
		Validate validate = getValidate("test_module_complex_ruby");
		try {
			validate.execute();
			fail("Complex ruby was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue("Complex ruby was not detected", e.getMessage().contains("version must be specified"));
		}
	}

	@Test
	public void moduleWithInterpolatedComplexRuby() throws Exception {
		Validate validate = getValidate("test_module_interpolated_complex_ruby");
		try {
			validate.execute();
			fail("Interpolated complex ruby was not detected");
		}
		catch(MojoFailureException e) {
			assertContains("Interpolated complex ruby was not detected", "version must be specified", e);
		}
	}

	@Test
	public void moduleWithInvalidMetadata() throws Exception {
		Validate validate = getValidate("test_metadata_invalid");
		try {
			validate.execute();
			fail("Invalid metadata was not detected");
		}
		catch(MojoFailureException e) {
			assertContains("Invalid metadata was not detected", "mismatched input", e);
		}
	}

	@Test
	public void moduleWithNoMetadataNorModulefile() throws Exception {
		Validate validate = getValidate("test_no_metadata_nor_modulefile");
		try {
			validate.execute();
			fail("Module found although both metadata.json and Modulefile is missing");
		}
		catch(MojoFailureException e) {
			assertContains("Module found although both metadata.json and Modulefile is missing", "No modules found", e);
		}
	}

	@Test
	public void moduleWithNoModulefile() throws Exception {
		Validate validate = getValidate("test_metadata_no_modulefile");
		try {
			validate.execute();
		}
		catch(MojoFailureException e) {
			fail("Validation failed when Modulefile is missing: " + e.getMessage());
		}
	}

	@Test
	public void moduleWithoutMetadataName() throws Exception {
		Validate validate = getValidate("test_metadata_no_name");
		try {
			validate.execute();
			fail("Missing module name in metadata.json was not detected");
		}
		catch(MojoFailureException e) {
			assertMissingAttribute("Missing module name in metadata.json was not detected", "name", e);
		}
	}

	@Test
	public void moduleWithoutMetadataVersion() throws Exception {
		Validate validate = getValidate("test_metadata_no_version");
		try {
			validate.execute();
			fail("Missing module version in metadata.json was not detected");
		}
		catch(MojoFailureException e) {
			assertMissingAttribute("Missing module version in metadata.json was not detected", "version", e);
		}
	}

	@Test
	public void moduleWithoutName() throws Exception {
		Validate validate = getValidate("test_module_no_name");
		try {
			validate.execute();
			fail("Missing module name was not detected");
		}
		catch(MojoFailureException e) {
			assertContains("Missing module name was not detected", "A full name (user-module) must be specified", e);
		}
	}

	@Test
	public void moduleWithoutVersion() throws Exception {
		Validate validate = getValidate("test_module_no_version");
		try {
			validate.execute();
			fail("Missing module version was not detected");
		}
		catch(MojoFailureException e) {
			assertContains("Missing module version was not detected", "A version must be specified", e);
		}
	}

	//	@Test
	public void unresolvedDependency() throws Exception {
		Validate validate = getValidate(ForgeIT.testModuleB);
		try {
			validate.execute();
			fail("Unresolved dependency was not detected");
		}
		catch(MojoFailureException e) {
			assertContains("Unresolved dependency was not detected", "Couldn't resolve reference to Module", e);
		}
	}

	@Test
	public void validateOK() throws Exception {
		Validate validate = getValidate(ForgeIT.testModuleC);
		try {
			validate.execute();
		}
		catch(MojoFailureException e) {
			fail("Validation of OK module failed: " + e.getMessage());
		}
	}
}
