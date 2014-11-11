/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Itemis AB (http://www.itemis.eu) - initial API and implementation
 *   Puppet Labs - adpated for validation
 *
 */
package com.puppetlabs.geppetto.validation.runner;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubyStandaloneSetup;
import com.puppetlabs.geppetto.ruby.jrubyparser.JRubyServices;
import com.puppetlabs.geppetto.validation.ValidationOptions;

/**
 * Runner/Helper that can perform diagnostics/validation of ruby files.
 */
public class RubyDiagnosticsRunner extends RubyStandaloneSetup {
	private final Injector injector;

	public RubyDiagnosticsRunner(ValidationOptions options) {
		injector = setUp(JRubyServices.class);
	}

	public RubyHelper getRubyHelper() {
		return injector.getInstance(RubyHelper.class);
	}
}
