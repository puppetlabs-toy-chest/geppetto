package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceFactory;

public class ResourceFactoryDescriptor extends ModuleInjectorProvider implements Resource.Factory.Descriptor {
	@Override
	public Resource.Factory createFactory() {
		return getInjector().getInstance(IResourceFactory.class);
	}
}
