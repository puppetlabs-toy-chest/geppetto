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

import java.util.Map;

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Maps;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;

/**
 * A simple preference page for selection of puppet target.
 * This is also the puppet root preference pane.
 * <p>
 * Note: Previous versions used "2.8" but this version will never be released as 2.7 is the last in the 2.x series. The implementation below
 * corrects this by updating the preferences from "2.8" to "3.0" if the value "2.8" is encountered.
 * </p>
 */
public class PPGeneralPreferencePage extends AbstractPreferencePage {

	/**
	 * Specialized version that corrects stored "2.8" value for the "3.0" release.
	 */
	private static class PuppetVersionFieldEditor extends RadioGroupFieldEditor {

		private static final Map<String, String> deprecatedVersions;

		static {
			deprecatedVersions = Maps.newHashMap();
			deprecatedVersions.put("2.8", "3.0");
			deprecatedVersions.put("PE 2.0", "2.7");
			deprecatedVersions.put("PE 2.7", "2.7");
			deprecatedVersions.put("PE 2.8", "2.7");
			deprecatedVersions.put("PE 3.0", "3.0");
			deprecatedVersions.put("PE 3.1", "3.3");
			deprecatedVersions.put("PE 3.7", "3.7");
		}

		/**
		 * @param puppetTargetVersion
		 * @param string
		 * @param i
		 * @param strings
		 * @param fieldEditorParent
		 */
		public PuppetVersionFieldEditor(String puppetTargetVersion, String string, int i, String[][] strings, Composite fieldEditorParent) {
			super(puppetTargetVersion, string, i, strings, fieldEditorParent);
		}

		@Override
		protected void doLoad() {
			String v = getPreferenceStore().getString(getPreferenceName());
			if(v != null) {
				v = deprecatedVersions.get(v);
				if(v != null)
					getPreferenceStore().setValue(getPreferenceName(), v);
			}
			super.doLoad();
		}

		@Override
		protected void doLoadDefault() {
			String v = getPreferenceStore().getDefaultString(getPreferenceName());
			if(v != null) {
				v = deprecatedVersions.get(v);
				if(v != null)
					getPreferenceStore().setDefault(getPreferenceName(), v);
			}
			super.doLoadDefault();
		}
	}

	@Override
	protected void createFieldEditors() {
		PuppetTarget[] targets = PuppetTarget.values();
		String[][] strings = new String[targets.length][];
		for(int idx = 0; idx < targets.length; ++idx) {
			PuppetTarget target = targets[idx];
			strings[idx] = new String[] { target.getLiteral(), target.getLiteral() };
		}

		addField(new PuppetVersionFieldEditor(PPPreferenceConstants.PUPPET_TARGET_VERSION, //
			"Puppet target version", 1, strings, getFieldEditorParent()));
	}
}
