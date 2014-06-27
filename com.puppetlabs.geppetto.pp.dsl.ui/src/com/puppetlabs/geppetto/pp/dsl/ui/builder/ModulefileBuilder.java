package com.puppetlabs.geppetto.pp.dsl.ui.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This class is just a placeholder to avoid warnings about a missing builder in old workspaces
 * where the .project file declares a reference to a 'modulefileBuilder'. The Modulefile is no
 * longer in use by puppet and other relevant pars of this builder has been moved to
 * com.puppetlabs.geppetto.module.dsl.ui.builder.MetadataBuildParticipant
 */
public class ModulefileBuilder extends IncrementalProjectBuilder {
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		return null;
	}
}
