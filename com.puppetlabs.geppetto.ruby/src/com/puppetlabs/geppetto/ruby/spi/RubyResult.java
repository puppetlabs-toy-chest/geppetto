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
package com.puppetlabs.geppetto.ruby.spi;

import java.util.Collections;
import java.util.List;

/**
 * Class that holds some type of result along with the issues generated when parsing
 * the ruby source.
 */
public class RubyResult<T> implements IRubyParseResult<T> {
	private final T result;

	private final List<IRubyIssue> issues;

	public RubyResult(T result, List<IRubyIssue> issues) {
		this.result = result;
		this.issues = issues == null
			? Collections.<IRubyIssue> emptyList()
			: Collections.unmodifiableList(issues);
	}

	/**
	 * @return the issues
	 */
	public List<IRubyIssue> getIssues() {
		return issues;
	}

	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}

	@Override
	public boolean hasErrors() {
		for(IRubyIssue issue : issues)
			if(issue.isSyntaxError())
				return true;
		return false;
	}

	@Override
	public boolean hasIssues() {
		return !issues.isEmpty();
	}
}
