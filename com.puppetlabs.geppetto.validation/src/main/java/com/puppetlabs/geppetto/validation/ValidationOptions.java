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
package com.puppetlabs.geppetto.validation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Sets;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.module.dsl.validation.DefaultModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleValidationAdvisorBean;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.PotentialProblemsAdvisorBean;
import com.puppetlabs.geppetto.validation.runner.DefaultEncodingProvider;
import com.puppetlabs.geppetto.validation.runner.GeppettoRC;
import com.puppetlabs.geppetto.validation.runner.GeppettoRC.Advices;
import com.puppetlabs.geppetto.validation.runner.IEncodingProvider;

public class ValidationOptions {
	public static final Set<String> DEFAULT_EXCLUDES;

	static {
		DEFAULT_EXCLUDES = Collections.unmodifiableSet(Sets.newHashSet("**/pkg/**", "**/spec/**"));
	}

	private boolean allowFileOverride;

	private boolean checkLayout;

	private boolean checkModuleSemantics;

	private boolean checkReferences;

	private ComplianceLevel complianceLevel;

	private IEncodingProvider encodingProvider;

	private String environment;

	private Set<String> excludeGlobs;

	private boolean extractTypes;

	private FileFilter fileFilter;

	private FileType fileType;

	private String manifestDir;

	private IModuleValidationAdvisor moduleValidationAdvisor;

	private IPotentialProblemsAdvisor problemsAdvisor;

	private String searchPath;

	private FileFilter validationFilter;

	private File validationRoot;

	public ValidationOptions() {
		fileType = FileType.DETECT;
	}

	public ValidationOptions(ValidationOptions source) {
		allowFileOverride = source.allowFileOverride;
		checkLayout = source.checkLayout;
		checkModuleSemantics = source.checkModuleSemantics;
		checkReferences = source.checkReferences;
		complianceLevel = source.complianceLevel;
		encodingProvider = source.encodingProvider;
		environment = source.environment;
		excludeGlobs = source.excludeGlobs;
		extractTypes = source.extractTypes;
		fileFilter = source.fileFilter;
		fileType = source.fileType;
		manifestDir = source.manifestDir;
		moduleValidationAdvisor = source.moduleValidationAdvisor;
		problemsAdvisor = source.problemsAdvisor;
		searchPath = source.searchPath;
		validationFilter = source.validationFilter;
		validationRoot = source.validationRoot;
	}

	/**
	 * Defaults to {@link PuppetTarget#getDefault()#getComplianceLevel()} if not specified.
	 *
	 * @return the value of the '<em>complianceLevel</em>' attribute.
	 */
	public ComplianceLevel getComplianceLevel() {
		if(complianceLevel == null)
			complianceLevel = PuppetTarget.getDefault().getComplianceLevel();
		return complianceLevel;
	}

	/**
	 * @return the value of the '<em>encodingProvider</em>' attribute.
	 */
	public IEncodingProvider getEncodingProvider() {
		if(encodingProvider == null)
			encodingProvider = new DefaultEncodingProvider();
		return encodingProvider;
	}

	/**
	 * @return the value of the '<em>environment</em>' attribute.
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @return the excludeGlobs
	 */
	public Set<String> getExcludeGlobs() {
		return excludeGlobs == null
			? Collections.<String> emptySet()
			: excludeGlobs;
	}

	/**
	 * @return the fileFilter
	 */
	public FileFilter getFileFilter() {
		if(fileFilter == null)
			fileFilter = FileUtils.DEFAULT_FILE_FILTER;
		return fileFilter;
	}

	/**
	 * @return the value of the '<em>fileType</em>' attribute.
	 */
	public FileType getFileType() {
		return fileType;
	}

	/**
	 * @return the value of the '<em>manifestDir</em>' attribute.
	 */
	public String getManifestDir() {
		return manifestDir;
	}

	/**
	 * @return The advisor used when validating metadata.json
	 */
	public IModuleValidationAdvisor getModuleValidationAdvisor() {
		if(moduleValidationAdvisor == null)
			moduleValidationAdvisor = DefaultModuleValidationAdvisor.INSTANCE;
		return moduleValidationAdvisor;
	}

	/**
	 * @return the value of the '<em>problemsAdvisor</em>' attribute.
	 */
	public IPotentialProblemsAdvisor getProblemsAdvisor() {
		if(problemsAdvisor == null)
			problemsAdvisor = getComplianceLevel().createValidationAdvisor(DefaultPotentialProblemsAdvisor.INSTANCE);
		return problemsAdvisor;
	}

	/**
	 * @return the value of the '<em>searchPath</em>' attribute.
	 */
	public String getSearchPath() {
		return searchPath;
	}

	/**
	 * @return The root folder for the validation
	 */
	public File getValidationRoot() {
		return validationRoot;
	}

	/**
	 * @return the allowFileOverride
	 */
	public boolean isAllowFileOverride() {
		return allowFileOverride;
	}

	/**
	 * @return the value of the '<em>checkLayout</em>' attribute.
	 */
	public boolean isCheckLayout() {
		return checkLayout;
	}

	/**
	 * Checking module semantics means that the module's dependencies are
	 * satisfied.
	 *
	 * @return the value of the '<em>checkModuleSemantics</em>' attribute.
	 */
	public boolean isCheckModuleSemantics() {
		return checkModuleSemantics;
	}

	/**
	 * @return the value of the '<em>checkReferences</em>' attribute.
	 */
	public boolean isCheckReferences() {
		return checkReferences;
	}

	/**
	 * @return wether or not type documentation should be extracted
	 */
	public boolean isExtractTypes() {
		return extractTypes;
	}

	/**
	 * @return wether or not to add ruby warnings to the diagnostic output
	 */
	public boolean isRubyWarnings() {
		return true;
	}

	/**
	 * Returns <code>true</code> if no validation filter has been specified or if the filter
	 * accepts the given candidate
	 *
	 * @param candidate
	 *            The candidate to check
	 * @return <code>true</code> if the file should be validated.
	 * @see #setValidationFilter(FileFilter)
	 */
	public boolean isValidationCandidate(File candidate) {
		return validationFilter == null || validationFilter.accept(candidate);
	}

	public ValidationOptions mergeFileOverride(Diagnostic diagnostics) {
		File geppettoRCFile = new File(validationRoot, ".geppetto-rc.json");

		try (InputStream in = new BufferedInputStream(new FileInputStream(geppettoRCFile))) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
			GeppettoRC geppettoRC = mapper.readValue(in, GeppettoRC.class);
			ValidationOptions options = new ValidationOptions(this);
			Advices advice = geppettoRC.getAdvice();
			IPotentialProblemsAdvisor ppAdvisor = options.getProblemsAdvisor();
			IModuleValidationAdvisor moduleAdvisor = options.getModuleValidationAdvisor();
			if(advice != null) {
				PotentialProblemsAdvisorBean ppBean = advice.getManifest();
				if(ppBean != null)
					ppAdvisor = ppBean.merge(ppAdvisor);

				ModuleValidationAdvisorBean mvBean = advice.getModule();
				if(mvBean != null)
					moduleAdvisor = mvBean.merge(moduleAdvisor);
			}
			IValidationAdvisor.ComplianceLevel level = geppettoRC.getComplianceLevel();
			if(level == null)
				level = options.getComplianceLevel();
			else
				options.setComplianceLevel(level);
			options.setProblemsAdvisor(level.createValidationAdvisor(ppAdvisor));
			options.setModuleValidationAdvisor(moduleAdvisor);

			Set<String> exclusionPatterns = geppettoRC.getExcludes();
			if(exclusionPatterns != null) {
				Set<String> merged = Sets.newHashSet(exclusionPatterns);
				merged.addAll(options.getExcludeGlobs());
				options.setExcludeGlobs(merged);
			}
			return options;
		}
		catch(FileNotFoundException e) {
		}
		catch(IOException e) {
			diagnostics.addChild(new ExceptionDiagnostic(
				Diagnostic.ERROR, ValidationService.PP_LINKING, "Unable to parse .geppetto-rc.json", e));
		}
		return this;
	}

	/**
	 * Sets the value of the '<em>allowFileOverride</em>' attribute.
	 *
	 * @param allowFileOverride
	 *            the new value of the '<em>allowFileOverride</em>' attribute.
	 */
	public void setAllowFileOverride(boolean allowFileOverride) {
		this.allowFileOverride = allowFileOverride;
	}

	/**
	 * Sets the value of the '<em>checkLayout</em>' attribute.
	 *
	 * @param checkLayout
	 *            the new value of the '<em>checkLayout</em>' attribute.
	 */
	public void setCheckLayout(boolean checkLayout) {
		this.checkLayout = checkLayout;
	}

	/**
	 * Sets the value of the '<em>checkModuleSemantics</em>' attribute.
	 *
	 * @param checkModuleSemantics
	 *            the new value of the '<em>checkModuleSemantics</em>'
	 *            attribute.
	 */
	public void setCheckModuleSemantics(boolean checkModuleSemantics) {
		this.checkModuleSemantics = checkModuleSemantics;
	}

	/**
	 * Sets the value of the '<em>checkReferences</em>' attribute.
	 *
	 * @param checkReferences
	 *            the new value of the '<em>checkReferences</em>' attribute.
	 */
	public void setCheckReferences(boolean checkReferences) {
		this.checkReferences = checkReferences;
	}

	/**
	 * Sets the value of the '<em>complianceLevel</em>' attribute.
	 *
	 * @param complianceLevel
	 *            the new value of the '<em>complianceLevel</em>' attribute.
	 */
	public void setComplianceLevel(ComplianceLevel complianceLevel) {
		this.complianceLevel = complianceLevel;
	}

	/**
	 * Sets the value of the '<em>encodingProvider</em>' attribute.
	 *
	 * @param encodingProvider
	 *            the new value of the '<em>encodingProvider</em>' attribute.
	 */
	public void setEncodingProvider(IEncodingProvider encodingProvider) {
		this.encodingProvider = encodingProvider;
	}

	/**
	 * Sets the value of the '<em>environment</em>' attribute.
	 *
	 * @param environment
	 *            the new value of the '<em>environment</em>' attribute.
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	/**
	 * @param excludeGlobs
	 *            the excludeGlobs to set
	 */
	public void setExcludeGlobs(Set<String> excludeGlobs) {
		this.excludeGlobs = excludeGlobs;
	}

	/**
	 * @param extractTypes
	 *            the extractTypes to set
	 */
	public void setExtractTypes(boolean extractTypes) {
		this.extractTypes = extractTypes;
	}

	/**
	 * @param fileFilter
	 *            the fileFilter to set
	 */
	public void setFileFilter(FileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

	/**
	 * Sets the value of the '<em>fileType</em>' attribute.
	 *
	 * @param fileType
	 *            the new value of the '<em>fileType</em>' attribute.
	 */
	public void setFileType(FileType fileType) {
		this.fileType = fileType == null
			? FileType.DETECT
			: fileType;
	}

	/**
	 * Sets the value of the '<em>manifestDir</em>' attribute.
	 *
	 * @param manifestDir
	 *            the new value of the '<em>manifestDir</em>' attribute.
	 */
	public void setManifestDir(String manifestDir) {
		this.manifestDir = manifestDir;
	}

	/**
	 * @param moduleValidationAdvisor
	 *            the moduleValidationAdvisor to set
	 */
	public void setModuleValidationAdvisor(IModuleValidationAdvisor moduleValidationAdvisor) {
		this.moduleValidationAdvisor = moduleValidationAdvisor;
	}

	/**
	 * Sets the value of the '<em>problemsAdvisor</em>' attribute.
	 *
	 * @param problemsAdvisor
	 *            the new value of the '<em>problemsAdvisor</em>' attribute.
	 */
	public void setProblemsAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
		this.problemsAdvisor = problemsAdvisor;
	}

	/**
	 * Sets the value of the '<em>searchPath</em>' attribute.
	 *
	 * @param searchPath
	 *            the new value of the '<em>searchPath</em>' attribute.
	 */
	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}

	/**
	 * Specify a specific a filter for the candidates to be validated. This filter does not limit the scope
	 * used when for the validating, just the actual files that will be validated.
	 *
	 * @param validationFilter
	 *            The filter for the candidates to validate
	 * @see #isValidationCandidate(File)
	 */
	public void setValidationFilter(FileFilter validationFilter) {
		this.validationFilter = validationFilter;
	}

	/**
	 * Sets the value of the '<em>validationRoot</em>' attribute.
	 *
	 * @param validationRoot
	 *            the new value of the '<em>validationRoot</em>' attribute.
	 */
	public void setValidationRoot(File validationRoot) {
		this.validationRoot = validationRoot;
	}
}
