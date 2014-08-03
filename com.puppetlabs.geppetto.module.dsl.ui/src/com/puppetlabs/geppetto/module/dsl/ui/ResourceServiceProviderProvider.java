package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Provider;

public class ResourceServiceProviderProvider extends ModuleInjectorProvider implements Provider<IResourceServiceProvider> {
	@Override
	public IResourceServiceProvider get() {
		return getInjector().getInstance(IResourceServiceProvider.class);
	}
}
