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
package com.puppetlabs.geppetto.pp.dsl.tests;

import static com.google.inject.util.Modules.override;
import static com.puppetlabs.geppetto.injectable.CommonModuleProvider.getCommonModule;

import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.service.AbstractGenericModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.pp.dsl.PPRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.PPStandaloneSetup;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.tests.PPTestSetup3_0.AllWarningPotentialProblems;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisorProvider;

/**
 * Adds handling of PPTP.
 *
 */
public class PPTestSetup extends PPStandaloneSetup {
	public static class PPTestModule extends AbstractGenericModule {
		public com.google.inject.Provider<IValidationAdvisor> provideValidationAdvisor() {
			return ValidationAdvisorProvider.create(
				IValidationAdvisor.ComplianceLevel.PUPPET_2_7, new AllWarningPotentialProblems());
		}

		// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
		public com.google.inject.Provider<XtextResourceSet> provideXtextResourceSet() {
			return new Provider<XtextResourceSet>() {

				@Override
				public XtextResourceSet get() {
					XtextResourceSet resourceSet = new SynchronizedXtextResourceSet();
					resourceSet.getResource(PuppetTarget.PUPPET27.getPlatformURI(), true);
					return resourceSet;
				}

			};
		}

	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(override(getCommonModule(), new PPRuntimeModule()).with(new PPTestModule()));
	}
}
