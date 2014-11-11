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
package com.puppetlabs.geppetto.ruby;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.inject.Singleton;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;
import com.puppetlabs.geppetto.ruby.spi.RubyResult;

@Singleton
public class MockRubyServices implements IRubyServices {
	private static final IRubyParseResult<List<PPFunctionInfo>> emptyFunctionInfo = new RubyResult<>(
		Collections.<PPFunctionInfo> emptyList(), RubyHelper.emptyIssues);

	private static final IRubyParseResult<List<PPTypeInfo>> emptyTypeInfo = new RubyResult<>(
		Collections.<PPTypeInfo> emptyList(), RubyHelper.emptyIssues);

	private static final IRubyParseResult<List<PPProviderInfo>> emptyProviderInfo = new RubyResult<>(
		Collections.<PPProviderInfo> emptyList(), RubyHelper.emptyIssues);

	private static final IRubyParseResult<Map<String, String>> emptyRakeTaskDescriptors = new RubyResult<>(
		Collections.<String, String> emptyMap(), RubyHelper.emptyIssues);

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(File file) throws IOException {
		return emptyFunctionInfo;
	}

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getFunctionInfo(String fileName, Reader reader) {
		return emptyFunctionInfo;
	}

	@Override
	public IRubyParseResult<List<PPFunctionInfo>> getLogFunctions(File file) throws IOException, RubySyntaxException {
		return emptyFunctionInfo;
	}

	@Override
	public IRubyParseResult<PPTypeInfo> getMetaTypeProperties(File file) throws IOException, RubySyntaxException {
		return new RubyResult<>(null, RubyHelper.emptyIssues);
	}

	@Override
	public IRubyParseResult<PPTypeInfo> getMetaTypeProperties(String fileName, Reader reader) {
		return new RubyResult<>(null, RubyHelper.emptyIssues);
	}

	@Override
	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(File file) throws IOException, RubySyntaxException {
		return emptyProviderInfo;
	}

	@Override
	public IRubyParseResult<List<PPProviderInfo>> getProviderInfo(String fileName, Reader reader) {
		return emptyProviderInfo;
	}

	@Override
	public IRubyParseResult<Map<String, String>> getRakefileTaskDescriptions(File file) {
		return emptyRakeTaskDescriptors; // empty map - can't discover anything about rakefiles
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(File file) throws IOException {
		return emptyTypeInfo;
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return emptyTypeInfo;
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException {
		return emptyTypeInfo;
	}

	@Override
	public IRubyParseResult<List<PPTypeInfo>> getTypePropertiesInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return emptyTypeInfo;
	}

	@Override
	public boolean isMockService() {
		return true;
	}

	@Override
	public List<IRubyIssue> parse(File file) throws IOException {
		return RubyHelper.emptyIssues;
	}

	@Override
	public List<IRubyIssue> parse(String path, Reader reader) throws IOException {
		return RubyHelper.emptyIssues;
	}

}
