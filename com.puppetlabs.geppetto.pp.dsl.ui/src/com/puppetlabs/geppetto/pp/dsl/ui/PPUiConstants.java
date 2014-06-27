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
package com.puppetlabs.geppetto.pp.dsl.ui;

import org.eclipse.core.runtime.QualifiedName;

/**
 * Constants for PP Ui
 *
 */
public interface PPUiConstants {
	String PLUGIN_ID = "com.puppetlabs.geppetto.pp.dsl.ui";

	QualifiedName PROJECT_PROPERTY_MODULEVERSION = new QualifiedName(PPUiConstants.PLUGIN_ID, "version");

	QualifiedName PROJECT_PROPERTY_MODULENAME = new QualifiedName(PPUiConstants.PLUGIN_ID, "moduleName");

	String PUPPET_TASK_MARKER_TYPE = "com.puppetlabs.geppetto.pp.dsl.ui.puppetTaskMarker";

	String MODULEFILE_BUILDER_ID = "com.puppetlabs.geppetto.pp.dsl.ui.modulefileBuilder";

	/**
	 * Name of hidden project that contains the target platform .pptp file.
	 */
	String PPTP_TARGET_PROJECT_NAME = ".com_puppetlabs_geppetto_pptp_target";

	/**
	 * Name of project to be deleted on startup (hack).
	 */
	String OLD_PPTP_TARGET_PROJECT_NAME = ".org_cloudsmith_geppetto_pptp_target";

	String PUPPET_NATURE_ID = "com.puppetlabs.geppetto.pp.dsl.ui.puppetNature";

	String DEBUG_OPTION_PARSER = PLUGIN_ID + "/debug/parser";

}
