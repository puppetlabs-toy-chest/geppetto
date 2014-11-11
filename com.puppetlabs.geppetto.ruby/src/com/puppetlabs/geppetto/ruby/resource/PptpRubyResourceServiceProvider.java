package com.puppetlabs.geppetto.ruby.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;

/**
 * An IResourceServiceProvider for PPTP Ruby.
 * This implementation optimizes which .rb instances which will be visited by restricing {@link #canHandle(URI)} to only
 * operate on the paths where
 * PPTP contributions can be made.
 */
public class PptpRubyResourceServiceProvider extends DefaultResourceServiceProvider {
	@Inject
	private IFileExcluder folderDiscriminator;

	@Override
	public boolean canHandle(URI uri) {
		return super.canHandle(uri) &&
			!(PptpRubyResource.detectLoadType(uri) == PptpRubyResource.LoadType.IGNORED || folderDiscriminator.isExcluded(uri));
	}
}
