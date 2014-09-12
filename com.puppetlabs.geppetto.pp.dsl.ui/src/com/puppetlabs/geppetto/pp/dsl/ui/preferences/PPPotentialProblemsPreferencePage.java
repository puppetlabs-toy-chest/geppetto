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

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.ValidationPreferenceFieldEditor;

/**
 * A preference pane for potential problems.
 */
public class PPPotentialProblemsPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, "Interpolated hyphen without surrounding {}", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, "Strings containing \"false\" or \"true\"", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, "Missing 'default' in selector", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_STRING, "Assignment to $string", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_TRUSTED, "Assignment to $trusted", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_VALIDITY_ASSERTED_AT_RUNTIME, "Validity not asserted until runtime", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_DEPRECATED_PLUS_EQUALS, "Use of deprecated += operator", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_DEPRECATED_IMPORT, "Use of deprecated 'import' keyword", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_DEPRECATED_VARIABLE_NAME, "Use of deprecated variable name", getFieldEditorParent()));
	}

}
