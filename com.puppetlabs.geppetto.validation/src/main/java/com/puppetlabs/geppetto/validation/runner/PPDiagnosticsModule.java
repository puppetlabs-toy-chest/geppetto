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

import com.google.inject.Provider;
import com.puppetlabs.geppetto.pp.dsl.IFileExcluder;
import com.puppetlabs.geppetto.pp.dsl.PPRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisorProvider;
import com.puppetlabs.geppetto.validation.ValidationOptions;

/**
 * Provides bindings for the PPDiagnostician.
 */
public class PPDiagnosticsModule extends PPRuntimeModule {

	private final ValidationOptions validationOptions;

	public PPDiagnosticsModule(ValidationOptions validationOptions) {
		this.validationOptions = validationOptions;
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

	@Override
	public Class<? extends IFileExcluder> bindIFolderDiscriminator() {
		return OptionsBasedFileExcluder.class;
	}

	/**
	 * Overrides the PPLinker used by default, to a linker that does not process documentation and that performs no
	 * resource linking. (To allow this to be performed separately).
	 */
	@Override
	public Class<? extends ILinker> bindILinker() {
		return LazyLinker.class;
	}

	public ValidationOptions bindValidationOptions() {
		return validationOptions;
	}

	/**
	 * Bind a ValidationAdvisorProvider.
	 *
	 * @return
	 */
	@Override
	public Provider<IValidationAdvisor> provideValidationAdvisor() {
		return ValidationAdvisorProvider.create(validationOptions.getComplianceLevel(), validationOptions.getProblemsAdvisor());
	}
}
