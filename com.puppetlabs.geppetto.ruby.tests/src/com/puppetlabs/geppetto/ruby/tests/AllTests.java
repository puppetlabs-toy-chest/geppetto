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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Tests.
 */
@SuiteClasses({
	// @fmtOff
	TestRubyDocProcessor.class,
	TestRubyDocProcessor2.class,
	PptpResourceTests.class,
	SmokeTest.class,
	PuppetFunctionTests.class,
	PuppetTypeTests.class,
	PuppetTPTests.class
	// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
}
