package com.puppetlabs.geppetto.module.dsl.ui.preferences;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

public class PreferencedBasedValidationAdvisor implements IModuleValidationAdvisor {
	private final ModulePreferencesHelper preferenceHelper;

	@Inject
	public PreferencedBasedValidationAdvisor(ModulePreferencesHelper preferenceHelper) {
		this.preferenceHelper = preferenceHelper;
	}

	@Override
	public ValidationPreference getCircularDependency() {
		return preferenceHelper.getCircularDependencyPreference();
	}

	@Override
	public ValidationPreference getDependencyVersionMismatch() {
		return preferenceHelper.getDependencyVersionMismatchPreference();
	}

	@Override
	public ValidationPreference getDeprecatedKey() {
		return preferenceHelper.getDeprecatedKeyPreference();
	}

	@Override
	public ValidationPreference getMissingForgeRequiredFields() {
		return preferenceHelper.getMissingForgeRequiredFieldsPreference();
	}

	@Override
	public ValidationPreference getModulefileExists() {
		return preferenceHelper.getModulefileExistsPreference();
	}

	@Override
	public ValidationPreference getModulefileExistsAndIsUsed() {
		return preferenceHelper.getModulfileExistsAndIsUsedPreference();
	}

	@Override
	public ValidationPreference getModuleNameNotStrict() {
		return preferenceHelper.getModuleNameNotStrictPreference();
	}

	@Override
	public ValidationPreference getModuleRedefinition() {
		return preferenceHelper.getModuleRedefinitionPreference();
	}

	@Override
	public ValidationPreference getUnexpectedSubmodule() {
		return preferenceHelper.getUnexpectedSubmodulePreference();
	}

	@Override
	public ValidationPreference getUnrecognizedKey() {
		return preferenceHelper.getUnrecognizedKeyPreference();
	}

	@Override
	public ValidationPreference getUnresolvedReference() {
		return preferenceHelper.getUnresolvedReferencePreference();
	}

	@Override
	public ValidationPreference getWhitespaceInTag() {
		return preferenceHelper.getWhitespaceInTagPreference();
	}
}
