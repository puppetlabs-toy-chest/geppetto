/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.validation.runner;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.resource.IContainer.Manager;

import com.puppetlabs.geppetto.module.dsl.ModuleRuntimeModule;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;

/**
 * Provides bindings for the ModuleDiagnostician.
 */
public class ModuleDiagnosticsModule extends ModuleRuntimeModule {

	private final IModuleValidationAdvisor moduleValidationAdvisor;

	public ModuleDiagnosticsModule(IModuleValidationAdvisor moduleValidationAdvisor) {
		this.moduleValidationAdvisor = moduleValidationAdvisor;
	}

	/**
	 * Bind an instance of a registry that is unique to this injector. Required since each validation execution may use
	 * a different potential problems validator. If the global registry is used, it will screw up for concurrent users.
	 * The registry uses delegation to the global registry.
	 */
	@Override
	@org.eclipse.xtext.service.SingletonBinding(eager = true)
	public EValidator.Registry bindEValidatorRegistry() {
		return new EValidatorRegistryImpl(EValidator.Registry.INSTANCE);
	}

	@Override
	public Class<? extends Manager> bindIContainer$Manager() {
		return ValidationStateBasedContainerManager.class;
	}

	/**
	 * Overrides the PPLinker used by default, to a linker that does not process documentation and that performs no
	 * resource linking. (To allow this to be performed separately).
	 */
	@Override
	public Class<? extends ILinker> bindILinker() {
		return LazyLinker.class;
	}

	/**
	 * Bind a ValidationAdvisorProvider.
	 *
	 * @return
	 */
	public IModuleValidationAdvisor bindIModuleValidationAdvisor() {
		return moduleValidationAdvisor;
	}
}
