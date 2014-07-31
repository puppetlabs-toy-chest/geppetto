package com.puppetlabs.geppetto.module.dsl.ui.internal;

import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.module.dsl.ui.preferences.ModulePreferencesHelper;

public class Activator extends ModuleActivator {
	public static String getId() {
		return getInstance().getBundle().getSymbolicName();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Injector injector = getInjector(COM_PUPPETLABS_GEPPETTO_MODULE_DSL_MODULE);
		injector.getInstance(ModulePreferencesHelper.class).stop();
		super.stop(context);
	}
}
