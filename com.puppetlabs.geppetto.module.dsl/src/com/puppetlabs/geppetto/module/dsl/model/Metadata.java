package com.puppetlabs.geppetto.module.dsl.model;

public interface Metadata {
	String KEY_AUTHOR = "author";

	String KEY_DEPENDENCIES = "dependencies";

	String KEY_DESCRIPTION = "description";

	String KEY_LICENSE = "license";

	String KEY_NAME = "name";

	String KEY_OPERATINGSYSTEM_SUPPORT = "operatingsystem_support";

	String KEY_PROJECT_PAGE = "project_page";

	String KEY_REQUIREMENTS = "requirements";

	String KEY_SOURCE = "source";

	String KEY_SUMMARY = "summary";

	String KEY_TAGS = "tags";

	String KEY_VERSION = "version";

	String[] ALL_KEYS = new String[] {
			KEY_AUTHOR, KEY_NAME, KEY_DEPENDENCIES, KEY_DESCRIPTION, KEY_LICENSE, KEY_OPERATINGSYSTEM_SUPPORT, KEY_PROJECT_PAGE,
			KEY_REQUIREMENTS, KEY_SOURCE, KEY_SUMMARY, KEY_TAGS, KEY_VERSION };

	String KEY_CHECKSUMS = "checksums";

	String KEY_TYPES = "types";

	String[] DEPRECATED_KEYS = new String[] { KEY_CHECKSUMS, KEY_TYPES };
}
