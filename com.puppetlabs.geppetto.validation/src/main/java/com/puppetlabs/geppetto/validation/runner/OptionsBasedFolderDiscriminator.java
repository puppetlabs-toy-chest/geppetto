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

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.pp.dsl.FolderDiscriminator;
import com.puppetlabs.geppetto.validation.ValidationOptions;

@Singleton
public class OptionsBasedFolderDiscriminator extends FolderDiscriminator {
	@Inject
	private ValidationOptions validationOptions;

	@Override
	protected Set<String> getExcludePatterns() {
		Set<String> patterns = Sets.newHashSet(FileUtils.DEFAULT_EXCLUDES);
		patterns.removeAll(ValidationOptions.DEFAULT_EXCLUTION_PATTERNS);
		patterns.addAll(validationOptions.getFolderExclusionPatterns());
		return patterns;
	}
}
