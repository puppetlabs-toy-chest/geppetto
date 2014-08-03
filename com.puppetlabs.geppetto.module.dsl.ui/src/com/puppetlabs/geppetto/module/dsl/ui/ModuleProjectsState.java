package com.puppetlabs.geppetto.module.dsl.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

/**
 * An {@link IAllContainersState} that considers all projects with nature {@link #PUPPET_NATURE} as candidates
 */
@Singleton
public class ModuleProjectsState extends AbstractAllContainersState {
	public static boolean isAccessiblePuppetProject(final IProject p) {
		try {
			return p != null && XtextProjectHelper.hasNature(p) && p.hasNature(PUPPET_NATURE);
		}
		catch(CoreException e) {
			return false;
		}
	}

	public final static String PUPPET_NATURE = "com.puppetlabs.geppetto.pp.dsl.ui.puppetNature";

	private final static Logger log = Logger.getLogger(ModuleProjectsState.class);

	@Override
	public Collection<URI> doInitContainedURIs(String containerHandle) {
		try {
			IPath projectPath = new Path(null, containerHandle).makeAbsolute();
			if(projectPath.segmentCount() == 1) {
				IProject project = getWorkspaceRoot().getProject(containerHandle);
				if(ModuleProjectsState.isAccessiblePuppetProject(project))
					return getMapper().getAllEntries(project).keySet();
			}
		}
		catch(IllegalArgumentException e) {
			ModuleProjectsState.log.warn((("Cannot init contained URIs for containerHandle \'" + containerHandle) + "\'"), e);
		}
		return Collections.emptyList();
	}

	@Override
	protected String doInitHandle(URI uri) {
		if(uri.isPlatform()) {
			IFile file = getWorkspaceRoot().getFile(new Path(uri.toPlatformString(true)));
			if(file != null)
				return file.getProject().getName();
		}
		return null;
	}

	@Override
	protected List<String> doInitVisibleHandles(final String handle) {
		try {
			IProject project = getWorkspaceRoot().getProject(handle);
			if(ModuleProjectsState.isAccessiblePuppetProject(project)) {
				List<String> result = Lists.newArrayList();
				result.add(handle);
				for(IProject referencedProject : getWorkspaceRoot().getProjects(0))
					if(ModuleProjectsState.isAccessiblePuppetProject(referencedProject))
						result.add(referencedProject.getName());
				return result;
			}
		}
		catch(IllegalArgumentException e) {
			ModuleProjectsState.log.warn((("Cannot init visible handles for containerHandle \'" + handle) + "\'"), e);
		}
		return null;
	}
}
