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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

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

	private void putEntry(TarArchiveOutputStream stream, ModuleName moduleName, Version version, String name,
			String content) throws IOException {
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
		try (FileOutputStream tmp = new FileOutputStream("/home/thhal/tmp/foo.tar.gz")) {
			tmp.write(releaseFile);
		}

		Release newRelease = ForgeIT.getTestUserForge().createReleaseService().create(
			moduleName.getOwner(), moduleName.getName(), "Some notes about this release",
			new ByteArrayInputStream(releaseFile), releaseFile.length);
		assertNotNull("Null Release", newRelease);
		assertEquals("Incorrect release version", newRelease.getVersion(), TEST_RELEASE_VERSION);
	}
}
