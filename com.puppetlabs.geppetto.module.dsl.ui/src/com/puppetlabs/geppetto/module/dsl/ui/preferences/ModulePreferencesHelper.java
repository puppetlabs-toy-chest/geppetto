package com.puppetlabs.geppetto.module.dsl.ui.preferences;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.Constants;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

@Singleton
public class ModulePreferencesHelper implements ModulePreferenceConstants, IPropertyChangeListener {
	private final RebuildChecker rebuildChecker;

	private final IPreferenceStore store;

	// @fmtOff
	private final List<String> requiresRebuild = Lists.newArrayList(
		PROBLEM_CIRCULAR_DEPENDENCY,
		PROBLEM_DEPENDENCY_VERSION_MISMATCH,
		PROBLEM_DEPRECATED_KEY,
		PROBLEM_MODULE_NAME_NOT_STRICT,
		PROBLEM_MODULE_REDEFINITION,
		PROBLEM_MODULEFILE_EXISTS,
		PROBLEM_MODULEFILE_EXISTS_AND_IS_USED,
		PROBLEM_MISSING_FORGE_REQUIRED_FIELDS,
		PROBLEM_UNEXPECTED_SUBMODULE,
		PROBLEM_UNRECOGNIZED_KEY,
		PROBLEM_UNRESOLVED_REFERENCE,
		PROBLEM_WHITESPACE_IN_TAG);
	// @fmtOn

	@Inject
	public ModulePreferencesHelper(IPreferenceStoreAccess access, RebuildChecker rebuildChecker) {
		store = access.getWritablePreferenceStore();
		store.setDefault(FORGE_LOCATION, Constants.FORGE_SERVICE_BASE_URL);
		store.setDefault(FORGE_LOGIN, ModuleName.safeOwner(System.getProperty("user.name")));
		store.setDefault(PROBLEM_CIRCULAR_DEPENDENCY, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_DEPRECATED_KEY, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_DEPENDENCY_VERSION_MISMATCH, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_MODULE_NAME_NOT_STRICT, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_MODULE_REDEFINITION, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_MODULEFILE_EXISTS, ValidationPreference.IGNORE.toString());
		store.setDefault(PROBLEM_MODULEFILE_EXISTS_AND_IS_USED, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_MISSING_FORGE_REQUIRED_FIELDS, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_UNEXPECTED_SUBMODULE, ValidationPreference.ERROR.toString());
		store.setDefault(PROBLEM_UNRECOGNIZED_KEY, ValidationPreference.WARNING.toString());
		store.setDefault(PROBLEM_UNRESOLVED_REFERENCE, ValidationPreference.ERROR.toString());
		store.setDefault(PROBLEM_WHITESPACE_IN_TAG, ValidationPreference.WARNING.toString());
		this.rebuildChecker = rebuildChecker;
		store.addPropertyChangeListener(this);
	}

	public ValidationPreference getCircularDependencyPreference() {
		return getValidationPreference(PROBLEM_CIRCULAR_DEPENDENCY);
	}

	public ValidationPreference getDependencyVersionMismatchPreference() {
		return getValidationPreference(PROBLEM_DEPENDENCY_VERSION_MISMATCH);
	}

	public ValidationPreference getDeprecatedKeyPreference() {
		return getValidationPreference(PROBLEM_DEPRECATED_KEY);
	}

	public String getForgeLogin() {
		return store.getString(FORGE_LOGIN);
	}

	public String getForgeURI() {
		return store.getString(FORGE_LOCATION);
	}

	public ValidationPreference getMissingForgeRequiredFieldsPreference() {
		return getValidationPreference(PROBLEM_MISSING_FORGE_REQUIRED_FIELDS);
	}

	public ValidationPreference getModulefileExistsPreference() {
		return getValidationPreference(PROBLEM_MODULEFILE_EXISTS);
	}

	public ValidationPreference getModuleNameNotStrictPreference() {
		return getValidationPreference(PROBLEM_MODULE_NAME_NOT_STRICT);
	}

	public ValidationPreference getModuleRedefinitionPreference() {
		return getValidationPreference(PROBLEM_MODULE_REDEFINITION);
	}

	public ValidationPreference getModulfileExistsAndIsUsedPreference() {
		return getValidationPreference(PROBLEM_MODULEFILE_EXISTS_AND_IS_USED);
	}

	public ValidationPreference getUnexpectedSubmodulePreference() {
		return getValidationPreference(PROBLEM_UNEXPECTED_SUBMODULE);
	}

	public ValidationPreference getUnrecognizedKeyPreference() {
		return getValidationPreference(PROBLEM_UNRECOGNIZED_KEY);
	}

	public ValidationPreference getUnresolvedReferencePreference() {
		return getValidationPreference(PROBLEM_UNRESOLVED_REFERENCE);
	}

	protected ValidationPreference getValidationPreference(String key) {
		return ValidationPreference.fromString(store.getString(key));
	}

	public ValidationPreference getWhitespaceInTagPreference() {
		return getValidationPreference(PROBLEM_WHITESPACE_IN_TAG);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(requiresRebuild.contains(event.getProperty()))
			rebuildChecker.triggerBuild();
	}

	public void stop() {
		store.removePropertyChangeListener(this);
	}
}
