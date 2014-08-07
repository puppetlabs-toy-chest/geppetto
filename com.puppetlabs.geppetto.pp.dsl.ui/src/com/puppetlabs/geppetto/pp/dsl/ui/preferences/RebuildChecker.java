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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.ui.builder.PPBuildJob;

@Singleton
public class RebuildChecker extends Job {
	private final IWorkspace workspace;

	private int buildPending = 0;

	/**
	 * The purpose of the RebuildChecker is to collect/aggregate build triggering events
	 * to avoid having to issue multiple rebuilds for a set of changes.
	 * This job reschedules itself.
	 */
	@Inject
	public RebuildChecker(IWorkspace workspace) {
		super("Puppet RebuildChecker");
		setSystem(true);
		setUser(false);
		this.workspace = workspace;
		schedule();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if(monitor.isCanceled())
			return Status.CANCEL_STATUS;

		int buildType = 0;
		synchronized(this) {
			if(buildPending == 0) {
				schedule(500);
				return Status.OK_STATUS;
			}
			buildType = buildPending;

			// We let a clean build be followed by a full build
			if(buildPending == IncrementalProjectBuilder.CLEAN_BUILD)
				buildPending = IncrementalProjectBuilder.FULL_BUILD;
			else
				buildPending = 0;
		}

		// run a build. This job will be rescheduled once the build completes
		new PPBuildJob(workspace, buildType, this).schedule();
		return Status.OK_STATUS;
	}

	public synchronized void triggerBuild() {
		// Careful so that we don't demote the CLEAN_BUILD to a FULL_BUILD
		if(buildPending != IncrementalProjectBuilder.CLEAN_BUILD)
			buildPending = IncrementalProjectBuilder.FULL_BUILD;
	}

	public synchronized void triggerClean() {
		buildPending = IncrementalProjectBuilder.CLEAN_BUILD;
	}
}
