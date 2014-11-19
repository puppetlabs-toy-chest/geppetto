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

import static com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics.ISSUE_PROPOSAL_SUFFIX;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.common.os.FileExcluderProvider;
import com.puppetlabs.geppetto.diagnostic.DetailedFileDiagnostic;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisorProvider;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import com.puppetlabs.geppetto.validation.runner.DirectoryValidatorFactory;
import com.puppetlabs.geppetto.validation.runner.ModuleInjections;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsRunner;

/**
 * Note that all use of monitor assumes SubMonitor semantics (the receiver does *not* call done on monitors, this is the
 * responsibility of the caller if caller is not using a SubMonitor).
 */
public class ValidationServiceImpl implements ValidationService {
	private static final String[] emptyData = new String[0];

	/**
	 * Add an exception diagnostic (not associated with any particular file).
	 *
	 * @param diagnostics
	 * @param message
	 * @param e
	 */
	public static void addExceptionDiagnostic(Diagnostic diagnostics, String message, Exception e) {
		ExceptionDiagnostic bd = new ExceptionDiagnostic(Diagnostic.ERROR, ValidationService.INTERNAL_ERROR, message, e);
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
	public static void addFileDiagnostic(Diagnostic diagnostics, int severity, DiagnosticType type, File file, File rootDirectory,
			String message, String issueId) {

		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(severity, type, message, pathToFile(file.getAbsolutePath(), rootDirectory));
		dft.setLineNumber(-1);
		dft.setLength(-1);
		dft.setOffset(-1);
		dft.setIssue(issueId);
		dft.setIssueData(emptyData);
		dft.setNode("");
		diagnostics.addChild(dft);
	}

	private static void addIssueDiagnostic(Diagnostic diagnostics, DiagnosticType type, Issue issue, File processedFile, File rootDirectory) {
		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(
			translateIssueSeverity(issue.getSeverity()), type, issue.getMessage(), relativeFile(processedFile, rootDirectory));

		if(!Forge.MODULEFILE_NAME.equals(processedFile.getName())) {
			// This info pertains to metadata.json which is generated from the Modulefile
			// in case it didn't exist. The information is useless when 'processedFile' indicates
			// the Modulefile
			dft.setLineNumber(issue.getLineNumber());
			dft.setLength(issue.getLength());
			dft.setOffset(issue.getOffset());
		}
		String issueCode = issue.getCode();
		if(issueCode != null && issueCode.endsWith(ISSUE_PROPOSAL_SUFFIX))
			issueCode = issueCode.substring(0, issueCode.length() - ISSUE_PROPOSAL_SUFFIX.length());
		dft.setIssue(issueCode);
		dft.setIssueData(issue.getData());
		dft.setNode("");
		diagnostics.addChild(dft);
	}

	/**
	 * Translate and add Xtext PP issue diagnostics to the chain.
	 *
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	public static void addModuleIssueDiagnostic(Diagnostic diagnostics, Issue issue, File processedFile, File rootDirectory) {
		addIssueDiagnostic(diagnostics, ValidationService.MODULE, issue, processedFile, rootDirectory);
	}

	/**
	 * Translate and add Xtext PP issue diagnostics to the chain.
	 *
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	public static void addPPIssueDiagnostic(Diagnostic diagnostics, Issue issue, File processedFile, File rootDirectory) {
		addIssueDiagnostic(diagnostics, issue.isSyntaxError()
			? ValidationService.PP_SYNTAX
			: ValidationService.PP_LINKING, issue, processedFile, rootDirectory);
	}

	/**
	 * Translate and add ruby issue diagnostics to the chain.
	 *
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	public static void addRubyIssueDiagnostic(Diagnostic diagnostics, IRubyIssue issue, File processedFile, File rootDirectory) {

		int severity = issue.isSyntaxError()
			? Diagnostic.ERROR
			: Diagnostic.WARNING;
		DiagnosticType type = issue.isSyntaxError()
			? IRubyServices.RUBY_SYNTAX
			: IRubyServices.RUBY;

		DetailedFileDiagnostic dft = new DetailedFileDiagnostic(severity, type, issue.getMessage(), pathToFile(
			issue.getFileName(), rootDirectory));
		dft.setLineNumber(issue.getLine());
		dft.setLength(issue.getLength());
		dft.setOffset(issue.getStartOffset());
		dft.setIssue(issue.getIdString());
		dft.setIssueData(emptyData); // TODO: the Ruby issue passes // Object[]
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

	@Inject
	private DirectoryValidatorFactory directoryValidatorFactory;

	@Inject
	private Provider<PPDiagnosticsRunner> ppDiagRunnerProvider;

	@Inject
	private ValidationAdvisorProvider advisorProvider;

	@Inject
	private FileExcluderProvider fileExcluderProvider;

	@Inject
	private ModuleInjections moduleInjections;

	private void configureProviders(ValidationOptions options) {
		advisorProvider.setComplianceLevel(options.getComplianceLevel());
		advisorProvider.setProblemsAdvisor(options.getProblemsAdvisor());
		fileExcluderProvider.setExcludeGlobs(options.getExcludeGlobs());
		fileExcluderProvider.setRoot(options.getValidationRoot().toPath());
		moduleInjections.setOptions(options);
	}

	private boolean hasModulesSubDirectory(File root) {
		File modulesDir = new File(root, "modules");
		return modulesDir.isDirectory();
	}

	@Override
	public BuildResult validate(Diagnostic diagnostics, ValidationOptions options, File source, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("diagnostics can not be null");
		if(source == null)
			throw new IllegalArgumentException("source can not be null");
		if(!source.exists())
			throw new IllegalArgumentException("source does not exist");
		if(!source.canRead())
			throw new IllegalArgumentException("source can not be read");

		String sourceName = source.getName();
		boolean isDirectory = source.isDirectory();
		boolean isPP = !isDirectory && sourceName.endsWith(".pp");
		boolean isRB = !isDirectory && sourceName.endsWith(".rb");
		boolean isMetadataJsonFile = !isDirectory && sourceName.equals(Forge.METADATA_JSON_NAME);

		configureProviders(options);
		FileType fileType = options.getFileType();
		if(fileType == FileType.DETECT) {
			if(!isDirectory)
				fileType = FileType.SINGLE_SOURCE_FILE;
			else {
				// A directory that does not have a 'Modulefile' or other
				// recognized module metadata is treated as a root
				// A directory that has a "modules" subdirectory is treated as a
				// root
				if(hasModulesSubDirectory(source) || !new File(source, Forge.METADATA_JSON_NAME).isFile())
					fileType = FileType.PUPPET_ROOT;
				else
					fileType = FileType.MODULE_ROOT;
			}
		}
		boolean rubyServicesPresent = false;

		if(fileType == FileType.SINGLE_SOURCE_FILE && options.isValidationCandidate(source)) {
			if(isDirectory)
				throw new IllegalArgumentException("source is not a single source file as stated in options");

			if(isPP) {
				validatePPFile(ppDiagRunnerProvider.get(), diagnostics, source, source.getParentFile(), monitor);
			}
			else if(isRB) {
				RubyHelper rubyHelper = ppDiagRunnerProvider.get().getRubyHelper();
				rubyServicesPresent = rubyHelper.isRubyServicesAvailable();
				validateRubyFile(rubyHelper, diagnostics, source, source.getParentFile(), monitor);
			}
			else if(isMetadataJsonFile)
				validateMetadataJsonFile(diagnostics, options, source, source.getParentFile(), monitor);
			else
				throw new IllegalArgumentException("unsupported source type");

			return new BuildResult(rubyServicesPresent);
		}

		if(!isDirectory)
			throw new IllegalArgumentException("source is not a directory as dictated by options");

		try {
			return directoryValidatorFactory.create(diagnostics, source, options).validateDirectory(monitor);
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while setting up pp diagnostics.", e);
			return new BuildResult(false); // give up
		}
	}

	@Override
	public Resource validate(Diagnostic diagnostics, ValidationOptions options, String code, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("DiagnosticChain can not be null");
		if(code == null)
			throw new IllegalArgumentException("code can not be null");

		configureProviders(options);
		final SubMonitor ticker = SubMonitor.convert(monitor, 3);

		Resource r = null;
		worked(ticker, 1);
		worked(ticker, 1);
		File f = new File("/unnamed.pp");
		PPDiagnosticsRunner ppRunner = ppDiagRunnerProvider.get();
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
			addPPIssueDiagnostic(diagnostics, issue, f, f.getParentFile());
		}
		worked(ticker, 1);
		return r;
	}

	/**
	 * Validates a Modulefile by a) loading it, and b) validating the metadata
	 *
	 * @param diagnostics
	 * @param source
	 * @param parentFile
	 */
	private void validateMetadataJsonFile(Diagnostic diagnostics, ValidationOptions options, File f, File root, IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2);
		worked(ticker, 1);
		try {
			FileInputStream input = new FileInputStream(f);
			PPDiagnosticsRunner ppDr = new PPDiagnosticsRunner();
			Resource r = ppDr.loadResource(input, URI.createFileURI(f.getPath()));
			IResourceValidator rv = ppDr.getModuleResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			worked(ticker, 1);
			for(Issue issue : issues) {
				addModuleIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(IOException e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
	}

	private void validatePPFile(PPDiagnosticsRunner dr, Diagnostic diagnostics, File f, File root, IProgressMonitor monitor) {
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
				addPPIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(IOException e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
	}

	private void validateRubyFile(RubyHelper rubyHelper, Diagnostic diagnostics, File f, File root, IProgressMonitor monitor) {
		SubMonitor ticker = SubMonitor.convert(monitor, 1);
		try {
			for(IRubyIssue issue : rubyHelper.parse(f)) {
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
