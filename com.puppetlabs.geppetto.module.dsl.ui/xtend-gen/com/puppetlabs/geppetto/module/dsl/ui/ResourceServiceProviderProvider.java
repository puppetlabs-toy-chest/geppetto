package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.module.dsl.ui.ModuleInjectorProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;

@SuppressWarnings("all")
public class ResourceServiceProviderProvider extends ModuleInjectorProvider implements Provider<IResourceServiceProvider> {
  public IResourceServiceProvider get() {
    Injector _injector = this.getInjector();
    return _injector.<IResourceServiceProvider>getInstance(IResourceServiceProvider.class);
  }
}
