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
package com.puppetlabs.geppetto.ui.wizard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;

import com.puppetlabs.geppetto.ui.util.ResourceUtil;

/**
 * A New Wizard for a free-form puppet project.
 * Does not create any content except the project.
 */
public class NewPuppetProjectWizard extends AbstractPuppetProjectWizard implements INewWizard {

	@Override
	protected String getWizardPageDescriptionKey() {
		return "_UI_PuppetProject_description"; //$NON-NLS-1$
	}

	@Override
	protected String getWizardPageTitleKey() {
		return "_UI_PuppetProject_title";
	}

	@Override
	protected String getWizardWindowTitleKey() {
		return "_UI_NewPuppetProject_title";
	}

	@Override
	protected void initializeProjectContents(IProgressMonitor monitor) throws Exception {
		// no content generated
	}

	@Override
	public boolean performFinish() {
		boolean result = super.performFinish();
		if(!result)
			return result;
		ResourceUtil.selectFile(project);
		return true;

	}
}
