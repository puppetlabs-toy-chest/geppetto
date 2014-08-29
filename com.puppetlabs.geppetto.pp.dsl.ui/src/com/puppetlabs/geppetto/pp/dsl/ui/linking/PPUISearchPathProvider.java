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
package com.puppetlabs.geppetto.pp.dsl.ui.linking;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPathProvider;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;

/**
 * A Puppet SearchPathProvider based on preferences.
 */

public class PPUISearchPathProvider extends PPSearchPathProvider {
	@Inject
	IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	private IAllContainersState allContainers;

	@Inject
	private IWorkspace workspace;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPathProvider#get(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public PPSearchPath get(Resource r) {
		String handle = allContainers.getContainerHandle(r.getURI());
		// get project
		IProject project = workspace.getRoot().getProject(handle);

		// get project specific preference and use them if they are enabled
		IPreferenceStore store = preferenceStoreAccess.getContextPreferenceStore(project);
		return PPSearchPath.fromString(
			store.getString(PPPreferenceConstants.PUPPET_PROJECT_PATH), URI.createFileURI(project.getLocation().toOSString()),
			store.getString(PPPreferenceConstants.PUPPET_ENVIRONMENT), store.getString(PPPreferenceConstants.PUPPET_MANIFEST_DIR));

	}
}
