package com.puppetlabs.geppetto.module.dsl.model;

public interface OS {
	String KEY_OPERATINGSYSTEM = "operatingsystem";

	String KEY_OPERATINGSYSTEMRELEASE = "operatingsystemrelease";

	String[] ALL_KEYS = new String[] { KEY_OPERATINGSYSTEM, KEY_OPERATINGSYSTEMRELEASE };
}
