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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

// @fmtOff
@SuiteClasses({
	ValidateTestMojo.class,
	PublishTestMojo.class,
	RepublishTestMojo.class,
	ValidateTest2Mojo.class,
})
// @fmtOn
@RunWith(Suite.class)
public class ForgeIT {
	private static String createInitPP(VersionedName release) {
		StringBuilder bld = new StringBuilder();
		bld.append("class ");
		bld.append(release.getModuleName().getName());
		bld.append(" {\n}\n");
		return bld.toString();
	}

	private static Metadata createMetadata(VersionedName release, VersionedName... dependencies) {
		Metadata md = new Metadata();
		md.setName(release.getModuleName());
		md.setVersion(release.getVersion());
		md.setSummary("This is a Geppetto Test Module");
		md.setAuthor("Geppetto Dev Team");
		md.setSource("https://github.com/puppetlabs/geppetto.git");
		md.setLicense("Apache 2.0");
		List<Dependency> deps = new ArrayList<>(dependencies.length);
		for(VersionedName vn : dependencies) {
			Dependency dep = new Dependency();
			dep.setName(vn.getModuleName());
			dep.setVersionRequirement(getCompatibleRange(vn.getVersion()));
			deps.add(dep);
		}
		md.setDependencies(deps);
		return md;
	}

	public static void createModule(File dir, VersionedName release, VersionedName... deps) throws IOException {
		dir = new File(dir, release.getModuleName().getName());
		dir.mkdirs();
		try (PrintStream md = new PrintStream(new FileOutputStream(new File(dir, "metadata.json")), false, "UTF-8")) {
			md.print(createMetadata(release, deps).toString());
		}
		File manifests = new File(dir, "manifests");
		manifests.mkdir();
		try (PrintStream init = new PrintStream(new FileOutputStream(new File(manifests, "init.pp")), false, "UTF-8")) {
			init.print(createInitPP(release).toString());
		}
	}

	public static String generateModuleName() {
		String rnd = "000" + Integer.toString((int) (Math.random() * 10000));
		return "test_module_" + rnd.substring(rnd.length() - 4);
	}

	private static VersionRange getCompatibleRange(Version v) {
		StringBuilder bld = new StringBuilder();
		bld.append(v.getMajor());
		bld.append('.');
		bld.append(v.getMinor());
		bld.append(".x");
		return VersionRange.fromString(bld.toString());
	}

	@BeforeClass
	public static void init() throws IOException {
		TEST_POM_DIR = new File(new File(System.getProperty("basedir", ".")), "target/test-projects/publisher");
		FileUtils.rmR(new File(TEST_POM_DIR, "target"));

		testModuleA = new VersionedName("geppetto", generateModuleName(), "1.0.0");
		testModuleB = new VersionedName("geppetto", generateModuleName(), "1.0.0");
		testModuleC = new VersionedName("geppetto", generateModuleName(), "1.0.0");

		TEST_MODULES_DIR = new File(new File(System.getProperty("basedir", ".")), "target/test-modules");
		createModule(TEST_MODULES_DIR, testModuleA, testModuleB);
		createModule(TEST_MODULES_DIR, testModuleB, testModuleC);
		createModule(TEST_MODULES_DIR, testModuleC);

	}

	static File TEST_POM_DIR;

	static File TEST_MODULES_DIR;

	static VersionedName testModuleA;

	static VersionedName testModuleB;

	static VersionedName testModuleC;
}
