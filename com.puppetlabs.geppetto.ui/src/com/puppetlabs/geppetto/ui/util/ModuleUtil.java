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
package com.puppetlabs.geppetto.ui.util;

import java.net.URI;

import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.model.AbbrevRelease;

public class ModuleUtil {
	public static VersionedName getVersionedName(AbbrevRelease release) {
		if(release == null)
			return null;
		URI uri = release.getUri();
		if(uri != null) {
			String path = uri.getPath();
			if(path != null) {
				int lastSlashIdx = path.lastIndexOf('/');
				if(lastSlashIdx >= 0)
					return new VersionedName(path.substring(lastSlashIdx + 1));
			}
		}
		return null;
	}
}
