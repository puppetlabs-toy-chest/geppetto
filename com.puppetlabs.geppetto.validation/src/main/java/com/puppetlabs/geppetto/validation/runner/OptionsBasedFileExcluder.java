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
package com.puppetlabs.geppetto.validation.runner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.FileExcluder;
import com.puppetlabs.geppetto.validation.ValidationOptions;

@Singleton
public class OptionsBasedFileExcluder extends FileExcluder {
	private static final Path DOT = Paths.get(".");

	private final Set<String> patterns;

	private final Path root;

	@Inject
	public OptionsBasedFileExcluder(ValidationOptions validationOptions) {
		this.patterns = validationOptions.getExcludeGlobs();
		this.root = validationOptions.getValidationRoot().toPath();
	}

	@Override
	protected Set<String> getExcludeGlobs() {
		return patterns;
	}

	/**
	 * Returns the path relative to the validation root prefixed with &quot;./&quot; so
	 * that it will match patterns that start with wildcard
	 */
	@Override
	protected Path getRelativePath(Path path) {
		return path != null && path.startsWith(root)
			? DOT.resolve(root.relativize(path))
			: null;
	}

	@Override
	protected Path getRelativePath(URI uri) {
		if(uri != null) {
			String filePath = uri.toFileString();
			if(filePath != null) {
				return getRelativePath(Paths.get(filePath));
			}
		}
		return null;
	}

}
