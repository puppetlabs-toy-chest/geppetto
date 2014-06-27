package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Provider;

class ResourceServiceProviderProvider extends ModuleInjectorProvider implements Provider<IResourceServiceProvider> {
	override IResourceServiceProvider get() {
		injector.getInstance(IResourceServiceProvider)
	}
}
