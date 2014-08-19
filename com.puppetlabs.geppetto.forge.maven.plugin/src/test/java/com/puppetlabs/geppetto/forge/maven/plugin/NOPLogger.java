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
package com.puppetlabs.geppetto.forge.maven.plugin;

import org.slf4j.helpers.MarkerIgnoringBase;

@SuppressWarnings("serial")
public class NOPLogger extends MarkerIgnoringBase {

	@Override
	final public void debug(String msg) {
	}

	@Override
	final public void debug(String format, Object arg) {
	}

	@Override
	public void debug(String format, Object... argArray) {
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
	}

	@Override
	public void debug(String msg, Throwable t) {
	}

	@Override
	public void error(String msg) {
	}

	@Override
	public void error(String format, Object arg1) {
	}

	@Override
	public void error(String format, Object... argArray) {
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
	}

	@Override
	public void error(String msg, Throwable t) {
	}

	@Override
	public String getName() {
		return "NOP";
	}

	@Override
	public void info(String msg) {
	}

	@Override
	public void info(String format, Object arg1) {
	}

	@Override
	public void info(String format, Object... argArray) {
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
	}

	@Override
	public void info(String msg, Throwable t) {
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void trace(String msg) {
	}

	@Override
	public void trace(String format, Object arg) {
	}

	@Override
	public void trace(String format, Object... argArray) {
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
	}

	@Override
	public void trace(String msg, Throwable t) {
	}

	@Override
	public void warn(String msg) {
	}

	@Override
	public void warn(String format, Object arg1) {
	}

	@Override
	public void warn(String format, Object... argArray) {
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
	}

	@Override
	public void warn(String msg, Throwable t) {
	}
}
