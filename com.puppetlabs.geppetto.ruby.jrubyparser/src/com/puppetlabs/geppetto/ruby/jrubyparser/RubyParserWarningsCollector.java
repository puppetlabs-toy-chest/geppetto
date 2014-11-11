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

import java.util.Collections;
import java.util.List;

import org.jrubyparser.IRubyWarnings;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.lexer.SyntaxException;

import com.google.common.collect.Lists;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;

/**
 * Collects warnings and errors from JRubyParser callbacks
 */
public class RubyParserWarningsCollector implements IRubyWarnings {

	public static class RubyIssue implements IRubyIssue {
		private static final Object[] EMPTY_DATA = {};

		private ID id;

		private int line;

		private int startLine;

		private String fileName;

		private String message;

		private Object[] data;

		private int startOffset;

		private int length;

		protected RubyIssue(ID id, int line, int startLine, String fileName, String message, Object... data) {
			if(id == null)
				throw new IllegalArgumentException("ID may not be null");
			this.id = id;
			this.line = line;
			this.startLine = startLine;
			this.fileName = fileName;
			this.message = message;
			this.data = data;
			this.startOffset = -1;
			this.length = -1;
		}

		protected RubyIssue(ID id, SourcePosition position, String message, Object... data) {
			this.id = id;
			this.line = position.getEndLine();
			this.startLine = position.getStartLine();
			this.startOffset = position.getStartOffset();
			this.length = position.getEndOffset() - position.getStartOffset();
			this.fileName = position.getFile();
			this.message = message;
			this.data = data;
		}

		protected RubyIssue(SyntaxException error) {
			this(null, error.getPosition(), error.getMessage() == null
				? "Syntax error"
				: error.getMessage(), EMPTY_DATA);
		}

		@Override
		public Object[] getData() {
			return data;
		}

		/**
		 * Returns null if issue did not report a filename
		 *
		 * @return
		 */
		@Override
		public String getFileName() {
			return fileName;
		}

		/**
		 * Implementation specific.
		 *
		 * @return null if this issue represents a syntax error.
		 */
		public ID getId() {
			return id;
		}

		/**
		 * Returns "jruby.syntax.error" if this issue represents a syntax error,
		 * else the ID as a string. The ID is ruby parser implementation
		 * specific.
		 *
		 * @return
		 */
		@Override
		public String getIdString() {
			return id == null
				? "jruby.syntax.error"
				: id.toString();
		}

		@Override
		public int getLength() {
			return length;
		}

		@Override
		public int getLine() {
			return line;
		}

		@Override
		public String getMessage() {
			return message;
		}

		/**
		 * Returns -1 if no start line has been set.
		 *
		 * @return
		 */
		@Override
		public int getStartLine() {
			return startLine;
		}

		@Override
		public int getStartOffset() {
			return startOffset;
		}

		/**
		 * Indicates if this is a syntax error. This is the same as getId() ==
		 * null.
		 *
		 * @return
		 */
		@Override
		public boolean isSyntaxError() {
			return id == null;
		}

		@Override
		public String toString() {
			StringBuilder bld = new StringBuilder();
			bld.append(message);
			if(fileName != null) {
				bld.append(": ");
				bld.append(fileName);
				if(line >= 0) {
					bld.append(':');
					bld.append(line);
				}
			}
			return bld.toString();
		}
	}

	private final List<IRubyIssue> issues;

	private final boolean verbose;

	public RubyParserWarningsCollector(boolean collectWarnings, boolean verbose) {
		this.issues = collectWarnings
			? Lists.<IRubyIssue> newArrayList()
			: null;
		this.verbose = verbose;
	}

	protected void addIssue(RubyIssue issue) {
		if(issues != null)
			issues.add(issue);
	}

	public List<IRubyIssue> getIssues() {
		return issues == null
			? Collections.<IRubyIssue> emptyList()
			: Collections.unmodifiableList(issues);
	}

	@Override
	public boolean isVerbose() {
		return verbose;
	}

	@Override
	public void warn(ID id, SourcePosition position, String message, Object... data) {
		warning(id, position, message, data);
	}

	@Override
	public void warn(ID id, String fileName, int lineNumber, String message, Object... data) {
		warning(id, fileName, lineNumber, message, data);
	}

	@Override
	public void warn(ID id, String message, Object... data) {
		warning(id, message, data);
	}

	@Override
	public void warning(ID id, SourcePosition position, String message, Object... data) {
		addIssue(new RubyIssue(id, position, message, data));
	}

	@Override
	public void warning(ID id, String fileName, int lineNumber, String message, Object... data) {
		addIssue(new RubyIssue(id, lineNumber, -1, fileName, message, data));
	}

	@Override
	public void warning(ID id, String message, Object... data) {
		addIssue(new RubyIssue(id, -1, -1, null, message, data));
	}
}
