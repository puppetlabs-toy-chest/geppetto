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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.StringFieldEditor;

/**
 * A simple preference page for puppet manifestdir
 */
public class PPManifestDirPreferencePage extends AbstractRebuildingPreferencePage {

	private static final String PAGE_ID = "com.puppetlabs.geppetto.pp.dsl.PP.manifestDir";

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(getPreferenceId(), "Manifest Directory", getFieldEditorParent()));
	}

	@Override
	protected String getPreferenceId() {
		return PPPreferenceConstants.PUPPET_MANIFEST_DIR;
	}

	@Override
	protected String qualifiedName() {
		return PAGE_ID;
	}
}
