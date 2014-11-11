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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.common.os.IFileExcluder;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.linking.DiagnosticConsumerBasedMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.linking.IMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.linking.PPResourceLinker;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.IConfigurableProvider;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import com.puppetlabs.geppetto.pp.dsl.parser.antlr.PPParser;

/**
 * Runner/Helper that can perform diagnostics/validation of PP files.
 */
public class PPDiagnosticsRunner {

	private class ModuleExport implements AllModulesState.Export {

		private static final long serialVersionUID = 1L;

		final private File file;

		final private int startOffset;

		final private int length;

		final private int line;

		final private IEObjectDescription desc;

		public ModuleExport(File f, IEObjectDescription desc, int offset, int line, int length) {
			this.file = f;
			this.desc = desc;
			this.startOffset = offset;
			this.line = line;
			this.length = length;
		}

		@Override
		public String getDefaultValueText() {
			String text = desc.getUserData(PPDSLConstants.DEFAULT_EXPRESSION_DATA);
			if(text != null)
				return text.trim();
			return text;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.IExportsPerModule.Export #getEClass()
		 */
		@Override
		public EClass getEClass() {
			return desc.getEClass();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.ExportsPerModule.Export #getFile()
		 */
		@Override
		public File getFile() {
			return file;
		}

		@Override
		public String getLastNameSegment() {
			return desc.getName().getLastSegment();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.ExportsPerModule.Export #getLength()
		 */
		@Override
		public int getLength() {
			return length;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.ExportsPerModule.Export #getLine()
		 */
		@Override
		public int getLine() {
			return line;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.IExportsPerModule.Export #getName()
		 */
		@Override
		public String getName() {
			return PPDiagnosticsRunner.this.converter.toString(desc.getName());
		}

		@Override
		public String getNameWithoutLastSegment() {
			return PPDiagnosticsRunner.this.converter.toString(desc.getName().skipLast(1));
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.IExportsPerModule.Export #getParentName()
		 */
		@Override
		public String getParentName() {
			return desc.getUserData(PPDSLConstants.PARENT_NAME_DATA);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.puppetlabs.geppetto.validation.runner.ExportsPerModule.Export #getStart()
		 */
		@Override
		public int getStart() {
			return startOffset;
		}

	}

	public static final String PPTPCONTAINER = "_pptp";

	@Inject
	private XtextResourceSet resourceSet;

	/**
	 * The linker that performs the "special" PP linking. Normally used/called from the PPLinker.
	 */
	@Inject
	private PPResourceLinker resourceLinker;

	@Inject
	private IFileExcluder fileExcluder;

	@Inject
	private IResourceServiceProvider resourceServiceProvider;

	@Inject
	private ResourceDescriptionsProvider indexProvider;

	@Inject
	private IQualifiedNameConverter converter;

	@Inject
	private IEncodingProvider encodingProvider;

	@Inject
	private ISearchPathProvider searchPathProvider;

	@Inject
	private IResourceValidator resourceValidator;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private PPParser ppParser;

	@Inject
	private ISerializer serializer;

	private final Map<String, File> pathToFileMap;

	private String ROOTCONTAINER = null;

	private Multimap<String, String> restricted = null;

	private final Function<QualifiedName, String> fQualifiedToString = new Function<QualifiedName, String>() {

		@Override
		public String apply(QualifiedName from) {
			return converter.toString(from);
		}
	};

	public PPDiagnosticsRunner() {
		pathToFileMap = Maps.newHashMap();
	}

	private void _configureTransitiveClosure(Set<MetadataInfo> processed, final IPath rootModule,
			final Multimap<String, String> restricted, final MetadataInfo mi) {
		if(processed.contains(mi))
			return; // circular !
		for(MetadataInfo.Resolution r : mi.getResolvedDependencies()) {
			MetadataInfo dep = r.metadata;
			IPath dp = new Path(dep.getFile().getAbsolutePath()).removeLastSegments(1);
			restricted.put(rootModule.toString(), dp.toString());
			processed.add(mi);
			_configureTransitiveClosure(processed, rootModule, restricted, dep);
		}
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
	public void configureContainers(File root, Collection<MetadataInfo> moduleInfo, Iterable<URI> resources) {
		List<String> allContainers = Lists.newArrayList();
		Multimap<String, URI> containedResources = HashMultimap.create();
		restricted = HashMultimap.create();

		ROOTCONTAINER = root.getAbsolutePath();
		allContainers.add(ROOTCONTAINER);
		allContainers.add(PPTPCONTAINER);

		final List<IPath> modulePaths = Lists.newArrayList();

		for(MetadataInfo mi : moduleInfo) {
			File f = mi.getFile();
			// get path to directory (the moduleinfo file is for the metadata
			// file itself
			IPath p = new Path(f.getAbsolutePath()).removeLastSegments(1);
			modulePaths.add(p);
			allContainers.add(p.toString());
			if(mi.isRole()) {
				// This means the dependencies are restricted to the transitive
				// closure of the module's'
				// dependencies + the pptp
				configureTransitiveClosure(p, restricted, mi);
				restricted.put(p.toString(), PPTPCONTAINER);
			}
		}

		// sort uri's into respective container
		for(URI uri : resources) {

			// pptp is special
			if("pptp".equals(uri.fileExtension())) {
				containedResources.put(PPTPCONTAINER, uri);
				continue;
			}

			// if path starts with a module's prefix it is in that container
			Path p = new Path(uri.toFileString());
			IPath modulePath = null;
			for(IPath prefix : modulePaths)
				if(prefix.isPrefixOf(p)) {
					modulePath = prefix;
					break;
				}

			// if not in any module, it is in the root container
			if(modulePath == null)
				containedResources.put(ROOTCONTAINER, uri);
			else
				containedResources.put(modulePath.toString(), uri);
		}

		// Create the "all state" and set it in as resourceset adapter
		// (This is where Xtext will find it later)
		ValidationContainersStateFactory factory = new ValidationContainersStateFactory();
		IAllContainersState allState = factory.getContainersState(allContainers, containedResources, restricted);
		resourceSet.eAdapters().add(new DelegatingIAllContainerAdapter(allState));
	}

	public void configureEncoding(IEncodingProvider provider) {
		if(provider == null)
			provider = new DefaultEncodingProvider();
		this.encodingProvider = provider;
	}

	public void configureSearchPath(File root, String searchPath, String environment, String manifestDir) {
		if(searchPathProvider instanceof IConfigurableProvider)
			((IConfigurableProvider) searchPathProvider).configure(
				URI.createFileURI(root.getAbsolutePath()), searchPath, environment, manifestDir);
	}

	private void configureTransitiveClosure(final IPath rootModule, final Multimap<String, String> restricted, final MetadataInfo mi) {
		Set<MetadataInfo> processed = Sets.newHashSet();

		// it can see itself
		restricted.put(rootModule.toString(), rootModule.toString());
		_configureTransitiveClosure(processed, rootModule, restricted, mi);
	}

	// private PPGrammarAccess getGrammarAccess() {
	// return get(PPGrammarAccess.class);
	// }

	private ModuleExport createExport(IEObjectDescription desc) {
		// String name = converter.toString(desc.getName());
		File f = uri2File(desc.getEObjectURI());
		ICompositeNode node = NodeModelUtils.getNode(desc.getEObjectOrProxy());
		int line = -1;
		int offset = -1;
		int length = 0;
		if(node != null) {
			line = node.getStartLine();
			offset = node.getOffset();
			length = node.getLength();
		}
		ModuleExport me = new ModuleExport(f, desc, offset, line, length);
		return me;
	}

	/**
	 * Translates all Exports and Imports and stores this in an ExportsPerModule.
	 *
	 * @return
	 */
	public AllModulesState getAllModulesState() {
		final AllModulesState result = new AllModulesState();

		if(resourceSet == null || resourceSet.getResources().size() < 1)
			return result;

		// The container manager knows about resource <-> container mapping
		final ValidationStateBasedContainerManager validationContainerManager = getContainerManager();

		// ResourceDescriptions is an index of all exports
		IResourceDescriptions descriptionIndex = getResourceDescriptions();

		// translate all exports and create a map from IEObjectDescription to
		// Export
		Map<URI, ModuleExport> exports = Maps.newHashMap();
		for(IResourceDescription rdesc : descriptionIndex.getAllResourceDescriptions()) {
			String handle = validationContainerManager.getContainerHandle(rdesc, descriptionIndex);

			if(handle == null)
				continue;
			// TODO: This is a leap of faith, handles are all paths, except the
			// special "_pptp" path
			File moduleDir = new File(handle);

			for(IEObjectDescription desc : rdesc.getExportedObjects()) {
				ModuleExport me = createExport(desc);
				exports.put(desc.getEObjectURI(), me);
				result.addExport(moduleDir, me);
			}
		}
		for(Resource r : resourceSet.getResources()) {
			// get module (i.e. container handle) of importing container
			File importingModuleDir = getContainerHandle(r.getURI(), descriptionIndex, validationContainerManager);
			if(importingModuleDir == null)
				continue;

			// get the imports recorded during linking
			PPImportedNamesAdapter importedAdapter = PPImportedNamesAdapterFactory.eINSTANCE.adapt(r);
			for(IEObjectDescription desc : importedAdapter.getResolvedDescriptions()) {
				// get the container (e.g. a module) of the the desc
				File moduleDir = getContainerHandle(desc.getEObjectURI(), descriptionIndex, validationContainerManager);
				if(moduleDir == null)
					continue;
				ModuleExport me = exports.get(desc.getEObjectURI());
				result.addImport(importingModuleDir, moduleDir, me);
			}
			// get the ambiguities recording during linking
			for(IEObjectDescription desc : importedAdapter.getAmbiguousDescriptions()) {
				// get the container (e.g. a module) of the the desc
				File moduleDir = getContainerHandle(desc.getEObjectURI(), descriptionIndex, validationContainerManager);
				if(moduleDir == null)
					continue;
				ModuleExport me = exports.get(desc.getEObjectURI());
				result.addAmbiguity(importingModuleDir, moduleDir, me);
			}
			// TODO: RECORD BOTH NAME FILE, AND LOCATIONS FOR THAT NAME
			result.addUnresolved(importingModuleDir, r.getURI(), importedAdapter.getUnresolved(), fQualifiedToString);
			// result.addAllUnresolvedNames(
			// importingModuleDir,
			// Iterables.transform(importedAdapter.getUnresolvedNames(), new
			// Function<QualifiedName, String>() {
			//
			// @Override
			// public String apply(QualifiedName from) {
			// return converter.toString(from);
			// }
			// }));

		}

		result.setRestricted(restricted);
		return result;

	}

	private File getContainerHandle(URI uri, IResourceDescriptions index, ValidationStateBasedContainerManager manager) {
		IResourceDescription resourceDescription = index.getResourceDescription(uri.trimFragment());
		String containerHandle = manager.getContainerHandle(resourceDescription, index);
		if(containerHandle == null)
			return null;
		File moduleDir = new File(containerHandle);
		return moduleDir;

	}

	private ValidationStateBasedContainerManager getContainerManager() {
		// The container manager knows about resource <-> container mapping
		// An overridden manager is expected with API extensions
		IContainer.Manager manager = resourceServiceProvider.getContainerManager();
		if(!(manager instanceof ValidationStateBasedContainerManager))
			throw new IllegalStateException("Guice Configuration Error: container manager must be a ValidationStateBasedContainerManager");

		return (ValidationStateBasedContainerManager) manager;
	}

	public PPSearchPath getDefaultSearchPath() {
		// NOTE: This is a bit ugly, but it is known to return the default
		// search path since the
		// configuration has a search path provider that only returns the
		// default, and the parameter
		// EMF Resource is not used.
		return searchPathProvider.get(null);
	}

	IQualifiedNameConverter getIQualifiedNameConverter() {
		return qualifiedNameConverter;
	}

	public IResourceValidator getPPResourceValidator() {
		return resourceValidator;
	}

	/**
	 * Get the (total) description index The Xtext API requires a resource, but since everything is in the same resource
	 * set, it does not matter which resource that is picked (pick first). Note - caller must check if the resource set
	 * is empty.
	 */
	private IResourceDescriptions getResourceDescriptions() {
		return indexProvider.getResourceDescriptions(resourceSet.getResources().get(0));
	}

	public EList<Resource> getResources() {
		return resourceSet.getResources();
	}

	public boolean isExcluded(File f) {
		return fileExcluder.isExcluded(f.toPath());
	}

	/**
	 * Loads a .pp, .pptp or .rb resource using the resource factory configured for the extension. The resource is added to the
	 * resource set.
	 *
	 * @return The loaded resource
	 */
	public Resource loadResource(InputStream in, URI uri) throws IOException {
		// Lookup the factory to use for the resource
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(uri);
		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, encodingProvider.getEncoding(uri));

		Resource r = factory.createResource(uri);
		resourceSet.getResources().add(r);
		r.load(in, options);

		return r;
	}

	/**
	 * Load a resource from a String. The URI must be well formed for the language being the content of the given
	 * sourceString (the uri determined the factory to use and the encoding via an IEncodingProvider).
	 *
	 * @param sourceString
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public final Resource loadResource(String sourceString, URI uri) {
		try {
			return loadResource(new StringInputStream(sourceString, encodingProvider.getEncoding(uri)), uri);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Resource loadResource(URI uri) throws IOException {
		Resource resource = resourceSet.createResource(uri);
		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, encodingProvider.getEncoding(uri));

		resource.load(options);
		resourceSet.getResources().add(resource);
		return resource;

	}

	/**
	 * Loads a resource to the resource set. The intended use is to load a .pptp resource.
	 *
	 * @param uri
	 *            uri to a .pptp resource (typically).
	 * @return the resource
	 */
	public Resource loadResource1(URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		resourceSet.getResources().add(resource);
		return resource;

	}

	public IParseResult parseString(ParserRule rule, String s) throws PPSyntaxErrorException {
		IParseResult result = ppParser.parse(rule, new StringReader(s));
		if(result.hasSyntaxErrors())
			throw new PPSyntaxErrorException(result);
		return result;
	}

	public void resolveCrossReferences(Resource resource, boolean profileThis, final IProgressMonitor monitor) {
		if(resource instanceof LazyLinkingResource) {
			// The default linking
			long before = System.currentTimeMillis();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return monitor.isCanceled();
				}
			};

			((LazyLinkingResource) resource).resolveLazyCrossReferences(cancelMonitor);
			long afterLazy = System.currentTimeMillis();
			if(profileThis)
				System.err.printf("LazyLinkingResource.resolveLazyCrossReferences: (%s)\n", afterLazy - before);

			// just in case some other crazy thing is sent here, check that it is a pp resource
			// (pp resource linking is not relevant on anything but .pp resources).
			if(resourceServiceProvider.canHandle(resource.getURI())) {
				// The PP resource linking (normally done by PP Linker (but
				// without documentation association)
				//
				final ListBasedDiagnosticConsumer consumer = new ListBasedDiagnosticConsumer();
				IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(consumer);
				resourceLinker.link(((LazyLinkingResource) resource).getParseResult().getRootASTElement(), acceptor, profileThis);
				resource.getErrors().addAll(consumer.getResult(Severity.ERROR));
				resource.getWarnings().addAll(consumer.getResult(Severity.WARNING));
			}
			if(profileThis) {
				long afterPP = System.currentTimeMillis();
				System.err.printf("PP linker: (%s)\n", afterPP - afterLazy);
			}
		}
		else {
			long before = System.currentTimeMillis();
			EcoreUtil.resolveAll(resource);
			long after = System.currentTimeMillis();
			if(profileThis)
				System.err.printf("EcoreUtil.resolveAll: (%s)\n", after - before);
		}

	}

	public String serialize(EObject obj) {
		SaveOptions options = SaveOptions.newBuilder().getOptions();
		return serializer.serialize(obj, options);
	}

	public String serializeFormatted(EObject obj) {
		return serializer.serialize(obj, SaveOptions.newBuilder().format().getOptions());
	}

	private File uri2File(URI uri) {

		String path = uri.isFile()
			? uri.toFileString()
			: uri.path();
		File f = pathToFileMap.get(path);
		if(f != null)
			return f;
		f = new File(path);
		pathToFileMap.put(path, f);
		return f;
	}

}
