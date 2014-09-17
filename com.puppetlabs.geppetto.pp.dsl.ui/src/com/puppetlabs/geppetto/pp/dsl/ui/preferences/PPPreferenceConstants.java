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

/**
 * Constants for PP Preferences
 */
public class PPPreferenceConstants {
	/**
	 * Appended to preference ID to form name of 'use project settings' flag
	 */
	public static final String USE_PROJECT_SETTINGS = "useProjectSettings";

	public static final String AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS = "autoEditCompleteCompundBlocks";

	public static final String AUTO_EDIT_STRATEGY = "autoEditStrategy";

	public static final String PROBLEM_ASSIGNMENT_TO_VAR_NAMED_STRING = "assignmentToVarNamedSrring";

	public static final String PROBLEM_ASSIGNMENT_TO_VAR_NAMED_TRUSTED = "assignmentToVarNamedTrusted";

	public static final String PROBLEM_ATTRIBUTE_IS_NOT_OCTAL = "attributeIsNotOctal";

	public static final String PROBLEM_ATTRIBUTE_IS_NOT_STRING = "attributeIsNotString";

	public static final String PROBLEM_BOOLEAN_STRING = "booleanString";

	public static final String PROBLEM_CASE_DEFAULT_LAST = "CaseDefaultLast";

	public static final String PROBLEM_DEPRECATED_IMPORT = "importIsDeprecated";

	public static final String PROBLEM_DEPRECATED_NODE_INHERITANCE = "deprecatedNodeInheritance";

	public static final String PROBLEM_DEPRECATED_PLUS_EQUALS = "deprecatedPlusEquals";

	public static final String PROBLEM_DEPRECATED_VARIABLE_NAME = "deprecatedVariableName";

	public static final String PROBLEM_DQ_STRING_NOT_REQUIRED = "dqStringNotRequired";

	public static final String PROBLEM_DQ_STRING_NOT_REQUIRED_VAR = "dqStringNotRequiredVar";

	public static final String PROBLEM_ENSURE_NOT_FIRST = "ensureNotFirst";

	public static final String PROBLEM_INTERPOLATED_HYPHEN = "problemInterpolatedHyphen";

	public static final String PROBLEM_MISSING_DEFAULT = "missingDefault";

	public static final String PROBLEM_ML_COMMENTS = "mlComments";

	public static final String PROBLEM_RTOL_RELATIONSHIP = "rightToLeftRelationships";

	public static final String PROBLEM_SELECTOR_DEFAULT_LAST = "SelectDefaultLast";

	public static final String PROBLEM_UNBRACED_INTERPOLATION = "unbracedInterpolation";

	public static final String PROBLEM_UNQUOTED_RESOURCE_TITLE = "unquotedResourceTitle";

	public static final String PROBLEM_VALIDITY_ASSERTED_AT_RUNTIME = "validityAssertedAtRuntime";

	public static final String PUPPET_ENVIRONMENT = "puppetEnvironment";

	public static final String PUPPET_ENVIRONMENT__ENABLED = "puppetEnvironmentEnabled";

	public static final String PUPPET_FOLDER_FILTER = "folderFilter";

	public static final String PUPPET_MANIFEST_DIR = "puppetManifestDir";

	public static final String PUPPET_PROJECT_PATH = "puppetPath";

	public static final String PUPPET_PROJECT_PATH__ENABLED = "puppetPathEnabled";

	public static final String PUPPET_TARGET_VERSION = "pptpVersion";

	public static final String SAVE_ACTION_ENSURE_ENDS_WITH_NL = "ensureEndsWithNewLine";

	public static final String SAVE_ACTION_FORMAT = "formatOnSave";

	public static final String SAVE_ACTION_REPLACE_FUNKY_SPACES = "replaceFunkySpaces";

	public static final String SAVE_ACTION_TRIM_LINES = "trimLines";

	public static final String SAVE_ACTIONS_ID = "com.puppetlabs.geppetto.pp.dsl.PP.saveactions";

	public static final String SAVE_ACTIONS_USE_PROJECT_SETTINGS = SAVE_ACTIONS_ID + "." + USE_PROJECT_SETTINGS;
}
