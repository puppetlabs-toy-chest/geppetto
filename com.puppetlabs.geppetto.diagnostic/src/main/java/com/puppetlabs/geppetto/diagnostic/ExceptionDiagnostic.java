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

/**
 */
public class ExceptionDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private Exception exception;

	public ExceptionDiagnostic(int severity, DiagnosticType type, String message, Exception exception) {
		super(severity, type, message);
		this.exception = exception;
	}

	@Override
	public boolean appendMessage(StringBuilder bld) {
		boolean didAppend = super.appendMessage(bld);
		Exception e = getException();
		if(e != null) {
			if(didAppend)
				bld.append(':');
			bld.append(e.getClass().getSimpleName());
			String msg = e.getMessage();
			if(!(msg == null || msg.isEmpty())) {
				bld.append('(');
				bld.append(msg);
				bld.append(')');
			}
			didAppend = true;
		}
		return didAppend;
	}

	@Override
	public Exception getException() {
		return exception;
	}
}
