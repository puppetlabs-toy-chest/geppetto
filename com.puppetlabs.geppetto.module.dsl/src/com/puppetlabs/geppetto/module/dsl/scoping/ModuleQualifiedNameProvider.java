package com.puppetlabs.geppetto.module.dsl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;

@Singleton
public class ModuleQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	@Inject
	private ModuleUtil moduleUtil;

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof JsonMetadata)
			return moduleUtil.getName((JsonMetadata) obj);
		return null;
	}
}
