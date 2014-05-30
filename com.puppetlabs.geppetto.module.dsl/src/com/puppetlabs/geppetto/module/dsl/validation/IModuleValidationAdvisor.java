package com.puppetlabs.geppetto.module.dsl.validation;

import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

public interface IModuleValidationAdvisor {
	/**
	 * How should circular module dependencies be reported (ignore, warning, error).
	 */
	ValidationPreference getCircularDependency();

	/**
	 * How should version ranges that doesn't match the target version be handled
	 */
	ValidationPreference getDependencyVersionMismatch();

	/**
	 * How should deprecated keys be handled
	 */
	ValidationPreference getDeprecatedKey();

	/**
	 * How should empty or missing fields required by Puppet Forge be handled
	 */
	ValidationPreference getMissingForgeRequiredFields();

	/**
	 * How should existence of the deprecated Modulefile be handled
	 */
	ValidationPreference getModulefileExists();

	/**
	 * How should existence of the deprecated Modulefile be handled in case the metadata.json is missing
	 */
	ValidationPreference getModulefileExistsAndIsUsed();

	/**
	 * How should ModuleNames that contains upper cased characters or owner that start with
	 * a digit be handled
	 */
	ValidationPreference getModuleNameNotStrict();

	/**
	 * How should detection of multiple modules with the same name be handled
	 */
	ValidationPreference getModuleRedefinition();

	/**
	 * How should unrecognized keys be handled
	 */
	ValidationPreference getUnrecognizedKey();

	/**
	 * How should unresolved references be handled
	 */
	ValidationPreference getUnresolvedReference();

	/**
	 * How should submodule directories be handled
	 */
	ValidationPreference getUnexpectedSubmodule();

	/**
	 * How should tags containing whitespace characters be handled
	 */
	ValidationPreference getWhitespaceInTag();
}
