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
package com.puppetlabs.geppetto.validation.runner;

import static com.puppetlabs.geppetto.injectable.CommonModuleProvider.getCommonModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.pp.dsl.PPStandaloneSetup;
import com.puppetlabs.geppetto.validation.ValidationOptions;

/**
 * Setup of PP runtime with overrides for validation service.
 */
public class PPDiagnosticsSetup extends PPStandaloneSetup {

	private final ValidationOptions validationOptions;

	public PPDiagnosticsSetup(ValidationOptions validationOptions) {
		this.validationOptions = validationOptions;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(getCommonModule(), new PPDiagnosticsModule(validationOptions));
	}

}
