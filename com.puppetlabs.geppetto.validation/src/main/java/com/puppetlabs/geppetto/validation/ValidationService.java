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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.validation.runner.BuildResult;

/**
 */
public interface ValidationService {
	DiagnosticType PP_LINKING = new DiagnosticType("PP", ValidationService.class.getName());

	DiagnosticType PP_SYNTAX = new DiagnosticType("PP_SYNTAX", ValidationService.class.getName());

	DiagnosticType MODULE = new DiagnosticType("MODULE", ValidationService.class.getName());

	DiagnosticType INTERNAL_ERROR = new DiagnosticType("INTERNAL_ERROR", ValidationService.class.getName());

	DiagnosticType CATALOG_PARSER = new DiagnosticType("CATALOG_PARSER", ValidationService.class.getName());

	DiagnosticType CATALOG = new DiagnosticType("CATALOG", ValidationService.class.getName());

	/**
	 * Performs validation and reports diagnostics for all files given by source under the control of options. If a set
	 * of examinedFiles is given the diagnostics reported is limited to this set. An empty set is the same as reporting
	 * diagnostics for all files in source.
	 * The options may be null, in which case a syntax check is performed on everything in the intersection of source
	 * and examinedFiles.
	 * The examinedFiles may contain individual regular files or directories. Directories in examinedFiles are validated
	 * as modules. One special case is when examinedFiles contains the source and source is a Directory - this is
	 * interpreted as source is a PUPPET-ROOT, and that validation for everything in the non-modules part is wanted (and
	 * possibly for a select set of modules).
	 *
	 * @param diagnostics
	 *            DiagnosticChain will receive calls to add Diagnostic instances for discovered problems/information.
	 * @param options
	 *            Options that controls various aspects of the validation
	 * @param source
	 *            A String containing PP code to be validated. Errors are reported for a fictious file "unnamed.pp".
	 * @param monitor
	 *            Monitor where progress is reported. Also used as cancellation mechanism
	 */
	BuildResult validate(Diagnostic diagnostics, ValidationOptions options, File source, IProgressMonitor monitor);

	/**
	 * Validates PP syntax for code given in code parameter.
	 *
	 * @param diagnostics
	 *            DiagnosticChain will receive calls to add Diagnostic instances for discovered problems/information.
	 * @param options
	 *            Options that controls various aspects of the validation
	 * @param code
	 *            A String containing PP code to be validated. Errors are reported for a fictious file "unnamed.pp".
	 * @param monitor
	 *            Monitor where progress is reported. Also used as cancellation mechanism
	 */
	Resource validate(Diagnostic diagnostics, ValidationOptions options, String code, IProgressMonitor monitor);
}
