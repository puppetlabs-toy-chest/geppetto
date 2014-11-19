/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.forge.model;

import java.util.Collection;

public class ForgeDocs {
	private Collection<Type> types;

	private Collection<NamedDocItem> functions;

	public Collection<NamedDocItem> getFunctions() {
		return functions;
	}

	public Collection<Type> getTypes() {
		return types;
	}

	public void setFunctions(Collection<NamedDocItem> functions) {
		this.functions = functions;
	}

	public void setTypes(Collection<Type> types) {
		this.types = types;
	}
}
