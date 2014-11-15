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
package com.puppetlabs.geppetto.validation.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.NamedTypeItem;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.PPDSLConstants;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import com.puppetlabs.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import com.puppetlabs.geppetto.pp.pptp.IDocumented;
import com.puppetlabs.geppetto.pp.pptp.INamed;
import com.puppetlabs.geppetto.pp.pptp.Provider;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubySyntaxException;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyResource;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyResource.LoadType;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyResource.RubyIssueDiagnostic;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.semver.VersionRange;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.AllModulesState;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import com.puppetlabs.geppetto.validation.runner.DirectoryValidator;
import com.puppetlabs.geppetto.validation.runner.MetadataInfo;
import com.puppetlabs.geppetto.validation.runner.ModuleDiagnosticsRunner;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsRunner;
import com.puppetlabs.geppetto.validation.runner.RakefileInfo;
import com.puppetlabs.geppetto.validation.runner.RakefileInfo.Rakefile;
import com.puppetlabs.geppetto.validation.runner.RakefileInfo.Raketask;
import com.puppetlabs.geppetto.validation.runner.RubyDiagnosticsRunner;

public class DirectoryValidatorImpl implements DirectoryValidator {
	private static final String NAME_OF_DIR_WITH_RESTRICTED_SCOPE = "roles";

	private static final FileFilter metadataFileFilter = new FileFilter() {

		@Override
		public boolean accept(File file) {
			String name = file.getName();
			return (name.equals(Forge.METADATA_JSON_NAME) || name.equals(Forge.MODULEFILE_NAME)) && file.isFile();
		}
	};

	private static final FileFilter ppFileFilter = new FileFilter() {

		@Override
		public boolean accept(File file) {
			return file.getName().endsWith(".pp") && file.isFile();
		}

	};

	private static final FileFilter RakefileFileFilter = new FileFilter() {

		@Override
		public boolean accept(final File file) {
			String name = file.getName();
			String lcname = name.toLowerCase();
			if(lcname.startsWith("rakefile")) {
				int length = lcname.length();
				if(!(length == 8 //
					||
					(length == 11 && lcname.endsWith(".rb")) //
				|| (length == 13 && lcname.endsWith(".rake"))))
					return false;
			}
			else if(!lcname.endsWith(".rake")) {
				return false;
			}
			// If the file is a directory, or something else, do not include it
			return file.isFile();
		}
	};

	private static final FileFilter rbFileFilter = new FileFilter() {

		@Override
		public boolean accept(File file) {
			return file.getName().endsWith(".rb") && file.isFile();
		}

	};

	private static <T extends INamed & IDocumented> List<NamedTypeItem> convertToNamedTypeItems(Collection<T> items) {
		if(items == null || items.isEmpty())
			return Collections.emptyList();

		List<NamedTypeItem> fitems = Lists.newArrayListWithCapacity(items.size());
		for(T item : items) {
			NamedTypeItem npItem = new NamedTypeItem();
			npItem.setName(item.getName());
			npItem.setDocumentation(item.getDocumentation());
			fitems.add(npItem);
		}
		return fitems;
	}

	private static boolean isOnPath(File f, PPSearchPath searchPath) {

		return searchPath.searchIndexOf(URI.createFileURI(f.getPath())) >= 0;
	}

	private final Diagnostic diagnostics;

	private final ValidationOptions options;

	private final List<File> ppFiles;

	private final PPDiagnosticsRunner ppRunner;

	private final ModuleDiagnosticsRunner mdRunner;

	private final List<File> metadataFiles;

	private final List<File> rakeFiles;

	private final List<File> rbFiles;

	private final File root;

	private final RubyHelper rubyHelper;

	@Inject
	public DirectoryValidatorImpl(PPDiagnosticsRunner ppRunner, @Assisted Diagnostic diagnostics, @Assisted File root,
			@Assisted ValidationOptions options) throws Exception {
		this.diagnostics = diagnostics;
		this.root = root;
		this.options = options;
		this.ppRunner = ppRunner;

		mdRunner = new ModuleDiagnosticsRunner(options);
		mdRunner.createInjectorAndDoEMFRegistration();
		rubyHelper = new RubyDiagnosticsRunner(options).getRubyHelper();
		ppFiles = findPPFiles();
		rbFiles = findRubyFiles();
		rakeFiles = findRakefiles();
		metadataFiles = findMetadataFiles();
	}

	/**
	 * Add an exception diagnostic (not associated with any particular file).
	 *
	 * @param message
	 * @param e
	 */
	private void addExceptionDiagnostic(String message, Exception e) {
		ValidationServiceImpl.addExceptionDiagnostic(diagnostics, message, e);
	}

	/**
	 * Add diagnostic for a file.
	 *
	 * @param severity
	 * @param file
	 * @param message
	 * @param issueId
	 */
	private void addFileDiagnostic(ValidationPreference pref, DiagnosticType type, File file, String message, String issueId) {
		int severity = Diagnostic.OK;
		if(ValidationPreference.ERROR == pref)
			severity = Diagnostic.ERROR;
		else if(ValidationPreference.WARNING == pref)
			severity = Diagnostic.WARNING;
		if(severity != Diagnostic.OK)
			ValidationServiceImpl.addFileDiagnostic(diagnostics, severity, type, file, root, message, issueId);
	}

	/**
	 * Translate and add Xtext issue diagnostics to the chain.
	 *
	 * @param issue
	 * @param processedFile
	 */
	private void addIssueDiagnostic(Issue issue, File processedFile) {
		ValidationServiceImpl.addIssueDiagnostic(diagnostics, issue, processedFile, root);
	}

	/**
	 * Translate and add ruby issue diagnostics to the chain.
	 *
	 * @param issue
	 * @param processedFile
	 */
	private void addRubyIssueDiagnostic(IRubyIssue issue, File processedFile) {
		ValidationServiceImpl.addRubyIssueDiagnostic(diagnostics, issue, processedFile, root);
	}

	private void checkLayout() {
		Set<File> rootsChecked = new HashSet<File>();
		for(File metadata : metadataFiles) {
			if(options.isValidationCandidate(metadata)) {
				File moduleRoot = metadata.getParentFile();
				if(rootsChecked.add(moduleRoot))
					checkModuleLayout(moduleRoot);
			}
		}
	}

	private void checkModuleLayout(File moduleRoot) {
		IModuleValidationAdvisor mdAdvisor = options.getModuleValidationAdvisor();

		if(hasModulesSubDirectory(moduleRoot))
			addFileDiagnostic(
				mdAdvisor.getUnexpectedSubmodule(), ValidationService.MODULE, new File(moduleRoot, "modules"),
				"Submodules in a module is not allowed", ModuleDiagnostics.ISSUE__UNEXPECTED_SUBMODULE_DIRECTORY);

		File moduleFile = new File(moduleRoot, Forge.MODULEFILE_NAME);
		boolean hasMetadataJsonFile = new File(moduleRoot, Forge.METADATA_JSON_NAME).isFile();
		if(moduleFile.isFile()) {
			if(hasMetadataJsonFile)
				addFileDiagnostic(
					mdAdvisor.getModulefileExists(), ValidationService.MODULE, moduleFile, "Modulefile is deprecated. Using metadata.json",
					ModuleDiagnostics.ISSUE__MODULEFILE_IS_DEPRECATED);
			else
				addFileDiagnostic(
					mdAdvisor.getModulefileExistsAndIsUsed(), ValidationService.MODULE, moduleFile,
					"Modulefile is deprecated. Building metadata.json from modulefile",
					ModuleDiagnostics.ISSUE__MODULEFILE_IS_DEPRECATED_BUT_USED);
		}
		else {
			if(!hasMetadataJsonFile)
				addFileDiagnostic(
					ValidationPreference.ERROR, ValidationService.MODULE, moduleRoot, "Missing metadata.json",
					ModuleDiagnostics.ISSUE__MISSING_METADATA_JSON_FILE);
		}
	}

	/**
	 * Collects file matching filter while skipping all symbolically linked files.
	 *
	 * @param files
	 *            The initial set of files and directories
	 * @param filter
	 *            The filter to apply
	 * @param result
	 */
	private void collectFiles(File[] files, FileFilter filter, List<File> result) {
		for(File f : files) {
			if(Files.isSymbolicLink(f.toPath()))
				continue;

			File[] dirFiles = f.listFiles(options.getFileFilter());
			if(dirFiles == null) {
				if(filter.accept(f) && !ppRunner.isExcluded(f))
					result.add(f);
			}
			else
				collectFiles(dirFiles, filter, result);
		}
	}

	private Multimap<ModuleName, MetadataInfo> collectModuleData(List<Entry<File, Resource>> modulesToValidate, final SubMonitor ticker) {
		Set<URI> resourcesOnPath = Sets.newHashSet();
		Set<File> filesOnPath = Sets.newHashSet();
		final PPSearchPath searchPath = ppRunner.getDefaultSearchPath();
		for(File f : metadataFiles) {
			f = f.getAbsoluteFile();
			String absPath = f.getPath();
			if(isOnPath(pathToFile(absPath), searchPath)) {
				filesOnPath.add(f);
				if(Forge.MODULEFILE_NAME.equals(f.getName()))
					absPath = new File(f.getParentFile(), Forge.METADATA_JSON_NAME).getPath();
				resourcesOnPath.add(URI.createFileURI(absPath));
			}
		}

		// Ensure that all modules visible on the path are loaded but remember those that
		// were found using the searchPath (as opposed to the ones found at the root)
		Set<File> nonRootsOnPath = Sets.newHashSet();
		List<File> resolvedPath = searchPath.getResolvedPath();
		for(File f : resolvedPath) {
			File dir = null;
			if("*".equals(f.getName()))
				dir = f.getParentFile();
			else if(f.isDirectory())
				dir = f;

			if(dir != null) {
				File metadata = new File(dir, Forge.METADATA_JSON_NAME);
				if(!metadata.isFile()) {
					metadata = new File(dir, Forge.MODULEFILE_NAME);
					if(!metadata.isFile())
						continue;
				}
				f = metadata;
			}
			else {
				String n = f.getName();
				if(!((Forge.METADATA_JSON_NAME.equals(n) || Forge.MODULEFILE_NAME.equals(n)) && f.isFile()))
					continue;
			}

			f = f.getAbsoluteFile();
			if(filesOnPath.add(f)) {
				nonRootsOnPath.add(f);
				resourcesOnPath.add(URI.createFileURI(Forge.MODULEFILE_NAME.equals(f.getName())
					? new File(f.getParentFile(), Forge.METADATA_JSON_NAME).getPath()
					: f.getPath()));
			}
		}

		Map<File, Resource> mdResources = loadModuleResources(filesOnPath, ticker);
		if(options.isCheckModuleSemantics())
			for(Entry<File, Resource> r : mdResources.entrySet()) {
				File f = r.getKey();
				if(!nonRootsOnPath.contains(f) && options.isValidationCandidate(f))
					modulesToValidate.add(r);
			}

		final IPath nodeRootPath = new Path(root.getAbsolutePath()).append(NAME_OF_DIR_WITH_RESTRICTED_SCOPE);
		final Multimap<ModuleName, MetadataInfo> moduleData = ArrayListMultimap.create();
		ModuleUtil mdUtil = mdRunner.getModuleUtil();
		for(File f : filesOnPath) {
			// load and remember all that loaded ok

			Resource mdResource = mdResources.get(f);
			if(mdResource == null)
				continue;

			EList<EObject> contents = mdResource.getContents();
			if(contents.isEmpty())
				continue;

			EObject first = contents.get(0);
			if(first instanceof JsonMetadata) {
				JsonMetadata m = (JsonMetadata) first;
				ModuleName moduleName = mdUtil.createModuleName(mdUtil.getName(m));
				if(moduleName != null)
					// remember the metadata and where it came from
					// and if it represents a NODE as opposed to a regular MODULE
					moduleData.put(
						moduleName, new MetadataInfo(mdUtil.getApiMetadata(m), f, nodeRootPath.isPrefixOf(new Path(f.getAbsolutePath()))));
			}
		}

		if(options.isCheckModuleSemantics())
			for(Map.Entry<ModuleName, Collection<MetadataInfo>> entry : moduleData.asMap().entrySet()) {
				Collection<MetadataInfo> mis = entry.getValue();
				boolean redefined = mis.size() > 1;
				for(MetadataInfo mi : mis) {
					for(Dependency dep : mi.getMetadata().getDependencies()) {
						boolean resolved = false;
						Collection<MetadataInfo> targetMis = moduleData.get(dep.getName());
						for(MetadataInfo targetMi : targetMis) {
							VersionRange vr = dep.getVersionRequirement();
							if(vr == null || vr.isIncluded(targetMi.getMetadata().getVersion())) {
								mi.addResolvedDependency(dep, targetMi);
								resolved = true;
							}
						}
						if(!resolved)
							mi.addUnresolvedDependency(dep);
					}
					if(redefined)
						addFileDiagnostic(
							options.getModuleValidationAdvisor().getModuleRedefinition(), ValidationService.MODULE, mi.getFile(),
							"Redefinition - equally named module already exists", ModuleDiagnostics.ISSUE__MODULE_REDEFINITION);
				}
			}

		return moduleData;
	}

	/**
	 * Calculate containers
	 * sets up iterateable over all files including pptp
	 */
	private void configureContainers(Multimap<ModuleName, MetadataInfo> moduleData) {
		List<URI> uris = Lists.newArrayList();
		for(File f : ppFiles)
			uris.add(URI.createFileURI(f.getPath()));
		for(File f : rbFiles)
			uris.add(URI.createFileURI(f.getPath()));

		PuppetTarget target = PuppetTarget.forComplianceLevel(options.getComplianceLevel(), false);
		uris.add(target.getPlatformURI());
		URI typesURI = target.getTypesURI();
		if(typesURI != null)
			uris.add(typesURI);

		ppRunner.configureContainers(root, moduleData.values(), uris);
	}

	private List<File> findFiles(FileFilter filter) {
		File[] files = root.listFiles(options.getFileFilter());
		if(files == null || files.length == 0)
			return Collections.emptyList();
		List<File> result = Lists.newArrayList();
		collectFiles(files, filter, result);
		return result;

	}

	private List<File> findMetadataFiles() {
		return findFiles(metadataFileFilter);
	}

	private List<File> findPPFiles() {
		return findFiles(ppFileFilter);
	}

	private List<File> findRakefiles() {
		return findFiles(RakefileFileFilter);
	}

	private List<File> findRubyFiles() {
		return findFiles(rbFileFilter);
	}

	/**
	 * @param f
	 * @param ticker
	 */
	private Rakefile getRakefileInformation(File f, SubMonitor ticker) {
		try {
			IRubyParseResult<Map<String, String>> rr = rubyHelper.getRakefileTaskDescriptions(f);
			Map<String, String> taskInfo = rr.getResult();
			Path rootPath = new Path(root.getAbsolutePath());
			Path rakefilePath = new Path(f.getAbsolutePath());
			Rakefile result = new Rakefile(rakefilePath.makeRelativeTo(rootPath));

			if(taskInfo == null)
				return result;

			for(Entry<String, String> entry : taskInfo.entrySet())
				result.addTask(new Raketask(entry.getKey(), entry.getValue()));
			return result;
		}
		catch(IOException e) {
			// simply do not return any information - validation should have dealt with errors
			return null;
		}
		catch(RubySyntaxException e) {
			// simply do not return any information - validation should have dealt with errors
			return null;
		}
		finally {
			worked(ticker, 1);
		}
	}

	private boolean hasModulesSubDirectory(File root) {
		File modulesDir = new File(root, "modules");
		return modulesDir.isDirectory();
	}

	private Map<File, Resource> loadModuleResources(Set<File> files, SubMonitor ticker) {
		// Load all pp
		// crosslink and validate all
		Map<File, Resource> moduleResources = Maps.newHashMapWithExpectedSize(files.size());
		for(File f : files) {
			try {
				InputStream in;
				File mf;
				if(Forge.MODULEFILE_NAME.equals(f.getName())) {
					mf = new File(f.getParentFile(), Forge.METADATA_JSON_NAME);
					if(files.contains(mf))
						continue;

					Diagnostic loadDiag = new Diagnostic();
					Metadata m = mdRunner.getForge().loadModulefile(f, loadDiag);
					if(loadDiag.getSeverity() >= Diagnostic.ERROR) {
						diagnostics.addChildren(loadDiag.getChildren());
						continue;
					}
					in = new ByteArrayInputStream(m.toString().getBytes(Charsets.UTF_8));
				}
				else {
					in = new FileInputStream(f);
					mf = f;
				}
				try {
					moduleResources.put(f, ppRunner.loadResource(in, URI.createFileURI(mf.getPath())));
				}
				finally {
					in.close();
				}
			}
			catch(Exception e) {
				addExceptionDiagnostic("Exception while processing file: " + f.getPath(), e);
			}
			// consume one pp tick
			worked(ticker, 1);
		}

		// Must set the root in all resources to allow cross reference error reports to contain
		// relative paths
		for(Resource r : moduleResources.values())
			rememberRootInResource(r);
		return moduleResources;
	}

	private Map<File, Resource> loadPpResources(SubMonitor ticker) {
		// Load all pp
		// crosslink and validate all
		Map<File, Resource> ppResources = Maps.newHashMapWithExpectedSize(ppFiles.size());
		for(File f : ppFiles) {
			try {
				Resource r = ppRunner.loadResource(new FileInputStream(f), URI.createFileURI(f.getPath()));
				if(r != null)
					ppResources.put(f, r);
			}
			catch(Exception e) {
				addExceptionDiagnostic("Exception while processing file: " + f.getPath(), e);
			}
			// consume one pp tick
			worked(ticker, 1);
		}

		// Must set the root in all resources to allow cross reference error reports to contain
		// relative paths
		for(Resource r : ppResources.values())
			rememberRootInResource(r);
		return ppResources;
	}

	private void loadPptp() {
		try {
			PuppetTarget target = PuppetTarget.forComplianceLevel(options.getComplianceLevel(), false);
			ppRunner.loadResource(target.getPlatformURI());
			URI typesURI = target.getTypesURI();
			if(typesURI != null)
				ppRunner.loadResource(typesURI);
		}
		catch(IOException e) {
			addExceptionDiagnostic("Internal Error: Could not load pptp.", e);
		}
	}

	private RakefileInfo loadRakeFiles(SubMonitor ticker) {
		RakefileInfo rakefileInfo = new RakefileInfo();
		// System.err.println("Processing Rakefiles count: " + rakeFiles.size());

		for(File f : rakeFiles) {
			if(options.isValidationCandidate(f))
				validateRubyFile(f, ticker.newChild(1));

			// parsing adds one rakefile work tick
			rakefileInfo.addRakefile(getRakefileInformation(f, ticker.newChild(1)));

		}
		return rakefileInfo;
	}

	private void loadRubyFiles(Multimap<ModuleName, MetadataInfo> moduleInfos, SubMonitor ticker) {
		// Load all ruby
		Multimap<String, Provider> allProviders = ArrayListMultimap.create();
		Map<File, Type> allTypes = Maps.newHashMap();
		for(File f : rbFiles) {
			try {
				// Skip "Rakefile.rb" or they will be processed twice (but still tick x2
				// onece for validate and once for load - as this is included in work-count)
				if(f.getName().toLowerCase().equals("rakefile.rb")) {
					worked(ticker, 2);
					continue;
				}

				URI uri = URI.createFileURI(f.getPath());
				if(PptpRubyResource.detectLoadType(uri) == LoadType.IGNORED) {
					validateRubyFile(f, ticker);
					continue;
				}

				Resource r = ppRunner.loadResource(new FileInputStream(f), uri);
				if(options.isValidationCandidate(f)) {
					for(org.eclipse.emf.ecore.resource.Resource.Diagnostic diag : r.getErrors())
						if(diag instanceof RubyIssueDiagnostic)
							addRubyIssueDiagnostic(((RubyIssueDiagnostic) diag).getIssue(), f);
					for(org.eclipse.emf.ecore.resource.Resource.Diagnostic diag : r.getWarnings())
						if(diag instanceof RubyIssueDiagnostic)
							addRubyIssueDiagnostic(((RubyIssueDiagnostic) diag).getIssue(), f);

					if(options.isExtractTypes())
						for(EObject c : r.getContents()) {
							if(c instanceof Type)
								allTypes.put(f, (Type) c);
							else if(c instanceof Provider) {
								Provider p = (Provider) c;
								allProviders.put(p.getTypeName(), p);
							}
						}
				}

				if(options.isCheckReferences())
					rememberRootInResource(r);
				worked(ticker, 1);
			}
			catch(Exception e) {
				addExceptionDiagnostic("Internal Error: Exception while processing file: " + f.getName(), e);
			}
		}

		if(options.isExtractTypes()) {
			// Key all MetadataInfo with their respective module directory
			Map<java.nio.file.Path, MetadataInfo> mdInfos = Maps.newHashMap();
			for(MetadataInfo mdInfo : moduleInfos.values())
				mdInfos.put(mdInfo.getFile().getParentFile().toPath(), mdInfo);

			for(Map.Entry<File, Type> typeEntry : allTypes.entrySet()) {
				Type t = typeEntry.getValue();
				com.puppetlabs.geppetto.forge.model.Type ft = new com.puppetlabs.geppetto.forge.model.Type();
				ft.setDocumentation(t.getDocumentation());
				ft.setName(t.getName());
				ft.setParameters(convertToNamedTypeItems(t.getParameters()));
				ft.setProperties(convertToNamedTypeItems(t.getProperties()));
				ft.setProviders(convertToNamedTypeItems(allProviders.get(t.getName())));

				java.nio.file.Path typePath = typeEntry.getKey().toPath();
				for(Map.Entry<java.nio.file.Path, MetadataInfo> mdEntry : mdInfos.entrySet())
					if(typePath.startsWith(mdEntry.getKey())) {
						mdEntry.getValue().addType(ft);
						break;
					}
			}
		}
	}

	/**
	 * Translate a file path to a file relative to rootFolder (if under this root, else return an absolute File).
	 *
	 * @param filePath
	 * @return
	 */
	private File pathToFile(String filePath) {
		Path problemPath = new Path(filePath);
		Path rootPath = new Path(root.getPath());
		IPath relativePath = problemPath.makeRelativeTo(rootPath);
		return relativePath.toFile();
	}

	private void rememberRootInResource(Resource r) {
		if(r == null)
			throw new IllegalArgumentException("resource can not be null");
		URI uri = URI.createFileURI(root.getAbsolutePath());
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		adapter.put(PPDSLConstants.RESOURCE_PROPERTY__ROOT_URI, uri);
	}

	/**
	 * @param monitor
	 * @return
	 */
	public BuildResult validateDirectory(IProgressMonitor monitor) {

		if(options.isCheckLayout())
			checkLayout();

		final int workload = ppFiles.size() + metadataFiles.size() * 3 + rbFiles.size() * 2 //
			+ rakeFiles.size() * 2 //
			+ 1 // load pptp
			+ 1 // "for the pot" (to make sure there is a final tick to report)
		;

		final SubMonitor ticker = SubMonitor.convert(monitor, workload); // TODO: scaling

		ppRunner.configureEncoding(options.getEncodingProvider());
		ppRunner.configureSearchPath(root, options.getSearchPath(), options.getEnvironment(), options.getManifestDir());

		// Load pptp
		if(options.isCheckReferences()) {
			loadPptp();
			if(diagnostics.getSeverity() >= Diagnostic.ERROR)
				// Give up
				return new BuildResult(rubyHelper.isRubyServicesAvailable());
		}
		worked(ticker, 1);

		// collect info in a structure
		List<Entry<File, Resource>> modulesToValidate = Lists.newArrayList();
		Multimap<ModuleName, MetadataInfo> moduleData = collectModuleData(modulesToValidate, ticker);

		// TODO: Wasteful to calculate the URL's more than once.
		// Could be done once per pp and rb (to separate the processing), or have all in one pile
		// and let processing look at extension.

		configureContainers(moduleData);

		// Load all ruby
		loadRubyFiles(moduleData, ticker);
		RakefileInfo rakefileInfo = loadRakeFiles(ticker);

		validatePpResources(ticker.newChild(1));
		validateModules(modulesToValidate, ticker.newChild(1));

		AllModulesState all = ppRunner.getAllModulesState();

		// set the root to allow relative lookup of module exports
		all.setRoot(root);
		// make sure everything is consumed
		ticker.setWorkRemaining(0);

		BuildResult buildResult = new BuildResult(rubyHelper.isRubyServicesAvailable());
		// buildResult.setExportsForNodes(result);
		buildResult.setAllModuleReferences(all);
		buildResult.setModuleData(moduleData);
		buildResult.setRakefileInfo(rakefileInfo);
		return buildResult;
	}

	private void validateModules(List<Entry<File, Resource>> modulesToValidate, final SubMonitor ticker) {
		IResourceValidator validator = mdRunner.getModuleResourceValidator();
		for(Entry<File, Resource> r : modulesToValidate) {

			CancelIndicator cancelMonitor = new CancelIndicator() {
				@Override
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			for(Issue issue : validator.validate(r.getValue(), CheckMode.ALL, cancelMonitor))
				addIssueDiagnostic(issue, r.getKey());
		}
	}

	/**
	 * Load all pp
	 * crosslink and validate all
	 */
	private void validatePpResources(final SubMonitor ticker) {
		IResourceValidator validator = ppRunner.getPPResourceValidator();
		long maxLinkTime = 0;
		// Turn on for debugging particular files
		// File slowCandidate = new File("/Users/henrik/gitrepos/forge-modules/jeffmccune-mockbuild/manifests/init.pp");

		final CancelIndicator cancelMonitor = new CancelIndicator() {
			@Override
			public boolean isCanceled() {
				return ticker.isCanceled();
			}
		};
		for(Entry<File, Resource> r : loadPpResources(ticker).entrySet()) {
			File f = r.getKey();
			long beforeTime = System.currentTimeMillis();
			boolean profileThis = false; // /* for debugging slow file */
			// f.equals(slowCandidate);
			if(options.isCheckReferences())
				ppRunner.resolveCrossReferences(r.getValue(), profileThis, ticker);
			long afterTime = System.currentTimeMillis();
			if(afterTime - beforeTime > maxLinkTime) {
				maxLinkTime = afterTime - beforeTime;
			}

			if(options.isValidationCandidate(f)) {
				List<Issue> issues = validator.validate(r.getValue(), CheckMode.ALL, cancelMonitor);
				for(Issue issue : issues)
					addIssueDiagnostic(issue, f);
			}
		}

	}

	private void validateRubyFile(File f, SubMonitor ticker) {
		if(!options.isValidationCandidate(f))
			return;

		try {
			for(IRubyIssue issue : rubyHelper.parse(f))
				addRubyIssueDiagnostic(issue, f);
		}
		catch(RubySyntaxException e) {
			addRubyIssueDiagnostic(e, f);
			for(IRubyIssue issue : e.getIssues())
				addRubyIssueDiagnostic(issue, f);
		}
		catch(Exception e) {
			addExceptionDiagnostic("Internal Error: Exception while processing file: " + f.toString(), e);
		}
		worked(ticker, 1);
	}

	private void worked(SubMonitor monitor, int amount) throws OperationCanceledException {
		if(monitor.isCanceled())
			throw new OperationCanceledException();
		monitor.worked(amount);
	}

}
