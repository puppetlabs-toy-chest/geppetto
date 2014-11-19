package com.puppetlabs.geppetto.module.dsl.validation;

import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

public class ModuleValidationAdvisorWrapper implements IModuleValidationAdvisor {
	private IModuleValidationAdvisor advisor;

	public ModuleValidationAdvisorWrapper(IModuleValidationAdvisor advisor) {
		this.advisor = advisor;
	}

	@Override
	public ValidationPreference getCircularDependency() {
		return advisor.getCircularDependency();
	}

	@Override
	public ValidationPreference getDependencyVersionMismatch() {
		return advisor.getDependencyVersionMismatch();
	}

	@Override
	public ValidationPreference getDeprecatedKey() {
		return advisor.getDeprecatedKey();
	}

	@Override
	public ValidationPreference getMissingForgeRequiredFields() {
		return advisor.getMissingForgeRequiredFields();
	}

	@Override
	public ValidationPreference getModuleClassNotInInitPP() {
		return advisor.getModuleClassNotInInitPP();
	}

	@Override
	public ValidationPreference getModulefileExists() {
		return advisor.getModulefileExists();
	}

	@Override
	public ValidationPreference getModulefileExistsAndIsUsed() {
		return advisor.getModulefileExistsAndIsUsed();
	}

	@Override
	public ValidationPreference getModuleNameNotStrict() {
		return advisor.getModuleNameNotStrict();
	}

	@Override
	public ValidationPreference getModuleRedefinition() {
		return advisor.getModuleRedefinition();
	}

	@Override
	public ValidationPreference getUnexpectedSubmodule() {
		return advisor.getUnexpectedSubmodule();
	}

	@Override
	public ValidationPreference getUnrecognizedKey() {
		return advisor.getUnrecognizedKey();
	}

	@Override
	public ValidationPreference getUnresolvedReference() {
		return advisor.getUnresolvedReference();
	}

	@Override
	public ValidationPreference getWhitespaceInTag() {
		return advisor.getWhitespaceInTag();
	}

	public void setAdvisor(IModuleValidationAdvisor advisor) {
		this.advisor = advisor;
	}
}
