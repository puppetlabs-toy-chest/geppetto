package com.puppetlabs.geppetto.module.dsl.model;

public interface Requirement {
	String KEY_NAME = "name";

	String KEY_VERSION_REQUIREMENT = "version_requirement";

	String[] ALL_KEYS = new String[] { KEY_NAME, KEY_VERSION_REQUIREMENT };
}
