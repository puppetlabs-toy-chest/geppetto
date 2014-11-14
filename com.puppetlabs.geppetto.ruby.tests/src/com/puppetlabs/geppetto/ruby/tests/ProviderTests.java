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
package com.puppetlabs.geppetto.ruby.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsSetup;

public class ProviderTests {
	@Inject
	private ValidationService validationService;

	@Inject
	private TestDataProvider testDataProvider;

	private ValidationOptions getValidationOptions() {
		ValidationOptions options = new ValidationOptions();
		options.setCheckReferences(true);
		options.setCheckLayout(true);
		options.setFileType(FileType.MODULE_ROOT);
		return options;
	}

	@Before
	public void setUp() {
		new PPDiagnosticsSetup().createInjectorAndDoEMFRegistration().injectMembers(this);
	}

	@Test
	public void testProviderNotPresent() throws Exception {
		ValidationOptions options = getValidationOptions();
		File source = testDataProvider.getTestFile(new Path("testData/pp-modules-ruby/module-x"));
		options.setValidationRoot(source);
		Diagnostic diag = new Diagnostic();
		validationService.validate(diag, options, source, new NullProgressMonitor());
		assertTrue("Errors should be present", diag.getErrorCount() > 0);
	}

	@Test
	public void testProviderPresent() throws Exception {
		ValidationOptions options = getValidationOptions();
		File source = testDataProvider.getTestFile(new Path("testData/pp-modules-ruby/module-y"));
		options.setValidationRoot(source);
		Diagnostic diag = new Diagnostic();
		validationService.validate(diag, options, source, new NullProgressMonitor());
		assertTrue("No errors should be present", diag.getErrorCount() == 0);
	}
}
