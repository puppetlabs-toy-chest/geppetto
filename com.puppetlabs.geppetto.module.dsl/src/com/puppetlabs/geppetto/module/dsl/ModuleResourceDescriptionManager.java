package com.puppetlabs.geppetto.module.dsl;

import java.util.Collection;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;

public class ModuleResourceDescriptionManager extends DefaultResourceDescriptionManager {
	@Inject
	private IFileExcluder fileExcluder;

	@Override
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		return fileExcluder.isExcluded(candidate.getURI())
			? false
			: super.isAffected(deltas, candidate, context);
	}

	@Override
	protected boolean isAffected(Collection<QualifiedName> importedNames, IResourceDescription description) {
		return description == null || fileExcluder.isExcluded(description.getURI())
			? false
			: super.isAffected(importedNames, description);
	}
}
