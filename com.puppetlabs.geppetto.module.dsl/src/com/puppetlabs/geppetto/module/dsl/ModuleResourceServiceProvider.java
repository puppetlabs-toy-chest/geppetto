package com.puppetlabs.geppetto.module.dsl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;

/**
 * {@link IResourceServiceProvider} that handles 'metadata.json' URI's
 */
public class ModuleResourceServiceProvider extends DefaultResourceServiceProvider {
	@Inject
	private IFileExcluder fileExcluder;

	@Override
	public boolean canHandle(URI uri) {
		return uri != null && "metadata.json".equals(uri.lastSegment()) && !fileExcluder.isExcluded(uri);
	}
}
