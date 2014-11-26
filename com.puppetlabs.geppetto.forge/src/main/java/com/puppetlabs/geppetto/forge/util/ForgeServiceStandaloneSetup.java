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
package com.puppetlabs.geppetto.forge.util;

import java.util.List;

import com.google.inject.Module;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.client.ForgeHttpModule;
import com.puppetlabs.geppetto.forge.impl.ForgeServiceModule;

/**
 * @author thhal
 */
public class ForgeServiceStandaloneSetup extends ForgeStandaloneSetup {
	private class HttpModule extends ForgeHttpModule {
		@Override
		protected String doGetBaseURL() {
			return forgeServiceURL;
		}
	}

	private final String forgeServiceURL;

	public ForgeServiceStandaloneSetup(String forgeServiceURL) {
		this.forgeServiceURL = forgeServiceURL;
	}

	@Override
	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		super.addModules(diagnostic, modules);
		modules.add(new HttpModule());
		modules.add(new ForgeServiceModule());
	}
}
