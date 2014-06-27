package com.puppetlabs.geppetto.module.dsl.ui

import com.google.common.collect.Lists
import com.google.inject.Singleton
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.containers.IAllContainersState
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.containers.AbstractAllContainersState

/**
 * An {@link IAllContainersState} that considers all projects with nature {@link #PUPPET_NATURE}
 * as candidates
 */
@Singleton
class ModuleProjectsState extends AbstractAllContainersState {

	public static val PUPPET_NATURE = "com.puppetlabs.geppetto.pp.dsl.ui.puppetNature"
 
	static val log = Logger.getLogger(ModuleProjectsState)

	def static boolean isAccessiblePuppetProject(IProject p) {
		p != null && XtextProjectHelper.hasNature(p) && p.hasNature(PUPPET_NATURE)
	}

	override doInitContainedURIs(String containerHandle) {
		try {
			val projectPath = new Path(null, containerHandle).makeAbsolute()
			if (projectPath.segmentCount == 1) {
				val project = workspaceRoot.getProject(containerHandle)
				if (project.isAccessiblePuppetProject)
					return mapper.getAllEntries(project).keySet()
			}
		} catch (IllegalArgumentException e) {
			log.warn("Cannot init contained URIs for containerHandle '" + containerHandle + "'", e)
		}
		Collections.emptyList()
	}

	override protected doInitHandle(URI uri) {
		if (uri.isPlatform) {
			val file = workspaceRoot.getFile(new Path(uri.toPlatformString(true)))
			if (file != null)
				return file.project.name
		}
		null
	}

	override protected doInitVisibleHandles(String handle) {
		try {
			val project = workspaceRoot.getProject(handle)
			if (project.isAccessiblePuppetProject) {
				val result = Lists.newArrayList()
				result.add(handle)
				for (referencedProject : workspaceRoot.getProjects(0))
					if (referencedProject.isAccessiblePuppetProject)
						result.add(referencedProject.name)
				return result
			}
		} catch (IllegalArgumentException e) {
			log.warn("Cannot init visible handles for containerHandle '" + handle + "'", e)
		}
		Collections.emptyList()
	}
}
