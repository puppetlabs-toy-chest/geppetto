/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs - initial API and implementation
 */
package com.puppetlabs.geppetto.injectable.eclipse.impl;

import java.net.URI;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.eclipse.core.net.proxy.IProxyData;

import com.puppetlabs.geppetto.injectable.eclipse.Activator;

public class ProxiedRoutePlanner implements HttpRoutePlanner {
	private final SchemeRegistry schemeRegistry;

	public ProxiedRoutePlanner(SchemeRegistry schemeRegistry) {
		this.schemeRegistry = schemeRegistry;
	}

	@Override
	public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
		if(request == null || target == null)
			throw new IllegalStateException("target and request must not be null.");

		boolean secure = schemeRegistry.getScheme(target.getSchemeName()).isLayered();
		IProxyData[] select = Activator.getInstance().getProxyService().select(URI.create(target.toURI()));
		for(IProxyData pd : select)
			if(pd.getType().equals(IProxyData.HTTPS_PROXY_TYPE))
				return new HttpRoute(target, null, new HttpHost(pd.getHost(), pd.getPort(), "https"), secure);

		for(IProxyData pd : select)
			if(pd.getType().equals(IProxyData.HTTP_PROXY_TYPE))
				return new HttpRoute(target, null, new HttpHost(pd.getHost(), pd.getPort()), secure);

		return new HttpRoute(target, null, secure);
	}
}
