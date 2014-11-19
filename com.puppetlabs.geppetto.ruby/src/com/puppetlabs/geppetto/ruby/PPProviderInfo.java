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
package com.puppetlabs.geppetto.ruby;

import com.puppetlabs.geppetto.common.Strings;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.Provider;

/**
 * Represents information about a PP Type Provider (a Resource provider).
 */
public class PPProviderInfo {

	public final String typeName;

	public final String providerName;

	public final String documentation;

	public PPProviderInfo(String typeName, String providerName, String documentation) {
		this.typeName = typeName;
		this.providerName = providerName;
		this.documentation = Strings.trimToNull(documentation);
	}

	public String getDocumentation() {
		return documentation;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getTypeName() {
		return typeName;
	}

	public Provider toProvider() {
		Provider provider = PPTPFactory.eINSTANCE.createProvider();
		provider.setName(providerName);
		provider.setTypeName(typeName);
		provider.setDocumentation(documentation);
		return provider;
	}
}
