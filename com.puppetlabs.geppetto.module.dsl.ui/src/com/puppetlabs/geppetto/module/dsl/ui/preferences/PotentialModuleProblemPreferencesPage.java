package com.puppetlabs.geppetto.module.dsl.ui.preferences;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.ValidationPreferenceFieldEditor;

public class PotentialModuleProblemPreferencesPage extends AbstractPreferencePage implements ModulePreferenceConstants {
	@Override
	protected void createFieldEditors() {
		addField(new ValidationPreferenceFieldEditor(PROBLEM_CIRCULAR_DEPENDENCY, //
			"Circular Dependency", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_DEPENDENCY_DECLARED_MORE_THAN_ONCE, //
			"Dependency Declared More Than Once", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_DEPENDENCY_VERSION_MISMATCH, //
			"Dependency Version Mismatch", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MODULE_CLASS_NOT_IN_INIT_PP, //
			"Module Class not defined in manifests/init.pp", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MODULE_NAME_NOT_STRICT, //
			"Module Name not Strictly Correct", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MODULE_REDEFINITION, //
			"Module is Redefined", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MODULEFILE_EXISTS, //
			"Deprecated Modulefile Exists", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MODULEFILE_EXISTS_AND_IS_USED, //
			"Deprecated Modulefile is used", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_MISSING_FORGE_REQUIRED_FIELDS, //
			"Missing fields required by Puppet Forge", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_DEPRECATED_KEY, //
			"Deprecated metadata key", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_UNRECOGNIZED_KEY, //
			"Unrecognized metadata key", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_UNRESOLVED_REFERENCE, //
			"Unresolved Module Reference", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_UNEXPECTED_SUBMODULE, //
			"Unexpected Submodule Directory", getFieldEditorParent()));
		addField(new ValidationPreferenceFieldEditor(PROBLEM_WHITESPACE_IN_TAG, //
			"Tag Contains Whitespace", getFieldEditorParent()));
	}
}
