package com.puppetlabs.geppetto.pp.dsl.validation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PotentialProblemsAdvisorBean implements IPotentialProblemsAdvisor, Serializable {

	private static final long serialVersionUID = 1L;

	private final ValidationPreference assignmentToVarNamedString;

	private final ValidationPreference assignmentToVarNamedTrusted;

	private final ValidationPreference attributeIsNotString;

	private final ValidationPreference booleansInStringForm;

	private final ValidationPreference caseDefaultShouldAppearLast;

	private final ValidationPreference deprecatedImport;

	private final ValidationPreference deprecatedNodeInheritance;

	private final ValidationPreference deprecatedPlusEquals;

	private final ValidationPreference deprecatedVariableName;

	private final ValidationPreference dqStringNotRequired;

	private final ValidationPreference dqStringNotRequiredVariable;

	private final ValidationPreference ensureShouldAppearFirstInResource;

	private final ValidationPreference interpolatedNonBraceEnclosedHyphens;

	private final ValidationPreference missingDefaultInSelector;

	private final ValidationPreference mlComments;

	private final ValidationPreference rightToLeftRelationships;

	private final ValidationPreference selectorDefaultShouldAppearLast;

	private final ValidationPreference unbracedInterpolation;

	private final ValidationPreference unquotedResourceTitles;

	private final ValidationPreference validityAssertedAtRuntime;

	public PotentialProblemsAdvisorBean() {
		this(DefaultPotentialProblemsAdvisor.INSTANCE);
	}

	public PotentialProblemsAdvisorBean(IPotentialProblemsAdvisor ppAdvisor) {
		this.assignmentToVarNamedString = ppAdvisor.getAssignmentToVarNamedString();
		this.assignmentToVarNamedTrusted = ppAdvisor.getAssignmentToVarNamedTrusted();
		this.attributeIsNotString = ppAdvisor.getAttributeIsNotString();
		this.booleansInStringForm = ppAdvisor.getBooleansInStringForm();
		this.caseDefaultShouldAppearLast = ppAdvisor.getCaseDefaultShouldAppearLast();
		this.deprecatedImport = ppAdvisor.getDeprecatedImport();
		this.deprecatedNodeInheritance = ppAdvisor.getDeprecatedNodeInheritance();
		this.deprecatedPlusEquals = ppAdvisor.getDeprecatedPlusEquals();
		this.deprecatedVariableName = ppAdvisor.getDeprecatedVariableName();
		this.dqStringNotRequired = ppAdvisor.getDqStringNotRequired();
		this.dqStringNotRequiredVariable = ppAdvisor.getDqStringNotRequiredVariable();
		this.ensureShouldAppearFirstInResource = ppAdvisor.getEnsureShouldAppearFirstInResource();
		this.interpolatedNonBraceEnclosedHyphens = ppAdvisor.getInterpolatedNonBraceEnclosedHyphens();
		this.missingDefaultInSelector = ppAdvisor.getMissingDefaultInSelector();
		this.mlComments = ppAdvisor.getMlComments();
		this.selectorDefaultShouldAppearLast = ppAdvisor.getSelectorDefaultShouldAppearLast();
		this.rightToLeftRelationships = ppAdvisor.getRightToLeftRelationships();
		this.unbracedInterpolation = ppAdvisor.getUnbracedInterpolation();
		this.unquotedResourceTitles = ppAdvisor.getUnquotedResourceTitles();
		this.validityAssertedAtRuntime = ppAdvisor.getValidityAssertedAtRuntime();
	}

	@JsonCreator
	public PotentialProblemsAdvisorBean(
		// @fmtOff
			@JsonProperty("assignment_to_var_named_string") ValidationPreference assignmentToVarNamedString,
			@JsonProperty("assignment_to_var_named_trusted") ValidationPreference assignmentToVarNamedTrusted,
			@JsonProperty("attribute_is_not_string") ValidationPreference attributeIsNotString,
			@JsonProperty("booleans_in_string_form") ValidationPreference booleansInStringForm,
			@JsonProperty("case_default_should_appear_last") ValidationPreference caseDefaultShouldAppearLast,
			@JsonProperty("deprecated_import") ValidationPreference deprecatedImport,
			@JsonProperty("deprecated_node_inheritance") ValidationPreference deprecatedNodeInheritance,
			@JsonProperty("deprecated_plus_equals") ValidationPreference deprecatedPlusEquals,
			@JsonProperty("deprecated_variable_name") ValidationPreference deprecatedVariableName,
			@JsonProperty("dqString_not_required") ValidationPreference dqStringNotRequired,
			@JsonProperty("dqString_not_required_variable") ValidationPreference dqStringNotRequiredVariable,
			@JsonProperty("ensure_should_appear_first_in_resource") ValidationPreference ensureShouldAppearFirstInResource,
			@JsonProperty("interpolated_non_brace_enclosed_hyphens") ValidationPreference interpolatedNonBraceEnclosedHyphens,
			@JsonProperty("missing_default_in_selector") ValidationPreference missingDefaultInSelector,
			@JsonProperty("ml_comments") ValidationPreference mlComments,
			@JsonProperty("right_to_left_relationships") ValidationPreference rightToLeftRelationships,
			@JsonProperty("selector_default_should_appear_last") ValidationPreference selectorDefaultShouldAppearLast,
			@JsonProperty("unbraced_interpolation") ValidationPreference unbracedInterpolation,
			@JsonProperty("unquoted_resource_titles") ValidationPreference unquotedResourceTitles,
			@JsonProperty("validity_asserted_at_runtime") ValidationPreference validityAssertedAtRuntime) {
		// @fmtOn
		this.assignmentToVarNamedString = assignmentToVarNamedString;
		this.assignmentToVarNamedTrusted = assignmentToVarNamedTrusted;
		this.attributeIsNotString = attributeIsNotString;
		this.booleansInStringForm = booleansInStringForm;
		this.caseDefaultShouldAppearLast = caseDefaultShouldAppearLast;
		this.deprecatedImport = deprecatedImport;
		this.deprecatedNodeInheritance = deprecatedNodeInheritance;
		this.deprecatedPlusEquals = deprecatedPlusEquals;
		this.deprecatedVariableName = deprecatedVariableName;
		this.dqStringNotRequired = dqStringNotRequired;
		this.dqStringNotRequiredVariable = dqStringNotRequiredVariable;
		this.ensureShouldAppearFirstInResource = ensureShouldAppearFirstInResource;
		this.interpolatedNonBraceEnclosedHyphens = interpolatedNonBraceEnclosedHyphens;
		this.missingDefaultInSelector = missingDefaultInSelector;
		this.mlComments = mlComments;
		this.rightToLeftRelationships = rightToLeftRelationships;
		this.selectorDefaultShouldAppearLast = selectorDefaultShouldAppearLast;
		this.unbracedInterpolation = unbracedInterpolation;
		this.unquotedResourceTitles = unquotedResourceTitles;
		this.validityAssertedAtRuntime = validityAssertedAtRuntime;
	}

	@Override
	public ValidationPreference getAssignmentToVarNamedString() {
		return assignmentToVarNamedString;
	}

	@Override
	public ValidationPreference getAssignmentToVarNamedTrusted() {
		return assignmentToVarNamedTrusted;
	}

	@Override
	public ValidationPreference getAttributeIsNotString() {
		return attributeIsNotString;
	}

	@Override
	public ValidationPreference getBooleansInStringForm() {
		return booleansInStringForm;
	}

	@Override
	public ValidationPreference getCaseDefaultShouldAppearLast() {
		return caseDefaultShouldAppearLast;
	}

	@Override
	public ValidationPreference getDeprecatedImport() {
		return deprecatedImport;
	}

	@Override
	public ValidationPreference getDeprecatedNodeInheritance() {
		return deprecatedNodeInheritance;
	}

	@Override
	public ValidationPreference getDeprecatedPlusEquals() {
		return deprecatedPlusEquals;
	}

	@Override
	public ValidationPreference getDeprecatedVariableName() {
		return deprecatedVariableName;
	}

	@Override
	public ValidationPreference getDqStringNotRequired() {
		return dqStringNotRequired;
	}

	@Override
	public ValidationPreference getDqStringNotRequiredVariable() {
		return dqStringNotRequiredVariable;
	}

	@Override
	public ValidationPreference getEnsureShouldAppearFirstInResource() {
		return ensureShouldAppearFirstInResource;
	}

	@Override
	public ValidationPreference getInterpolatedNonBraceEnclosedHyphens() {
		return interpolatedNonBraceEnclosedHyphens;
	}

	@Override
	public ValidationPreference getMissingDefaultInSelector() {
		return missingDefaultInSelector;
	}

	@Override
	public ValidationPreference getMlComments() {
		return mlComments;
	}

	@Override
	public ValidationPreference getRightToLeftRelationships() {
		return rightToLeftRelationships;
	}

	@Override
	public ValidationPreference getSelectorDefaultShouldAppearLast() {
		return selectorDefaultShouldAppearLast;
	}

	@Override
	public ValidationPreference getUnbracedInterpolation() {
		return unbracedInterpolation;
	}

	@Override
	public ValidationPreference getUnquotedResourceTitles() {
		return unquotedResourceTitles;
	}

	@Override
	public ValidationPreference getValidityAssertedAtRuntime() {
		return validityAssertedAtRuntime;
	}

	public IPotentialProblemsAdvisor merge(IPotentialProblemsAdvisor problemsAdvisor) {
		// @fmtOff
		return new PotentialProblemsAdvisorBean(
			assignmentToVarNamedString == null ? problemsAdvisor.getAssignmentToVarNamedString() : assignmentToVarNamedString,
			assignmentToVarNamedTrusted == null ? problemsAdvisor.getAssignmentToVarNamedTrusted() : assignmentToVarNamedTrusted,
			attributeIsNotString == null ? problemsAdvisor.getAttributeIsNotString() : attributeIsNotString,
			booleansInStringForm == null ? problemsAdvisor.getBooleansInStringForm() : booleansInStringForm,
			caseDefaultShouldAppearLast == null ? problemsAdvisor.getCaseDefaultShouldAppearLast() : caseDefaultShouldAppearLast,
			deprecatedImport == null ? problemsAdvisor.getDeprecatedImport() : deprecatedImport,
			deprecatedNodeInheritance == null ? problemsAdvisor.getDeprecatedNodeInheritance() : deprecatedNodeInheritance,
			deprecatedPlusEquals == null ? problemsAdvisor.getDeprecatedPlusEquals() : deprecatedPlusEquals,
			deprecatedVariableName == null ? problemsAdvisor.getDeprecatedVariableName() : deprecatedVariableName,
			dqStringNotRequired == null ? problemsAdvisor.getDqStringNotRequired() : dqStringNotRequired,
			dqStringNotRequiredVariable == null ? problemsAdvisor.getDqStringNotRequiredVariable() : dqStringNotRequiredVariable,
			ensureShouldAppearFirstInResource == null ? problemsAdvisor.getEnsureShouldAppearFirstInResource() : ensureShouldAppearFirstInResource,
			interpolatedNonBraceEnclosedHyphens == null ? problemsAdvisor.getInterpolatedNonBraceEnclosedHyphens() : interpolatedNonBraceEnclosedHyphens,
			missingDefaultInSelector == null ? problemsAdvisor.getMissingDefaultInSelector() : missingDefaultInSelector,
			mlComments == null ? problemsAdvisor.getMlComments() : mlComments,
			rightToLeftRelationships == null ? problemsAdvisor.getRightToLeftRelationships() : rightToLeftRelationships,
			selectorDefaultShouldAppearLast == null ? problemsAdvisor.getSelectorDefaultShouldAppearLast() : selectorDefaultShouldAppearLast,
			unbracedInterpolation == null ? problemsAdvisor.getUnbracedInterpolation() : unbracedInterpolation,
			unquotedResourceTitles == null ? problemsAdvisor.getUnquotedResourceTitles() : unquotedResourceTitles,
			validityAssertedAtRuntime == null ? problemsAdvisor.getValidityAssertedAtRuntime() : validityAssertedAtRuntime);
		// @fmtOn
	}
}
