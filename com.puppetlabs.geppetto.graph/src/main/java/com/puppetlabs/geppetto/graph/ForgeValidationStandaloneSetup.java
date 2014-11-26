/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.graph;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.util.ForgeServiceStandaloneSetup;
import com.puppetlabs.geppetto.graph.dependency.DependencyGraphModule;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsSetup;
import com.puppetlabs.geppetto.validation.runner.ValidationModule;

public class ForgeValidationStandaloneSetup extends ForgeServiceStandaloneSetup {
	private final String repoHrefPrefix;

	public ForgeValidationStandaloneSetup(String forgeServiceURL, String repoHrefPrefix) {
		super(forgeServiceURL);
		this.repoHrefPrefix = repoHrefPrefix;
	}

	@Override
	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		super.addModules(diagnostic, modules);
		modules.add(new ValidationModule());
		Class<? extends IHrefProducer> hrefProducerClass = repoHrefPrefix == null
			? RelativeFileHrefProducer.class
			: GithubURLHrefProducer.class;
		modules.add(new DependencyGraphModule(hrefProducerClass, repoHrefPrefix));
	}

	@Override
	protected Injector createInjector(final List<Module> modules) {
		return new PPDiagnosticsSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(modules);
			}
		}.createInjectorAndDoEMFRegistration();
	}
}
