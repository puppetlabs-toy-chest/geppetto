package com.puppetlabs.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.xtext.util.Wrapper;
import org.junit.Test;

public class RepublishTestMojo extends AbstractForgeTestMojo {
	@Test
	public void republish() throws Exception {
		MavenSession session = packageGeneratedModule(ForgeIT.testModuleC);
		Publish publish = (Publish) lookupConfiguredMojo(session, newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			// Publish will execute but do nothing. The result is OK.
			final Wrapper<Boolean> msgFound = new Wrapper<Boolean>(false);
			StringBuilder bld = new StringBuilder();
			ForgeIT.testModuleC.getModuleName().toString(bld);
			bld.append(':');
			ForgeIT.testModuleC.getVersion().toString(bld);
			bld.append(" has already been published");
			final String expectedMessage = bld.toString();
			publish.setLogger(new NOPLogger() {
				@Override
				public void warn(String message) {
					if(message.contains(expectedMessage))
						msgFound.set(true);
				}
			});
			publish.execute();
			assertTrue("Expected 'already been published' did not show up", msgFound.get());
		}
		catch(MojoFailureException e) {
			fail("Republishing of OK module failed: " + e.getMessage());
		}
	}
}
