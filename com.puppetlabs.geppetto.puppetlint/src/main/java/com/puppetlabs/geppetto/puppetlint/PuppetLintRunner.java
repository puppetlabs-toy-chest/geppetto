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
package com.puppetlabs.geppetto.puppetlint;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * An interface to the <a href="http://puppet-lint.com">puppet-lint</a> program.
 */
public interface PuppetLintRunner {
	/**
	 * An issue produced by puppet-lint
	 */
	interface Issue {

		/**
		 * @return the checkName
		 */
		public abstract String getCheckName();

		/**
		 * @return the lineNumber
		 */
		public abstract int getLineNumber();

		/**
		 * @return the message
		 */
		public abstract String getMessage();

		/**
		 * @return the path
		 */
		public abstract String getPath();

		/**
		 * @return the severity
		 */
		public abstract Severity getSeverity();

	}

	enum Severity {
		ERROR, WARNING
	}

	/**
	 * Check name may only contain lowercase letters, digits, and underscores and may
	 * not start or end with underscore.
	 */
	Pattern CHECK_PATTERN = Pattern.compile("^[a-z0-9]+(?:[a-z0-9_]*[a-z0-9]+)?$");

	/**
	 * List of known puppet-lint options that are not checks.
	 */
	Set<String> NOT_CHECK_OPTIONS = new HashSet<>(Arrays.asList(new String[] {
		"version", "config", "with-context", "with-filename", "fail-on-warnings", "error-level", "show-ignored", "relative", "l", "load",
		"fix", "log-format", "only-checks" }));

	/**
	 * @return The version of the puppet-lint program.
	 * @throws IOException
	 *             if the program cannot execute or is unable to produce the version information
	 */
	String getVersion() throws IOException;

	/**
	 * Run puppet lint on the specified directory or file
	 *
	 * @param fileOrDirectory
	 *            The file or directory to check
	 * @param inverted
	 *            When <code>true</code>, pass each check as
	 *            --no-&lt;check&gt;-check. When <code>false</code>, use <code>--only-checks</code> parameter followed by a comma separated
	 *            <code>&lt;check&gt;,&lt;check&gt;,...</code> list
	 * @param checks
	 *            The checks to be performed. Use the name of the check only, not &quot;--no-&lt;check&gt;-check&quot;
	 * @return
	 * @throws IOException
	 */
	List<Issue> run(File fileOrDirectory, boolean inverted, String... checks) throws IOException;
}
