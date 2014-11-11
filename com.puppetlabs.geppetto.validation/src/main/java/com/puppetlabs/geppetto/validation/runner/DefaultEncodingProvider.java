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
package com.puppetlabs.geppetto.validation.runner;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Singleton;

/**
 * A default implementation of IEncodingProvider that always returns UTF-8
 */
@Singleton
public class DefaultEncodingProvider implements IEncodingProvider {
	@Override
	public String getEncoding(URI uri) {
		return "UTF-8";
	}

}
