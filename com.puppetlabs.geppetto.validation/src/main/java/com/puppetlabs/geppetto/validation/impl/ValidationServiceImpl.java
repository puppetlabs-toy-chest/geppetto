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
package com.puppetlabs.geppetto.validation.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.puppetlabs.geppetto.diagnostic.DetailedFileDiagnostic;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import com.puppetlabs.geppetto.validation.runner.DirectoryValidator;
import com.puppetlabs.geppetto.validation.runner.ModuleDiagnosticsRunner;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsRunner;
import com.puppetlabs.geppetto.validation.runner.PuppetCatalogCompilerRunner;

/**
 * Note that all use of monitor assumes SubMonitor semantics (the receiver does *not* call done on monitors, this is the
 * responsibility of the caller if caller is not using a SubMonitor).
 */
public class ValidationServiceImpl implements ValidationService {
	/**
	 * Add an exception diagnostic (not associated with any particular file).
	 *
	 * @param diagnostics
	 * @param message
	 * @param e
	 */
	public static void addExceptionDiagnostic(Diagnostic diagnostics, String message, Exception e) {
		ExceptionDiagnostic bd = new ExceptionDiagnostic(Diagnostic.ERROR, INTERNAL_ERROR, message, e);
		diagnostics.addChild(bd);
	}

	/**
	 * Add diagnostic for a file.
	 *
	 * @param diagnostics
	 * @param severity
	 * @param file
	 * @param rootDirectory
	 *            - used to relativize the file path in the report
	 * @param message
	 * @param issueId
	 */
	public static void addFileDiagnostic(Diagnostic diagnostics, int severity, File file, File rootDirectory,
			String message, String issueId) {

		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(severity, UNKNOWN, message, pathToFile(
			file.getAbsolutePath(), rootDirectory));
		dft.setLineNumber(-1);
		dft.setLength(-1);
		dft.setOffset(-1);
		dft.setIssue(issueId);
		dft.setIssueData(new String[] {});
		dft.setNode("");
		diagnostics.addChild(dft);
	}

	/**
	 * Translate and add Xtext issue diagnostics to the chain.
	 *
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	public static void addIssueDiagnostic(Diagnostic diagnostics, Issue issue, File processedFile, File rootDirectory) {
		DiagnosticType type = issue.isSyntaxError()
				? GEPPETTO_SYNTAX
						: GEPPETTO;
		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(
			translateIssueSeverity(issue.getSeverity()), type, issue.getMessage(), relativeFile(
				processedFile, rootDirectory));

		if(!Forge.MODULEFILE_NAME.equals(processedFile.getName())) {
			// This info pertains to metadata.json which is generated from the Modulefile
			// in case it didn't exist. The information is useless when 'processedFile' indicates
			// the Modulefile
			dft.setLineNumber(issue.getLineNumber());
			dft.setLength(issue.getLength());
			dft.setOffset(issue.getOffset());
		}
		dft.setIssue(issue.getCode());
		dft.setIssueData(issue.getData());
		dft.setNode("");
		diagnostics.addChild(dft);
	}

	/**
	 * Translate and add ruby issue diagnostics to the chain.
	 *
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	public static void addRubyIssueDiagnostic(Diagnostic diagnostics, IRubyIssue issue, File processedFile,
			File rootDirectory) {

		int severity = issue.isSyntaxError()
				? Diagnostic.ERROR
						: Diagnostic.WARNING;
		DiagnosticType type = issue.isSyntaxError()
				? RUBY_SYNTAX
						: RUBY;

		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(severity, type, issue.getMessage(), pathToFile(
			issue.getFileName(), rootDirectory));
		dft.setLineNumber(issue.getLine());
		dft.setLength(issue.getLength());
		dft.setOffset(issue.getStartOffset());
		dft.setIssue(issue.getIdString());
		dft.setIssueData(new String[] {}); // TODO: the Ruby issue passes // Object[]
		dft.setNode("");
		diagnostics.addChild(dft);
	}

	/**
	 * Translate a file path to a file relative to rootFolder (if under this root, else return an absolute File).
	 *
	 * @param uri
	 * @param rootFolder
	 *            - root directory/folder or a file name
	 * @return
	 */
	private static File pathToFile(String filePath, File rootFolder) {
		Path problemPath = new Path(filePath);
		Path rootPath = new Path(rootFolder.getPath());
		IPath relativePath = problemPath.makeRelativeTo(rootPath);
		return relativePath.toFile();
	}

	private static File relativeFile(File file, File rootFolder) {
		IPath problemPath = Path.fromOSString(file.getPath());
		IPath relativePath = problemPath.makeRelativeTo(Path.fromOSString(rootFolder.getPath()));
		return relativePath.toFile();
	}

	/**
	 * Translate the issue severity (an enum) to BasicDiagnostic (they are probably the same...)
	 *
	 * @param severity
	 * @return
	 */
	private static int translateIssueSeverity(Severity severity) {
		switch(severity) {
			case INFO:
				return Diagnostic.INFO;
			case WARNING:
				return Diagnostic.WARNING;

			default:
			case ERROR:
				return Diagnostic.ERROR;

		}
	}

	/**
	 * Asserts a file/directory's existence and that it can be read.
	 *
	 * @throw IllegalArgumentException if the file is not the right type, does not exists, or is not readable.
	 * @param file
	 * @param assertIsDirectory
	 *            - check if file is a directory
	 */
	private void assertFileAvailability(File file, boolean assertIsDirectory) {
		if(assertIsDirectory) {
			if(file.isDirectory() && file.canRead())
				return;
			throw new IllegalArgumentException("The file: " + file.toString() + " is not a readable directory.");
		}
		else if(file.exists() && file.canRead())
			return;
		throw new IllegalArgumentException("The file: " + file.toString() + " is not a readable file.");
	}

	private boolean hasModulesSubDirectory(File root) {
		File modulesDir = new File(root, "modules");
		return modulesDir.isDirectory();
	}

	/**
	 * @param monitor
	 *            - client should call done unless using a SubMonitor
	 */
	@Override
	public BuildResult validate(Diagnostic diagnostics, ValidationOptions options, File source, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("diagnostics can not be null");
		if(source == null)
			throw new IllegalArgumentException("source can not be null");
		if(options == null)
			throw new IllegalArgumentException("options can not null");
		if(!source.exists())
			throw new IllegalArgumentException("source does not exist");
		if(!source.canRead())
			throw new IllegalArgumentException("source can not be read");
		String sourceName = source.getName();
		boolean isDirectory = source.isDirectory();
		boolean isPP = !isDirectory && sourceName.endsWith(".pp");
		boolean isRB = !isDirectory && sourceName.endsWith(".rb");
		boolean isMetadataJsonFile = !isDirectory && sourceName.equals(Forge.METADATA_JSON_NAME);

		if(options.getFileType() == FileType.DETECT) {
			options = new ValidationOptions(options);
			if(!isDirectory)
				options.setFileType(FileType.SINGLE_SOURCE_FILE);
			else {
				// A directory that does not have a 'Modulefile' or other
				// recognized module metadata is treated as a root
				// A directory that has a "modules" subdirectory is treated as a
				// root
				if(hasModulesSubDirectory(source) || !new File(source, Forge.METADATA_JSON_NAME).isFile())
					options.setFileType(FileType.PUPPET_ROOT);
				else
					options.setFileType(FileType.MODULE_ROOT);
			}
		}
		boolean rubyServicesPresent = false;

		if(options.getFileType() == FileType.SINGLE_SOURCE_FILE && options.isValidationCandidate(source)) {
			if(isDirectory)
				throw new IllegalArgumentException("source is not a single source file as stated in options");
			if(isPP) {
				PPDiagnosticsRunner ppDr = new PPDiagnosticsRunner();
				try {
					ppDr.setUp(options.getComplianceLevel(), options.getProblemsAdvisor());
					validatePPFile(ppDr, diagnostics, source, source.getParentFile(), monitor);
				}
				catch(Exception e) {
					addExceptionDiagnostic(
						diagnostics, "Internal error: Exception while setting up/tearing down validation", e);
				}
				finally {
					ppDr.tearDown();
				}

			}
			else if(isRB) {
				RubyHelper rubyHelper = new RubyHelper();
				rubyServicesPresent = rubyHelper.isRubyServicesAvailable();
				try {
					rubyHelper.setUp();
					validateRubyFile(rubyHelper, diagnostics, source, source.getParentFile(), monitor);
				}
				catch(Exception e) {
					addExceptionDiagnostic(
						diagnostics, "Internal error: Exception while setting up/tearing down pp validation", e);
				}
				finally {
					rubyHelper.tearDown();
				}

			}
			else if(isMetadataJsonFile)
				validateMetadataJsonFile(diagnostics, options, source, source.getParentFile(), monitor);
			else
				throw new IllegalArgumentException("unsupported source type");
			return new BuildResult(rubyServicesPresent);
		}

		if(!isDirectory)
			throw new IllegalArgumentException("source is not a directory as dictated by options");

		return new DirectoryValidator(diagnostics, source, options).validateDirectory(monitor);
	}

	/**
	 * TODO: Is currently limited to .pp content.
	 */
	@Override
	public Resource validate(Diagnostic diagnostics, ValidationOptions options, String code, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("DiagnosticChain can not be null");
		if(code == null)
			throw new IllegalArgumentException("code can not be null");
		final SubMonitor ticker = SubMonitor.convert(monitor, 3);

		PPDiagnosticsRunner ppRunner = new PPDiagnosticsRunner();
		Resource r = null;
		worked(ticker, 1);
		try {
			ppRunner.setUp(options.getComplianceLevel(), options.getProblemsAdvisor());
			worked(ticker, 1);
			File f = new File("/unnamed.pp");
			r = ppRunner.loadResource(code, URI.createFileURI(f.getPath()));
			// no need to remember "/" as the root
			IResourceValidator rv = ppRunner.getPPResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, f.getParentFile());
			}
			worked(ticker, 1);
			ppRunner.tearDown();
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error, failed to setUp PPDiagnostic.", e);
		}
		return r;
	}

	@Override
	public void validateManifest(Diagnostic diagnostics, ValidationOptions options, File sourceFile,
			IProgressMonitor monitor) {
		options = new ValidationOptions(options);
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.SINGLE_SOURCE_FILE);

		validate(diagnostics, options, sourceFile, monitor);
	}

	/**
	 * @deprecated use {@link #validate(DiagnosticChain, String)} instead.
	 */
	@Override
	@Deprecated
	public Resource validateManifest(Diagnostic diagnostics, ValidationOptions options, String code,
			IProgressMonitor monitor) {
		return validate(diagnostics, options, code, monitor);
	}

	/**
	 * Validates a Modulefile by a) loading it, and b) validating the metadata
	 *
	 * @param diagnostics
	 * @param source
	 * @param parentFile
	 */
	private void validateMetadataJsonFile(Diagnostic diagnostics, ValidationOptions options, File f, File root,
			IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2);
		worked(ticker, 1);
		try {
			ModuleDiagnosticsRunner mdRunner = new ModuleDiagnosticsRunner(options);
			FileInputStream input = new FileInputStream(f);
			Resource r = mdRunner.loadResource(input, URI.createFileURI(f.getPath()));
			IResourceValidator rv = mdRunner.getModuleResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			worked(ticker, 1);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
	}

	@Override
	public BuildResult validateModule(Diagnostic diagnostics, ValidationOptions options, File moduleRoot,
			IProgressMonitor monitor) {
		options = new ValidationOptions(options);
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.MODULE_ROOT);

		return validate(diagnostics, options, moduleRoot, monitor);
	}

	private void validatePPFile(PPDiagnosticsRunner dr, Diagnostic diagnostics, File f, File root,
			IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2);
		worked(ticker, 1);
		try {
			FileInputStream input = new FileInputStream(f);
			Resource r = dr.loadResource(input, URI.createFileURI(f.getPath()));
			IResourceValidator rv = dr.getPPResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			worked(ticker, 1);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
	}

	@Override
	public void validateRepository(Diagnostic diagnostics, ValidationOptions options, File catalogRoot,
			File factorData, File siteFile, String nodeName, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("diagnostics can not be null");
		if(catalogRoot == null)
			throw new IllegalArgumentException("catalogRoot can not be null");
		if(options == null)
			throw new IllegalArgumentException("options can not be null");
		if(factorData == null)
			throw new IllegalArgumentException("factorData can not be null");
		if(siteFile == null)
			throw new IllegalArgumentException("siteFile can not be null");
		if(nodeName == null || nodeName.length() < 1)
			throw new IllegalArgumentException("nodeName can not be null or empty");

		assertFileAvailability(factorData, false);
		assertFileAvailability(siteFile, false);
		assertFileAvailability(catalogRoot, true);

		final SubMonitor ticker = SubMonitor.convert(monitor, 2000);
		// perform static validation first
		validateRepository(diagnostics, options, catalogRoot, ticker.newChild(1000));

		// check for early exit due to cancel or errors
		int severity = diagnostics.getSeverity();
		if(ticker.isCanceled() || severity > Diagnostic.WARNING)
			return;

		// perform a catalog production
		PuppetCatalogCompilerRunner runner = new PuppetCatalogCompilerRunner();
		runner.compileCatalog(siteFile, catalogRoot, nodeName, factorData, ticker.newChild(1000));
		diagnostics.addChildren(runner.getDiagnostics());
	}

	@Override
	public BuildResult validateRepository(Diagnostic diagnostics, ValidationOptions options, File catalogRoot,
			IProgressMonitor monitor) {
		options = new ValidationOptions(options);
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		return validate(diagnostics, options, catalogRoot, monitor);
	}

	private void validateRubyFile(RubyHelper rubyHelper, Diagnostic diagnostics, File f, File root,
			IProgressMonitor monitor) {
		SubMonitor ticker = SubMonitor.convert(monitor, 1);
		try {
			IRubyParseResult result = rubyHelper.parse(f);
			for(IRubyIssue issue : result.getIssues()) {
				addRubyIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
		worked(ticker, 1);
	}

	private void worked(SubMonitor monitor, int amount) throws OperationCanceledException {
		if(monitor.isCanceled())
			throw new OperationCanceledException();
		monitor.worked(amount);
	}
}
