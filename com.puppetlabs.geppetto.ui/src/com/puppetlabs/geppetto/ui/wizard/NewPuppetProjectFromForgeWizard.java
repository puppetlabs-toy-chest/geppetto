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

import static com.puppetlabs.geppetto.common.Strings.trimToNull;
import static com.puppetlabs.geppetto.ui.UIPlugin.getLocalString;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.Modules;
import com.puppetlabs.geppetto.forge.v3.model.AbbrevRelease;
import com.puppetlabs.geppetto.forge.v3.model.Module;
import com.puppetlabs.geppetto.module.dsl.ui.quickfix.NewWithKeyword;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.dialog.ReleaseListSelectionDialog;
import com.puppetlabs.geppetto.ui.dialog.ReleaseListSelectionDialog.Elem;

public class NewPuppetProjectFromForgeWizard extends NewPuppetModuleProjectWizard implements NewWithKeyword {

	protected class PuppetProjectFromForgeCreationPage extends NewPuppetModuleProjectWizard.PuppetProjectCreationPage {
		private Listener keywordModifyListener = new Listener() {
			public void handleEvent(Event e) {
				selectButton.setEnabled(validateKeyword());
			}
		};

		protected Text projectNameField;

		protected Text keywordField;

		protected Text selectedReleaseField;

		protected Button selectButton;

		protected PuppetProjectFromForgeCreationPage(String pageName) {
			super(pageName);
			setNeedsProgressMonitor(true);
		}

		@Override
		public void createControl(Composite parent) {
			parent = new Composite(parent, SWT.NONE);

			GridLayout gridLayout = new GridLayout();
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;

			parent.setLayout(gridLayout);

			GridDataFactory.fillDefaults().applyTo(parent);

			Composite composite = new Composite(parent, SWT.NONE);

			gridLayout = new GridLayout(3, false);
			gridLayout.marginHeight = 10;
			gridLayout.marginWidth = 10;

			composite.setLayout(gridLayout);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(composite);

			createReleaseGroup(composite);

			super.createControl(parent);
			for(Control c1 : parent.getChildren())
				if(c1 instanceof Composite)
					for(Control c2 : ((Composite) c1).getChildren())
						if(c2 instanceof Composite)
							for(Control c3 : ((Composite) c2).getChildren())
								if(c3 instanceof Text && getProjectName().equals(((Text) c3).getText())) {
									projectNameField = (Text) c3;
									break;

								}
			setControl(parent);
		}

		protected void createReleaseGroup(Composite parent) {
			Label keywordLabel = new Label(parent, SWT.NONE);
			keywordLabel.setText(getLocalString("_UI_Keyword_label")); //$NON-NLS-1$

			keywordField = new Text(parent, SWT.BORDER);
			keywordField.addListener(SWT.Modify, keywordModifyListener);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(keywordField);

			selectButton = new Button(parent, SWT.PUSH);
			selectButton.setText(getLocalString("_UI_Select_label")); //$NON-NLS-1$
			selectButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					promptForReleaseSelection();
				}
			});
			selectButton.setEnabled(false);

			Label selectedReleaseLabel = new Label(parent, SWT.NONE);
			selectedReleaseLabel.setText(getLocalString("_UI_SelectedRelease_label")); //$NON-NLS-1$

			selectedReleaseField = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(selectedReleaseField);
		}

		protected List<Elem> getReleaseChoices(final String keyword) {
			try {
				final List<Elem> choices = new ArrayList<Elem>();
				getContainer().run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask("Fetching latest releases from the Forge", 55);
						try {
							final Exception[] te = new Exception[1];
							Thread t = new Thread() {
								@Override
								public void run() {
									try {
										for(Module module : moduleService.listAll(
											new Modules.WithText(keyword), Modules.LAST_RELEASED, false)) {
											for(AbbrevRelease ab : module.getReleases())
												choices.add(new Elem(ab));
										}
									}
									catch(SocketException e) {
										// A user abort will cause a "Socket closed" exception We don't
										// want to report that.
										if(!"Socket closed".equals(e.getMessage()))
											te[0] = e;
									}
									catch(Exception e) {
										te[0] = e;
									}
								}
							};
							t.start();
							int idx = 0;
							while(t.isAlive()) {
								t.join(1000);
								if(monitor.isCanceled()) {
									moduleService.abortCurrentRequest();
									throw new OperationCanceledException();
								}
								if(++idx <= 5)
									monitor.worked(1);
							}
							if(te[0] != null)
								throw new InvocationTargetException(te[0]);
						}
						catch(RuntimeException e) {
							throw e;
						}
						catch(InvocationTargetException e) {
							throw e;
						}
						catch(Exception e) {
							throw new InvocationTargetException(e);
						}
						finally {
							monitor.done();
						}
					}
				});
				if(!choices.isEmpty())
					return choices;
				MessageDialog.openConfirm(
					getShell(), getLocalString("_UI_No_modules_found"),
					getLocalString("_UI_No_module_matching_keyword", keyword));
			}
			catch(InvocationTargetException e) {
				Throwable t = e.getTargetException();
				StringBuilder builder = new StringBuilder();
				builder.append(t.getClass().getName());
				builder.append("\n");
				builder.append(t.getMessage());
				builder.append("\n\n(See the log view for technical details).");
				MessageDialog.openError(getShell(), "Error while communicating with the ForgeAPI.", //
					builder.toString()); //
				log.error("Error while communicating with the ForgeAPI", t);
			}
			catch(InterruptedException e) {
			}
			return Collections.emptyList();
		}

		protected void promptForReleaseSelection() {
			if(!validateKeyword())
				return;
			List<Elem> choices = getReleaseChoices(keywordField.getText());
			if(choices.isEmpty())
				return;

			ReleaseListSelectionDialog dialog = new ReleaseListSelectionDialog(getShell(), choices);
			if(release != null)
				dialog.setInitialElementSelections(Collections.singletonList(release));

			if(dialog.open() == Window.OK) {
				setRelease((Elem) dialog.getFirstResult());
			}
		}

		protected void setRelease(Elem selectedRelease) {
			release = selectedRelease.getVersionedName();
			selectedReleaseField.setText(release.toString());
			projectNameField.setText(release.getModuleName().getName());
			validatePage();
		}

		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);

			if(visible) {
				keywordField.setFocus();
				if(initialKeyword != null) {
					keywordField.setText(initialKeyword);
					initialKeyword = null;
					promptForReleaseSelection();
				}
			}
		}

		protected boolean validateKeyword() {
			String keyword = trimToNull(keywordField.getText());
			if(keyword == null) {
				setErrorMessage(getLocalString("_UI_Keyword_Search_at_least_one_character"));
				return false;
			}
			if(keyword.length() > 30) {
				setErrorMessage(getLocalString("_UI_Keyword_Search_max_length_exceeded", 30));
				return false;
			}
			Matcher m = OK_KEYWORD_CHARACTERS.matcher(keyword);
			if(!m.matches()) {
				setErrorMessage(getLocalString("_UI_Keyword_Search_bad_characters"));
				return false;
			}
			m = KEYWORD_AT_LEAST_ONE_CHARACTER.matcher(keyword);
			if(!m.find()) {
				setErrorMessage(getLocalString("_UI_Keyword_Search_at_least_one_character"));
				return false;
			}
			setErrorMessage(null);
			return true;
		}

		@Override
		protected boolean validatePage() {
			setErrorMessage(null);
			if(release == null) {
				if(!validateKeyword())
					return false;

				setMessage(getLocalString("_UI_ModuleCannotBeEmpty_message")); //$NON-NLS-1$
				return false;
			}

			if(super.validatePage()) {
				String preferredProjectName = release.getModuleName().getName();

				if(!preferredProjectName.equals(getProjectName())) {
					setMessage(
						getLocalString("_UI_ProjectNameShouldMatchModule_message", preferredProjectName), WARNING); //$NON-NLS-1$
				}

				return true;
			}
			return false;
		}
	}

	private static final Pattern OK_KEYWORD_CHARACTERS = Pattern.compile("^[0-9A-Za-z_/-]*$");

	private static final Pattern KEYWORD_AT_LEAST_ONE_CHARACTER = Pattern.compile("[A-Za-z]+");

	private static final Logger log = Logger.getLogger(NewPuppetProjectFromForgeWizard.class);

	@Inject
	private ForgeService forgeService;

	@Inject
	private Modules moduleService;

	protected VersionedName release;

	protected String initialKeyword;

	@Override
	protected String getProjectCreationPageDescription() {
		return getLocalString("_UI_PuppetProjectFromForge_description"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectCreationPageTitle() {
		return getLocalString("_UI_PuppetProjectFromForge_title"); //$NON-NLS-1$
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UIPlugin.getImageDesc("full/wizban/NewPuppetProject.png")); //$NON-NLS-1$
		setWindowTitle(getLocalString("_UI_NewPuppetProjectFromForge_title")); //$NON-NLS-1$
	}

	@Override
	protected void initializeProjectContents(IProgressMonitor monitor) throws Exception {
		if(release == null)
			return;

		SubMonitor submon = SubMonitor.convert(monitor, "Installing project...", 100);
		try {
			Version v = release.getVersion();
			VersionRange vr = v == null
					? null
					: VersionRange.exact(v);

			File projectDir = project.getLocation().toFile();
			forgeService.install(release.getModuleName(), vr, projectDir, true, true);
			submon.worked(70);
			project.refreshLocal(IResource.DEPTH_INFINITE, submon.newChild(30));
		}
		finally {
			monitor.done();
		}
	}

	@Override
	protected boolean mayHaveExistingContents() {
		return false;
	}

	@Override
	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
		return new PuppetProjectFromForgeCreationPage(pageName);
	}

	public void startWithKeyword(String keyword) {
		initialKeyword = keyword;
	}

}
