package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceFactory;

class ResourceFactoryDescriptor extends ModuleInjectorProvider implements Resource.Factory.Descriptor {
	override createFactory() {
		injector.getInstance(IResourceFactory)
	}
}
