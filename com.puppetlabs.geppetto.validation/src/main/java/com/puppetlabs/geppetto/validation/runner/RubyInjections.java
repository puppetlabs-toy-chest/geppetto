/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.validation.runner;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubyStandaloneSetup;
import com.puppetlabs.geppetto.ruby.jrubyparser.JRubyServices;

public class RubyInjections {
	private final Injector injector;

	public RubyInjections() {
		this.injector = new RubyStandaloneSetup().setUp(JRubyServices.class);
	}

	/**
	 * @return the rubyHelper
	 */
	public RubyHelper getRubyHelper() {
		return injector.getInstance(RubyHelper.class);
	}
}
