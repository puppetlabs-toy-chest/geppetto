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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.ide.DialogUtil;

import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.util.ResourceUtil;

public abstract class AbstractPuppetProjectWizard extends Wizard implements IWorkbenchWizard {

	protected class PuppetProjectCreationPage extends WizardNewProjectCreationPage {

		protected PuppetProjectCreationPage(String pageName) {
			super(pageName);
			setInitialProjectName("unnamed");
		}

		@Override
		protected boolean validatePage() {
			if(super.validatePage()) {
				IPath locationPath = getLocationPath();
				projectLocation = Platform.getLocation().equals(locationPath)
						? null
						: locationPath;
				projectContainer = getProjectHandle().getFullPath();
				return true;
			}
			return false;
		}
	}

	protected IPath projectLocation;

	protected IPath projectContainer;

	protected IProject project;

	@Override
	public void addPages() {
		WizardPage newProjectCreationPage = newProjectCreationPage("NewProjectCreationPage"); //$NON-NLS-1$

		newProjectCreationPage.setTitle(UIPlugin.getLocalString(getWizardPageTitleKey()));
		newProjectCreationPage.setDescription(UIPlugin.getLocalString(getWizardPageDescriptionKey()));

		addPage(newProjectCreationPage);
	}

	protected boolean generateContent() {
		return true;
	}

	protected abstract String getWizardPageDescriptionKey();

	protected abstract String getWizardPageTitleKey();

	protected abstract String getWizardWindowTitleKey();

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UIPlugin.getImageDesc("full/wizban/NewPuppetProject.png")); //$NON-NLS-1$
		setWindowTitle(UIPlugin.getLocalString(getWizardWindowTitleKey())); //$NON-NLS-1$
	}

	protected abstract void initializeProjectContents(IProgressMonitor monitor) throws Exception;

	/**
	 * When creating a new Puppet Module we might end up in the wizard from an import operation such
	 * as "Import projects from Git". When this happens, we want to preserve the contents. Subclasses
	 * that actually perform import (such as the ImportPuppetModuleFromForgeWizard) must instead ask
	 * the user if existing content should be cleared prior to import.
	 *
	 * @return
	 */
	protected boolean mayHaveExistingContents() {
		return true;
	}

	protected WizardPage newProjectCreationPage(String pageName) {
		return new PuppetProjectCreationPage(pageName);
	}

	@Override
	public boolean performFinish() {
		try {
			project = null;
			getContainer().run(false, false, new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) throws InvocationTargetException {
					SubMonitor monitor = SubMonitor.convert(progressMonitor, 100);
					try {
						String projectName = projectContainer.segment(0);
						if(projectLocation == null)
							projectLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(projectName);
						File projectDir = projectLocation.toFile();
						if(projectDir.exists() && !mayHaveExistingContents()) {
							if(!MessageDialog.openConfirm(
								getShell(), UIPlugin.getLocalString("_UI_Confirm_Overwrite"),
								UIPlugin.getLocalString("_UI_Directory_not_empty", projectDir.getAbsolutePath())))
								// User don't want us to overwrite
								return;

							FileUtils.rmR(projectDir);
						}

						project = ResourceUtil.createProject(
							projectContainer, URI.createFileURI(projectDir.getAbsolutePath()),
							Collections.<IProject> emptyList(), monitor.newChild(1));

						initializeProjectContents(monitor.newChild(80));
					}
					catch(Exception exception) {
						throw new InvocationTargetException(exception);
					}
					finally {
						progressMonitor.done();
					}
				}
			});
			return project != null;
		}
		catch(InvocationTargetException e) {
			Throwable t = e.getTargetException();
			String title = UIPlugin.getLocalString("_UI_CreateProject_title");
			if(t instanceof PartInitException)
				DialogUtil.openError(getShell(), title, t.getMessage(), (PartInitException) t);
			else if(t instanceof CoreException)
				ErrorDialog.openError(getShell(), title, t.getMessage(), ((CoreException) t).getStatus());
			else
				MessageDialog.openError(getShell(), title, t.getMessage());
		}
		catch(InterruptedException e) {
		}
		return false;
	}
}
