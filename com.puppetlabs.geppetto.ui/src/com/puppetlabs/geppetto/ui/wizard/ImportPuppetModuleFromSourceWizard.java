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

import static com.puppetlabs.geppetto.ui.UIPlugin.getLocalString;
import static java.lang.String.format;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.internal.ide.dialogs.IDEResourceInfoUtils;
import org.eclipse.ui.internal.ide.dialogs.IFileStoreFilter;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.Strings;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ui.internal.Activator;
import com.puppetlabs.geppetto.ui.UIPlugin;

public class ImportPuppetModuleFromSourceWizard extends AbstractPuppetModuleWizard implements IImportWizard {
	/**
	 * A label provider that distinguishes between files, folders, module folders, and if the folder or module
	 * is a project or not. The standard icon for file, folder, and project is used. A module will have an folder
	 * or project icont that has the puppet project overlay
	 */
	private static class FileLabelProvider extends LabelProvider {
		private static final ImageDescriptor MODULE_OVL = UIPlugin.getImageDesc("obj8/puppet-7x8px.png");

		private static final Image IMG_FOLDER = PlatformUI.getWorkbench().getSharedImages().getImage(
			ISharedImages.IMG_OBJ_FOLDER);

		private static final Image IMG_OBJ_PROJECT = PlatformUI.getWorkbench().getSharedImages().getImage(
			org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);

		private static final Image IMG_FILE = PlatformUI.getWorkbench().getSharedImages().getImage(
			ISharedImages.IMG_OBJ_FILE);

		private static final Image IMG_MODULE_PROJECT = new DecorationOverlayIcon(
			IMG_OBJ_PROJECT, MODULE_OVL, IDecoration.TOP_RIGHT).createImage();

		private static final Image IMG_MODULE = new DecorationOverlayIcon(IMG_FOLDER, MODULE_OVL, IDecoration.TOP_RIGHT).createImage();

		@Override
		public Image getImage(Object element) {
			if(element instanceof IFileStore) {
				IFileStore curr = (IFileStore) element;
				if(curr.fetchInfo().isDirectory()) {
					return isModule(curr)
							? (isProject(curr)
									? IMG_MODULE_PROJECT
											: IMG_MODULE)
											: (isProject(curr)
													? IMG_OBJ_PROJECT
															: IMG_FOLDER);
				}
				return IMG_FILE;
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if(element instanceof IFileStore) {
				return ((IFileStore) element).getName();
			}
			return super.getText(element);
		}
	}

	/**
	 * A content provider that servers up directories only and excludes all directories
	 * that starts with a dot. It also excludes the workspace root and any folder that is
	 * an imported project.
	 */
	private class FolderContentProvider implements ITreeContentProvider {
		private final IFileStoreFilter fileFilter;

		public FolderContentProvider() {
			fileFilter = new IFileStoreFilter() {
				@Override
				public boolean accept(IFileStore fs) {
					if(fs.getName().startsWith("."))
						return false;
					try {
						File file = fs.toLocalFile(EFS.NONE, null);
						if(!file.isDirectory())
							return false;

						// Exclude the workspace itself and projects that are already
						// in the workspace
						IContainer wsFile = workspace.getRoot().getContainerForLocation(
							Path.fromOSString(file.getAbsolutePath()));
						if(wsFile != null) {
							switch(wsFile.getType()) {
								case IResource.PROJECT:
								case IResource.ROOT:
									return false;
							}
						}
						return true;
					}
					catch(CoreException e) {
						return false;
					}
				}
			};
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return parentElement instanceof IFileStore
					? IDEResourceInfoUtils.listFileStores(
						(IFileStore) parentElement, fileFilter, new NullProgressMonitor())
						: EMPTY;
		}

		@Override
		public Object[] getElements(Object element) {
			return getChildren(element);
		}

		@Override
		public Object getParent(Object element) {
			return element instanceof IFileStore
					? ((IFileStore) element).getParent()
							: null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private class ImportModuleFromSourcePage extends WizardPage {
		protected Text rootDirectoryField;

		protected Text projectNameField;

		protected Label projectNameInfoField;

		protected Button browseButton;

		private ImportModuleFromSourcePage(String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(2, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);

			createReleaseGroup(composite);
			setControl(composite);
			Dialog.applyDialogFont(composite);
		}

		protected void createReleaseGroup(Composite parent) {
			Label moduleDirLabel = new Label(parent, SWT.NONE);
			moduleDirLabel.setText(getLocalString("_UI_SelectRootDirectory_label"));

			Composite moduleDirGroup = new Composite(parent, SWT.NONE);
			moduleDirGroup.setLayout(new GridLayout(2, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(moduleDirGroup);
			rootDirectoryField = new Text(moduleDirGroup, SWT.BORDER);
			rootDirectoryField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					setProjectName();
				}
			});
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(rootDirectoryField);
			browseButton = new Button(moduleDirGroup, SWT.PUSH);
			browseButton.setText(getLocalString("_UI_Browse_label"));
			browseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent se) {
					selectModuleDir();
				}
			});

			Label projectNameLabel = new Label(parent, SWT.NONE);
			projectNameLabel.setText(getLocalString("_UI_ProjectName_label"));
			Composite projectNameGroup = new Composite(parent, SWT.NONE);
			projectNameGroup.setLayout(new GridLayout(2, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(projectNameGroup);
			projectNameField = new Text(projectNameGroup, SWT.BORDER);
			projectNameField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					validatePage();
				}
			});
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(projectNameField);
			projectNameInfoField = new Label(projectNameGroup, SWT.NONE);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).minSize(250, SWT.DEFAULT).grab(true, false).applyTo(
				projectNameInfoField);
		}

		private IStatus nope(String msg) {
			return nope(msg, null);
		}

		private IStatus nope(String msg, Throwable e) {
			return new Status(IStatus.ERROR, Activator.getId(), msg, e);
		}

		protected void selectModuleDir() {
			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(), new FileLabelProvider(), new FolderContentProvider());

			String moduleDir = Strings.trimToNull(rootDirectoryField.getText());
			if(moduleDir == null)
				moduleDir = System.getProperty("user.home");
			IPath selectionPath = Path.fromOSString(moduleDir);

			dialog.setTitle(getLocalString("_UI_BrowseModules_label"));
			dialog.setMessage(getLocalString("_UI_BrowseModules_description"));
			dialog.setValidator(new ISelectionStatusValidator() {
				@Override
				public IStatus validate(Object[] selection) {
					if(selection.length < 1)
						return nope("");
					if(selection.length > 1)
						return nope("Only one module can be selected");

					IFileStore fs = (IFileStore) selection[0];
					if(!fs.fetchInfo().isDirectory())
						return nope(fs.getName() + " is not folder");

					return Status.OK_STATUS;
				}
			});
			try {
				dialog.setInput(EFS.getStore(URIUtil.toURI(selectionPath)));
				if(dialog.open() == Window.CANCEL)
					return;

				Object[] result = dialog.getResult();
				if(result.length != 1)
					return;

				IFileStore fs = (IFileStore) result[0];
				File file = fs.toLocalFile(EFS.NONE, null);
				rootDirectoryField.setText(file.getAbsolutePath());
			}
			catch(CoreException e) {
				ErrorDialog.openError(getShell(), null, null, e.getStatus());
			}
		}

		private void setProjectName() {
			String proposedName = null;
			boolean isProject = false;
			boolean isDir = false;
			String moduleDir = Strings.trimToNull(rootDirectoryField.getText());
			IPath modulePath = null;
			if(moduleDir != null) {
				File moduleRoot = new File(moduleDir);
				isDir = moduleRoot.isDirectory();
				if(isDir) {
					modulePath = Path.fromOSString(moduleRoot.getAbsolutePath());
					try {
						File projectDesc = new File(moduleRoot, IProjectDescription.DESCRIPTION_FILE_NAME);
						isProject = projectDesc.canRead();
						if(isProject) {
							IProjectDescription desc = ((Workspace) workspace).loadProjectDescription(modulePath.append(IProjectDescription.DESCRIPTION_FILE_NAME));
							proposedName = desc.getName();
						}
						else {
							Metadata md = getForge().createFromModuleDirectory(moduleRoot, null, null, new Diagnostic());
							if(md != null) {
								ModuleName mn = md.getName();
								if(mn != null)
									proposedName = mn.getName();
							}
						}
					}
					catch(Exception e) {
					}
					if(proposedName == null)
						proposedName = moduleRoot.getName();
				}
			}
			projectNameField.setEditable(!isProject);
			proposedName = Strings.trimToNull(proposedName);
			if(!(isProject || proposedName == null))
				proposedName = ModuleName.safeName(proposedName, false);

			projectNameField.setText(proposedName == null
					? ""
							: proposedName);
			projectNameInfoField.setText(isProject
				? "(derived from existing .project file)"
						: "");
			validatePage();
		}

		private void validatePage() {
			setErrorMessage(null);
			setMessage(getLocalString("_UI_BrowseModules_description"));
			setPageComplete(false);

			String moduleDir = Strings.trimToNull(rootDirectoryField.getText());
			if(moduleDir == null) {
				setErrorMessage("A module directory must be entered");
				return;
			}

			String projectName = Strings.trimToNull(projectNameField.getText());
			if(projectName == null) {
				setErrorMessage("A project name must be entered");
				return;
			}

			File moduleRoot = new File(moduleDir);
			if(!moduleRoot.isDirectory()) {
				setErrorMessage(format("%s is not a directory", moduleDir));
				return;
			}

			if(!isModule(moduleRoot))
				setMessage(
					format("%s is not a module (contains neither metadata.json nor Modulefile)", moduleDir), WARNING);

			IProject project = workspace.getRoot().getProject(projectName);
			if(project.exists()) {
				setErrorMessage(format("A project named '%s' already exists", projectName));
				return;
			}

			IPath modulePath = Path.fromOSString(moduleRoot.getAbsolutePath());
			projectLocation = Platform.getLocation().equals(modulePath.removeLastSegments(1))
					? null
							: modulePath;
			projectContainer = Path.fromOSString(projectName);
			setPageComplete(true);
		}
	}

	private static boolean isModule(File file) {
		return new File(file, Forge.METADATA_JSON_NAME).exists() || new File(file, Forge.MODULEFILE_NAME).exists();
	}

	private static boolean isModule(IFileStore fs) {
		try {
			return isModule(fs.toLocalFile(EFS.NONE, null));
		}
		catch(CoreException e) {
			return false;
		}
	}

	private static boolean isProject(IFileStore fs) {
		return fs.getChild(IProjectDescription.DESCRIPTION_FILE_NAME).fetchInfo().exists();
	}

	private static final Object[] EMPTY = new Object[0];

	@Inject
	private IWorkspace workspace;

	@Override
	protected boolean generateContent() {
		return false;
	}

	@Override
	protected String getWizardPageDescriptionKey() {
		return "_UI_PuppetModuleFromSource_description"; //$NON-NLS-1$
	}

	@Override
	protected String getWizardPageTitleKey() {
		return "_UI_PuppetModuleFromSource_title"; //$NON-NLS-1$
	}

	@Override
	protected String getWizardWindowTitleKey() {
		return "_UI_ImportPuppetModule_title"; //$NON-NLS-1$
	}

	@Override
	protected WizardPage newProjectCreationPage(String pageName) {
		return new ImportModuleFromSourcePage(pageName);
	}
}
