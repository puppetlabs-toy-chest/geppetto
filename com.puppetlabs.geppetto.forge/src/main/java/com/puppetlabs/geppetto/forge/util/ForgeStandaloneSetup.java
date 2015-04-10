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
package com.puppetlabs.geppetto.forge.util;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.puppetlabs.geppetto.common.util.ModuleProvider;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.impl.ForgeModule;
import com.puppetlabs.geppetto.injectable.CommonModuleProvider;

/**
 * @author thhal
 */
public class ForgeStandaloneSetup {
	private Injector injector;

	private final List<ModuleProvider> moduleProviders = new ArrayList<>();

	public void addModuleProvider(ModuleProvider provider) {
		moduleProviders.add(provider);
	}

	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		modules.add(new ForgeModule());
		modules.add(CommonModuleProvider.getCommonModule());
	}

	protected Injector createInjector(List<Module> modules) {
		return Guice.createInjector(modules);
	}

	public Forge getForge(Diagnostic diagnostic) {
		return getInstance(Forge.class, diagnostic);
	}

	public ForgeService getForgeService(Diagnostic diagnostic) {
		return getInstance(ForgeService.class, diagnostic);
	}

	protected synchronized Injector getInjector(Diagnostic diag) {
		if(injector == null) {
			List<Module> modules = new ArrayList<>();
			addModules(diag, modules);
			for(ModuleProvider provider : moduleProviders)
				provider.addModules(modules);
			if(diag.getSeverity() <= Diagnostic.WARNING) {
				injector = createInjector(modules);
			}
		}
		return injector;
	}

	public <T> T getInstance(Class<? extends T> type, Diagnostic diagnostic) {
		return getInjector(diagnostic).getInstance(type);
	}
}
