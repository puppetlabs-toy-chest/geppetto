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
package com.puppetlabs.geppetto.forge.v2.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.puppetlabs.geppetto.forge.model.Constants;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.service.ReleaseService;
import com.puppetlabs.geppetto.semver.Version;

public class DefaultReleaseService extends AbstractForgeService implements ReleaseService {
	private static String getReleasePath(String owner, String name, Version version) {
		StringBuilder bld = new StringBuilder();
		bld.append(Constants.COMMAND_GROUP_RELEASES);
		bld.append('/');
		bld.append(owner);
		bld.append('/');
		bld.append(name);
		bld.append('/');
		version.toString(bld);
		return bld.toString();
	}

	@Override
	public Release create(String owner, String name, String notes, InputStream gzipFile, long fileSize)
			throws IOException {
		Map<String, String> parts = new HashMap<String, String>();
		parts.put("owner", owner);
		parts.put("module", name);
		if(notes != null && !notes.isEmpty())
			parts.put("notes", notes);
		return getClient(true).postUpload(
			Constants.COMMAND_GROUP_RELEASES, parts, gzipFile, "application/x-compressed-tar", "tempfile.tar.gz",
			fileSize, Release.class);
	}

	@Override
	public void delete(String owner, String name, Version version) throws IOException {
		getClient(true).delete(getReleasePath(owner, name, version));
	}

	@Override
	public Release update(String owner, String name, Version version, Release release) throws IOException {
		return getClient(true).patch(getReleasePath(owner, name, version), release, Release.class);
	}
}
