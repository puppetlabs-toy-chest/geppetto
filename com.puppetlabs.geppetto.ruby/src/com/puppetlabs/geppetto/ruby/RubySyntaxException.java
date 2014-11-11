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

import java.util.List;

import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;

/**
 * A simplified RubySyntaxException that should be thrown in operations where it
 * is expected that syntax errors are not present.
 */
public class RubySyntaxException extends Exception implements IRubyIssue {

	private static final long serialVersionUID = 1L;

	private final IRubyIssue syntaxIssue;

	private final List<IRubyIssue> issues;

	public RubySyntaxException(IRubyIssue syntaxIssue, List<IRubyIssue> issues) {
		super(syntaxIssue.getMessage());
		this.syntaxIssue = syntaxIssue;
		this.issues = issues;
	}

	@Override
	public Object[] getData() {
		return syntaxIssue.getData();
	}

	@Override
	public String getFileName() {
		return syntaxIssue.getFileName();
	}

	@Override
	public String getIdString() {
		return syntaxIssue.getIdString();
	}

	public List<IRubyIssue> getIssues() {
		return issues;
	}

	@Override
	public int getLength() {
		return syntaxIssue.getLength();
	}

	@Override
	public int getLine() {
		return syntaxIssue.getLine();
	}

	public int getStartLine() {
		return syntaxIssue.getStartLine();
	}

	public int getStartOffset() {
		return syntaxIssue.getStartOffset();
	}

	@Override
	public boolean isSyntaxError() {
		return true;
	}
}
