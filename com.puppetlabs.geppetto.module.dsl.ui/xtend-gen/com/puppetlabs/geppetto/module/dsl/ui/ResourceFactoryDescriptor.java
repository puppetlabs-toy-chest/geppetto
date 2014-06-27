package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.module.dsl.ui.ModuleInjectorProvider;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceFactory;

@SuppressWarnings("all")
public class ResourceFactoryDescriptor extends ModuleInjectorProvider implements Resource.Factory.Descriptor {
  public Resource.Factory createFactory() {
    Injector _injector = this.getInjector();
    return _injector.<IResourceFactory>getInstance(IResourceFactory.class);
  }
}
