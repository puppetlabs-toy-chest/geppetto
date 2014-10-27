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
package com.puppetlabs.geppetto.pp.dsl;

import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;

/**
 * Discriminates folders based on expressions that matches path segments
 */
public interface IFileExcluder {
	/**
	 * <p>
	 * Checks if the given Path is matched by the exclude pattern and returns <code>true</code> if that is the case.
	 * </p>
	 *
	 * @param path
	 *            The path to check.
	 * @return <code>true</code> if the path is excluded by the pattern
	 */
	boolean isExcluded(Path path);

	/**
	 * <p>
	 * Checks if the path of the URI is matched by the exclude pattern and returns <code>true</code> if that is the case.
	 * </p>
	 *
	 * @param uri
	 *            The URI to check.
	 * @return <code>true</code> if the URI path is excluded by the pattern
	 */
	boolean isExcluded(URI uri);
}
