/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.ruby;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyResourceFactory;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyRuntimeModule;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;

public class RubyStandaloneSetup {

	private Injector pptpRubyInjector;

	public Injector getPptpRubyInjector() {
		return pptpRubyInjector;
	}

	public Injector setUp(final Class<? extends IRubyServices> rubyServicesClass) {
		Map<String, Object> factoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();

		PptpRubyResourceFactory rubyResourceFactory = null;
		if(!factoryMap.containsKey("rb")) {
			rubyResourceFactory = new PptpRubyResourceFactory();
			factoryMap.put("rb", rubyResourceFactory);
		}

		pptpRubyInjector = Guice.createInjector(new PptpRubyRuntimeModule() {

			@Override
			public Class<? extends IRubyServices> bindIRubyServices() {
				return rubyServicesClass;
			}

		});
		// register rb
		// expects the PptpRubyResourceFactory to have been registered via a Eclipse Extension
		// register the resource service provider (in a UI scnario this is registered by the Activator)
		IResourceServiceProvider pptpRubyServiceProvider = pptpRubyInjector.getInstance(IResourceServiceProvider.class);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("rb", pptpRubyServiceProvider);

		if(rubyResourceFactory != null)
			pptpRubyInjector.injectMembers(rubyResourceFactory);
		return pptpRubyInjector;
	}
}
