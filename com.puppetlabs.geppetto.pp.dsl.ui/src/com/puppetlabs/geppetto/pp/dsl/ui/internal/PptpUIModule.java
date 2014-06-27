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
package com.puppetlabs.geppetto.pp.dsl.ui.internal;

import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import com.puppetlabs.geppetto.pp.dsl.pptp.PptpRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.ui.pptp.PptpResourceUiServiceProvider;

/**
 * @author henrik
 *
 */
public class PptpUIModule extends PptpRuntimeModule {

	public Class<? extends org.eclipse.xtext.ui.resource.IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return PptpResourceUiServiceProvider.class;
		// return org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider.class;
	}

	public void configureLanguageSpecificURIEditorOpener(com.google.inject.Binder binder) {
		if(PlatformUI.isWorkbenchRunning())
			binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(
				LanguageSpecificURIEditorOpener.class);
	}

	// must bind a description label provider...
	public void configureResourceUIServiceLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class).annotatedWith(
			org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider.class).to(
			com.puppetlabs.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider.class);
	}

}
