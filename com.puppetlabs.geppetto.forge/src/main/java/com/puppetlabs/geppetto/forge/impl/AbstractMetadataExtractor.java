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
package com.puppetlabs.geppetto.forge.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.MetadataExtractor;
import com.puppetlabs.geppetto.forge.model.Metadata;

public abstract class AbstractMetadataExtractor implements MetadataExtractor {
	@Override
	public boolean canExtractFrom(File moduleDirectory, FileFilter filter) {
		File mdSource = new File(moduleDirectory, getPrimarySource());
		return filter.accept(mdSource) && mdSource.exists();
	}

	@Override
	public Metadata parseMetadata(File moduleDirectory, FileFilter filter, File[] extractedFrom, Diagnostic result)
			throws IOException {
		File metadataFile = new File(moduleDirectory, getPrimarySource());
		if(extractedFrom != null)
			extractedFrom[0] = metadataFile;

		if(!canExtractFrom(moduleDirectory, filter))
			throw new FileNotFoundException(metadataFile.getAbsolutePath());

		return performMetadataExtraction(metadataFile, result);
	}

	protected abstract Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException;
}
