package com.puppetlabs.geppetto.module.dsl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

/**
 * {@link IResourceServiceProvider} that handles 'metadata.json' URI's
 */
public class ModuleResourceServiceProvider extends DefaultResourceServiceProvider {
	@Override
	public boolean canHandle(URI uri) {
		return isMetadataJsonFile(uri);
	}

	public static boolean isMetadataJsonFile(URI uri) {
		return uri == null
				? false
				: "metadata.json".equals(uri.lastSegment());
	}
}
