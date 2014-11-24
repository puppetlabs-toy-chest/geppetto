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
package com.puppetlabs.geppetto.forge.v2.api.it;

import static org.junit.Assert.fail;

import java.net.InetSocketAddress;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

/**
 * @author thhal
 */
public class SLLTest {

	private String[] getCipherSuites() throws NoSuchAlgorithmException, KeyManagementException {
		// Create an SSLContext that uses our TrustManager
		SSLContext context = SSLContext.getInstance("TLS");
		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		context.init(null, new TrustManager[] { tm }, new SecureRandom());
		SSLParameters params = context.getSupportedSSLParameters();
		return params.getCipherSuites();
	}

	@Test
	public void testSSL() throws Exception {
		URI baseURI = URI.create("https://forgestagingapi.puppetlabs.com");
		SSLSocket sock = (SSLSocket) SSLSocketFactory.getDefault().createSocket();
		sock.setEnableSessionCreation(true);
		sock.connect(new InetSocketAddress(baseURI.getHost(), 443));
		try {
			sock.startHandshake();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			StringBuilder bld = new StringBuilder();
			bld.append("Unable to connect:");
			bld.append(e.getMessage());
			bld.append("\nSupported cipher suites are:\n");
			for(String suite : getCipherSuites()) {
				bld.append('\t');
				bld.append(suite);
				bld.append('\n');
			}
			String txt = bld.toString();
			System.out.println(txt);
			fail(txt);
		}
		SSLSession session = sock.getSession();
		System.out.format("Connected. Using cihper suite: %s%n", session.getCipherSuite());
	}

}
