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
package com.puppetlabs.geppetto.pp.dsl.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.IConfigurableProvider;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;

/**
 * Implementation of an {@link ISearchPathProvider} that returns a path based on a default path.
 *
 */
@Singleton
public class PPSearchPathProvider implements ISearchPathProvider, IConfigurableProvider {
	private String path;

	private URI rootDirectory;

	private String environment;

	private String manifestDir;

	@Override
	public void configure(URI rootDirectory, String path, String environment, String manifestDir) {
		this.rootDirectory = rootDirectory;
		this.path = path;
		this.environment = environment;
		this.manifestDir = manifestDir;
	}

	@Override
	public PPSearchPath get(Resource r) {
		return PPSearchPath.fromString(path, rootDirectory, environment, manifestDir);
	}
}
