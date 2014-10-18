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

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.Sets;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.module.dsl.validation.DefaultModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.validation.runner.DefaultEncodingProvider;
import com.puppetlabs.geppetto.validation.runner.IEncodingProvider;

public class ValidationOptions {
	private ComplianceLevel complianceLevel;

	private IEncodingProvider encodingProvider;

	private String environment;

	private String manifestDir;

	private FileType fileType;

	private URI platformURI;

	private IPotentialProblemsAdvisor problemsAdvisor;

	private String searchPath;

	private boolean checkLayout;

	private boolean checkModuleSemantics;

	private boolean checkReferences;

	private boolean allowFileOverride;

	private IModuleValidationAdvisor moduleValidationAdvisor;

	private Set<String> folderExclusionPatterns;

	private FileFilter fileFilter;

	private FileFilter validationFilter;

	public static final Set<String> DEFAULT_EXCLUTION_PATTERNS;

	static {
		DEFAULT_EXCLUTION_PATTERNS = Collections.unmodifiableSet(Sets.newHashSet("pkg", "spec"));
	}

	public ValidationOptions() {
		fileType = FileType.DETECT;
	}

	public ValidationOptions(ValidationOptions source) {
		complianceLevel = source.complianceLevel;
		encodingProvider = source.encodingProvider;
		environment = source.environment;
		manifestDir = source.manifestDir;
		fileType = source.fileType;
		platformURI = source.platformURI;
		problemsAdvisor = source.problemsAdvisor;
		searchPath = source.searchPath;
		checkLayout = source.checkLayout;
		checkModuleSemantics = source.checkModuleSemantics;
		checkReferences = source.checkReferences;
		moduleValidationAdvisor = source.moduleValidationAdvisor;
		folderExclusionPatterns = source.folderExclusionPatterns;
		fileFilter = source.fileFilter;
		validationFilter = source.validationFilter;
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
	 * @return the folderExclusionPattern
	 */
	public Set<String> getFolderExclusionPatterns() {
		if(folderExclusionPatterns == null) {
			folderExclusionPatterns = Sets.newHashSet(FileUtils.DEFAULT_EXCLUDES);
			folderExclusionPatterns.addAll(DEFAULT_EXCLUTION_PATTERNS);
		}
		return folderExclusionPatterns;
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
			moduleValidationAdvisor = new DefaultModuleValidationAdvisor();
		return moduleValidationAdvisor;
	}

	/**
	 * A URI to a pptp resource in string form. If null, a default pptp will be
	 * used when validating. An unloadable pptp reference will result in an
	 * internal error.
	 *
	 * @return the value of the '<em>platformURI</em>' attribute.
	 */
	public URI getPlatformURI() {
		return platformURI;
	}

	/**
	 * @return the value of the '<em>problemsAdvisor</em>' attribute.
	 */
	public IPotentialProblemsAdvisor getProblemsAdvisor() {
		if(problemsAdvisor == null)
			problemsAdvisor = getComplianceLevel().createValidationAdvisor(new DefaultPotentialProblemsAdvisor());
		return problemsAdvisor;
	}

	/**
	 * @return the value of the '<em>searchPath</em>' attribute.
	 */
	public String getSearchPath() {
		return searchPath;
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
	 * @param folderExclusionPatterns
	 *            the folderExclusionPattern to set
	 */
	public void setFolderExclusionPatterns(Set<String> folderExclusionPatterns) {
		this.folderExclusionPatterns = folderExclusionPatterns;
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
	 * Sets the value of the '<em>platformURI</em>' attribute.
	 *
	 * @param platformURI
	 *            the new value of the '<em>platformURI</em>' attribute.
	 */
	public void setPlatformURI(URI platformURI) {
		this.platformURI = platformURI;
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
}
