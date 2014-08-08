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
package com.puppetlabs.geppetto.forge.v2.api.it;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase.TestModule;
import com.puppetlabs.geppetto.forge.v2.ForgeAPI;

// @fmtOff
@SuiteClasses({
	ReleaseTestCreate.class,
})
// @fmtOn
@RunWith(Suite.class)
public class ForgeIT {

	static ForgeAPI getTestUserForge() {
		if(testUserForge == null)
			testUserForge = new ForgeAPI(new TestModule());
		return testUserForge;
	}

	private static ForgeAPI testUserForge;
}
