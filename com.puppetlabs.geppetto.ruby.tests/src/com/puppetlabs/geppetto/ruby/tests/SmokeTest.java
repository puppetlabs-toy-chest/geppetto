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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.junit.Test;

import com.puppetlabs.geppetto.ruby.RubySyntaxException;

public class SmokeTest extends AbstractRubyTests {
	@Test
	public void testConfiguration() {
		assertTrue("Should have a real ruby service configured", helper.isRubyServicesAvailable());
	}

	@Test
	public void testHelloBrokenWorld() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld.rb"));
		try {
			helper.parse(aRubyFile);
			fail("Expected one syntax error");
		}
		catch(RubySyntaxException e) {
			assertEquals("source line starts with 1", 1, e.getLine());
			assertEquals("the file path is reported", aRubyFile.getPath(), e.getFileName());
			assertTrue("the error message is the expected", e.getMessage().contains("unexpected tLPAREN_ARG"));
			assertTrue("expects no extra issues", e.getIssues().isEmpty());
		}
	}

	@Test
	public void testHelloBrokenWorld2() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld2.rb"));
		try {
			helper.parse(aRubyFile);
			fail("Expected one syntax error");
		}
		catch(RubySyntaxException e) {
			assertEquals("source line is 2", 2, e.getLine());
			assertEquals("the file path is reported", aRubyFile.getPath(), e.getFileName());
			assertTrue("the error message is the expected", e.getMessage().contains("unexpected tLPAREN_ARG"));
			assertTrue("expects no extra issues", e.getIssues().isEmpty());
		}
	}

	@Test
	public void testHelloWorld() throws Exception {
		File aRubyFile = testDataProvider.getTestFile(new Path("testData/ruby/helloWorld.rb"));
		helper.parse(aRubyFile);
	}
}
