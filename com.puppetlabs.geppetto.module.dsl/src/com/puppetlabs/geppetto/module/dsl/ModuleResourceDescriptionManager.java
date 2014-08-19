package com.puppetlabs.geppetto.module.dsl;

import java.util.Collection;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.IFolderDiscriminator;

public class ModuleResourceDescriptionManager extends DefaultResourceDescriptionManager {
	@Inject
	private IFolderDiscriminator folderDiscriminator;

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
