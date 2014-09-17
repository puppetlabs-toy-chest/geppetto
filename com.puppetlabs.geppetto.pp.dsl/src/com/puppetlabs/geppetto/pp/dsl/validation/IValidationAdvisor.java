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

import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_2_6;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_2_7;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_0;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_2;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_4;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_5;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_6;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_3_7;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisor.ValidationAdvisor_4_0;

/**
 * An advisor to validation. Different implementations of this class capture the validation rules specific
 * to a language version.
 */
public interface IValidationAdvisor extends IPotentialProblemsAdvisor {
	enum ComplianceLevel {
		PUPPET_2_6("2.6") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_2_6(problemsAdvisor);
			}
		},
		PUPPET_2_7("2.7") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_2_7(problemsAdvisor);
			}
		},
		PUPPET_3_0("3.0") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_0(problemsAdvisor);
			}
		},
		PUPPET_3_2("3.2") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_2(problemsAdvisor);
			}
		},
		PUPPET_3_4("3.4") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_4(problemsAdvisor);
			}
		},
		PUPPET_3_5("3.5") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_5(problemsAdvisor);
			}
		},
		PUPPET_3_6("3.6") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_6(problemsAdvisor);
			}
		},
		PUPPET_3_7("3.7") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_3_7(problemsAdvisor);
			}
		},
		PUPPET_4_0("4.0") {
			@Override
			public IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
				return new ValidationAdvisor_4_0(problemsAdvisor);
			}
		};

		private final String label;

		private ComplianceLevel(String label) {
			this.label = label;
		}

		public abstract IValidationAdvisor createValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor);

		@Override
		public String toString() {
			return label;
		}
	}

	/**
	 * The 3.5 --parser future allows any value as hash key
	 */
	boolean allowAnyValueAsHashKey();

	/**
	 * The 3.5 --parser future allows chained assignments
	 */
	boolean allowChainedAssignments();

	/**
	 * The 3.2 --parser future allows blocks to end with an expression
	 */
	boolean allowExpressionLastInBlocks();

	/**
	 * If 3.0 extended dependency types should be allowed
	 * (resource | resourceref | collection | variable | quoted text | selector | case statement | hasharrayaccesses)
	 * See geppetto Issue #400.
	 */
	boolean allowExtendedDependencyTypes();

	/**
	 * If 3.5 extended match RHS expressions (string, type, or variables) should be allowed.
	 * See issue GEP-110
	 */
	boolean allowExtendedMatchRHS();

	/**
	 * The 3.7 --parser future allows a title that is literal default in resource body
	 */
	boolean allowExtendedTitleExpressions();

	/**
	 * Should Hash be allowed in a selector.
	 * Puppet issue #5516
	 */
	boolean allowHashInSelector();

	/**
	 * Before 3.0 and hiera support, a class can not inherit from a parameterized class.
	 */
	boolean allowInheritanceFromParameterizedClass();

	/**
	 * If lambdas are allowed or not
	 */
	boolean allowLambdas();

	/**
	 * Starting with 4.0, integer literals are parsed as such which means that a decimal
	 * mode will be interpreted as decimal, not octal.
	 */
	boolean allowModeWithNonOctalIntegerLiterals();

	/**
	 * Before 3.2 modulo operator '%' was not supported.
	 *
	 * @return
	 */
	boolean allowModulo();

	/**
	 * Should more than 2 at (i.e. []) operators be allowed in sequence e.g. $a[x][y][z]
	 * Puppet issue #6269
	 */
	boolean allowMoreThan2AtInSequence();

	/**
	 * The 3.5 with --parser future should allow the expressions if, unless, and case as r-values.
	 */
	boolean allowRHSConditionals();

	/**
	 * 3.2 --parser future adds an expression separator (';')
	 */
	boolean allowSeparatorExpression();

	/**
	 * The 3.7 --parser future allows splash attributes in resource body
	 */
	boolean allowSplashAttribute();

	/**
	 * @return wether or not type definitions are allowed (introduced in Puppet 4.x)
	 */
	boolean allowTypeDefinitions();

	/**
	 * The "unless" statement was added in Puppet 3.0.
	 *
	 * @return
	 */
	boolean allowUnless();

	/**
	 * The 3.2 --parser future allows unless to have an else (but not ifelse)
	 */
	boolean allowUnlessElse();

	/**
	 * Prior to 2.7 it was not possible to use unquoted qualified resource names.
	 *
	 * @return
	 */
	boolean allowUnquotedQualifiedResourceNames();

	/**
	 * Prior to version 2.7.8, an optional end comma in a definition argument list causes parse exception.
	 *
	 * @return
	 */
	ValidationPreference definitionArgumentListEndComma();

	/**
	 * Prior to 3.0, a missing $ in a definition parameter name declaration was deprecated.
	 * In 3.0 it is an error.
	 */
	ValidationPreference definitionParamterMissingDollar();

	/**
	 * Hyphens in names are deprecated
	 * Puppet issue #10146
	 * And will be errors in later releases.
	 */
	ValidationPreference hyphensInNames();

	/**
	 * Prior to 2.7 (?) it was not possible to have case labels with a ".".
	 *
	 * @return
	 */
	ValidationPreference periodInCase();

	/**
	 * How should relationships goign right to left be reported.
	 */
	@Override
	ValidationPreference rightToLeftRelationships();

	/**
	 * How should unqualified variable references be reported (ignore, warning, error).
	 */
	ValidationPreference unqualifiedVariables();
}
