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

	private boolean buildPending;

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

		synchronized(this) {
			if(!buildPending) {
				schedule(500);
				return Status.OK_STATUS;
			}
			buildPending = false;
		}

		// run a build. This job will be rescheduled once the build completes
		new PPBuildJob(workspace, this).schedule();
		return Status.OK_STATUS;
	}

	public synchronized void triggerBuild() {
		buildPending = true;
	}
}
