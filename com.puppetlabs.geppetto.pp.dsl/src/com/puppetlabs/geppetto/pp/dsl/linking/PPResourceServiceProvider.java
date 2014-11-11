package com.puppetlabs.geppetto.pp.dsl.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;

/**
 * {@link IResourceServiceProvider} that handles 'metadata.json' URI's
 */
public class PPResourceServiceProvider extends DefaultResourceServiceProvider {
	@Inject
	private IFileExcluder folderDiscriminator;

	@Override
	public boolean canHandle(URI uri) {
		return uri != null && !folderDiscriminator.isExcluded(uri) && super.canHandle(uri);
	}
}
