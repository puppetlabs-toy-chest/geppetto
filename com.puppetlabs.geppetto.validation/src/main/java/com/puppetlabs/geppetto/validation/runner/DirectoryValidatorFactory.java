/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.validation.runner;

import java.io.File;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.impl.DirectoryValidatorImpl;

public interface DirectoryValidatorFactory {
	DirectoryValidatorImpl create(Diagnostic diagnostics, File root, ValidationOptions options);
}
