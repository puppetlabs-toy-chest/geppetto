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
package com.puppetlabs.geppetto.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;

import com.puppetlabs.geppetto.forge.model.ModuleName;

/**
 * @author thhal
 *
 */
public class NewPuppetModuleProjectWizard extends AbstractPuppetModuleWizard implements INewWizard {
	protected class PuppetModuleCreationPage extends PuppetProjectCreationPage {

		protected PuppetModuleCreationPage(String pageName) {
			super(pageName);
			setInitialProjectName("unnamed");
		}

		@Override
		protected boolean validatePage() {
			try {
				ModuleName.checkName(getProjectName(), true);
				return super.validatePage();
			}
			catch(IllegalArgumentException e) {
				setErrorMessage("Project name must be a valid module name: " + e.getMessage());
				return false;
			}
		}
	}

	@Override
	protected String getWizardPageDescriptionKey() {
		return "_UI_PuppetModule_description"; //$NON-NLS-1$
	}

	@Override
	protected String getWizardPageTitleKey() {
		return "_UI_PuppetModule_title"; //$NON-NLS-1$
	}

	@Override
	protected String getWizardWindowTitleKey() {
		return "_UI_NewPuppetModule_title"; //$NON-NLS-1$
	}

	@Override
	protected WizardPage newProjectCreationPage(String pageName) {
		return new PuppetModuleCreationPage(pageName);
	}

}
