package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.module.dsl.ui.internal.ModuleActivator;

public class ModuleInjectorProvider {
	public Injector getInjector() {
		return ModuleActivator.getInstance().getInjector(ModuleActivator.COM_PUPPETLABS_GEPPETTO_MODULE_DSL_MODULE);
	}
}
