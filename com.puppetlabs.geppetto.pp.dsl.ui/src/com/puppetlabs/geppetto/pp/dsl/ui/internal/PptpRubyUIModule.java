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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.GlobalURIEditorOpener;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.ui.pptp.PptpResourceUiServiceProvider;

/**
 * UI Runtime module for Ruby PPTP variant.
 */
public class PptpRubyUIModule extends PptpRubyRuntimeModule {

	/**
	 * Modified version of hte GlobalURIEditorOpener that simply uses the configured default
	 * editor for the extension. We need to register this since we have no editor id registered
	 * for the {@link PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME}. The {@link LanguageSpecificURIEditorOpener} requires that
	 * such an editor has been registered.
	 */
	public static class UseDefaultURIEditorOpener extends GlobalURIEditorOpener {
		@Override
		public IEditorPart open(URI uri, boolean select) {
			return openDefaultEditor(uri, null, -1, select);
		}

		@Override
		public IEditorPart open(URI referenceOwnerURI, EReference reference, int indexInList, boolean select) {
			return openDefaultEditor(referenceOwnerURI, reference, indexInList, select);
		}
	}

	public Class<? extends org.eclipse.xtext.ui.resource.IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return PptpResourceUiServiceProvider.class;
		// return org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider.class;
	}

	public void configureLanguageSpecificURIEditorOpener(com.google.inject.Binder binder) {
		if(PlatformUI.isWorkbenchRunning())
			binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(UseDefaultURIEditorOpener.class);
	}

	// must bind a description label provider...
	public void configureResourceUIServiceLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class).annotatedWith(
			org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider.class).to(
			com.puppetlabs.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider.class);
	}

}
