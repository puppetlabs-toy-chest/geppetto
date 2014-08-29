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

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.google.common.collect.Sets;
import com.puppetlabs.geppetto.pp.dsl.ui.PPUiConstants;
import com.puppetlabs.geppetto.pp.dsl.ui.internal.PPActivator;

/**
 * The PPBuildJob is used when there is a need to rebuild projects (such as after preference changes).
 */
public class PPBuildJob extends Job {
	final private IWorkspace workspace;

	final private Set<IProject> projects;

	final private Job jobToScheduleAfterBuild;

	final private int buildType;

	private static final Logger log = Logger.getLogger(PPBuildJob.class);

	public PPBuildJob(IProject project) {
		super("Building Puppet Projects");
		this.workspace = project.getWorkspace();
		this.projects = Sets.newHashSet(project);
		this.jobToScheduleAfterBuild = null;
		setPriority(Job.BUILD);
		this.buildType = IncrementalProjectBuilder.FULL_BUILD;
		setRule(workspace.getRoot());
	}

	public PPBuildJob(IWorkspace workspace, int buildType, Job jobToScheduleAfterBuild) {
		super("Building Puppet Projects");
		this.buildType = buildType;
		this.workspace = workspace;
		this.jobToScheduleAfterBuild = jobToScheduleAfterBuild;
		projects = Sets.newHashSet();
		;
		for(IProject p : workspace.getRoot().getProjects())
			try {
				if(!p.isAccessible())
					continue;
				if(p.getNature(PPUiConstants.PUPPET_NATURE_ID) != null)
					projects.add(p);
			}
			catch(CoreException e) {
				log.error("Failed to get puppet nature information from project", e);
			}
		setPriority(Job.BUILD);
		setRule(workspace.getRoot());
	}

	public PPBuildJob(IWorkspace workspace, Job jobToScheduleAfterBuild) {
		this(workspace, IncrementalProjectBuilder.FULL_BUILD, jobToScheduleAfterBuild);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			if(projects.isEmpty())
				return Status.OK_STATUS;

			final SubMonitor ticker = SubMonitor.convert(monitor, projects.size() * 100 * 2);

			MultiStatus status = new MultiStatus(
				PPActivator.getInstance().getBundle().getSymbolicName(), IStatus.OK, "Build failures", null);

			// Rebuild the given projects in the order stipulated by the workspace
			for(IBuildConfiguration buildConfig : ((Workspace) workspace).getBuildOrder()) {
				IProject p = buildConfig.getProject();
				if(projects.contains(p)) {
					try {
						ticker.setTaskName("Building project " + p.getName());
						p.build(buildType, ticker.newChild(100));
					}
					catch(CoreException e) {
						status.add(e.getStatus());
					}
				}
			}
			return status.isOK()
				? Status.OK_STATUS
				: status;

		}
		finally {
			if(jobToScheduleAfterBuild != null)
				jobToScheduleAfterBuild.schedule();
		}
	}
}
