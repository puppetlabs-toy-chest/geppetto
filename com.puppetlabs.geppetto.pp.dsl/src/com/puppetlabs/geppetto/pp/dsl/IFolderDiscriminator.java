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

import org.eclipse.emf.common.util.URI;

/**
 * Discriminates folders based on expressions that matches path segments
 */
public interface IFolderDiscriminator {
	/**
	 * <p>
	 * Checks if the given URI contains a path segment that is matched by the exclude pattern and returns
	 * <code>true</code> if that is the case.
	 * </p>
	 * <p>
	 * The last path segment is considered to be a file name unless the URI has a trailing separator. File names do not
	 * participate in the check.
	 * </p>
	 *
	 * @param uri
	 *            The URI to check.
	 * @return <code>true</code> if the URI path contains a folder that is excluded by the pattern
	 */
	boolean isExcluded(URI uri);

	/**
	 * @param segment
	 *            The segment to check
	 * @return <code>true</code> if the given segment is matched by the exclusion expressions
	 */
	boolean isSegmentExcluded(String segment);
}
