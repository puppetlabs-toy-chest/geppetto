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
package com.puppetlabs.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;

import com.puppetlabs.geppetto.forge.model.VersionedName;

public class AbstractForgeTestMojo {
	@Rule
	public final MojoRule mojoRule = new MojoRule(new AbstractMojoTestCase() {
		/**
		 * Patched version of the mojo lookup that ensures that goal specific execution configuration
		 * is appliled.
		 */
		@Override
		protected Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution) throws Exception,
				ComponentConfigurationException {
			MavenProject project = session.getCurrentProject();
			MojoDescriptor mojoDescriptor = execution.getMojoDescriptor();
			PluginDescriptor pluginDescriptor = mojoDescriptor.getPluginDescriptor();
			Plugin plugin = project.getPlugin(pluginDescriptor.getPluginLookupKey());
			pluginDescriptor.setPlugin(plugin);

			Xpp3Dom finalConfiguration = execution.getConfiguration();
			if(plugin != null) {
				String goal = execution.getGoal();
				for(PluginExecution pe : plugin.getExecutions()) {
					if(pe.getGoals().contains(goal)) {
						Xpp3Dom execConfig = (Xpp3Dom) pe.getConfiguration();
						if(execConfig != null)
							finalConfiguration = Xpp3Dom.mergeXpp3Dom(execConfig, finalConfiguration);
						break;
					}
				}
			}
			execution.setConfiguration(finalConfiguration);
			return super.lookupConfiguredMojo(session, execution);
		}
	});

	private MavenProject project;

	protected void assertContains(String message, String match, Throwable e) {
		String em = e.getMessage();
		if(em == null)
			em = "null";
		if(!em.contains(match)) {
			StringBuilder bld = new StringBuilder();
			bld.append(message);
			bld.append(": No '");
			bld.append(match);
			bld.append("' in '");
			bld.append(em);
			bld.append('\'');
			fail(bld.toString());
		}
	}

	protected void assertMissingAttribute(String message, String key, Throwable e) {
		assertContains(message, "Missing required attribute \"" + key + '"', e);
	}

	protected <T extends AbstractForgeMojo> T getMojo(File modulesRoot, String goal) throws Exception {
		@SuppressWarnings("unchecked")
		T mojo = (T) mojoRule.lookupConfiguredMojo(project, goal);
		assertNotNull(mojo);
		mojoRule.setVariableValueToObject(mojo, "modulesRoot", modulesRoot.getPath());
		return mojo;
	}

	protected Package getPackage(String project) throws Exception {
		return getMojo(new File(ForgeIT.WORKSPACE_DIR, project), "package");
	}

	protected Package getPackage(VersionedName release) throws Exception {
		return getMojo(new File(ForgeIT.TEST_MODULES_DIR, release.getModuleName().getName()), "package");
	}

	protected Publish getPublish(String project) throws Exception {
		return getMojo(new File(ForgeIT.WORKSPACE_DIR, project), "publish");
	}

	protected Publish getPublish(VersionedName release) throws Exception {
		return getMojo(new File(ForgeIT.TEST_MODULES_DIR, release.getModuleName().getName()), "publish");
	}

	protected Validate getValidate(String project) throws Exception {
		return getMojo(new File(ForgeIT.WORKSPACE_DIR, project), "validate");
	}

	protected Validate getValidate(VersionedName release) throws Exception {
		return getMojo(new File(ForgeIT.TEST_MODULES_DIR, release.getModuleName().getName()), "validate");
	}

	@Before
	public void setup() throws Exception {
		project = mojoRule.readMavenProject(ForgeIT.TEST_POM_DIR);
	}
}
