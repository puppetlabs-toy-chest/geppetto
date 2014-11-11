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
package com.puppetlabs.geppetto.ruby.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.junit.Test;

import com.puppetlabs.geppetto.ruby.PPFunctionInfo;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;

public class PuppetFunctionTests extends AbstractRubyTests {
	@Test
	public void testParseFunctionInNestedModules() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest.rb"));
		IRubyParseResult<List<PPFunctionInfo>> rr = helper.getFunctionInfo(aRubyFile);
		List<PPFunctionInfo> foundFunctions = rr.getResult();
		assertEquals("Should have found one function", 1, foundFunctions.size());
		PPFunctionInfo info = foundFunctions.get(0);
		assertEquals("Should have found echotest", "echotest", info.getFunctionName());
		assertTrue("Should have been an rValue", info.isRValue());
	}

	@Test
	public void testParseFunctionInQualifiedNamedModule() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest2.rb"));
		IRubyParseResult<List<PPFunctionInfo>> rr = helper.getFunctionInfo(aRubyFile);
		assertFalse("Should have found no issues", rr.hasIssues());
		List<PPFunctionInfo> foundFunctions = rr.getResult();
		assertEquals("Should have found one function", 1, foundFunctions.size());
		PPFunctionInfo info = foundFunctions.get(0);
		assertEquals("Should have found echotest", "echotest2", info.getFunctionName());
		assertTrue("Should have been an rValue", info.isRValue());

	}

	@Test
	public void testParseFunctionWithFullyQualifiedName() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest3.rb"));
		IRubyParseResult<List<PPFunctionInfo>> rr = helper.getFunctionInfo(aRubyFile);
		assertFalse("Should have found no issues", rr.hasIssues());
		List<PPFunctionInfo> foundFunctions = rr.getResult();
		assertEquals("Should have found one function", 1, foundFunctions.size());
		PPFunctionInfo info = foundFunctions.get(0);
		assertEquals("Should have found echotest", "echotest3", info.getFunctionName());
		assertTrue("Should have been an rValue", info.isRValue());
	}

	@Test
	public void testParseFunctionWithoutRtypeOrDoc() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path(
			"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/nodoc-nortype-function.rb"));
		IRubyParseResult<List<PPFunctionInfo>> rr = helper.getFunctionInfo(aRubyFile);
		assertFalse("Should have found no issues", rr.hasIssues());
		List<PPFunctionInfo> foundFunctions = rr.getResult();
		assertEquals("Should have found one function", 1, foundFunctions.size());
		PPFunctionInfo info = foundFunctions.get(0);
		assertEquals("Should have found echotest", "docless", info.getFunctionName());
		assertFalse("Should not have been an rValue", info.isRValue());
		assertEquals("Should be no documentation", "", info.getDocumentation());
	}
}
