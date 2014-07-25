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
package com.puppetlabs.geppetto.forge.v2.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.v2.service.ForgeService;

/**
 * Abstract base class for all services provided by the ForgeAPI API
 */
public abstract class AbstractForgeService implements ForgeService {
	@Inject
	private Gson gson;

	@Inject
	private ForgeClient forgeClient;

	@Override
	public void abortCurrentRequest() {
		forgeClient.abortCurrentRequest();
	}

	ForgeClient getClient(boolean authenticated) throws IOException {
		if(authenticated)
			forgeClient.authenticate();
		return forgeClient;
	}

	/**
	 * Converts a java bean into a hash map using the beans json representation.
	 *
	 * @param bean
	 *            The bean to convert
	 * @return The resulting map
	 */
	protected Map<String, String> toQueryMap(Object bean) {
		Map<String, String> result = new HashMap<String, String>();
		if(bean != null) {
			JsonElement json = gson.toJsonTree(bean);
			if(json.isJsonObject()) {
				for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
					JsonElement element = entry.getValue();
					if(element.isJsonPrimitive())
						result.put(entry.getKey(), element.getAsString());
				}
			}
		}
		return result;
	}
}
