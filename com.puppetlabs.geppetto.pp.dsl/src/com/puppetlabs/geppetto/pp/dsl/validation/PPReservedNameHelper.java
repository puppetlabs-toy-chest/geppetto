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

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Helps with reserved names.
 */
public class PPReservedNameHelper {

	public static boolean isReservedClassName(String s) {
		return reservedClassNames.contains(s);
	}

	public static boolean isReservedTypeName(String s) {
		return reservedTypeNames.contains(s);
	}

	public static final List<String> reservedClassNames = ImmutableList.of("main", "settings");

	public static final Set<String> reservedTypeNames = ImmutableSet.of(
		"type", "any", "scalar", "boolean", "numeric", "integer", "float", "collection", "array", "hash", "tuple", "struct", "variant",
		"optional", "enum", "regexp", "pattern", "runtime");
}
