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

public class DefaultReleaseService extends AbstractForgeService implements ReleaseService {
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
}
