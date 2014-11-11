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
package com.puppetlabs.geppetto.validation.impl;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.puppetlabs.geppetto.common.os.FileExcluderProvider;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisorProvider;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import com.puppetlabs.geppetto.validation.runner.DirectoryValidator;
import com.puppetlabs.geppetto.validation.runner.DirectoryValidatorFactory;
import com.puppetlabs.geppetto.validation.runner.IEncodingProvider;

/**
 * Injects bindings are configured from a ValidationOptions instance and then delegates the call
 * to the actual implementation in {@link ValidationServiceImpl}.
 */
public class ValidationServiceOptionsBinder implements ValidationService {

	class OptionsBindingModule extends AbstractModule {
		private final ValidationOptions options;

		public OptionsBindingModule(ValidationOptions options) {
			this.options = options;
		}

		@Override
		protected void configure() {
			bind(ValidationOptions.class).toInstance(options);
			bind(IEncodingProvider.class).toInstance(options.getEncodingProvider());
			install(new FactoryModuleBuilder().implement(DirectoryValidator.class, DirectoryValidatorImpl.class).build(
				DirectoryValidatorFactory.class));
		}
	}

	@Inject
	private Injector injector;

	@Inject
	private ValidationAdvisorProvider advisorProvider;

	@Inject
	private FileExcluderProvider fileExcluderProvider;

	private void configureProviders(ValidationOptions options) {
		advisorProvider.setComplianceLevel(options.getComplianceLevel());
		advisorProvider.setProblemsAdvisor(options.getProblemsAdvisor());
		fileExcluderProvider.setExcludeGlobs(options.getExcludeGlobs());
		fileExcluderProvider.setRoot(options.getValidationRoot().toPath());
	}

	@Override
	public BuildResult validate(Diagnostic diagnostics, ValidationOptions options, File source, IProgressMonitor monitor) {
		configureProviders(options);
		return injector.createChildInjector(new OptionsBindingModule(options)).getInstance(ValidationServiceImpl.class).validate(
			diagnostics, source, monitor);
	}

	@Override
	public Resource validate(Diagnostic diagnostics, ValidationOptions options, String code, IProgressMonitor monitor) {
		configureProviders(options);
		return injector.createChildInjector(new OptionsBindingModule(options)).getInstance(ValidationServiceImpl.class).validate(
			diagnostics, code, monitor);
	}
}
