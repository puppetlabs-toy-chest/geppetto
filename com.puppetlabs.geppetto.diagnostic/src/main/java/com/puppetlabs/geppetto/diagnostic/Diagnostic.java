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
package com.puppetlabs.geppetto.diagnostic;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Diagnostic implements Formattable, Serializable, Iterable<Diagnostic> {
	public static final DiagnosticType CHAIN = new DiagnosticType("CHAIN", Diagnostic.class.getName());

	private static final long serialVersionUID = 1L;

	public static final int FATAL = 5;

	public static final int ERROR = 4;

	public static final int WARNING = 3;

	public static final int INFO = 2;

	public static final int DEBUG = 1;

	public static final int OK = 0;

	private static final String[] severityStrings = new String[] { "OK", "DEBUG", "INFO", "WARNING", "ERROR", "FATAL" };

	private static final SimpleDateFormat ISO_8601_TZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	private static final String[] emptyData = new String[0];

	public static int getSeverity(String severityString) {
		int severity = OK;
		switch(severityString.toUpperCase()) {
			case "OK":
				severity = Diagnostic.OK;
				break;
			case "DEBUG":
				severity = Diagnostic.DEBUG;
				break;
			case "INFO":
				severity = Diagnostic.INFO;
				break;
			case "WARNING":
				severity = Diagnostic.WARNING;
				break;
			case "ERROR":
				severity = Diagnostic.ERROR;
				break;
			case "FATAL":
				severity = Diagnostic.FATAL;
				break;
			default:
				throw new IllegalArgumentException();
		}
		return severity;
	}

	/**
	 * Return the severity as a string. The string &quot;UNKNOWN(&lt;severity&gt;)&quot; will be returned if the
	 * argument represents an unknown severity.
	 *
	 * @param severity
	 * @return A string representing the severity
	 */
	public static String getSeverityString(int severity) {
		return severity >= 0 && severity < severityStrings.length
			? severityStrings[severity]
			: format("UNKNOWN(%d)", severity);
	}

	private long timestamp;

	private int severity;

	private String message;

	private List<Diagnostic> children = Collections.emptyList();

	private DiagnosticType type;

	private String issue;

	private String[] issueData;

	public Diagnostic() {
		setType(CHAIN);
		setSeverity(OK);
	}

	public Diagnostic(Diagnostic source) {
		setSeverity(source.getSeverity());
		setMessage(source.getMessage());
		setType(source.getType());
		setTimestamp(source.getTimestamp());
		setIssue(source.getIssue());
		setIssueData(source.getIssueData());
	}

	/**
	 * Creates a new Diagnostic instance
	 *
	 * @param severity
	 *            Severity (see constants in {@link MessageWithSeverity})
	 * @param type
	 *            The type of message
	 * @param message
	 *            The textual content of the message
	 */
	public Diagnostic(int severity, DiagnosticType type, String message) {
		setSeverity(severity);
		setMessage(message);
		setType(type);
		setTimestamp(System.currentTimeMillis());
	}

	public void addChild(Diagnostic child) {
		if(getSeverity() < child.getSeverity())
			setSeverity(child.getSeverity());
		int sz = children.size();
		if(sz == 0)
			children = Collections.singletonList(child);
		else if(sz == 1) {
			children = new ArrayList<>(children);
			children.add(child);
		}
		else
			children.add(child);
		childAdded(child);
	}

	public void addChildren(Collection<? extends Diagnostic> children) {
		for(Diagnostic child : children)
			addChild(child);
	}

	public boolean appendLocationLabel(StringBuilder builder, boolean withOffsets) {
		return false;
	}

	/**
	 * Append message to <code>bld</code>
	 *
	 * @param bld
	 * @return <code>true</code> if at least one character was appended
	 */
	public boolean appendMessage(StringBuilder bld) {
		if(!(message == null || message.isEmpty())) {
			bld.append(message);
			return true;
		}
		return false;
	}

	/**
	 * Implementors may want to override this method for direct logging purposes
	 *
	 * @param child
	 *            The child that was added to this instance
	 */
	protected void childAdded(Diagnostic child) {
		// Default is to do nothing.
	}

	@Override
	public final void formatTo(Formatter formatter, int flags, int width, int precision) {
		StringBuilder bld = new StringBuilder();
		formatTo(bld);
		try {
			if(precision >= 0 && bld.length() > precision) {
				bld.setLength(precision - 1);
				bld.append('*');
			}
			int pad = width - bld.length();
			if(pad > 0) {
				if((flags & FormattableFlags.LEFT_JUSTIFY) != 0)
					while(--pad >= 0)
						bld.append(' ');
				else
					while(--pad >= 0)
						bld.insert(0, ' ');
			}
			formatter.out().append(bld);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void formatTo(StringBuilder bld) {
		appendMessage(bld);
	}

	public List<Diagnostic> getChildren() {
		return children;
	}

	@JsonIgnore
	public int getErrorCount() {
		return getSeverityCount(Diagnostic.ERROR);
	}

	/**
	 * Scans the children, depth first, until an ExceptionDiagnostic is found. The exception held by that diagnostic is
	 * return.
	 *
	 * @return The first exception found or <code>null</code> if no exception exists.
	 */
	public Exception getException() {
		for(Diagnostic child : children) {
			Exception found = child.getException();
			if(found != null)
				return found;
		}
		return null;
	}

	/**
	 * Returns <tt>null</tt> unless subclassed
	 *
	 * @return <tt>null</tt>
	 * @see FileDiagnostic
	 */
	public File getFile() {
		return null;
	}

	/**
	 * The issue is a String naming a particular issue that makes it possible to have a more detailed understanding of
	 * an error and what could be done to repair it. (As opposed to parsing the error message to gain an understanding).
	 * Error messages may
	 *
	 * @return the value of the '<em>issue</em>' attribute.
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * The issue data is optional data associated with a particular issue - it is typically used to pass values
	 * calculated during validation and that may be meaningful to code that tries to repair or analyze a particular
	 * problem and where it may be expensive to recompute these values.
	 *
	 * @return the value of the '<em>issueData</em>' attribute.
	 */
	public String[] getIssueData() {
		return issueData;
	}

	/**
	 * Returns <tt>-1</tt> unless subclassed
	 *
	 * @return <tt>-1</tt>
	 * @see FileDiagnostic
	 */
	public int getLineNumber() {
		return -1;
	}

	/**
	 * Returns the result of calling {{@link #appendLocationLabel(StringBuilder, boolean)} on a StringBuilder or <tt>null</tt> if no
	 * location label is present.
	 *
	 * @param withOffsets
	 *            Flag that indicates if offsets from the beginning of file are of interest (can be used for
	 *            highlighting in editors).
	 * @return The location label or <tt>null</tt>
	 * @see FileDiagnostic
	 */
	public String getLocationLabel(boolean withOffsets) {
		StringBuilder bld = new StringBuilder();
		return appendLocationLabel(bld, withOffsets)
			? bld.toString()
			: null;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		StringBuilder bld = new StringBuilder();
		appendMessage(bld);
		return bld.toString();
	}

	/**
	 * @return the severity
	 */
	@JsonIgnore
	public int getSeverity() {
		return severity;
	}

	private int getSeverityCount(int severity) {
		int count = 0;
		for(Diagnostic d : this)
			if(d.getSeverity() == severity)
				++count;
		return count;
	}

	@JsonGetter("severity")
	public String getSeverityString() {
		return getSeverityString(getSeverity());
	}

	/**
	 * @return the source
	 */
	@JsonIgnore
	public String getSource() {
		return type.getSource();
	}

	@JsonIgnore
	public long getTimestamp() {
		return timestamp;
	}

	@JsonGetter("timestamp")
	public String getTimestampString() {
		synchronized(ISO_8601_TZ) {
			return ISO_8601_TZ.format(new Date(timestamp));
		}
	}

	/**
	 * @return The type of diagnostic
	 */
	@JsonIgnore
	public DiagnosticType getType() {
		return type;
	}

	@JsonGetter("type")
	public String getTypeString() {
		return type.getName();
	}

	@JsonIgnore
	public int getWarningCount() {
		return getSeverityCount(Diagnostic.WARNING);
	}

	@Override
	public Iterator<Diagnostic> iterator() {
		return getChildren().iterator();
	}

	/**
	 * This method is needed by net.sf.json but should not otherwise be used.
	 *
	 * @param children
	 *            The new children to set
	 * @see #addChild(Diagnostic)
	 * @see #addChildren(Collection)
	 */
	public void setChildren(List<Diagnostic> children) {
		this.children = Collections.emptyList();
		addChildren(children);
	}

	public void setIssue(String newIssue) {
		issue = newIssue;
	}

	public void setIssueData(String[] issueData) {
		this.issueData = issueData == null || issueData.length == 0
			? emptyData
			: issueData;
	}

	/**
	 * Ensures that the severity of the diagnostic and all its children is equal to or below the given <code>max</code>.
	 *
	 * @param max
	 *            The max severity
	 */
	public void setMaxSeverity(int max) {
		if(severity > max)
			severity = max;
		for(Diagnostic child : children)
			child.setMaxSeverity(severity);
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	@JsonIgnore
	public void setSeverity(int severity) {
		if(severity < this.severity)
			// Ensures children severity is not above this one
			setMaxSeverity(severity);
		else
			// Severity increase. Does not affect children
			this.severity = severity;
	}

	@JsonSetter("severity")
	public void setSeverityString(String severityString) {
		setSeverity(getSeverity(severityString));
	}

	@JsonIgnore
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@JsonSetter("timestamp")
	public void setTimestampString(String timestampString) {
		synchronized(ISO_8601_TZ) {
			try {
				setTimestamp(ISO_8601_TZ.parse(timestampString).getTime());
			}
			catch(ParseException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

	@JsonIgnore
	public void setType(DiagnosticType type) {
		this.type = type;
	}

	@Override
	public final String toString() {
		return toString(INFO);
	}

	public final String toString(int minSeverity) {
		StringBuilder bld = new StringBuilder();
		toString(minSeverity, bld, 0);
		return bld.toString();
	}

	private void toString(int severity, StringBuilder bld, boolean includeTopSeverity, int indent) {
		if(getSeverity() < severity)
			// Severity is transitive, so nothing to add here
			return;

		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		String resourcePath = getFile() == null
			? null
			: getFile().getPath();

		int top = children.size();
		if(getMessage() == null && resourcePath == null) {
			if(top == 0) {
				bld.append(getSeverityString());
				return;
			}
		}
		else {
			if(includeTopSeverity || indent > 0) {
				bld.append(getSeverityString());
				bld.append(':');
			}

			if(resourcePath != null) {
				bld.append(resourcePath);
				bld.append(':');
			}
			if(appendLocationLabel(bld, true))
				bld.append(':');

			if(!appendMessage(bld))
				bld.setLength(bld.length() - 1);

			if(top > 0) {
				bld.append('\n');
				indent += 4;
			}
		}

		if(top > 0) {
			int idx = 0;
			for(;;) {
				children.get(idx).toString(severity, bld, includeTopSeverity, indent);
				if(++idx >= top)
					break;
				bld.append('\n');
			}
		}
	}

	public void toString(int minSeverity, StringBuilder bld, int indent) {
		toString(minSeverity, bld, true, indent);
	}
}
