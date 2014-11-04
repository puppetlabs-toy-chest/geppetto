package com.puppetlabs.geppetto.validation.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;

public class TestValidatorService extends AbstractValidationTest {

	private void assertNotEquals(String message, Object expected, Object actual) {
		assertThat(message, expected, not(equalTo(actual)));
	}

	@Test
	public void validateAString_NotOk() throws Exception {
		String code = "$a = ";
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateManifest(chain, getValidationOptions(), code, null);
		assertTrue("There should be errors", chain.getChildren().size() != 0);
	}

	@Test
	public void validateAString_ok() throws Exception {
		String code = "$a = 'a::b'";
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateManifest(chain, getValidationOptions(), code, SubMonitor.convert(null));
		assertTrue("There should be no errors", chain.getChildren().size() == 0);
	}

	@Test
	public void validateCiruclarRepositories() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/circularModules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();

		// Set options like API1 would behave for a validateRepository
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);
		options.setProblemsAdvisor(DefaultPotentialProblemsAdvisor.INSTANCE);

		vs.validate(chain, options, root, SubMonitor.convert(null));

		int circularity = 0;
		int otherErrors = 0;

		for(Diagnostic e : chain)
			if(ModuleDiagnostics.ISSUE__CIRCULAR_DEPENDENCY.equals(e.getIssue()))
				circularity++;
			else if(e.getSeverity() >= Diagnostic.ERROR)
				otherErrors++;

		// for(Diagnostic d : chain.getChildren()) {
		// System.err.println(d.toString());
		// }
		// A->B->A A/Modulefile
		// A->B->C->A A/Modulefile
		// B->A->B B/Modulefile
		// B->C->A->B B/Modulefile
		// C->A->B->C C/Modulefile
		// D->D D/Modulefile

		assertEquals("There should be circularities", 6, circularity);
		assertEquals("There should be no other issues", 0, otherErrors);
	}

	@Test
	public void validateManifest_notok() throws Exception {
		File manifest = TestDataProvider.getTestFile(new Path("testData/manifests/not_ok_manifest.pp"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateManifest(chain, getValidationOptions(), manifest, SubMonitor.convert(null));
		assertTrue("There should be errors", countErrors(chain) > 0);
	}

	@Test
	public void validateManifest_ok() throws Exception {
		File manifest = TestDataProvider.getTestFile(new Path("testData/manifests/ok_manifest.pp"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setFileType(FileType.SINGLE_SOURCE_FILE);
		vs.validateManifest(chain, options, manifest, SubMonitor.convert(null));
		assertTrue("There should be no errors", countErrors(chain) == 0);
	}

	@Test
	public void validateModule_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/broken/broken-module/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateRepository(chain, getValidationOptions(), root, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertErrors(//
			asserter.messageFragment("mismatched input"), //
			asserter.issue(ModuleDiagnostics.ISSUE__MISSING_REQUIRED_ATTRIBUTE), //
			asserter.issue(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE), //
			asserter.issue(IPPDiagnostics.ISSUE__HYPHEN_IN_NAME), //
			asserter.messageFragment("unexpected tIDENTIFIER"));
		asserter.assertWarnings(
			asserter.issue(IPPDiagnostics.ISSUE__OCTAL_SHOULD_BE_STRING),
			asserter.issue(ModuleDiagnostics.ISSUE__MODULE_CLASS_NOT_IN_INIT_PP));
	}

	@Test
	public void validateModule_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/test-module/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateRepository(chain, getValidationOptions(), root, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		// no errors
		asserter.assertErrors();
		// no warnings
		asserter.assertWarnings();
	}

	@Test
	public void validateModuleWithSpaces_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/broken withSpaces/module"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validateRepository(chain, getValidationOptions(), root, SubMonitor.convert(null));
		assertNotEquals("There should be errors", 0, chain.getChildren().size());
		for(Diagnostic d : chain)
			if(d instanceof FileDiagnostic) {
				File f = ((FileDiagnostic) d).getFile();
				assertEquals("Reported files should start with 'manifests/'", "manifests/not ok manifest.pp", f.getPath());
			}
	}

	@Test
	public void validateRepository_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/forgeModules/lab42-activemq-0.1.2-withErrors/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();

		// Set options like API1 would behave for a validateRepository
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, options, root, SubMonitor.convert(null));
		assertNotEquals("There should be  errors", 0, chain.getChildren().size());
		Set<String> fileNames = Sets.newHashSet();
		for(Diagnostic d : chain) {
			if("This is not a boolean".equals(d.getMessage()))
				continue; // skip this error (UGLY)
			if(d instanceof FileDiagnostic)
				fileNames.add(((FileDiagnostic) d).getFile().getPath());
		}
		for(String s : fileNames) {
			File f = new File(s);
			assertTrue("Only files with errors should be reported", f.getName().startsWith("x-"));
		}
		assertEquals("Number of files with errors", 6, fileNames.size());
	}

	@Test
	public void validateRepository_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/forgeModules/lab42-activemq-0.1.2/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();

		// Set options like API1 would behave for a validateRepository
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, options, root, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());
	}

	@Test
	public void validateSeveralRepositories_2xok() throws Exception {
		validateSeveralRepositories_ok();
		validateSeveralRepositories_ok();
	}

	@Test
	public void validateSeveralRepositories_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();

		// Set options like API1 would behave for a validateRepository
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, options, root, SubMonitor.convert(null));
		int hyphenWarning = 0;
		for(Diagnostic e : chain)
			if(IPPDiagnostics.ISSUE__INTERPOLATED_HYPHEN.equals(e.getIssue()) || IPPDiagnostics.ISSUE__HYPHEN_IN_NAME.equals(e.getIssue()))
				hyphenWarning++;
		assertEquals("There should be 3 warnings", 3, chain.getChildren().size() - hyphenWarning);
	}

}
