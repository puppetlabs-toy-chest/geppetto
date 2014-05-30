package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.module.dsl.ui.internal.ModuleActivator;

@SuppressWarnings("all")
public class ModuleInjectorProvider {
  public Injector getInjector() {
    ModuleActivator _instance = ModuleActivator.getInstance();
    return _instance.getInjector(ModuleActivator.COM_PUPPETLABS_GEPPETTO_MODULE_DSL_MODULE);
  }
}
