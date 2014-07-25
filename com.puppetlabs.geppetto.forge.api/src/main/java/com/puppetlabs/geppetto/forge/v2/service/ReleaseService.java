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
package com.puppetlabs.geppetto.forge.v2.service;

import java.io.IOException;
import java.io.InputStream;

import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.semver.Version;

/**
 * A CRUD service for {@link Release} objects
 */
public interface ReleaseService extends ForgeService {

	/**
	 * Create a new Release based on the given <code>gzipFile</code>. The
	 * combination of name and owner in the template must be unique.
	 *
	 * @param owner
	 * @param name
	 * @param notes
	 * @param gzipFile
	 * @return
	 * @throws IOException
	 */
	Release create(String owner, String name, String notes, InputStream gzipFile, long fileSize) throws IOException;

	/**
	 * Delete the Release identified by <code>owner</code>, <code>name</code>, and <code>version.
	 *
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @throws IOException
	 */
	void delete(String owner, String name, Version version) throws IOException;

	/**
	 * Updates the Release identified by <code>owner</code>, <code>name</code>, and <code>version with
	 * new data.
	 *
	 * @param owner
	 *            The Module owner
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @param Release
	 *            New data for the Release.
	 * @return The updated Release
	 * @throws IOException
	 */
	Release update(String owner, String name, Version version, Release release) throws IOException;

}
