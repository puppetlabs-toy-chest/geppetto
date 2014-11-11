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
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.GlobalURIEditorOpener;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider;
import org.eclipse.xtext.ui.validation.LanguageAwareMarkerTypeProvider;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;

import com.google.inject.Binder;
import com.puppetlabs.geppetto.common.os.IFileExcluder;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.ui.builder.PreferenceBasedFileExcluder;
import com.puppetlabs.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import com.puppetlabs.geppetto.pp.dsl.ui.pptp.PptpResourceUiServiceProvider;
import com.puppetlabs.geppetto.ruby.jrubyparser.JRubyServices;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyRuntimeModule;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;

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

	@Override
	public Class<? extends IFileExcluder> bindIFileExcluder() {
		return PreferenceBasedFileExcluder.class;
	}

	public Class<? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return PptpResourceUiServiceProvider.class;
	}

	@Override
	public Class<? extends IRubyServices> bindIRubyServices() {
		return JRubyServices.class;
	}

	public Class<? extends MarkerTypeProvider> bindMarkerTypeProvider() {
		return LanguageAwareMarkerTypeProvider.class;
	}

	public void configureAbstractUIPlugin(Binder binder) {
		binder.bind(AbstractUIPlugin.class).toInstance(PPDSLActivator.getDefault());
	}

	public void configureLanguageSpecificURIEditorOpener(Binder binder) {
		if(PlatformUI.isWorkbenchRunning())
			binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(UseDefaultURIEditorOpener.class);
	}

	// must bind a description label provider...
	public void configureResourceUIServiceLabelProvider(Binder binder) {
		binder.bind(ILabelProvider.class).annotatedWith(ResourceServiceDescriptionLabelProvider.class).to(PPDescriptionLabelProvider.class);
	}

}
