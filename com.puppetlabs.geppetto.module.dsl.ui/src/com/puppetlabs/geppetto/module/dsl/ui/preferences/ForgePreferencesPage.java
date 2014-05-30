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
package com.puppetlabs.geppetto.module.dsl.ui.preferences;

import org.eclipse.jface.preference.StringFieldEditor;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.ModuleName.BadOwnerCharactersException;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;

/**
 * A simple preference page for reference to the puppet forge.
 */
public class ForgePreferencesPage extends AbstractPreferencePage implements ModulePreferenceConstants {

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(FORGE_LOCATION, "Module Forge Service URL", getFieldEditorParent()));
		addField(new StringFieldEditor(FORGE_LOGIN, "Module Forge Login", getFieldEditorParent()) {
			@Override
			protected boolean checkState() {
				if(super.checkState()) {
					try {
						ModuleName.checkOwner(getStringValue(), true);
						return true;
					}
					catch(BadOwnerCharactersException e) {
						setErrorMessage(e.getMessage());
						showErrorMessage();
					}
				}
				return false;
			}
		});
	}
}
