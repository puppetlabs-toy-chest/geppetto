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

import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyRuntimeModule;

/**
 * Adds support for PPTP Ruby by creating injectors and caching them using a language key
 */
public class PPDSLActivator extends PPActivator {
	public static final String PP_LANGUAGE_NAME = "com.puppetlabs.geppetto.pp.dsl.PP";

	private static BundleContext slaActivatorContext;

	private static final Logger logger = Logger.getLogger(PPDSLActivator.class);

	private static final QualifiedName LAST_BUILDER_VERSION = new QualifiedName("com.puppetlabs.geppetto.dsl.ui", "builder.version");

	public static PPDSLActivator getDefault() {
		return (PPDSLActivator) getInstance();
	}

	/**
	 * org.eclipse.jdt.core is added as an optional dependency in o.c.g.pp.dsl.ui and if JDT is present in
	 * the runtime, there is no need for the AggregateErrorLabel to do anything.
	 *
	 * @return true if JDT is present.
	 */
	public static boolean isJavaEnabled() {
		if(slaActivatorContext == null)
			return false;
		try {
			slaActivatorContext.getBundle().loadClass("org.eclipse.jdt.core.JavaCore");
			return true;
		}
		catch(Throwable e) {
		}
		return false;
	}

	private Map<String, Injector> injectors = Collections.synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

	private IResourceChangeListener projectChangeListener;

	@Override
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
			return Guice.createInjector(mergedModule);
		}
		catch(Exception e) {
			logger.error("Failed to create injector for " + language);
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to create injector for " + language, e);
		}
	}

	@Override
	public Injector getInjector(String language) {
		if(COM_PUPPETLABS_GEPPETTO_PP_DSL_PP.equals(language))
			return super.getInjector(language);

		synchronized(injectors) {
			Injector injector = injectors.get(language);
			if(injector == null) {
				injectors.put(language, injector = createInjector(language));
			}
			return injector;
		}
	}

	public Injector getPPInjector() {
		return this.getInjector(PP_LANGUAGE_NAME);
	}

	@Override
	protected Module getRuntimeModule(String grammar) {
		if(PptpRubyRuntimeModule.PPTP_RUBY_LANGUAGE_NAME.equals(grammar))
			return new PptpRubyRuntimeModule();
		else if(PPDSLConstants.PPTP_LANGUAGE_NAME.equals(grammar))
			return new PptpUIModule();
		return super.getRuntimeModule(grammar);
	}

	@Override
	protected Module getUiModule(String grammar) {
		if(PPDSLConstants.PPTP_LANGUAGE_NAME.equals(grammar))
			return new PptpUIModule(); // DefaultModules.EMPTY_MODULE;
		else if(PptpRubyRuntimeModule.PPTP_RUBY_LANGUAGE_NAME.equals(grammar))
			return new PptpRubyUIModule();

		return super.getUiModule(grammar);
	}

	protected void registerInjectorFor(String language) throws Exception {
		injectors.put(language, createInjector(language));
		// override(override(getRuntimeModule(language)).with(getSharedStateModule())).with(getUiModule(language))));
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// Force the PreferenceConverter class to be loaded at this point to get rid
		// of intermittent 'invalid thread access' when it's loaded by another thread
		// during injection
		PreferenceConverter.class.getCanonicalName();

		slaActivatorContext = context;
		try {
			registerInjectorFor(PptpRubyRuntimeModule.PPTP_RUBY_LANGUAGE_NAME);
			registerInjectorFor(PPDSLConstants.PPTP_LANGUAGE_NAME);
		}
		catch(Exception e) {
			Logger.getLogger(getClass()).error(e.getMessage(), e);
			throw e;
		}

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String lastBuilderVersion = workspace.getRoot().getPersistentProperty(LAST_BUILDER_VERSION);
		Bundle bundle = context.getBundle();
		String currentVersion = bundle.getVersion().toString();
		if(lastBuilderVersion == null || !lastBuilderVersion.equals(currentVersion)) {
			// Workspace was built using another version of Geppetto so schedule a
			// clean rebuild
			getPPInjector().getInstance(RebuildChecker.class).triggerBuild();
			workspace.getRoot().setPersistentProperty(LAST_BUILDER_VERSION, bundle.getVersion().toString());
		}

		projectChangeListener = new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				// We are only interested in when new projects are added
				// or projects are being removed
				try {
					IResourceDelta delta = event.getDelta();
					delta.accept(new IResourceDeltaVisitor() {
						private boolean done = false;

						@Override
						public boolean visit(IResourceDelta delta) throws CoreException {
							if(done)
								return false;

							// Rebuild if a project was added, removed, or changed its open status
							if(delta.getResource() instanceof IProject &&
								((delta.getKind() & (ADDED | REMOVED)) != 0 || (delta.getKind() & IResourceDelta.CHANGED) != 0 &&
									(delta.getFlags() & IResourceDelta.OPEN) != 0)) {
								done = true;
								getPPInjector().getInstance(RebuildChecker.class).triggerBuild();
								return false;
							}
							return true;
						}
					});
				}
				catch(CoreException e) {
				}
			}
		};
		workspace.addResourceChangeListener(projectChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(projectChangeListener);
		Injector injector = getPPInjector();
		PPPreferencesHelper preferenceHelper = injector.getInstance(PPPreferencesHelper.class);
		preferenceHelper.stop();
		slaActivatorContext = null;
		injector.getInstance(ImagesOnFileSystemRegistry.class).dispose();
		super.stop(context);
	}
}
