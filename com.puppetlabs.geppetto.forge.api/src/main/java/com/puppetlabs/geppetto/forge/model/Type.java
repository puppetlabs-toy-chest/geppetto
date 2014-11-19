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
package com.puppetlabs.geppetto.forge.model;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 */
public class Type extends NamedDocItem {
	@Expose
	private List<NamedDocItem> properties = Collections.emptyList();

	@Expose
	private List<NamedDocItem> parameters = Collections.emptyList();

	@Expose
	private List<NamedDocItem> providers = Collections.emptyList();

	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(!(o instanceof Type))
			return false;
		Type ot = (Type) o;
		return super.equals(o) && properties.equals(ot.properties) && parameters.equals(ot.parameters) && providers.equals(ot.providers);
	}

	/**
	 * @return the parameters
	 */
	public List<NamedDocItem> getParameters() {
		return parameters;
	}

	/**
	 * @return the properties
	 */
	public List<NamedDocItem> getProperties() {
		return properties;
	}

	/**
	 * @return the providers
	 */
	public List<NamedDocItem> getProviders() {
		return providers;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(List<NamedDocItem> parameters) {
		this.parameters = asUnmodifiableList(parameters);
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<NamedDocItem> properties) {
		this.properties = asUnmodifiableList(properties);
	}

	/**
	 * @param providers
	 *            the providers to set
	 */
	public void setProviders(List<NamedDocItem> providers) {
		this.providers = asUnmodifiableList(providers);
		;
	}
}
