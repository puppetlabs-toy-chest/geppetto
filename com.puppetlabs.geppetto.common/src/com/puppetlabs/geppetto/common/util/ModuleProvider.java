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
package com.puppetlabs.geppetto.common.util;

import java.util.List;

/**
 * An interface that makes it possible to provide modules without knowing anything
 * about the Guice component
 */
public interface ModuleProvider {
	/**
	 * Add Guice injection modules to the provided list.
	 *
	 * @param modules
	 *            the list to add modules to
	 */
	void addModules(List<? extends Object> modules);
}
