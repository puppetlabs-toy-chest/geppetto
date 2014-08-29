/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Itemis AB (http://www.itemis.eu) - initial API and implementation
 *   Puppet Labs - adpated for validation
 *
 */
package com.puppetlabs.geppetto.validation.runner;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersState;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.module.dsl.ModuleStandaloneSetup;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.validation.DefaultModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.validation.ValidationOptions;

/**
 * Runner/Helper that can perform diagnostics/validation of metadata.json files.
 */
public class ModuleDiagnosticsRunner extends ModuleStandaloneSetup {
	private final IModuleValidationAdvisor moduleValidationAdvisor;

	private final IEncodingProvider encodingProvider;

	private XtextResourceSet resourceSet;

	private IResourceValidator resourceValidator;

	private ModuleUtil moduleUtil;

	private Forge forge;

	public ModuleDiagnosticsRunner(ValidationOptions options) {
		IModuleValidationAdvisor mvAdvisor = options.getModuleValidationAdvisor();
		if(mvAdvisor == null)
			mvAdvisor = new DefaultModuleValidationAdvisor();
		this.moduleValidationAdvisor = mvAdvisor;
		IEncodingProvider encodingProvider = options.getEncodingProvider();
		if(encodingProvider == null)
			encodingProvider = new DefaultEncodingProvider();
		this.encodingProvider = encodingProvider;
	}

	/**
	 * Configure containers if something else than "everything is visible to everything" is wanted. This method must be
	 * called before resources are loaded.
	 *
	 * @param root
	 *            - where non modular content is contained
	 * @param moduleInfo
	 *            - information about modules and their dependencies
	 * @param resources
	 *            - URI's to all resources to be loaded
	 */
	public void configureContainers(File root, Collection<File> mdRoots, Iterable<URI> resources) {
		List<String> allContainers = Lists.newArrayList();
		Multimap<String, URI> containedResources = HashMultimap.create();

		String rootContainer = root.getAbsolutePath();
		allContainers.add(rootContainer);

		final List<IPath> modulePaths = Lists.newArrayList();

		for(File f : mdRoots) {
			// get path to directory (the moduleinfo file is for the metadata
			// file itself
			IPath p = new Path(f.getAbsolutePath()).removeLastSegments(1);
			modulePaths.add(p);
			allContainers.add(p.toString());
		}

		// sort uri's into respective container
		for(URI uri : resources) {

			// if path starts with a module's prefix it is in that container
			Path p = new Path(uri.toFileString());
			IPath modulePath = null;
			for(IPath prefix : modulePaths)
				if(prefix.isPrefixOf(p))
					modulePath = prefix;

			// if not in any module, it is in the root container
			if(modulePath == null)
				containedResources.put(rootContainer, uri);
			else
				containedResources.put(modulePath.toString(), uri);
		}

		// Create the "all state" and set it in as resourceset adapter
		// (This is where Xtext will find it later)
		ResourceSetBasedAllContainersState allState = new ResourceSetBasedAllContainersState();
		allState.configure(allContainers, containedResources);
		resourceSet.eAdapters().add(new DelegatingIAllContainerAdapter(allState));
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(new ModuleDiagnosticsModule(moduleValidationAdvisor));
	}

	public Forge getForge() {
		return forge;
	}

	public IResourceValidator getModuleResourceValidator() {
		return resourceValidator;
	}

	public ModuleUtil getModuleUtil() {
		return moduleUtil;
	}

	/**
	 * Loads a resource using the resource factory configured for the extension. All non null resources are added to the
	 * resource set.
	 */
	public Resource loadResource(InputStream in, URI uri) throws Exception {
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(uri);
		Resource r = factory.createResource(uri);
		resourceSet.getResources().add(r);

		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, encodingProvider.getEncoding(uri));
		r.load(in, options);

		return r;
	}

	@Override
	public void register(Injector injector) {
		super.register(injector);
		resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.setClasspathURIContext(getClass());

		resourceValidator = injector.getInstance(IResourceValidator.class);
		moduleUtil = injector.getInstance(ModuleUtil.class);
		forge = injector.getInstance(Forge.class);
	}
}
