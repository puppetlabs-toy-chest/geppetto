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
package com.puppetlabs.geppetto.pp.dsl.ui.builder;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.IResourceDescription;

import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.linking.PPTask;
import com.puppetlabs.geppetto.pp.dsl.ui.PPUiConstants;

/**
 * An XtextBuilderParticipant for Puppet.
 */
public class PPBuilderParticipant implements IXtextBuilderParticipant {
	public static final String PP_PROBLEM_MARKER_CHECK_FAST = "com.puppetlabs.geppetto.pp.dsl.ui.pp.check.fast";

	public static final String PP_PROBLEM_MARKER_CHECK_NORMAL = "com.puppetlabs.geppetto.pp.dsl.ui.pp.check.normal";

	public static final String PP_PROBLEM_MARKER_CHECK_EXPENSIVE = "com.puppetlabs.geppetto.pp.dsl.ui.pp.check.expensive";

	public static final String RB_PROBLEM_MARKER_CHECK_FAST = "com.puppetlabs.geppetto.pp.dsl.ui.rb.check.fast";

	public static final String RB_PROBLEM_MARKER_CHECK_NORMAL = "com.puppetlabs.geppetto.pp.dsl.ui.rb.check.normal";

	public static final String RB_PROBLEM_MARKER_CHECK_EXPENSIVE = "com.puppetlabs.geppetto.pp.dsl.ui.rb.check.expensive";

	private static List<PPTask> getTaskList(Resource r) {
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		@SuppressWarnings("unchecked")
		List<PPTask> tasks = (List<PPTask>) adapter.get(PPDSLConstants.RESOURCE_PROPERTY__TASK_LIST);
		if(tasks == null)
			return Collections.emptyList();
		return tasks;

	}

	private static void syncTasks(List<PPTask> taskList, URI uri) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		// Remove all previous tasks
		try {
			file.deleteMarkers(PPUiConstants.PUPPET_TASK_MARKER_TYPE, false, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
			System.err.println("Could not delete task markers");
		}

		// Add all tasks
		try {
			for(PPTask task : taskList) {
				IMarker m = file.createMarker(PPUiConstants.PUPPET_TASK_MARKER_TYPE);
				int prio = task.isImportant()
					? IMarker.PRIORITY_HIGH
					: IMarker.PRIORITY_NORMAL;
				m.setAttribute(IMarker.MESSAGE, task.getMsg());
				m.setAttribute(IMarker.PRIORITY, prio);
				m.setAttribute(IMarker.LINE_NUMBER, task.getLine());
				m.setAttribute(IMarker.CHAR_START, task.getOffset());
				m.setAttribute(IMarker.CHAR_END, task.getOffset() + task.getLength());
				m.setAttribute(IMarker.USER_EDITABLE, false);
			}

		}
		catch(CoreException e) {
			// TODO
			System.err.println("Could not create puppet task marker");
		}
	}

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		if(context.getBuildType() == BuildType.CLEAN)
			removeErrorMarkers(context.getBuiltProject());
		for(IResourceDescription.Delta delta : context.getDeltas()) {
			IResourceDescription resourceDescription = delta.getNew();
			if(resourceDescription != null) {
				URI uri = resourceDescription.getURI();
				if(uri != null) {
					if("pp".equals(uri.fileExtension())) {
						Resource r = context.getResourceSet().getResource(uri, true);
						List<PPTask> taskList = getTaskList(r);
						syncTasks(taskList, uri);
					}
				}

			}
		}
	}

	private void removeErrorMarkers(IProject project) throws CoreException {
		project.deleteMarkers(PP_PROBLEM_MARKER_CHECK_FAST, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(PP_PROBLEM_MARKER_CHECK_NORMAL, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(PP_PROBLEM_MARKER_CHECK_EXPENSIVE, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(RB_PROBLEM_MARKER_CHECK_FAST, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(RB_PROBLEM_MARKER_CHECK_NORMAL, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(RB_PROBLEM_MARKER_CHECK_EXPENSIVE, true, IResource.DEPTH_INFINITE);
	}
}
