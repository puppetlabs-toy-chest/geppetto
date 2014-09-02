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
package com.puppetlabs.geppetto.forge.v2.api.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.semver.Version;

public class ReleaseTestCreate extends ForgeAPITestBase {

	@BeforeClass
	public static void initialize() {
		String rnd = "000" + Integer.toString((int) (Math.random() * 10000));
		testModule = ForgeAPITestBase.TEST_MODULE_PFX + rnd.substring(rnd.length() - 4);
	}

	private static String testModule;

	private String createInitPP(ModuleName moduleName) {
		StringBuilder bld = new StringBuilder();
		bld.append("class ");
		bld.append(moduleName.getName());
		bld.append(" {\n}\n");
		return bld.toString();
	}

	private Metadata createMetadata(ModuleName moduleName, Version version) {
		Metadata md = new Metadata();
		md.setName(moduleName);
		md.setVersion(version);
		md.setSummary("This is a Geppetto Test Module");
		md.setAuthor("Geppetto Dev Team");
		md.setSource("https://github.com/puppetlabs/geppetto.git");
		md.setLicense("Apache 2.0");
		return md;
	}

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

	private ModuleName getModuleName() {
		return ModuleName.create(TEST_USER, testModule, true);
	}

	private byte[] getReleaseImage(ModuleName moduleName, Version version) throws IOException {
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		try (TarArchiveOutputStream tarImage = new TarArchiveOutputStream(new GZIPOutputStream(bytesOut))) {
			putEntry(tarImage, moduleName, version, "metadata.json", createMetadata(moduleName, version).toString());
			putEntry(tarImage, moduleName, version, "manifests/init.pp", createInitPP(moduleName));
		}
		return bytesOut.toByteArray();
	}

	private void putEntry(TarArchiveOutputStream stream, ModuleName moduleName, Version version, String name, String content)
			throws IOException {
		StringBuilder fullName = new StringBuilder();
		moduleName.toString(fullName);
		fullName.append('-');
		version.toString(fullName);
		fullName.append('/');
		fullName.append(name);
		TarArchiveEntry entry = new TarArchiveEntry(fullName.toString());
		byte[] img = content.getBytes(Charsets.UTF_8);
		entry.setSize(img.length);
		stream.putArchiveEntry(entry);
		stream.write(img);
		stream.closeArchiveEntry();
	}

	@Test
	public void testCreate() throws IOException {
		ModuleName moduleName = getModuleName();
		byte[] releaseFile = getReleaseImage(moduleName, TEST_RELEASE_VERSION);
		try {
			Release newRelease = ForgeIT.getTestUserForge().createReleaseService().create(
				moduleName.getOwner(), moduleName.getName(), "Some notes about this release", new ByteArrayInputStream(releaseFile),
				releaseFile.length);
			assertNotNull("Null Release", newRelease);
			assertEquals("Incorrect release version", newRelease.getVersion(), TEST_RELEASE_VERSION);
		}
		catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSSL() throws Exception {
		URI baseURI = URI.create(FORGE_STAGING_SERVICE_BASE_URL);
		Socket sock = SSLSocketFactory.getDefault().createSocket();
		sock.connect(new InetSocketAddress(baseURI.getHost(), 443));
		try {
			((SSLSocket) sock).startHandshake();
		}
		catch(SSLException e) {
			StringBuilder bld = new StringBuilder();
			bld.append("Unable to connect. Supported cipher suites are:\n");
			for(String suite : getCipherSuites()) {
				bld.append('\t');
				bld.append(suite);
				bld.append('\n');
			}
			fail(bld.toString());
		}
	}
}
