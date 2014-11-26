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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.pp.dsl.PPStandaloneSetup;

/**
 * Setup of PP runtime with overrides for validation service.
 */
public class PPDiagnosticsSetup extends PPStandaloneSetup {

	@Override
	public Injector createInjector() {
		// Remove the PPPackage from the validator registry since it's cached there
		// in a static registry.
		//
		// TODO: Figure out how to do this in a proper way so that we can run simultanious
		// validations with different settings
		//
		return Guice.createInjector(new ValidationModule());
	}

}
