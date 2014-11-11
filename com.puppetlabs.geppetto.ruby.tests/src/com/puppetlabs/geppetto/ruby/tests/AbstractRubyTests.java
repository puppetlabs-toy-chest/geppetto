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
package com.puppetlabs.geppetto.ruby.tests;

import org.junit.Before;
import org.junit.BeforeClass;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubyStandaloneSetup;
import com.puppetlabs.geppetto.ruby.jrubyparser.JRubyServices;

public class AbstractRubyTests {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		injector = new RubyStandaloneSetup().setUp(JRubyServices.class);
	}

	@Inject
	RubyHelper helper;

	@Inject
	TestDataProvider testDataProvider;

	@Before
	public void setUp() {
		injector.injectMembers(this);
	}
}
