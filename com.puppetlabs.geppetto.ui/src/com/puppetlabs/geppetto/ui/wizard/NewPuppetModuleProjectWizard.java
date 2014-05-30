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

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.ide.DialogUtil;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.common.os.StreamUtil.OpenBAStream;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.client.ForgeException;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ui.preferences.ModulePreferencesHelper;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.util.ResourceUtil;

public class NewPuppetModuleProjectWizard extends Wizard implements INewWizard {

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
				try {
					ModuleName.checkName(getProjectName(), true);
				}
				catch(IllegalArgumentException e) {
					setErrorMessage("Project name must be a valid module name: " + e.getMessage());
					return false;
				}
				return true;
			}

			return false;
		}

	}

	@Inject
	private Forge forge;

	@Inject
	protected ModulePreferencesHelper preferenceHelper;

	protected IPath projectLocation;

	protected IPath projectContainer;

	protected IProject project;

	@Override
	public void addPages() {
		WizardNewProjectCreationPage newProjectCreationPage = newProjectCreationPage("NewProjectCreationPage"); //$NON-NLS-1$

		newProjectCreationPage.setTitle(getProjectCreationPageTitle());
		newProjectCreationPage.setDescription(getProjectCreationPageDescription());

		addPage(newProjectCreationPage);
	}

	protected void createMetadataJSONFromLegacyModulefile(IFile moduleFile, IProgressMonitor monitor) throws Exception {
		IFile mdjson = moduleFile.getParent().getFile(Path.fromPortableString(METADATA_JSON_NAME));
		if(mdjson.exists())
			return;

		Diagnostic diag = new Diagnostic();
		Metadata md = forge.loadModulefile(moduleFile.getFullPath().toFile(), diag);
		if(diag.getSeverity() >= Diagnostic.ERROR) {
			Exception ex = diag.getException();
			if(ex == null)
				ex = new ForgeException(diag.getMessage());
			throw ex;
		}
		if(md == null)
			return;

		OpenBAStream oba = new OpenBAStream();
		PrintStream ps = new PrintStream(oba);
		ps.print(md.toString());
		ps.close();
		mdjson.create(oba.getInputStream(), IResource.DERIVED, monitor);
	}

	protected Forge getForge() {
		return forge;
	}

	private String getModuleOwner() {
		String moduleOwner = preferenceHelper.getForgeLogin();
		if(moduleOwner == null)
			moduleOwner = ModuleName.safeOwner(System.getProperty("user.name"));
		return moduleOwner;
	}

	protected String getProjectCreationPageDescription() {
		return UIPlugin.getLocalString("_UI_PuppetModuleProject_description"); //$NON-NLS-1$
	}

	protected String getProjectCreationPageTitle() {
		return UIPlugin.getLocalString(getProjectCreationPageTitleKey()); //$NON-NLS-1$
	}

	protected String getProjectCreationPageTitleKey() {
		return "_UI_PuppetModuleProject_title";
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UIPlugin.getImageDesc("full/wizban/NewPuppetProject.png")); //$NON-NLS-1$
		setWindowTitle(UIPlugin.getLocalString("_UI_NewPuppetModuleProject_title")); //$NON-NLS-1$
	}

	protected void initializeProjectContents(IProgressMonitor monitor) throws Exception {
		SubMonitor submon = SubMonitor.convert(monitor, 100);
		Metadata metadata = new Metadata();
		metadata.setName(ModuleName.create(getModuleOwner(), project.getName().toLowerCase(), true));
		metadata.setVersion(Version.fromString("0.1.0"));

		if(ResourceUtil.getFile(project.getFullPath().append("manifests/init.pp")).exists()) { //$NON-NLS-1$
			File metadataFile = project.getLocation().append(METADATA_JSON_NAME).toFile();
			submon.worked(20);

			if(!metadataFile.exists())
				forge.saveJSONMetadata(metadata, metadataFile);
			submon.worked(50);
		}
		else {
			forge.generate(project.getLocation().toFile(), metadata);
			submon.worked(70);
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, submon.newChild(30));
		monitor.done();
	}

	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
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
						if(projectDir.exists()) {
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
						IFile metadataFile = ResourceUtil.getFile(project.getFullPath().append(METADATA_JSON_NAME));
						if(!metadataFile.exists()) {
							IFile modulefile = ResourceUtil.getFile(project.getFullPath().append(MODULEFILE_NAME));
							if(modulefile.exists()) {
								createMetadataJSONFromLegacyModulefile(modulefile, monitor.newChild(1));
							}
						}

						if(metadataFile.exists()) {
							if(metadataFile.isDerived())
								metadataFile.setDerived(false, monitor.newChild(1));
							else
								monitor.worked(1);

							ResourceUtil.selectFile(metadataFile);
							try {
								ResourceUtil.openEditor(metadataFile);
							}
							catch(PartInitException partInitException) {
								MessageDialog.openError(
									getShell(),
									UIPlugin.getLocalString("_UI_OpenEditor_title"), partInitException.getMessage()); //$NON-NLS-1$
							}
						}
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
