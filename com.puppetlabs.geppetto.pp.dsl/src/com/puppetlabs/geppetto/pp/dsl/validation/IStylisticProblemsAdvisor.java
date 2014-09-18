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

/**
 * An interface for stylistic problems preferences.
 */
public interface IStylisticProblemsAdvisor {

	/**
	 * How an (optional) default that is not placed last should be validated for a case expression.
	 */
	ValidationPreference caseDefaultShouldAppearLast();

	/**
	 * How the 'ensure' property should be validated if not placed first among a resource's properties.
	 */
	ValidationPreference ensureShouldAppearFirstInResource();

	/**
	 * How to 'validate' the presence of ML comments.
	 */
	ValidationPreference mlComments();

	/**
	 * Octal number is not quoted
	 */
	ValidationPreference attributeIsNotString();

	/**
	 * How to validate right to left relationships ( e.g. a <- b and a <~ b)
	 */
	ValidationPreference rightToLeftRelationships();

	/**
	 * How an (almost required) default that is not placed last should be validated for a selector expression.
	 */
	ValidationPreference selectorDefaultShouldAppearLast();

}
