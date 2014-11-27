/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.module.dsl.validation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

public class ModuleValidationAdvisorBean implements IModuleValidationAdvisor, Serializable {
	private static final long serialVersionUID = 1L;

	private ValidationPreference circularDependency;

	private ValidationPreference dependencyDeclaredMoreThanOnce;

	private ValidationPreference dependencyVersionMismatch;

	private ValidationPreference deprecatedKey;

	private ValidationPreference missingForgeRequiredFields;

	private ValidationPreference moduleClassNotInInitPP;

	private ValidationPreference modulefileExists;

	private ValidationPreference modulefileExistsAndIsUsed;

	private ValidationPreference moduleNameNotStrict;

	private ValidationPreference moduleRedefinition;

	private ValidationPreference unexpectedSubmodule;

	private ValidationPreference unrecognizedKey;

	private ValidationPreference unresolvedReference;

	private ValidationPreference whitespaceInTag;

	public ModuleValidationAdvisorBean() {
		this(DefaultModuleValidationAdvisor.INSTANCE);
	}

	public ModuleValidationAdvisorBean(IModuleValidationAdvisor moduleAdvisor) {
		circularDependency = moduleAdvisor.getCircularDependency();
		dependencyVersionMismatch = moduleAdvisor.getDependencyVersionMismatch();
		deprecatedKey = moduleAdvisor.getDeprecatedKey();
		missingForgeRequiredFields = moduleAdvisor.getMissingForgeRequiredFields();
		moduleClassNotInInitPP = moduleAdvisor.getModuleClassNotInInitPP();
		moduleNameNotStrict = moduleAdvisor.getModuleNameNotStrict();
		moduleRedefinition = moduleAdvisor.getModuleRedefinition();
		modulefileExists = moduleAdvisor.getModulefileExists();
		modulefileExistsAndIsUsed = moduleAdvisor.getModulefileExistsAndIsUsed();
		unexpectedSubmodule = moduleAdvisor.getUnexpectedSubmodule();
		unrecognizedKey = moduleAdvisor.getUnrecognizedKey();
		unresolvedReference = moduleAdvisor.getUnresolvedReference();
		whitespaceInTag = moduleAdvisor.getWhitespaceInTag();
	}

	/**
	 * @return the circularDependency
	 */
	@Override
	@JsonProperty("circular_dependency")
	public ValidationPreference getCircularDependency() {
		return circularDependency == null
			? DefaultModuleValidationAdvisor.INSTANCE.getCircularDependency()
			: circularDependency;
	}

	@Override
	@JsonProperty("dependency_declared_more_than_once")
	public ValidationPreference getDependencyDeclaredMoreThanOnce() {
		return dependencyDeclaredMoreThanOnce;
	}

	/**
	 * @return the dependencyVersionMismatch
	 */
	@Override
	@JsonProperty("dependency_version_mismatch")
	public ValidationPreference getDependencyVersionMismatch() {
		return dependencyVersionMismatch == null
			? DefaultModuleValidationAdvisor.INSTANCE.getDependencyVersionMismatch()
			: dependencyVersionMismatch;
	}

	/**
	 * @return the deprecatedKey
	 */
	@Override
	@JsonProperty("deprecated_key")
	public ValidationPreference getDeprecatedKey() {
		return deprecatedKey == null
			? DefaultModuleValidationAdvisor.INSTANCE.getDeprecatedKey()
			: deprecatedKey;
	}

	/**
	 * @return the missingForgeRequiredFields
	 */
	@Override
	@JsonProperty("missing_forge_required_fields")
	public ValidationPreference getMissingForgeRequiredFields() {
		return missingForgeRequiredFields == null
			? DefaultModuleValidationAdvisor.INSTANCE.getMissingForgeRequiredFields()
			: missingForgeRequiredFields;
	}

	@Override
	@JsonProperty("module_class_not_in_init_pp")
	public ValidationPreference getModuleClassNotInInitPP() {
		return moduleClassNotInInitPP == null
			? DefaultModuleValidationAdvisor.INSTANCE.getModuleClassNotInInitPP()
			: moduleClassNotInInitPP;
	}

	/**
	 * @return the modulefileExists
	 */
	@Override
	@JsonProperty("modulefile_exists")
	public ValidationPreference getModulefileExists() {
		return modulefileExists == null
			? DefaultModuleValidationAdvisor.INSTANCE.getModulefileExists()
			: modulefileExists;
	}

	/**
	 * @return the modulefileExistsAndIsUsed
	 */
	@Override
	@JsonProperty("modulefile_exists_and_is_used")
	public ValidationPreference getModulefileExistsAndIsUsed() {
		return modulefileExistsAndIsUsed == null
			? DefaultModuleValidationAdvisor.INSTANCE.getModulefileExistsAndIsUsed()
			: modulefileExistsAndIsUsed;
	}

	/**
	 * @return the moduleNameNotStrict
	 */
	@Override
	@JsonProperty("module_name_not_strict")
	public ValidationPreference getModuleNameNotStrict() {
		return moduleNameNotStrict == null
			? DefaultModuleValidationAdvisor.INSTANCE.getModuleNameNotStrict()
			: moduleNameNotStrict;
	}

	/**
	 * @return the moduleRedefinition
	 */
	@Override
	@JsonProperty("module_redefinition")
	public ValidationPreference getModuleRedefinition() {
		return moduleRedefinition == null
			? DefaultModuleValidationAdvisor.INSTANCE.getModuleRedefinition()
			: moduleRedefinition;
	}

	/**
	 * @return the unexpectedSubmodule
	 */
	@Override
	@JsonProperty("unexpected_submodule")
	public ValidationPreference getUnexpectedSubmodule() {
		return unexpectedSubmodule == null
			? DefaultModuleValidationAdvisor.INSTANCE.getUnexpectedSubmodule()
			: unexpectedSubmodule;
	}

	/**
	 * @return the unrecognizedKey
	 */
	@Override
	@JsonProperty("unrecognized_key")
	public ValidationPreference getUnrecognizedKey() {
		return unrecognizedKey == null
			? DefaultModuleValidationAdvisor.INSTANCE.getUnrecognizedKey()
			: unrecognizedKey;
	}

	/**
	 * @return the unresolvedReference
	 */
	@Override
	@JsonProperty("unresolved_reference")
	public ValidationPreference getUnresolvedReference() {
		return unresolvedReference == null
			? DefaultModuleValidationAdvisor.INSTANCE.getUnresolvedReference()
			: unresolvedReference;
	}

	/**
	 * @return the whitespaceInTag
	 */
	@Override
	@JsonProperty("whitespace_in_tag")
	public ValidationPreference getWhitespaceInTag() {
		return whitespaceInTag == null
			? DefaultModuleValidationAdvisor.INSTANCE.getWhitespaceInTag()
			: whitespaceInTag;
	}

	public IModuleValidationAdvisor merge(IModuleValidationAdvisor moduleAdvisor) {
		ModuleValidationAdvisorBean c = new ModuleValidationAdvisorBean(this);
		if(circularDependency == null)
			c.circularDependency = moduleAdvisor.getCircularDependency();
		if(dependencyVersionMismatch == null)
			c.dependencyVersionMismatch = moduleAdvisor.getDependencyVersionMismatch();
		if(deprecatedKey == null)
			c.deprecatedKey = moduleAdvisor.getDeprecatedKey();
		if(missingForgeRequiredFields == null)
			c.missingForgeRequiredFields = moduleAdvisor.getMissingForgeRequiredFields();
		if(moduleClassNotInInitPP == null)
			c.moduleClassNotInInitPP = moduleAdvisor.getModuleClassNotInInitPP();
		if(moduleNameNotStrict == null)
			c.moduleNameNotStrict = moduleAdvisor.getModuleNameNotStrict();
		if(moduleRedefinition == null)
			c.moduleRedefinition = moduleAdvisor.getModuleRedefinition();
		if(modulefileExists == null)
			c.modulefileExists = moduleAdvisor.getModulefileExists();
		if(modulefileExistsAndIsUsed == null)
			c.modulefileExistsAndIsUsed = moduleAdvisor.getModulefileExistsAndIsUsed();
		if(unexpectedSubmodule == null)
			c.unexpectedSubmodule = moduleAdvisor.getUnexpectedSubmodule();
		if(unrecognizedKey == null)
			c.unrecognizedKey = moduleAdvisor.getUnrecognizedKey();
		if(unresolvedReference == null)
			c.unresolvedReference = moduleAdvisor.getUnresolvedReference();
		if(whitespaceInTag == null)
			c.whitespaceInTag = moduleAdvisor.getWhitespaceInTag();
		return c;
	}

	/**
	 * @param circularDependency
	 *            the circularDependency to set
	 */
	public void setCircularDependency(ValidationPreference circularDependency) {
		this.circularDependency = circularDependency;
	}

	/**
	 * @param dependencyDeclaredMoreThanOnce
	 *            the dependencyDeclaredMoreThanOnce to set
	 */
	public void setDependencyDeclaredMoreThanOnce(ValidationPreference dependencyDeclaredMoreThanOnce) {
		this.dependencyDeclaredMoreThanOnce = dependencyDeclaredMoreThanOnce;
	}

	/**
	 * @param dependencyVersionMismatch
	 *            the dependencyVersionMismatch to set
	 */
	public void setDependencyVersionMismatch(ValidationPreference dependencyVersionMismatch) {
		this.dependencyVersionMismatch = dependencyVersionMismatch;
	}

	/**
	 * @param deprecatedKey
	 *            the deprecatedKey to set
	 */
	public void setDeprecatedKey(ValidationPreference deprecatedKey) {
		this.deprecatedKey = deprecatedKey;
	}

	/**
	 * @param missingForgeRequiredFields
	 *            the missingForgeRequiredFields to set
	 */
	public void setMissingForgeRequiredFields(ValidationPreference missingForgeRequiredFields) {
		this.missingForgeRequiredFields = missingForgeRequiredFields;
	}

	/**
	 * @param moduleClassNotInInitPP
	 *            the moduleClassNotInInitPP to set
	 */
	public void setModuleClassNotInInitPP(ValidationPreference moduleClassNotInInitPP) {
		this.moduleClassNotInInitPP = moduleClassNotInInitPP;
	}

	/**
	 * @param modulefileExists
	 *            the modulefileExists to set
	 */
	public void setModulefileExists(ValidationPreference modulefileExists) {
		this.modulefileExists = modulefileExists;
	}

	/**
	 * @param modulefileExistsAndIsUsed
	 *            the modulefileExistsAndIsUsed to set
	 */
	public void setModulefileExistsAndIsUsed(ValidationPreference modulefileExistsAndIsUsed) {
		this.modulefileExistsAndIsUsed = modulefileExistsAndIsUsed;
	}

	/**
	 * @param moduleNameNotStrict
	 *            the moduleNameNotStrict to set
	 */
	public void setModuleNameNotStrict(ValidationPreference moduleNameNotStrict) {
		this.moduleNameNotStrict = moduleNameNotStrict;
	}

	/**
	 * @param moduleRedefinition
	 *            the moduleRedefinition to set
	 */
	public void setModuleRedefinition(ValidationPreference moduleRedefinition) {
		this.moduleRedefinition = moduleRedefinition;
	}

	/**
	 * @param unexpectedSubmodule
	 *            the unexpectedSubmodule to set
	 */
	public void setUnexpectedSubmodule(ValidationPreference unexpectedSubmodule) {
		this.unexpectedSubmodule = unexpectedSubmodule;
	}

	/**
	 * @param unrecognizedKey
	 *            the unrecognizedKey to set
	 */
	public void setUnrecognizedKey(ValidationPreference unrecognizedKey) {
		this.unrecognizedKey = unrecognizedKey;
	}

	/**
	 * @param unresolvedReference
	 *            the unresolvedReference to set
	 */
	public void setUnresolvedReference(ValidationPreference unresolvedReference) {
		this.unresolvedReference = unresolvedReference;
	}

	/**
	 * @param whitespaceInTag
	 *            the whitespaceInTag to set
	 */
	public void setWhitespaceInTag(ValidationPreference whitespaceInTag) {
		this.whitespaceInTag = whitespaceInTag;
	}
}
