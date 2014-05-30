package com.puppetlabs.geppetto.module.dsl.ui.preferences;

public interface ModulePreferenceConstants {
	String FORGE_LOCATION = "forgeLocation";

	String FORGE_LOGIN = "forgeLogin";

	String PROBLEM_CIRCULAR_DEPENDENCY = "circularModuleDependency";

	String PROBLEM_DEPENDENCY_VERSION_MISMATCH = "dependencyVersionRangeMismatch";

	String PROBLEM_DEPRECATED_KEY = "deprecatedKey";

	String PROBLEM_MODULE_NAME_NOT_STRICT = "moduleNameNotStrict";

	String PROBLEM_MODULE_REDEFINITION = "moduleRedefinition";

	String PROBLEM_MODULEFILE_EXISTS = "modulefileExists";

	String PROBLEM_MODULEFILE_EXISTS_AND_IS_USED = "modulefileExistsAndIsUsed";

	String PROBLEM_MISSING_FORGE_REQUIRED_FIELDS = "missingForgeRequiredFields";

	String PROBLEM_UNRECOGNIZED_KEY = "unrecognizedKey";

	String PROBLEM_UNRESOLVED_REFERENCE = "unresolvedReference";

	String PROBLEM_UNEXPECTED_SUBMODULE = "unexpectedSubmodule";

	String PROBLEM_WHITESPACE_IN_TAG = "whitespaceInTag";
}
