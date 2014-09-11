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
package com.puppetlabs.geppetto.common.os;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class UnicodeReaderFactory {
	/**
	 * <p>
	 * Creates a new {@link InputStreamReader} instance that will read bytes from the given <code>input</code>. A check is first made to see
	 * if BOM marker is found at the beginning of input. If it is, then the encoding denoted by the BOM will be used. When no BOM is found,
	 * the stream re-read using the provided defaultEncoding.
	 * </p>
	 * <p>
	 * The <code>input</code> will be wrapped in a {@link BufferedInputStream} unless it already supports the
	 * {@link InputStream#markSupported() mark and reset} methods.
	 * </p>
	 *
	 * @param input
	 *            The possibly BOM encoded Input stream.
	 * @param defaultEncoding
	 *            Default encoding to be used when no BOM is detected,
	 *            or <code>null</code> to use system default.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public static Reader createReader(InputStream input, String defaultEncoding) throws IOException {
		byte bom[] = new byte[4];
		String encoding = defaultEncoding;
		if(!(input.markSupported()))
			input = new BufferedInputStream(input);

		input.mark(4);
		int bytesRead = input.read(bom, 0, bom.length);
		int consumed = 0;

		int b1 = bom[1] & 0xff;
		int b2 = bom[2] & 0xff;
		int b3 = bom[3] & 0xff;
		switch(bom[0] & 0xff) {
			case 0x00:
				if(b1 == 0x00 && b2 == 0xFE && b3 == 0xFF) {
					encoding = "UTF-32BE";
					consumed = 4;
				}
				break;
			case 0xEF:
				if(b1 == 0xBB && b2 == 0xBF) {
					encoding = "UTF-8";
					consumed = 3;
				}
				break;
			case 0xFE:
				if(b1 == 0xFF) {
					encoding = "UTF-16BE";
					consumed = 2;
				}
			case 0xFF:
				if(b1 == 0xFE) {
					if(b2 == 0x00 && b3 == 0x00) {
						encoding = "UTF-32LE";
						consumed = 4;
					}
					else {
						encoding = "UTF-16LE";
						consumed = 2;
					}
				}
		}

		if(consumed < bytesRead) {
			input.reset();
			// Skip BOM
			while(--consumed >= 0)
				input.read();
		}

		return encoding == null
			? new InputStreamReader(input)
			: new InputStreamReader(input, encoding);
	}
}
