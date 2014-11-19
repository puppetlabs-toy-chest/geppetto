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
package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.lexer.LexerSource;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;
import org.jrubyparser.parser.Ruby18Parser;
import org.jrubyparser.parser.Ruby19Parser;
import org.jrubyparser.parser.Ruby20Parser;
import org.jrubyparser.parser.RubyParser;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.ruby.PPFunctionInfo;
import com.puppetlabs.geppetto.ruby.PPProviderInfo;
import com.puppetlabs.geppetto.ruby.PPTypeInfo;
import com.puppetlabs.geppetto.ruby.RubySyntaxException;
import com.puppetlabs.geppetto.ruby.jrubyparser.RubyParserWarningsCollector.RubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;
import com.puppetlabs.geppetto.ruby.spi.RubyParserConfiguration;
import com.puppetlabs.geppetto.ruby.spi.RubyResult;

@Singleton
public class JRubyServices implements IRubyServices {
	// private static final String[] functionModuleFQN = new String[] {
	// "Puppet", "Parser", "Functions"};
	private static final String functionDefinition = "newfunction";

	private static final String[] newFunctionFQN = new String[] { "Puppet", "Parser", "Functions", functionDefinition };

	private static final String[] NAGIOS_BASE_PATH = new String[] { "puppet", "external", "nagios", "base.rb" };

	@Inject
	private RubyParserConfiguration parserConfig;

	/**
	 * Where the parsing "magic" takes place. This impl is used instead of a
	 * similar in the Parser util class since that impl uses a Null warning
	 * collector.
	 *
	 * @param path
	 * @param reader
	 * @param configuration
	 * @return
	 * @throws IOException
	 */
	protected IRubyParseResult<Node> doParse(String path, Reader reader) throws IOException, RubySyntaxException {
		RubyParser parser;
		CompatVersion cv;
		switch(parserConfig.getVersion()) {
			case V1_8:
				cv = CompatVersion.RUBY1_8;
				parser = new Ruby18Parser();
				break;
			case V1_9:
				cv = CompatVersion.RUBY1_9;
				parser = new Ruby19Parser();
				break;
			default:
				cv = CompatVersion.RUBY2_0;
				parser = new Ruby20Parser();
		}
		RubyParserWarningsCollector warnings = new RubyParserWarningsCollector(parserConfig.isCollectWarnings(), true);
		parser.setWarnings(warnings);

		ParserConfiguration configuration = new ParserConfiguration(1, cv);
		LexerSource lexerSource = LexerSource.getSource(path, reader, configuration);

		Node parserResult = null;
		try {
			parserResult = parser.parse(configuration, lexerSource).getAST();
		}
		catch(SyntaxException e) {
			throw new RubySyntaxException(new RubyIssue(e), warnings.getIssues());
		}
		return new RubyResult<>(parserResult, warnings.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		return getFunctionInfo(internalParse(file));
	}

	protected IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(IRubyParseResult<Node> result) {
		List<PPFunctionInfo> functions = Lists.newArrayList();
		RubyCallFinder callFinder = new RubyCallFinder(result.getResult());
		for(GenericCallNode found : callFinder.findCalls(newFunctionFQN)) {
			Object arguments = callFinder.getConstEvaluator().eval(found.getArgs());

			// Result should be a list with a String, and a Map
			if(!(arguments instanceof List))
				continue;

			List<?> argList = (List<?>) arguments;
			if(argList.isEmpty())
				continue;

			Object name = argList.get(0);
			if(!(name instanceof String))
				continue;

			// Functions can lack rtype and documentation. In that case they just have name
			if(argList.size() == 1) {
				functions.add(new PPFunctionInfo((String) name, false, ""));
				continue;
			}

			Object hash = argList.get(1);
			if(!(hash instanceof Map<?, ?>))
				continue;

			Object type = ((Map<?, ?>) hash).get("type");
			boolean rValue = "rvalue".equals(type);
			Object doc = ((Map<?, ?>) hash).get("doc");
			String docString = doc == null
				? ""
				: doc.toString();

			functions.add(new PPFunctionInfo((String) name, rValue, docString));
		}
		return new RubyResult<>(functions, result.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getFunctionInfo(internalParse(fileName, reader));
	}

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getLogFunctions(File file) throws IOException, RubySyntaxException {
		List<PPFunctionInfo> functions = Lists.newArrayList();
		IRubyParseResult<Node> result = internalParse(file);
		Node root = result.getResult();
		RubyClassFinder classFinder = new RubyClassFinder(root);
		ClassNode logClass = classFinder.findClass("Puppet", "Util", "Log");
		if(logClass != null)
			for(Node n : logClass.getBody().childNodes()) {
				if(n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode) n).getNextNode();

				if(n.getNodeType() == NodeType.INSTASGNNODE) {
					InstAsgnNode instAsgn = (InstAsgnNode) n;
					if("levels".equals(instAsgn.getName())) {
						Object value = classFinder.getConstEvaluator().eval(instAsgn.getValue());
						if(!(value instanceof List<?>))
							break;
						for(Object o : (List<?>) value) {
							functions.add(new PPFunctionInfo((String) o, false, "Log a message on the server at level " + o + "."));
						}

					}
				}
			}
		return new RubyResult<>(functions, result.getIssues());

	}

	@Override
	public IRubyParseResult<PPTypeInfo> getMetaTypeProperties(File file) throws IOException, RubySyntaxException {
		return getMetaTypeProperties(internalParse(file));
	}

	protected IRubyParseResult<PPTypeInfo> getMetaTypeProperties(IRubyParseResult<Node> result) {
		return new RubyResult<>(new PPTypeFinder().findMetaTypeInfo(result.getResult()), result.getIssues());
	}

	@Override
	public IRubyParseResult<PPTypeInfo> getMetaTypeProperties(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getMetaTypeProperties(internalParse(fileName, reader));

	}

	@Override
	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(File file) throws IOException, RubySyntaxException {
		return getProviderInfo(internalParse(file));
	}

	protected IRubyParseResult<List<PPProviderInfo>> getProviderInfo(IRubyParseResult<Node> result) {
		return new RubyResult<>(new PPTypeFinder().findProviderInfo(result.getResult()), result.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getProviderInfo(internalParse(fileName, reader));
	}

	@Override
	public IRubyParseResult<Map<String, String>> getRakefileTaskDescriptions(File file) throws IOException, RubySyntaxException {
		return getRakefileTaskDescriptions(internalParse(file));
	}

	/**
	 * @param result
	 *            - the parsed result (without syntax errors)
	 * @return
	 */
	private IRubyParseResult<Map<String, String>> getRakefileTaskDescriptions(IRubyParseResult<Node> result) {
		return new RubyResult<>(new RubyRakefileTaskFinder(result.getResult()).findTasks(), result.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(File file) throws IOException, RubySyntaxException {
		return getTypeInfo(internalParse(file), isNagiosLoad(file));
	}

	protected IRubyParseResult<List<PPTypeInfo>> getTypeInfo(IRubyParseResult<Node> result, boolean nagiosLoad) {
		PPTypeFinder typeFinder = new PPTypeFinder();
		if(nagiosLoad)
			return new RubyResult<>(typeFinder.findNagiosTypeInfo(result.getResult()), result.getIssues());

		PPTypeInfo typeInfo = typeFinder.findTypeInfo(result.getResult());
		return new RubyResult<>(typeInfo == null
			? Collections.<PPTypeInfo> emptyList()
			: Collections.singletonList(typeInfo), result.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getTypeInfo(internalParse(fileName, reader), isNagiosLoad(fileName));
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException {
		return getTypePropertiesInfo(internalParse(file));
	}

	public IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(IRubyParseResult<Node> result) {
		List<PPTypeInfo> types = Lists.newArrayList();
		PPTypeFinder typeFinder = new PPTypeFinder();
		List<PPTypeInfo> typeInfo = typeFinder.findTypePropertyInfo(result.getResult());
		if(typeInfo != null)
			types.addAll(typeInfo);
		return new RubyResult<>(types, result.getIssues());
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getTypePropertiesInfo(internalParse(fileName, reader));
	}

	/**
	 * Implementation that exposes the Result impl class. Don't want callers of
	 * the JRubyService to see this.
	 *
	 * @param file
	 * @return
	 */
	protected IRubyParseResult<Node> internalParse(File file) throws IOException, RubySyntaxException {
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		try (Reader reader = new BufferedReader(new FileReader(file))) {
			return doParse(file.getAbsolutePath(), reader);
		}
	}

	protected IRubyParseResult<Node> internalParse(String path, Reader reader) throws IOException, RubySyntaxException {
		if(!(reader instanceof BufferedReader))
			reader = new BufferedReader(reader);
		try {
			return doParse(path, reader);
		}
		finally {
			StreamUtil.close(reader);
		}

	}

	@Override
	public boolean isMockService() {
		return false;
	}

	private boolean isNagiosLoad(File file) {
		return isNagiosLoad(file.getAbsolutePath());
	}

	private boolean isNagiosLoad(String filePath) {
		final int nlength = NAGIOS_BASE_PATH.length;
		final IPath path = Path.fromOSString(filePath);
		final int length = path.segmentCount();
		boolean nagiosLoad = true; // until proven wrong
		for(int ix = 0; ix > -4 && nagiosLoad; ix--) {
			nagiosLoad = NAGIOS_BASE_PATH[nlength - 1 + ix].equals(path.segment(length - 1 + ix));
		}
		return nagiosLoad;
	}

	/**
	 * IOExceptions thrown FileNotFound, and while reading
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<IRubyIssue> parse(File file) throws IOException, RubySyntaxException {
		return internalParse(file).getIssues();
	}

	@Override
	public List<IRubyIssue> parse(String path, Reader reader) throws IOException, RubySyntaxException {
		return internalParse(path, reader).getIssues();
	}
}
