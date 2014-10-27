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
package com.puppetlabs.geppetto.common.os;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
	public static class DefaultFileFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return !DEFAULT_EXCLUDES_MATCHER.matches(file.toPath());
		}
	}

	// @fmtOff
	public static final String[] DEFAULT_EXCLUDES = {
		"**/*~",
		"**/#*#",
		"**/.#*",
		"**/%*%",
		"**/._*",
		"**/.cvsignore",
		"**/vssver.scc",
		"**/.DS_Store",
		"**/.gitattributes",
		"**/.gitignore",
		"**/.gitmodules",
		"**/.hgignore",
		"**/.hgsub",
		"**/.hgsubstate",
		"**/.hgtags",
		"**/.bzrignore",
		"**/.project",
		"**/.classpath",
		"**/.bzrignore",
		"**/.bundle/**",
		"**/CVS/**",
		"**/SCCS/**",
		"**/.svn/**",
		"**/.git/**",
		"**/.hg/**",
		"**/.bzr/**",
		"**/.forge-releng/**",
		"**/.settings/**",
		"**/pkg/**",
		"**/coverage/**"
	};
	// @fmtOn

	// Directory names that should not be checksummed or copied.
	public static final PathMatcher DEFAULT_EXCLUDES_MATCHER = createGlobMatcher(Arrays.asList(DEFAULT_EXCLUDES));

	public static final FileFilter DEFAULT_FILE_FILTER = new DefaultFileFilter();

	public static void cp(File source, File destDir, String fileName) throws IOException {
		Files.copy(source.toPath(), new File(destDir, fileName).toPath(), StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS);
	}

	public static void cp(InputStream source, File destDir, String fileName) throws IOException {
		cp(source, destDir, fileName, -1);
	}

	public static void cp(InputStream source, File destDir, String fileName, long timestamp) throws IOException {
		Path target = destDir.toPath().resolve(fileName);
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		if(timestamp > 0)
			Files.setLastModifiedTime(target, FileTime.fromMillis(timestamp));
	}

	public static void cpR(File source, File destDir, FileFilter fileFilter, boolean createTop, boolean includeEmptyFolders)
			throws IOException {
		String name = source.getName();
		boolean isSymlink = isSymlink(source);

		File[] children = isSymlink
			? null
			: source.listFiles(fileFilter);

		if(children == null) {
			// File or symlink.
			File destFile = new File(destDir, name);
			if(destFile.exists())
				throw new IOException(destFile.getAbsolutePath() + " already exists");
			cp(source, destDir, name);
			return;
		}

		if(children.length == 0) {
			if(!includeEmptyFolders)
				return;
		}

		if(createTop) {
			destDir = new File(destDir, name);
		}

		if(!(destDir.mkdir() || destDir.isDirectory()))
			throw new IOException("Unable to create directory " + destDir.getAbsolutePath());

		for(File child : children)
			cpR(child, destDir, fileFilter, true, includeEmptyFolders);
	}

	public static PathMatcher createGlobMatcher(Iterable<String> excludes) {
		if(excludes == null)
			excludes = Collections.emptyList();

		Iterator<String> iter = excludes.iterator();
		if(!iter.hasNext())
			return new PathMatcher() {
				@Override
				public boolean matches(Path path) {
					return false;
				}
			};

		String first = iter.next();
		StringBuilder bld = new StringBuilder();
		bld.append("glob:");
		if(iter.hasNext()) {
			bld.append('{');
			bld.append(first);
			do {
				bld.append(',');
				bld.append(iter.next());
			} while(iter.hasNext());
			bld.append('}');
		}
		else
			bld.append(first);
		return FileSystems.getDefault().getPathMatcher(bld.toString());
	}

	/**
	 * @param file
	 *            The file to examine
	 * @return <code>true</code> if the <code>file</code> represents a symbolic link
	 */
	public static boolean isSymlink(File file) {
		return file != null && Files.isSymbolicLink(file.toPath());
	}

	/**
	 * @param file
	 *            The file that represents the symbolic link source
	 * @return The symbolic link target or <code>null</code> if file does not represent a symbolic link
	 * @throws IOException
	 */
	public static String readSymbolicLink(File file) throws IOException {
		if(file == null)
			return null;

		try {
			Path target = Files.readSymbolicLink(file.toPath());
			return target == null
				? null
				: target.toString();
		}
		catch(NotLinkException | UnsupportedOperationException e) {
		}
		return null;
	}

	public static void rmR(File fileOrDir) {
		rmR(fileOrDir, null);
	}

	public static void rmR(File fileOrDir, PathMatcher excludeMatcher) {
		if(excludeMatcher != null && excludeMatcher.matches(fileOrDir.toPath()))
			return;

		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				rmR(child, excludeMatcher);
		fileOrDir.delete();
	}

	public static void unzip(File zipFile, File destDir) throws IOException {
		try (InputStream in = new FileInputStream(zipFile)) {
			unzip(in, destDir);
		}
	}

	public static void unzip(InputStream inputs, File dest) throws IOException {

		if(!(dest.isDirectory() || dest.mkdirs()))
			throw new IOException("Not a directory: " + dest.getAbsolutePath());

		ZipInputStream input = new ZipInputStream(inputs);
		ZipEntry entry;
		while((entry = input.getNextEntry()) != null) {
			String name = entry.getName();
			if(entry.isDirectory()) {
				File destDir = new File(dest, name);
				if(!(destDir.isDirectory() || destDir.mkdir()))
					throw new IOException("Not a directory: " + destDir.getAbsolutePath());
				continue;
			}

			long entryTime = entry.getTime();

			// Fix entry time by taking the time zone into account
			entryTime += Calendar.getInstance().getTimeZone().getOffset(entryTime);

			cp(input, dest, name, entryTime);
		}
	}
}
