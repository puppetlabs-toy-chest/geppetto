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
package com.puppetlabs.geppetto.puppetlint;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.puppetlint.impl.ExternalModule;

/**
 * A service that enables files and folders to be examined by <a href="http://http://puppet-lint.com/">puppet-lint</a>.
 */
public class PuppetLintService {
	/**
	 * @return The singleton instance of this service
	 */
	public static synchronized PuppetLintService getInstance() {
		if(instance == null)
			instance = new PuppetLintService(Guice.createInjector(new ExternalModule()));
		return instance;
	}

	public static final DiagnosticType PUPPET_LINT = new DiagnosticType("PUPPET_LINT", PuppetLintService.class.getName());

	private static PuppetLintService instance;

	private final Injector injector;

	private PuppetLintService(Injector injector) {
		this.injector = injector;
	}

	/**
	 * @return A new PuppetLintRunner
	 */
	public PuppetLintRunner getPuppetLintRunner() {
		return injector.getInstance(PuppetLintRunner.class);
	}
}
