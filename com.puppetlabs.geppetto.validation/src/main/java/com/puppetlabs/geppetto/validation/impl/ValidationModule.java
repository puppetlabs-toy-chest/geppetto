package com.puppetlabs.geppetto.validation.impl;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.resource.IContainer.Manager;

import com.puppetlabs.geppetto.pp.dsl.PPRuntimeModule;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.ValidationStateBasedContainerManager;

public class ValidationModule extends PPRuntimeModule {

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

	public Class<? extends ValidationService> bindValidationService() {
		return ValidationServiceOptionsBinder.class;
	}
}
