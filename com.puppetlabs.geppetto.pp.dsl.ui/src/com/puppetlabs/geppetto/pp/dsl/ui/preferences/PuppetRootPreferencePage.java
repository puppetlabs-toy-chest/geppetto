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

//import org.eclipse.jface.preference.PathEditor;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.GlobPatternFieldEditor;

/**
 * A simple preference page for search path and environment
 */
public class PuppetRootPreferencePage extends AbstractRebuildingPreferencePage {

	private static final String PAGE_ID = "com.puppetlabs.geppetto.puppet.ui";

	@Override
	protected void createFieldEditors() {
		addField(new GlobPatternFieldEditor(getPreferenceId(), //
			"Glob Exclude Patterns (relative to workspace root)", getFieldEditorParent()) {

			@Override
			public void load() {
				// must clear before loading since a switch to project specific otherwise gets
				// the default content + the project specific content.
				getList().removeAll();
				super.load();
			}
		});
	}

	@Override
	protected String getPreferenceId() {
		return PPPreferenceConstants.PUPPET_EXCLUDE_GLOBS;
	}

	@Override
	protected String qualifiedName() {
		return PAGE_ID;
	}
}
