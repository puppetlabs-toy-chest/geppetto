/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.forge.model;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 * A module release requirement
 */
public class Requirement extends Entity {
	private static final long serialVersionUID = 1L;

	@Expose
	private String name;

	@Expose
	private VersionRange version_requirement;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version requirement
	 */
	public VersionRange getVersionRequirement() {
		return version_requirement;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = trimToNull(name);
	}

	/**
	 * @param versionRequirement
	 *            the version requirement to set
	 */
	public void setVersionRequirement(VersionRange versionRequirement) {
		this.version_requirement = versionRequirement;
	}
}
