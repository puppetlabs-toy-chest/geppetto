package com.puppetlabs.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;

public class TestPptpOptionHandling extends AbstractValidationTest {
	@Inject
	private ValidationService vs;

	/**
	 * Tests that nodex (with declares a depedency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 */
	@Test
	public void pptpOptionHandling() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testFor2_7_1/"));
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		options.setValidationRoot(root.getParentFile());
		options.setComplianceLevel(ComplianceLevel.PUPPET_2_6);
		vs.validate(chain, options, root, SubMonitor.convert(null));

		assertEquals("There should be 1 errors", 1, chain.getChildren().size());

		chain = new Diagnostic();
		options.setComplianceLevel(ComplianceLevel.PUPPET_2_7);
		vs.validate(chain, options, root, SubMonitor.convert(null));
		dumpErrors(chain);
		assertEquals("There should be 0 errors", 0, chain.getChildren().size());
	}

}
