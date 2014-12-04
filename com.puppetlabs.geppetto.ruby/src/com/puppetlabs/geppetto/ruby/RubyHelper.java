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
package com.puppetlabs.geppetto.ruby;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.common.Strings;
import com.puppetlabs.geppetto.diagnostic.DetailedFileDiagnostic;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.pp.pptp.Function;
import com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer;
import com.puppetlabs.geppetto.pp.pptp.MetaType;
import com.puppetlabs.geppetto.pp.pptp.MetaVariable;
import com.puppetlabs.geppetto.pp.pptp.NameSpace;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.Parameter;
import com.puppetlabs.geppetto.pp.pptp.Property;
import com.puppetlabs.geppetto.pp.pptp.Provider;
import com.puppetlabs.geppetto.pp.pptp.PuppetDistribution;
import com.puppetlabs.geppetto.pp.pptp.TPVariable;
import com.puppetlabs.geppetto.pp.pptp.TargetEntry;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeFragment;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;
import com.puppetlabs.geppetto.ruby.spi.RubyResult;

/**
 * Provides access to ruby parsing / information services. If an implementation
 * is not available, a mock service is used - this can be checked with {@link #isRubyServicesAvailable()}. The mock
 * service will provide an empty
 * parse result (i.e. "no errors or warning"), and will return empty results for
 * information.
 * The caller can then adjust how to deal with service not being present.
 * To use the RubyHelper, a call must be made to {@link #setUp()}, then followed
 * by a series of requests to parse or get information.
 */
@Singleton
public class RubyHelper {
	public static final List<IRubyIssue> emptyIssues = Collections.emptyList();

	private static final FilenameFilter rbFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".rb");
		}

	};

	private static final FileFilter dirFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}

	};

	private static void addProviderParameter(Type type) {
		synchronized(type) {
			for(Parameter param : type.getParameters())
				if("provider".equals(param.getName()))
					return;
			addProviderParameterNoCheck(type);
		}
	}

	private static void addProviderParameterNoCheck(Type type) {
		Parameter p = PPTPFactory.eINSTANCE.createParameter();
		p.setName("provider");
		p.setDocumentation("");
		p.setRequired(false);
		type.getParameters().add(p);
	}

	private static void checkArgs(File file) throws FileNotFoundException {
		if(file == null)
			throw new IllegalArgumentException("Given file is null");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
	}

	private static void checkArgs(String fileName, Reader reader) {
		if(fileName == null)
			throw new IllegalArgumentException("Given fileName is null");
		if(reader == null)
			throw new IllegalArgumentException("Given reader is null");
	}

	@Inject
	private IRubyServices rubyProvider;

	private TPVariable addTPVariable(ITargetElementContainer container, String name, String documentation, boolean deprecated) {
		TPVariable var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName(name);
		var.setDocumentation(documentation);
		var.setAssignable(false);
		var.setDeprecated(deprecated);
		container.getContents().add(var);
		return var;
	}

	private Diagnostic convert(IRubyIssue rse, int severity, DiagnosticType type) {
		DetailedFileDiagnostic fileDiag = new DetailedFileDiagnostic(severity, type, rse.getMessage(), new File(rse.getFileName()));
		fileDiag.setLineNumber(rse.getLine());
		fileDiag.setOffset(rse.getStartOffset());
		fileDiag.setLength(rse.getLength());
		return fileDiag;
	}

	private String[] extractVersionFromName(String name) {
		String[] result = new String[] { name, "" };
		int lastHypen = name.lastIndexOf('-');
		if(lastHypen == -1)
			return result;
		result[0] = name.substring(0, lastHypen);
		result[1] = name.substring(lastHypen + 1);
		return result;

	}

	private IRubyParseResult<List<Function>> functionInfoToFunction(IRubyParseResult<List<PPFunctionInfo>> functionInfos) {
		List<Function> result = Lists.newArrayList();
		for(PPFunctionInfo info : functionInfos.getResult()) {
			Function pptpFunc = PPTPFactory.eINSTANCE.createFunction();
			pptpFunc.setName(info.getFunctionName());
			pptpFunc.setRValue(info.isRValue());
			pptpFunc.setDocumentation(info.getDocumentation());
			result.add(pptpFunc);
		}
		return new RubyResult<>(result, functionInfos.getIssues());

	}

	/**
	 * Returns a list of custom PP parser functions from the given .rb file. The
	 * returned list is empty if no function could be found.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
	 */
	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getFunctionInfo(file);

	}

	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		checkArgs(fileName, reader);
		return rubyProvider.getFunctionInfo(fileName, reader);

	}

	public IRubyParseResult<PPTypeInfo> getMetaTypeInfo(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getMetaTypeProperties(file);

	}

	public IRubyParseResult<PPTypeInfo> getMetaTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		checkArgs(fileName, reader);
		return rubyProvider.getMetaTypeProperties(fileName, reader);
	}

	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getProviderInfo(file);
	}

	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		checkArgs(fileName, reader);
		return rubyProvider.getProviderInfo(fileName, reader);
	}

	public IRubyParseResult<Map<String, String>> getRakefileTaskDescriptions(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getRakefileTaskDescriptions(file);

	}

	public IRubyParseResult<List<PPTypeInfo>> getTypeFragments(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getTypePropertiesInfo(file);
	}

	public IRubyParseResult<List<PPTypeInfo>> getTypeFragments(String fileName, Reader reader) throws IOException, RubySyntaxException {
		checkArgs(fileName, reader);
		return rubyProvider.getTypePropertiesInfo(fileName, reader);
	}

	/**
	 * Returns a list of custom PP types from the given .rb file. The returned
	 * list is empty if no type could be found.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
	 */
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(File file) throws IOException, RubySyntaxException {
		checkArgs(file);
		return rubyProvider.getTypeInfo(file);

	}

	/**
	 * Returns a list of custom PP types from the given .rb file. The returned
	 * list is empty if no type could be found.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
	 */
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		checkArgs(fileName, reader);
		return rubyProvider.getTypeInfo(fileName, reader);
	}

	private List<String> getUpToPuppetSegments(Resource resource) {
		List<String> segments = resource.getURI().segmentsList();
		int lastPuppet = segments.lastIndexOf("puppet");
		return lastPuppet > 0
			? segments.subList(0, lastPuppet)
			: null;
	}

	private void handleException(Exception e, Diagnostic diag) {
		if(e instanceof RubySyntaxException) {
			RubySyntaxException rse = (RubySyntaxException) e;
			diag.addChild(convert(rse, Diagnostic.ERROR, IRubyServices.RUBY_SYNTAX));
			handleWarnings(rse.getIssues(), diag);
		}
		else
			diag.addChild(new ExceptionDiagnostic(Diagnostic.ERROR, IRubyServices.RUBY, e.getMessage(), e));
	}

	private void handleWarnings(List<IRubyIssue> issues, Diagnostic diag) {
		for(IRubyIssue re : issues)
			diag.addChild(convert(re, Diagnostic.WARNING, IRubyServices.RUBY));
	}

	/**
	 * Returns true if real ruby services are available.
	 */
	public boolean isRubyServicesAvailable() {
		return !rubyProvider.isMockService();
	}

	/**
	 * Loads a puppet distro into a pptp model and saves the result in a file.
	 * The distroDir should appoint a directory that contains the directories
	 * type and parser/functions. The path to the distroDir should contain a
	 * version string segment directly after a segment called 'puppet'. e.g.
	 * /somewhere/on/disk/puppet/2.6.2_0/some/path/to/puppet.
	 * Will also load default settings:: variables, and meta variables.
	 * Output file will contain a PPTP model as a result of this call.
	 *
	 * @param distroDir
	 *            - path to a puppet directory
	 * @param outputFile
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public void loadAndSaveDistro(File distroDir, File outputFile, Diagnostic diag) {
		TargetEntry target = loadDistroTarget(distroDir, diag);

		// Load the default settings:: variables
		loadSettings(target);

		// Load the default meta variables
		loadMetaVariables(target);

		loadPuppetVariables(target);

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(outputFile.getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);
		targetResource.getContents().add(target);
		try {
			targetResource.save(null);
		}
		catch(IOException e) {
			handleException(e, diag);
		}
	}

	/**
	 * Loads a Puppet distribution target. The file should point to the "lib/puppet"
	 * directory where the sub-directories "parser" and "type" are. The path to
	 * this directory is expected to have a ../../ name on the form puppet-version
	 * e.g. /somewhere/puppet-2.6.9/lib/puppet.
	 * The implementation will scan the known locations for definitions that
	 * should be reflected in the target - i.e. parser/functions/*.rb and
	 * type/*.rb
	 * Note, this does not load default settings:: variables.
	 *
	 * @throws IOException
	 *             on problems with reading files
	 * @throws RubySyntaxException
	 *             if there are syntax exceptions in the parsed ruby code
	 */
	public TargetEntry loadDistroTarget(File file, Diagnostic diag) {
		if(file == null)
			throw new IllegalArgumentException("File can not be null");

		// Create a puppet distro target and parse info from the file path
		//
		PuppetDistribution puppetDistro = PPTPFactory.eINSTANCE.createPuppetDistribution();
		puppetDistro.setDescription("Puppet Distribution");
		IPath path = Path.fromOSString(file.getAbsolutePath());

		// NOTE: This is wrong, will always result in a version == "" as the version is
		// part of the "puppet" directory, not the directory puppet-x.x.x/lib/puppet that is given to
		// this function.
		//
		// String versionString = "";
		// boolean nextIsVersion = false;
		String[] segments = path.segments();
		int sc = segments.length;
		if(segments.length < 3 || !"puppet".equals(segments[sc - 1]) || !"lib".equals(segments[sc - 2]))
			throw new IllegalArgumentException("path to .../puppet/lib is not correct");
		final String distroName = segments[sc - 3];
		if(!distroName.startsWith("puppet-"))
			throw new IllegalArgumentException("The ../../ of the given directory must be named on the form: 'puppet-<version>'");

		puppetDistro.setLabel("puppet");
		// 7 is the first char after 'puppet-'
		puppetDistro.setVersion(distroName.substring(7));

		// Load functions
		File parserDir = new File(file, "parser");
		File functionsDir = new File(parserDir, "functions");
		loadFunctions(puppetDistro, functionsDir, diag);

		// Load logger functions
		try {
			IRubyParseResult<List<Function>> rr = loadLoggerFunctions(new File(file, "util/log.rb"));
			for(Function f : rr.getResult())
				puppetDistro.getFunctions().add(f);
			handleWarnings(rr.getIssues(), diag);
		}
		catch(FileNotFoundException e) {
			// ignore
		}
		catch(Exception e) {
			handleException(e, diag);
		}

		loadProviders(puppetDistro, new File(file, "provider"), diag);

		// Load types
		File typesDir = new File(file, "type");
		loadTypes(puppetDistro, typesDir, diag);

		// load additional properties into types
		// (currently only known such construct is for 'file' type
		// this implementation does however search all subdirectories
		// for such additions
		//
		for(File subDir : typesDir.listFiles(dirFilter))
			loadTypeFragments(puppetDistro, subDir, diag);

		// load nagios types
		try {
			File nagios = new File(file, "external/nagios/base.rb");
			handleWarnings(loadNagiosTypes(puppetDistro, nagios), diag);
		}
		catch(FileNotFoundException e) {
			// ignore - no nagios
		}
		catch(Exception e) {
			handleException(e, diag);
		}

		// load metatype
		try {
			File typeFile = new File(file, "type.rb");
			handleWarnings(loadMetaType(puppetDistro, typeFile), diag);
		}
		catch(FileNotFoundException e) {
			// ignore
		}
		catch(Exception e) {
			handleException(e, diag);
		}

		return puppetDistro;
	}

	/**
	 * Load function(s) from a rubyfile (supposed to contain PP function
	 * declarations).
	 *
	 * @param rbFile
	 * @param rememberFile
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public IRubyParseResult<List<Function>> loadFunctions(File rbFile) throws IOException, RubySyntaxException {
		return functionInfoToFunction(getFunctionInfo(rbFile));
	}

	/**
	 * Load function info into target.
	 *
	 * @param target
	 * @param functionsDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadFunctions(TargetEntry target, File functionsDir, Diagnostic diag) {
		if(functionsDir.isDirectory())
			for(File rbFile : functionsDir.listFiles(rbFileFilter)) {
				try {
					IRubyParseResult<List<Function>> rr = loadFunctions(rbFile);
					for(Function f : rr.getResult())
						target.getFunctions().add(f);
					handleWarnings(rr.getIssues(), diag);
				}
				catch(Exception e) {
					handleException(e, diag);
				}
			}
	}

	public IRubyParseResult<List<Function>> loadLoggerFunctions(File rbFile) throws IOException, RubySyntaxException {
		checkArgs(rbFile);
		return functionInfoToFunction(rubyProvider.getLogFunctions(rbFile));
	}

	private List<IRubyIssue> loadMetaType(TargetEntry target, File rbFile) throws IOException, RubySyntaxException {
		IRubyParseResult<PPTypeInfo> rr = getMetaTypeInfo(rbFile);
		PPTypeInfo info = rr.getResult();
		MetaType type = PPTPFactory.eINSTANCE.createMetaType();
		type.setName(info.getTypeName());
		type.setDocumentation(info.getDocumentation());
		for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
			Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
			parameter.setName(entry.getKey());
			parameter.setDocumentation(entry.getValue().documentation);
			parameter.setRequired(entry.getValue().isRequired());
			type.getParameters().add(parameter);
		}

		// TODO: there are more interesting things to pick up (like valid
		// values)
		target.setMetaType(type);
		return rr.getIssues();
	}

	/**
	 * Loads meta variables into the target. These are variables that looks like
	 * local variables in every scope, but they are not found from the outside.
	 *
	 * @param target
	 */
	public void loadMetaVariables(TargetEntry target) {
		EList<MetaVariable> metaVars = target.getMetaVariables();

		MetaVariable metaName = PPTPFactory.eINSTANCE.createMetaVariable();
		metaName.setName("name");
		metaName.setDocumentation("");
		metaName.setDeprecated(false);
		metaVars.add(metaName);

		MetaVariable metaTitle = PPTPFactory.eINSTANCE.createMetaVariable();
		metaTitle.setName("title");
		metaTitle.setDocumentation("");
		metaTitle.setDeprecated(false);
		metaVars.add(metaTitle);

		MetaVariable metaModuleName = PPTPFactory.eINSTANCE.createMetaVariable();
		metaModuleName.setName("module_name");
		metaModuleName.setDocumentation("<p>The name of the containing module</p>");
		metaModuleName.setDeprecated(false);
		metaVars.add(metaModuleName);

		MetaVariable callerMetaModuleName = PPTPFactory.eINSTANCE.createMetaVariable();
		callerMetaModuleName.setName("caller_module_name");
		callerMetaModuleName.setDocumentation("<p>The name of the calling module</p>");
		callerMetaModuleName.setDeprecated(false);
		metaVars.add(callerMetaModuleName);
	}

	private List<IRubyIssue> loadNagiosTypes(TargetEntry target, File rbFile) throws IOException, RubySyntaxException {
		IRubyParseResult<List<Type>> rr = transformTypes(getTypeInfo(rbFile));
		for(Type t : rr.getResult())
			target.getTypes().add(t);
		return rr.getIssues();
	}

	public List<TargetEntry> loadPluginsTarget(File pluginsRoot, Diagnostic diag) {

		List<TargetEntry> result = Lists.newArrayList();
		// for all the directories in pluginsRoot, load the content of that directory
		// as a module
		if(pluginsRoot == null || !pluginsRoot.isDirectory())
			return result; // do nothing (an empty list)

		for(File pluginRoot : pluginsRoot.listFiles()) {
			String[] nameParts = extractVersionFromName(pluginRoot.getName());
			PuppetDistribution plugin = PPTPFactory.eINSTANCE.createPuppetDistribution();
			plugin.setDescription("Puppet Plugin");
			plugin.setLabel(nameParts[0]);
			plugin.setVersion(nameParts[1]);

			// load functions (lib/puppet/parser/functions/*), and types (lib/puppet/type/*)
			File lib = new File(pluginRoot, "lib/puppet");
			if(!lib.exists())
				continue; // has no content that can be handled

			// Load Functions
			File functionsDir = new File(new File(lib, "parser"), "functions");
			loadFunctions(plugin, functionsDir, diag);

			// Load Types
			File typesDir = new File(lib, "type");
			loadTypes(plugin, typesDir, diag);

			// load additional properties into types
			// (currently only known such construct is for 'file' type
			// this implementation does however search all subdirectories
			// for such additions
			//
			if(typesDir != null && typesDir.isDirectory())
				for(File subDir : typesDir.listFiles(dirFilter))
					loadTypeFragments(plugin, subDir, diag);

			if(plugin.getFunctions().size() > 0 || plugin.getTypes().size() > 0)
				result.add(plugin);
		}
		return result;
	}

	/**
	 * Load provider(s) from ruby file.
	 *
	 * @param rbFile
	 *            - the file to parse
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<Provider> loadProviders(File rbFile, Diagnostic diag) {
		try {
			IRubyParseResult<List<Provider>> rr = transformProviders(getProviderInfo(rbFile));
			handleWarnings(rr.getIssues(), diag);
			return rr.getResult();
		}
		catch(Exception e) {
			handleException(e, diag);
			return Collections.emptyList();
		}
	}

	/**
	 * Load provider info into target.
	 *
	 * @param target
	 * @param providersDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadProviders(TargetEntry target, File providersDir, Diagnostic diag) {
		File[] providerSubDirs = providersDir.listFiles();
		if(providerSubDirs != null)
			for(File providerSubDir : providerSubDirs) {
				File[] providerFiles = providerSubDir.listFiles(rbFileFilter);
				if(providerFiles != null)
					for(File providerFile : providerFiles)
						for(Provider t : loadProviders(providerFile, diag))
							target.getProviders().add(t);
				else if(providerSubDir.getName().endsWith(".rb"))
					for(Provider t : loadProviders(providerSubDir, diag))
						target.getProviders().add(t);
			}
	}

	public void loadPuppetVariables(TargetEntry target) {
		TPVariable var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("environment");
		var.setDocumentation("The node's current environment. Available when compiling a catalog for a node.");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("clientcert");
		var.setDocumentation("The node's certname setting. Available when compiling a catalog for a node.");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("clientversion");
		var.setDocumentation("The current version of the puppet agent. Available when compiling a catalog for a node.");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("servername");
		var.setDocumentation("The puppet master’s fully-qualified domain name. (Note that this information "
			+ "is gathered from the puppet master by Facter, rather than read from the config files; even if the "
			+ "master’s certname is set to something other than its fully-qualified domain name, this variable "
			+ "will still contain the server’s fqdn.)");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("serverip");
		var.setDocumentation("The puppet master's IP address");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("serverversion");
		var.setDocumentation("The current version of puppet on the puppet master.");
		var.setDeprecated(false);
		target.getContents().add(var);

		var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName("trusted");
		var.setDocumentation("$trusted will automatically contain trusted node data in future versions");
		var.setDeprecated(false);
		target.getContents().add(var);
	}

	/**
	 * Loads predefined variables in the settings:: namespace. These are hard to
	 * find in the puppet logic.
	 *
	 * @param target
	 */
	public void loadSettings(TargetEntry target) {
		NameSpace settings = PPTPFactory.eINSTANCE.createNameSpace();
		settings.setReserved(true);
		settings.setName("settings");
		target.getContents().add(settings);

		// Add known names in settings (the most common ones). This to avoid
		// warnings
		//
		SettingsData settingsData = new SettingsData();
		for(SettingsData.Setting s : settingsData.settings) {
			addTPVariable(settings, s.name, Strings.trimToNull(s.documentation), s.deprecated);
		}
	}

	public RubyResult<List<TypeFragment>> loadTypeFragments(File rbFile) throws IOException, RubySyntaxException {
		List<TypeFragment> result = Lists.newArrayList();
		IRubyParseResult<List<PPTypeInfo>> rr = getTypeFragments(rbFile);
		for(PPTypeInfo type : rr.getResult()) {
			TypeFragment fragment = PPTPFactory.eINSTANCE.createTypeFragment();
			fragment.setName(type.getTypeName());

			// add the properties (will typically load just one).
			for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getProperties().entrySet()) {
				Property property = PPTPFactory.eINSTANCE.createProperty();
				property.setName(entry.getKey());
				property.setDocumentation(entry.getValue().documentation);
				property.setRequired(entry.getValue().isRequired());
				fragment.getProperties().add(property);
			}

			// add the parameters (will typically load just one).
			for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getParameters().entrySet()) {
				Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
				parameter.setName(entry.getKey());
				parameter.setDocumentation(entry.getValue().documentation);
				parameter.setRequired(entry.getValue().isRequired());
				fragment.getParameters().add(parameter);
			}

			result.add(fragment);
		}
		return new RubyResult<>(result, rr.getIssues());

	}

	private void loadTypeFragments(TargetEntry target, File subDir, Diagnostic diag) {
		for(File f : subDir.listFiles(rbFileFilter)) {
			// try to get type property additions
			try {
				RubyResult<List<TypeFragment>> rr = loadTypeFragments(f);
				for(TypeFragment tf : rr.getResult())
					target.getTypeFragments().add(tf);
				handleWarnings(rr.getIssues(), diag);
			}
			catch(Exception e) {
				handleException(e, diag);
			}
		}
	}

	/**
	 * Load type(s) from ruby file.
	 *
	 * @param rbFile
	 *            - the file to parse
	 * @param rememberFiles
	 *            - if entries should remember the file they came from.
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public IRubyParseResult<List<Type>> loadTypes(File rbFile) throws IOException, RubySyntaxException {
		return transformTypes(getTypeInfo(rbFile));
	}

	/**
	 * Load type info into target.
	 *
	 * @param target
	 * @param typesDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadTypes(TargetEntry target, File typesDir, Diagnostic diag) {
		if(!typesDir.isDirectory())
			return;
		for(File rbFile : typesDir.listFiles(rbFileFilter))
			try {
				IRubyParseResult<List<Type>> rr = loadTypes(rbFile);
				for(Type t : rr.getResult()) {
					target.getTypes().add(t);
					for(Provider pv : target.getProviders()) {
						if(t.getName().equals(pv.getTypeName())) {
							addProviderParameter(t);
							break;
						}
					}
				}
				handleWarnings(rr.getIssues(), diag);
			}
			catch(Exception e) {
				handleException(e, diag);
			}
	}

	/**
	 * Parse a .rb file and return information about syntax errors and warnings.
	 * Must be preceded with a call to setUp().
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 *             if setUp was not called.
	 */
	public List<IRubyIssue> parse(File file) throws IOException, RubySyntaxException {
		return rubyProvider.parse(file);
	}

	/**
	 * Parse a .rb file and return information about syntax errors and warnings.
	 * Must be preceded with a call to setUp().
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 *             if setUp was not called.
	 */
	public List<IRubyIssue> parse(String path, Reader reader) throws IOException, RubySyntaxException {
		return rubyProvider.parse(path, reader);
	}

	public synchronized void registerProvider(Provider provider) {
		Resource resource = provider.eResource();
		if(resource == null)
			return;

		ResourceSet rs = resource.getResourceSet();
		if(rs == null)
			return;

		List<String> path = getUpToPuppetSegments(resource);
		if(path == null)
			return;

		String tname = provider.getTypeName();
		RESOURCES: for(Resource r : rs.getResources())
			if(r != resource)
				for(EObject o : r.getContents())
					if(o instanceof Type) {
						Type t = (Type) o;
						if(t.getName().equals(tname) && path.equals(getUpToPuppetSegments(r))) {
							addProviderParameter(t);
							break RESOURCES;
						}
					}
	}

	public synchronized void registerType(Type type) {
		synchronized(type) {
			List<Parameter> params = type.getParameters();
			for(Parameter param : params)
				if("provider".equals(param.getName()))
					return;

			Resource resource = type.eResource();
			if(resource == null)
				return;

			List<String> path = getUpToPuppetSegments(resource);
			if(path == null)
				return;

			ResourceSet rs = resource.getResourceSet();
			if(rs == null)
				return;

			String tname = type.getName();
			RESOURCES: for(Resource r : rs.getResources())
				if(r != resource)
					for(EObject o : r.getContents()) {
						if(o instanceof Provider && tname.equals(((Provider) o).getTypeName()) && path.equals(getUpToPuppetSegments(r))) {
							addProviderParameterNoCheck(type);
							break RESOURCES;
						}
					}
		}
	}

	private RubyResult<List<Provider>> transformProviders(IRubyParseResult<List<PPProviderInfo>> providerInfos) {
		List<Provider> result = Lists.newArrayList();
		for(PPProviderInfo info : providerInfos.getResult())
			result.add(info.toProvider());
		return new RubyResult<>(result, providerInfos.getIssues());
	}

	private RubyResult<List<Type>> transformTypes(IRubyParseResult<List<PPTypeInfo>> typeInfos) {
		List<Type> result = Lists.newArrayList();
		for(PPTypeInfo info : typeInfos.getResult()) {
			Type type = PPTPFactory.eINSTANCE.createType();
			type.setName(info.getTypeName());
			type.setDocumentation(info.getDocumentation());
			for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
				Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
				parameter.setName(entry.getKey());
				parameter.setDocumentation(entry.getValue().documentation);
				parameter.setRequired(entry.getValue().isRequired());
				parameter.setNamevar(entry.getValue().isNamevar());
				type.getParameters().add(parameter);
			}
			for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getProperties().entrySet()) {
				Property property = PPTPFactory.eINSTANCE.createProperty();
				property.setName(entry.getKey());
				property.setDocumentation(entry.getValue().documentation);
				property.setRequired(entry.getValue().isRequired());
				type.getProperties().add(property);
			}
			result.add(type);
		}
		return new RubyResult<>(result, typeInfos.getIssues());

	}
}
