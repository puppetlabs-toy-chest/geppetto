package com.puppetlabs.geppetto.ui.wizard;

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;
import static com.puppetlabs.geppetto.forge.model.Constants.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ui.preferences.ModulePreferencesHelper;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.ui.UIPlugin;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file called &quot;Modulefile&quot;.
 */

public class NewMetadataFileWizard extends BasicNewResourceWizard implements INewWizard {
	public class NewMetadatatJSONWizardPage extends WizardNewFileCreationPage {

		public NewMetadatatJSONWizardPage(IStructuredSelection selection) {
			super("newPuppetMetadataPage", selection);
			setTitle("Puppet metadata.json File");
			setFileName(METADATA_JSON_NAME);
			setFileExtension("");
			setDescription("This wizard creates a new metadata.json (meta data for a puppet module).");
		}

		@Override
		protected void createAdvancedControls(Composite parent) {
			// DO NOTHING - Not meaningful to link (user can always use general "create file" if this is wanted.
		}

		@Override
		protected void createLinkTarget() {
			// DO NOTHING - The advanced linked resource section is not created.
		}

		@Override
		protected InputStream getInitialContents() {
			// Cheat by creating content manually here - not really worth the trouble of creating a
			// model to get these empty strings
			//
			Metadata md = new Metadata();
			IPath path = getContainerFullPath();
			String folderName = path.lastSegment();
			md.setName(ModuleName.create(getModuleOwner(), folderName, false));
			md.setVersion(Version.create(0, 1, 0));
			return new ByteArrayInputStream(md.toString().getBytes(UTF_8));
		}

		@Override
		protected IStatus validateLinkedResource() {
			return Status.OK_STATUS;
		}

		@Override
		protected boolean validatePage() {
			boolean valid = super.validatePage();
			if(!getFileName().equals(MODULEFILE_NAME)) {
				setErrorMessage("File name must be '" + MODULEFILE_NAME + '\'');
				valid = false;
			}
			return valid;
		}
	}

	@Inject
	protected ModulePreferencesHelper preferenceHelper;

	// private ISelection selection;

	private NewMetadatatJSONWizardPage page;

	/**
	 * Constructor for NewManifestWizard.
	 */
	public NewMetadataFileWizard() {
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new NewMetadatatJSONWizardPage(selection);
		addPage(page);
	}

	private String getModuleOwner() {
		String moduleOwner = preferenceHelper.getForgeLogin();
		if(moduleOwner == null)
			moduleOwner = ModuleName.safeOwner(System.getProperty("user.name"));
		return moduleOwner;
	}

	@Override
	protected void initializeDefaultPageImageDescriptor() {
		ImageDescriptor desc = UIPlugin.getImageDesc("full/wizban/NewPuppetManifest.png");
		if(desc == null)
			desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfile_wiz.png");//$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	@Override
	public boolean performFinish() {
		// Open editor on new file.
		try {
			final IFile file = page.createNewFile();
			if(file == null)
				return false;

			selectAndReveal(file);

			getContainer().run(false, false, new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) throws InvocationTargetException {
					IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
					if(dw != null)
						try {
							IWorkbenchPage page = dw.getActivePage();
							if(page != null)
								IDE.openEditor(page, file, true);
						}
						catch(Exception e) {
							throw new InvocationTargetException(e);
						}
				}
			});

			return true;
		}
		catch(InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if(t instanceof PartInitException)
				DialogUtil.openError(
					getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage(), (PartInitException) t);
			else if(t instanceof CoreException)
				ErrorDialog.openError(
					getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage(),
					((CoreException) t).getStatus());
			else
				MessageDialog.openError(getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage());
		}
		catch(InterruptedException e) {
		}
		return false;
	}

}
