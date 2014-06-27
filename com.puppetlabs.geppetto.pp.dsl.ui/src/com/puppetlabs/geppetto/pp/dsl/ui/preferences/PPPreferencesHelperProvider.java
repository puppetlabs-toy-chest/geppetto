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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.ui.pptp.PptpTargetProjectHandler;

@Singleton
public class PPPreferencesHelperProvider implements Provider<PPPreferencesHelper> {
	@Inject
	private IPreferenceStoreAccess storeAccess;

	@Inject
	private PptpTargetProjectHandler pptpHandler;

	@Inject
	private RebuildChecker rebuildChecker;

	private PPPreferencesHelper preferencesHandler;

	/**
	 * @see com.google.inject.Provider#get()
	 */
	@Override
	public synchronized PPPreferencesHelper get() {
		if(preferencesHandler == null) {
			preferencesHandler = new PPPreferencesHelper(pptpHandler, rebuildChecker);
			preferencesHandler.initialize(storeAccess);
		}
		return preferencesHandler;
	}
}
