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
package com.puppetlabs.geppetto.common.os;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import com.google.inject.Provider;

public class FileExcluderProvider implements Provider<IFileExcluder> {
	private Path root;

	private Set<String> excludeGlobs;

	private final FileExcluder instance = new FileExcluder(this);

	public FileExcluderProvider() {
		root = null;
		excludeGlobs = Collections.emptySet();
	}

	@Override
	public IFileExcluder get() {
		return instance;
	}

	public Set<String> getExcludeGlobs() {
		return excludeGlobs;
	}

	public Path getRoot() {
		return root;
	}

	public void setExcludeGlobs(Set<String> excludeGlobs) {
		if(excludeGlobs == null)
			this.excludeGlobs = Collections.emptySet();
		else
			this.excludeGlobs = excludeGlobs;
		instance.reset();
	}

	public void setRoot(Path root) {
		this.root = root;
		instance.reset();
	}
}
