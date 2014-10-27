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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
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
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubySyntaxException;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.semver.VersionRange;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.impl.ValidationServiceImpl;
import com.puppetlabs.geppetto.validation.runner.RakefileInfo.Rakefile;
import com.puppetlabs.geppetto.validation.runner.RakefileInfo.Raketask;

public class DirectoryValidator {
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

	public DirectoryValidator(Diagnostic diagnostics, File root, ValidationOptions options) throws Exception {
		this.diagnostics = diagnostics;
		this.root = root;
		this.options = options;

		ppRunner = new PPDiagnosticsRunner();
		ppRunner.setUp(options);
		mdRunner = new ModuleDiagnosticsRunner(options);
		mdRunner.createInjectorAndDoEMFRegistration();
		rubyHelper = new RubyHelper();
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
	private void addFileDiagnostic(ValidationPreference pref, File file, String message, String issueId) {
		int severity = Diagnostic.OK;
		if(ValidationPreference.ERROR == pref)
			severity = Diagnostic.ERROR;
		else if(ValidationPreference.WARNING == pref)
			severity = Diagnostic.WARNING;
		if(severity != Diagnostic.OK)
			ValidationServiceImpl.addFileDiagnostic(diagnostics, severity, file, root, message, issueId);
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
			File moduleRoot = metadata.getParentFile();
			if(rootsChecked.add(moduleRoot))
				checkModuleLayout(moduleRoot);
		}
	}

	private void checkModuleLayout(File moduleRoot) {
		IModuleValidationAdvisor mdAdvisor = options.getModuleValidationAdvisor();

		if(hasModulesSubDirectory(moduleRoot))
			addFileDiagnostic(
				mdAdvisor.getUnexpectedSubmodule(), new File(moduleRoot, "modules"), "Submodules in a module is not allowed",
				ModuleDiagnostics.ISSUE__UNEXPECTED_SUBMODULE_DIRECTORY);

		File moduleFile = new File(moduleRoot, Forge.MODULEFILE_NAME);
		boolean hasMetadataJsonFile = new File(moduleRoot, Forge.METADATA_JSON_NAME).isFile();
		if(moduleFile.isFile()) {
			if(hasMetadataJsonFile)
				addFileDiagnostic(
					mdAdvisor.getModulefileExists(), moduleFile, "Modulefile is deprecated. Using metadata.json",
					ModuleDiagnostics.ISSUE__MODULEFILE_IS_DEPRECATED);
			else
				addFileDiagnostic(
					mdAdvisor.getModulefileExistsAndIsUsed(), moduleFile,
					"Modulefile is deprecated. Building metadata.json from modulefile",
					ModuleDiagnostics.ISSUE__MODULEFILE_IS_DEPRECATED_BUT_USED);
		}
		else {
			if(!hasMetadataJsonFile)
				addFileDiagnostic(
					ValidationPreference.ERROR, moduleRoot, "Missing metadata.json", ModuleDiagnostics.ISSUE__MISSING_METADATA_JSON_FILE);
		}
	}

	/**
	 * Collects file matching filter while skipping all symbolically linked files.
	 *
	 * @param root
	 * @param filter
	 * @param result
	 */
	private void collectFiles(File root, FileFilter filter, List<File> result) {
		for(File f : root.listFiles(options.getFileFilter())) {
			if(f.isDirectory())
				collectFiles(f, filter, result);
			else {
				if(FileUtils.isSymlink(f))
					continue;
				if(filter.accept(f) && !ppRunner.isExcluded(f))
					result.add(f);
			}
		}
	}

	private Multimap<ModuleName, MetadataInfo> collectModuleData(final SubMonitor ticker) {
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

		mdRunner.configureContainers(root, filesOnPath, resourcesOnPath);
		IResourceValidator validator = mdRunner.getModuleResourceValidator();

		Map<File, Resource> mdResources = loadModuleResources(filesOnPath, mdRunner, ticker);
		for(Entry<File, Resource> r : mdResources.entrySet()) {
			File f = r.getKey();
			if(nonRootsOnPath.contains(f) || !options.isValidationCandidate(f))
				continue;

			if(options.isCheckModuleSemantics()) {
				CancelIndicator cancelMonitor = new CancelIndicator() {
					@Override
					public boolean isCanceled() {
						return ticker.isCanceled();
					}
				};

				for(Issue issue : validator.validate(r.getValue(), CheckMode.ALL, cancelMonitor))
					addIssueDiagnostic(issue, f);
			}
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
				ModuleName moduleName = mdUtil.getName(m);
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
							options.getModuleValidationAdvisor().getModuleRedefinition(), mi.getFile(),
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
		URI uri = options.getPlatformURI();
		if(uri == null)
			uri = PuppetTarget.getDefault().getPlatformURI();
		ppRunner.configureContainers(root, moduleData.values(), //
			Iterables.concat(Iterables.transform(Iterables.concat(ppFiles, rbFiles), new Function<File, URI>() {
				@Override
				public URI apply(File from) {
					return URI.createFileURI(from.getPath());
				}
			}), Collections.singletonList(uri)));
	}

	private List<File> findFiles(File root, FileFilter filter) {
		List<File> result = Lists.newArrayList();
		collectFiles(root, filter, result);
		return result;

	}

	private List<File> findMetadataFiles() {
		return findFiles(root, metadataFileFilter);
	}

	private List<File> findPPFiles() {
		return findFiles(root, ppFileFilter);
	}

	private List<File> findRakefiles() {
		return findFiles(root, RakefileFileFilter);
	}

	private List<File> findRubyFiles() {
		return findFiles(root, rbFileFilter);
	}

	/**
	 * @param f
	 * @param ticker
	 */
	private Rakefile getRakefileInformation(File f, SubMonitor ticker) {
		try {
			Map<String, String> taskInfo = rubyHelper.getRakefileTaskDescriptions(f);
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

	private Map<File, Resource> loadModuleResources(Set<File> files, ModuleDiagnosticsRunner mdRunner, SubMonitor ticker) {
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
					moduleResources.put(f, mdRunner.loadResource(in, URI.createFileURI(mf.getPath())));
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
			URI platformURI = options.getPlatformURI();
			ppRunner.loadResource(platformURI != null
				? platformURI
				: PuppetTarget.getDefault().getPlatformURI());
		}
		catch(IOException e) {
			addExceptionDiagnostic("Internal Error: Could not load pptp.", e);
		}
	}

	private RakefileInfo loadRakeFiles(SubMonitor ticker) {
		RakefileInfo rakefileInfo = new RakefileInfo();
		// System.err.println("Processing Rakefiles count: " + rakeFiles.size());

		for(File f : rakeFiles) {
			validateRubyFile(f, ticker.newChild(1));

			// parsing adds one rakefile work tick
			rakefileInfo.addRakefile(getRakefileInformation(f, ticker.newChild(1)));

		}
		return rakefileInfo;
	}

	private void loadRubyFiles(SubMonitor ticker) {
		// Load all ruby
		for(File f : rbFiles) {
			try {
				// Skip "Rakefile.rb" or they will be processed twice (but still tick x2
				// onece for validate and once for load - as this is included in work-count)
				if(f.getName().toLowerCase().equals("rakefile.rb")) {
					worked(ticker, 2);
					continue;
				}
				validateRubyFile(f, ticker.newChild(1));

				// Load ruby file with pptp contribution
				// consumes one rb tick
				if(options.isCheckReferences()) {
					Resource r = ppRunner.loadResource(new FileInputStream(f), URI.createFileURI(f.getPath()));
					if(r != null)
						rememberRootInResource(r);
				}
				worked(ticker, 1);
			}
			catch(Exception e) {
				addExceptionDiagnostic("Internal Error: Exception while processing file: " + f.getName(), e);
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
	 * TODO: Horribly long method that should be refactored into several to get better optimization.
	 *
	 * @param monitor
	 * @return
	 */
	public BuildResult validateDirectory(IProgressMonitor monitor) {

		if(!(options.getFileType() == FileType.PUPPET_ROOT || options.getFileType() == FileType.MODULE_ROOT))
			throw new IllegalArgumentException("doDir can only process PUPPET_ROOT or MODULE_ROOT");

		if(options.isCheckLayout())
			checkLayout();

		final int workload = ppFiles.size() + metadataFiles.size() * 3 + rbFiles.size() * 2 //
			+ rakeFiles.size() * 2 //
			+ 1 // load pptp
			+ 1 // "for the pot" (to make sure there is a final tick to report)
		;

		final SubMonitor ticker = SubMonitor.convert(monitor, workload); // TODO: scaling

		boolean rubyServicesAvailable = false;
		try {
			rubyHelper.setUp();
			rubyServicesAvailable = rubyHelper.isRubyServicesAvailable();
		}
		catch(RuntimeException e) {
			addExceptionDiagnostic("Internal Error: Exception while setting up pp diagnostics.", e);
			return new BuildResult(rubyServicesAvailable); // give up
		}

		try {
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
			Multimap<ModuleName, MetadataInfo> moduleData = collectModuleData(ticker);

			// TODO: Wasteful to calculate the URL's more than once.
			// Could be done once per pp and rb (to separate the processing), or have all in one pile
			// and let processing look at extension.

			configureContainers(moduleData);

			// Load all ruby
			loadRubyFiles(ticker);
			RakefileInfo rakefileInfo = loadRakeFiles(ticker);

			validatePpResources(ticker.newChild(1));
			AllModulesState all = ppRunner.getAllModulesState();

			// set the root to allow relative lookup of module exports
			all.setRoot(root);
			// make sure everything is consumed
			ticker.setWorkRemaining(0);

			BuildResult buildResult = new BuildResult(rubyServicesAvailable);
			// buildResult.setExportsForNodes(result);
			buildResult.setAllModuleReferences(all);
			buildResult.setModuleData(moduleData);
			buildResult.setRakefileInfo(rakefileInfo);
			return buildResult;
		}
		finally {
			ppRunner.tearDown();
			rubyHelper.tearDown();
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
			IRubyParseResult result = rubyHelper.parse(f);
			for(IRubyIssue issue : result.getIssues()) {
				addRubyIssueDiagnostic(issue, f);
			}
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
