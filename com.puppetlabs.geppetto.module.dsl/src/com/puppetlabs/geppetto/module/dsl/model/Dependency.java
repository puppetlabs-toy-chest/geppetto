package com.puppetlabs.geppetto.module.dsl.model;

public interface Dependency {
	String KEY_NAME = "name";

	String KEY_VERSION_REQUIREMENT = "version_requirement";

	String[] ALL_KEYS = new String[] { KEY_NAME, KEY_VERSION_REQUIREMENT };

	String KEY_REPOSITORY = "repository";

	String[] DEPRECATED_KEYS = new String[] { KEY_REPOSITORY };
}
