/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.module.dsl.validation;

/**
 * Interface declaring diagnostic codess.
 * These can be used for association of quick fixes with errors and warnings.
 */
public interface ModuleDiagnostics {
	String ISSUE_PREFIX = "com.puppetlabs.geppetto.module.dsl.validation.issue.";

	String ISSUE_PROPOSAL_SUFFIX = ".prop";

	String ISSUE__CIRCULAR_DEPENDENCY = ISSUE_PREFIX + "CircularDependency";

	String ISSUE__DEPENDENCY_DECLARED_MORE_THAN_ONCE = ISSUE_PREFIX + "DependencyDeclaredMoreThanOnce";

	String ISSUE__DEPRECATED_KEY = ISSUE_PREFIX + "DeprecatedKey";

	String ISSUE__EMPTY_ATTRIBUTE = ISSUE_PREFIX + "EmptyAttribute";

	String ISSUE__INVALID_MODULE_NAME = ISSUE_PREFIX + "InvalidModuleName";

	String ISSUE__INVALID_TAG = ISSUE_PREFIX + "InvalidTag";

	String ISSUE__INVALID_VERSION = ISSUE_PREFIX + "InvalidVersion";

	String ISSUE__INVALID_VERSION_RANGE = ISSUE_PREFIX + "InvalidVersionRange";

	String ISSUE__MISSING_METADATA_JSON_FILE = ISSUE_PREFIX + "MissingMetadataJsonFile";

	String ISSUE__MISSING_REQUIRED_ATTRIBUTE = ISSUE_PREFIX + "MissingRequiredAttribute";

	String ISSUE__MODULE_CLASS_NOT_IN_INIT_PP = "ModuleClassNotInInitPP";

	String ISSUE__MODULE_REDEFINITION = ISSUE_PREFIX + "ModuleRedefined";

	String ISSUE__MODULE_VERSION_RANGE_MISMATCH = ISSUE_PREFIX + "VersionRangeMismatch";

	String ISSUE__MODULEFILE_IS_DEPRECATED = ISSUE_PREFIX + "ModulefileIsDeprecatedAndIgnored";

	String ISSUE__MODULEFILE_IS_DEPRECATED_BUT_USED = ISSUE_PREFIX + "ModulefileIsDeprecatedButUsed";

	String ISSUE__UNKNOWN_REQUIREMENT_NAME = ISSUE_PREFIX + "UnknownRequirementName";

	String ISSUE__UNEXPECTED_SUBMODULE_DIRECTORY = ISSUE_PREFIX + "UnexpectedSubmoduleDirectory";

	String ISSUE__UNRECOGNIZED_KEY = ISSUE_PREFIX + "UnrecognizedKey";
}
