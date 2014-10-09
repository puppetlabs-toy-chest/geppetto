/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.diagnostic;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class AppendableWriter extends Writer {
	private final Appendable appendable;

	AppendableWriter(Appendable target) {
		this.appendable = target;
	}

	@Override
	public Writer append(char c) throws IOException {
		appendable.append(c);
		return this;
	}

	@Override
	public Writer append(CharSequence charSeq) throws IOException {
		appendable.append(charSeq);
		return this;
	}

	@Override
	public Writer append(CharSequence charSeq, int start, int end) throws IOException {
		appendable.append(charSeq, start, end);
		return this;
	}

	@Override
	public void close() throws IOException {
		if(appendable instanceof Closeable) {
			((Closeable) appendable).close();
		}
	}

	@Override
	public void flush() throws IOException {
		if(appendable instanceof Flushable) {
			((Flushable) appendable).flush();
		}
	}

	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		for(int top = off + len; off < top; ++off)
			appendable.append(cbuf[off]);
	}

	@Override
	public void write(int c) throws IOException {
		appendable.append((char) c);
	}

	@Override
	public void write(String str) throws IOException {
		appendable.append(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		appendable.append(str, off, off + len);
	}
}
