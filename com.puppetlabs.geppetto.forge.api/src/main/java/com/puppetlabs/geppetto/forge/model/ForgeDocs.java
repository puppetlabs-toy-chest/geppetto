/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
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

import java.util.Collection;

public class ForgeDocs {
	private Collection<Type> types;

	private Collection<NamedDocItem> functions;

	private String name;

	private String version;

	private VersionedName release;

	public Collection<NamedDocItem> getFunctions() {
		return functions;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the release
	 */
	public VersionedName getRelease() {
		return release;
	}

	public Collection<Type> getTypes() {
		return types;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	public void setFunctions(Collection<NamedDocItem> functions) {
		this.functions = functions;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(VersionedName release) {
		this.release = release;
	}

	public void setTypes(Collection<Type> types) {
		this.types = types;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
