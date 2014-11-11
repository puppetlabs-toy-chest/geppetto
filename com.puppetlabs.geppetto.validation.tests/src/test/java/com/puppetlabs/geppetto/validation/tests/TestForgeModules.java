package com.puppetlabs.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;

public class TestForgeModules extends AbstractValidationTest {
	@Inject
	private ValidationService vs;

	@Test
	public void validateForge() throws Exception {
		File root = new File("/Users/henrik/gitrepos/forge-modules");
		if(!root.isDirectory())
			return;

		// TestDataProvider.getTestFile(new Path(
		// "testData/test-modules/"));
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		vs.validate(chain, options, root, SubMonitor.convert(null));
		assertEquals("There should be 0 errors", 0, chain.getChildren().size());
	}
}
