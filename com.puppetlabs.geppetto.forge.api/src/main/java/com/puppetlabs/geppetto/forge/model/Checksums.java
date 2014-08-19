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
package com.puppetlabs.geppetto.forge.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Entity that holds checksums
 */
public class Checksums extends Entity {
	private Map<String, byte[]> checksums;

	/**
	 * @return the checksums
	 */
	public Map<String, byte[]> getChecksums() {
		return checksums == null
				? Collections.<String, byte[]> emptyMap()
						: Collections.unmodifiableMap(checksums);
	}

	/**
	 * @param checksums
	 *            the checksums to set
	 */
	public void setChecksums(Map<String, byte[]> checksums) {
		this.checksums = (checksums == null || checksums.isEmpty())
				? null
						: new HashMap<String, byte[]>(checksums);
	}
}
