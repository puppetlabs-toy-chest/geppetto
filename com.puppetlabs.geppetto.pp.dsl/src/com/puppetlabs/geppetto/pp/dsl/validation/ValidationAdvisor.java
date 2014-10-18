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
package com.puppetlabs.geppetto.pp.dsl.validation;

public class ValidationAdvisor {

	public static class BaseValidationAdvisor implements IPotentialProblemsAdvisor, IStylisticProblemsAdvisor {

		private IPotentialProblemsAdvisor problemsAdvisor;

		protected BaseValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
			this.problemsAdvisor = problemsAdvisor;
		}

		@Override
		public ValidationPreference getAssignmentToVarNamedString() {
			return problemsAdvisor.getAssignmentToVarNamedString();
		}

		@Override
		public ValidationPreference getAssignmentToVarNamedTrusted() {
			return problemsAdvisor.getAssignmentToVarNamedTrusted();
		}

		@Override
		public ValidationPreference getAttributeIsNotString() {
			return problemsAdvisor.getAttributeIsNotString();
		}

		@Override
		public ValidationPreference getBooleansInStringForm() {
			return problemsAdvisor.getBooleansInStringForm();
		}

		@Override
		public ValidationPreference getCaseDefaultShouldAppearLast() {
			return problemsAdvisor.getCaseDefaultShouldAppearLast();
		}

		@Override
		public ValidationPreference getDeprecatedImport() {
			return problemsAdvisor.getDeprecatedImport();
		}

		@Override
		public ValidationPreference getDeprecatedNodeInheritance() {
			return problemsAdvisor.getDeprecatedNodeInheritance();
		}

		@Override
		public ValidationPreference getDeprecatedPlusEquals() {
			return problemsAdvisor.getDeprecatedPlusEquals();
		}

		@Override
		public ValidationPreference getDeprecatedVariableName() {
			return problemsAdvisor.getDeprecatedVariableName();
		}

		@Override
		public ValidationPreference getDqStringNotRequired() {
			return problemsAdvisor.getDqStringNotRequired();
		}

		@Override
		public ValidationPreference getDqStringNotRequiredVariable() {
			return problemsAdvisor.getDqStringNotRequiredVariable();
		}

		@Override
		public ValidationPreference getEnsureShouldAppearFirstInResource() {
			return problemsAdvisor.getEnsureShouldAppearFirstInResource();
		}

		@Override
		public ValidationPreference getInterpolatedNonBraceEnclosedHyphens() {
			return problemsAdvisor.getInterpolatedNonBraceEnclosedHyphens();
		}

		@Override
		public ValidationPreference getMissingDefaultInSelector() {
			return problemsAdvisor.getMissingDefaultInSelector();
		}

		@Override
		public ValidationPreference getMlComments() {
			return problemsAdvisor.getMlComments();
		}

		@Override
		public ValidationPreference getRightToLeftRelationships() {
			return problemsAdvisor.getRightToLeftRelationships();
		}

		@Override
		public ValidationPreference getSelectorDefaultShouldAppearLast() {
			return problemsAdvisor.getSelectorDefaultShouldAppearLast();
		}

		@Override
		public ValidationPreference getUnbracedInterpolation() {
			return problemsAdvisor.getUnbracedInterpolation();
		}

		@Override
		public ValidationPreference getUnquotedResourceTitles() {
			return problemsAdvisor.getUnquotedResourceTitles();
		}

		@Override
		public ValidationPreference getValidityAssertedAtRuntime() {
			return problemsAdvisor.getValidityAssertedAtRuntime();
		}
	}

	/**
	 * Validation Advisor for Puppet 2.6
	 */
	public static class ValidationAdvisor_2_6 extends BaseValidationAdvisor implements IValidationAdvisor {
		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_2_6(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowAnyValueAsHashKey() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowChainedAssignments() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowExpressionLastInBlocks() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowExtendedDependencyTypes() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowExtendedMatchRHS() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowExtendedTitleExpressions() {
			return false;
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowHashInSelector() {
			return true;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowInheritanceFromParameterizedClass() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowLambdas() {
			return false;
		}

		@Override
		public boolean allowModeWithNonOctalIntegerLiterals() {
			return true; // Always parsed as a string by Puppet so OK
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowModulo() {
			return false;
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowMoreThan2AtInSequence() {
			return true;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowRHSConditionals() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowSeparatorExpression() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowSplashAttribute() {
			return false;
		}

		@Override
		public boolean allowTypeDefinitions() {
			return false;
		}

		@Override
		public boolean allowUnless() {
			return false;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowUnlessElse() {
			return false;
		}

		@Override
		public boolean allowUnquotedQualifiedResourceNames() {
			return false;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.ERROR;
		}

		@Override
		public ValidationPreference definitionParamterMissingDollar() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference hyphensInNames() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference periodInCase() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.IGNORE;
		}
	}

	/**
	 * Validation Advisor for Puppet 2.7
	 */
	public static class ValidationAdvisor_2_7 extends ValidationAdvisor_2_6 implements IValidationAdvisor {

		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_2_7(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		@Override
		public boolean allowUnquotedQualifiedResourceNames() {
			return true;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference periodInCase() {
			return ValidationPreference.IGNORE;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.WARNING;
		}

	}

	/**
	 * Validation Advisor for Puppet 3.0
	 */
	public static class ValidationAdvisor_3_0 extends ValidationAdvisor_2_7 implements IValidationAdvisor {
		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_3_0(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowExtendedDependencyTypes() {
			return true;
		}

		/**
		 * @returns true, but unsure if really supported
		 */
		@Override
		public boolean allowInheritanceFromParameterizedClass() {
			return true;
		}

		@Override
		public boolean allowUnless() {
			return true;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.IGNORE;
		}

		@Override
		public ValidationPreference definitionParamterMissingDollar() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference hyphensInNames() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.ERROR;
		}

	}

	public static class ValidationAdvisor_3_2 extends ValidationAdvisor_3_0 implements IValidationAdvisor {
		protected ValidationAdvisor_3_2(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		@Override
		public boolean allowModulo() {
			return true;
		}
	}

	public static class ValidationAdvisor_3_4 extends ValidationAdvisor_3_2 implements IValidationAdvisor {
		protected ValidationAdvisor_3_4(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		@Override
		public ValidationPreference getAssignmentToVarNamedTrusted() {
			return ValidationPreference.ERROR;
		}
	}

	public static class ValidationAdvisor_3_5 extends ValidationAdvisor_3_4 implements IValidationAdvisor {
		protected ValidationAdvisor_3_5(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}
	}

	public static class ValidationAdvisor_3_6 extends ValidationAdvisor_3_5 implements IValidationAdvisor {
		protected ValidationAdvisor_3_6(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}
	}

	public static class ValidationAdvisor_3_7 extends ValidationAdvisor_3_6 implements IValidationAdvisor {
		protected ValidationAdvisor_3_7(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}
	}

	public static class ValidationAdvisor_4_0 extends ValidationAdvisor_3_7 implements IValidationAdvisor {
		protected ValidationAdvisor_4_0(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		@Override
		public boolean allowAnyValueAsHashKey() {
			return true;
		}

		@Override
		public boolean allowChainedAssignments() {
			return true;
		}

		@Override
		public boolean allowExpressionLastInBlocks() {
			return true;
		}

		@Override
		public boolean allowExtendedMatchRHS() {
			return true;
		}

		@Override
		public boolean allowExtendedTitleExpressions() {
			return true;
		}

		@Override
		public boolean allowLambdas() {
			return true;
		}

		@Override
		public boolean allowModeWithNonOctalIntegerLiterals() {
			return false; // Parsed as a integer by Puppet >= 4.0 so this is not OK
		}

		@Override
		public boolean allowRHSConditionals() {
			return true;
		}

		@Override
		public boolean allowSeparatorExpression() {
			return true;
		}

		@Override
		public boolean allowSplashAttribute() {
			return true;
		}

		@Override
		public boolean allowTypeDefinitions() {
			return true;
		}

		@Override
		public boolean allowUnlessElse() {
			return true;
		}

		@Override
		public ValidationPreference getDeprecatedImport() {
			return ValidationPreference.ERROR;
		}

		@Override
		public ValidationPreference getDeprecatedNodeInheritance() {
			return ValidationPreference.ERROR;
		}

		@Override
		public ValidationPreference getDeprecatedPlusEquals() {
			return ValidationPreference.ERROR;
		}

		@Override
		public ValidationPreference getDeprecatedVariableName() {
			return ValidationPreference.ERROR;
		}
	}
}
