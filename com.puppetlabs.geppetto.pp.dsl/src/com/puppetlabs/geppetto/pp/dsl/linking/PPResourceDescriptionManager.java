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
package com.puppetlabs.geppetto.pp.dsl.linking;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;

/**
 * Overrides the default to provide a PPResourceDescription instead of the default
 */
public class PPResourceDescriptionManager extends DefaultResourceDescriptionManager {
	@Inject
	private IFileExcluder folderDiscriminator;

	@Override
	protected IResourceDescription internalGetResourceDescription(Resource resource, IDefaultResourceDescriptionStrategy strategy) {
		return new PPResourceDescription(resource, strategy, getCache());

	}

	@Override
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		return folderDiscriminator.isExcluded(candidate.getURI())
			? false
			: super.isAffected(deltas, candidate, context);
	}

	@Override
	protected boolean isAffected(Collection<QualifiedName> importedNames, IResourceDescription description) {
		return description == null || folderDiscriminator.isExcluded(description.getURI())
			? false
			: super.isAffected(importedNames, description);
	}
}
