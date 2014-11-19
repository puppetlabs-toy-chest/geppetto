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
package com.puppetlabs.geppetto.validation.runner;

import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.common.os.FileExcluderProvider;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.module.dsl.ModuleStandaloneSetup;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleValidationAdvisorWrapper;
import com.puppetlabs.geppetto.validation.ValidationOptions;

public class ModuleInjections {
	private final Injector injector;

	public ModuleInjections() {
		this.injector = new ModuleStandaloneSetup() {
		}.createInjectorAndDoEMFRegistration();
	}

	/**
	 * @return the forge
	 */
	public Forge getForge() {
		return injector.getInstance(Forge.class);
	}

	/**
	 * @return the moduleUtil
	 */
	public ModuleUtil getModuleUtil() {
		return injector.getInstance(ModuleUtil.class);
	}

	/**
	 * @return the resourceValidator
	 */
	public IResourceValidator getResourceValidator() {
		return injector.getInstance(IResourceValidator.class);
	}

	public void setOptions(ValidationOptions options) {
		injector.getInstance(ModuleValidationAdvisorWrapper.class).setAdvisor(options.getModuleValidationAdvisor());
		FileExcluderProvider feProvider = injector.getInstance(FileExcluderProvider.class);
		feProvider.setExcludeGlobs(options.getExcludeGlobs());
		feProvider.setRoot(options.getValidationRoot().toPath());
	}
}
