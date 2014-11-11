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
package com.puppetlabs.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;

/**
 * @author thhal
 */
public class TestSearchPath extends AbstractValidationTest {
	@Inject
	private ValidationService vs;

	@Test
	public void testManifestDir() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/test-module"));
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setSearchPath(PPSearchPath.DEFAULT_PUPPET_PROJECT_PATH);
		options.setFileType(FileType.MODULE_ROOT);
		vs.validate(chain, options, root, SubMonitor.convert(null));

		// Without constraint that only things on path are validated - there should be two redefinition errors
		//
		assertEquals("There should be no errors", 0, countErrors(chain));
		assertDoesNotContainIssue(chain, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		// Default project path contains $manifestdir so this should fail
		options.setManifestDir("foobar");
		vs.validate(chain, options, root, SubMonitor.convert(null));
		assertContainsIssue(chain, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		// Remove $manifestdir from searhc path and test again. Should be OK now.
		chain = new Diagnostic();
		options.setSearchPath("lib/*:environments/$environment/*:manifests/*:modules/*");
		vs.validate(chain, options, root, SubMonitor.convert(null));
		assertDoesNotContainIssue(chain, IPPDiagnostics.ISSUE__NOT_ON_PATH);
	}
}
