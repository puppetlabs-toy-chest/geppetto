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

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.forge.Forge.FORGE;

import java.util.List;

import com.google.inject.Module;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.client.OAuthModule;

/**
 * @author thhal
 */
public class AuthenticatedForgeServiceStandaloneSetup extends ForgeServiceStandaloneSetup {
	private final String clientId;

	private final String clientSecret;

	private final String forgeLogin;

	private final String forgePassword;

	public AuthenticatedForgeServiceStandaloneSetup(String forgeServiceURL, String clientId, String clientSecret, String forgeLogin,
			String forgePassword) {
		super(forgeServiceURL);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.forgeLogin = forgeLogin;
		this.forgePassword = forgePassword;
	}

	@Override
	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		super.addModules(diagnostic, modules);

		if(forgeLogin == null || forgeLogin.length() == 0)
			diagnostic.addChild(new Diagnostic(ERROR, FORGE, "login must be specified"));

		if(forgePassword == null || forgePassword.length() == 0)
			diagnostic.addChild(new Diagnostic(ERROR, FORGE, "password must be specified"));

		if(diagnostic.getSeverity() >= ERROR)
			return;

		modules.add(new OAuthModule(clientId, clientSecret, forgeLogin, forgePassword));
	}
}
