/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Itemis AB (http://www.itemis.eu) - initial API and implementation
 *   Puppet Labs - adpated for validation
 *
 */
package com.puppetlabs.geppetto.validation.runner;

import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.common.os.FileExcluderProvider;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.module.dsl.ModuleStandaloneSetup;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.validation.ValidationOptions;

/**
 * Runner/Helper that can perform diagnostics/validation of metadata.json files.
 */
public class ModuleDiagnosticsRunner extends ModuleStandaloneSetup {
	private final ValidationOptions validationOptions;

	private IResourceValidator resourceValidator;

	private ModuleUtil moduleUtil;

	private Forge forge;

	public ModuleDiagnosticsRunner(ValidationOptions validationOptions) {
		this.validationOptions = validationOptions;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(new ModuleDiagnosticsModule(validationOptions));
	}

	public Forge getForge() {
		return forge;
	}

	public IResourceValidator getModuleResourceValidator() {
		return resourceValidator;
	}

	public ModuleUtil getModuleUtil() {
		return moduleUtil;
	}

	@Override
	public void register(Injector injector) {
		super.register(injector);
		resourceValidator = injector.getInstance(IResourceValidator.class);
		moduleUtil = injector.getInstance(ModuleUtil.class);
		forge = injector.getInstance(Forge.class);
		FileExcluderProvider fileExcluderProvider = injector.getInstance(FileExcluderProvider.class);
		fileExcluderProvider.setExcludeGlobs(validationOptions.getExcludeGlobs());
		fileExcluderProvider.setRoot(validationOptions.getValidationRoot().toPath());
	}
}
