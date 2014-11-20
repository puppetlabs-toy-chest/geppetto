package com.puppetlabs.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.xtext.util.Wrapper;
import org.junit.Test;

public class ValidateTest2Mojo extends AbstractForgeTestMojo {

	@Test
	public void moduleWithResolvedDependency() throws Exception {
		Validate validate = getValidate(ForgeIT.testModuleB);
		try {
			StringBuilder bld = new StringBuilder("Installing dependent module ");
			ForgeIT.testModuleC.getModuleName().toString(bld);
			bld.append(':');
			ForgeIT.testModuleC.getVersion().toString(bld);
			final String expectedMessage = bld.toString();
			final Wrapper<Boolean> msgFound = new Wrapper<Boolean>(false);
			validate.setLogger(new NOPLogger() {
				@Override
				public void info(String message) {
					if(message.contains(expectedMessage))
						msgFound.set(true);
				}
			});
			validate.execute();
			assertTrue("Expected 'Installing dependent module' did not show up", msgFound.get());
		}
		catch(MojoFailureException e) {
			fail("Failed to validate module with dependency: " + e.getMessage());
		}
	}
}
