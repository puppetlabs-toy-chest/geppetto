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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;

import com.google.inject.Inject;
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

public abstract class AbstractPuppetModuleWizard extends AbstractPuppetProjectWizard {

	@Inject
	private Forge forge;

	@Inject
	protected ModulePreferencesHelper preferenceHelper;

	protected Metadata createMetadataJSONFromLegacyModulefile(IFile moduleFile) throws Exception {
		IFile mdjson = moduleFile.getParent().getFile(Path.fromPortableString(METADATA_JSON_NAME));
		if(mdjson.exists())
			return null;

		Diagnostic diag = new Diagnostic();
		Metadata md = forge.loadModulefile(moduleFile.getFullPath().toFile(), diag);
		if(diag.getSeverity() >= Diagnostic.ERROR) {
			Exception ex = diag.getException();
			if(ex == null)
				ex = new ForgeException(diag.getMessage());
			throw ex;
		}
		if(md == null)
			return null;

		OpenBAStream oba = new OpenBAStream();
		PrintStream ps = new PrintStream(oba);
		ps.print(md.toString());
		ps.close();
		mdjson.create(oba.getInputStream(), IResource.DERIVED, new NullProgressMonitor());
		return md;
	}

	protected Forge getForge() {
		return forge;
	}

	protected String getModuleOwner() {
		String moduleOwner = preferenceHelper.getForgeLogin();
		if(moduleOwner == null)
			moduleOwner = ModuleName.safeOwner(System.getProperty("user.name"));
		return moduleOwner;
	}

	@Override
	protected void initializeProjectContents(IProgressMonitor monitor) throws Exception {
		SubMonitor submon = SubMonitor.convert(monitor, 100);
		File metadataFile = project.getLocation().append(METADATA_JSON_NAME).toFile();
		if(metadataFile.exists())
			submon.worked(70);
		else {
			submon.worked(20);
			IFile modulefile = ResourceUtil.getFile(project.getFullPath().append(MODULEFILE_NAME));
			Metadata metadata = null;
			if(modulefile.exists())
				metadata = createMetadataJSONFromLegacyModulefile(modulefile);
			submon.worked(20);

			if(metadata == null) {
				metadata = new Metadata();
				metadata.setName(ModuleName.create(getModuleOwner(), project.getName().toLowerCase(), true));
				metadata.setVersion(Version.fromString("0.1.0"));
				forge.saveJSONMetadata(metadata, metadataFile);
			}

			if(generateContent() && !ResourceUtil.getFile(project.getFullPath().append("manifests/init.pp")).exists()) { //$NON-NLS-1$
				forge.generate(project.getLocation().toFile(), metadata);
				submon.worked(30);
			}
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, submon.newChild(30));
		monitor.done();
	}

	@Override
	public boolean performFinish() {
		if(!super.performFinish())
			return false;

		try {
			IFile metadataFile = ResourceUtil.getFile(project.getFullPath().append(METADATA_JSON_NAME));
			if(!metadataFile.exists()) {
				IFile modulefile = ResourceUtil.getFile(project.getFullPath().append(MODULEFILE_NAME));
				if(modulefile.exists())
					createMetadataJSONFromLegacyModulefile(modulefile);
			}

			if(metadataFile.exists()) {
				if(metadataFile.isDerived())
					metadataFile.setDerived(false, new NullProgressMonitor());

				ResourceUtil.selectFile(metadataFile);
				try {
					ResourceUtil.openEditor(metadataFile);
				}
				catch(PartInitException partInitException) {
					MessageDialog.openError(getShell(), UIPlugin.getLocalString("_UI_OpenEditor_title"), partInitException.getMessage()); //$NON-NLS-1$
				}
			}
			return true;
		}
		catch(Exception e) {
			MessageDialog.openError(getShell(), UIPlugin.getLocalString("_UI_OpenEditor_title"), e.getMessage()); //$NON-NLS-1$
			return false;
		}
	}
}
