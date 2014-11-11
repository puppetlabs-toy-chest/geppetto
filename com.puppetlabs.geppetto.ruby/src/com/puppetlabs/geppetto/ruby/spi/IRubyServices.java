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
package com.puppetlabs.geppetto.ruby.spi;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.ruby.PPFunctionInfo;
import com.puppetlabs.geppetto.ruby.PPProviderInfo;
import com.puppetlabs.geppetto.ruby.PPTypeInfo;
import com.puppetlabs.geppetto.ruby.RubySyntaxException;

/**
 * The interface for a ruby service that provides parsing (to produce syntax
 * errors and warnings), and puppet specific parsing services such as finding
 * custom functions and types.
 */
public interface IRubyServices {

	DiagnosticType RUBY_SYNTAX = new DiagnosticType("RUBY_SYNTAX", IRubyServices.class.getName());

	DiagnosticType RUBY = new DiagnosticType("RUBY", IRubyServices.class.getName());

	IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;

	/**
	 * Turns the array called "@levels" in class Puppet::Utils::Log into
	 * functions (one per level defined in the array).
	 *
	 * @param file
	 *            - the "Log.rb" file in a puppet distro
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	IRubyParseResult<List<PPFunctionInfo>> getLogFunctions(File file) throws IOException, RubySyntaxException;

	/**
	 * Loads a PPTypeInfo describing metaparameters.
	 *
	 * @param file
	 *            - should typically reference 'Type.rb' in a puppet
	 *            distribution.
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	IRubyParseResult<PPTypeInfo> getMetaTypeProperties(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<PPTypeInfo> getMetaTypeProperties(String fileName, Reader reader) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPProviderInfo>> getProviderInfo(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPProviderInfo>> getProviderInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;

	/**
	 * Produce a Map of taks to description mapping. For tasks without a description, the description
	 * should be set ot an empty string.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 *             , RubySyntaxException
	 */
	IRubyParseResult<Map<String, String>> getRakefileTaskDescriptions(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPTypeInfo>> getTypeInfo(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPTypeInfo>> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;

	/**
	 * Parses and returns additional properties from a ruby file - such "extra"
	 * properties are returned as a PPTypeInfo that is partially filled with
	 * information.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException;

	IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;

	/**
	 * Indicates if this is a real service or one that produces no results.
	 *
	 * @return
	 */
	boolean isMockService();

	List<IRubyIssue> parse(File file) throws IOException, RubySyntaxException;

	List<IRubyIssue> parse(String path, Reader reader) throws IOException, RubySyntaxException;
}
