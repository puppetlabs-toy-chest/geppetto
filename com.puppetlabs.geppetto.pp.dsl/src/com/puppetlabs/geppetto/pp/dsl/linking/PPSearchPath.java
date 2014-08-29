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
package com.puppetlabs.geppetto.pp.dsl.linking;

import static com.puppetlabs.geppetto.common.Strings.trimToNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;

/**
 * notes:
 * rootURI - URI to file:: root, used to make paths relative if URI is an absolute file uri
 */
public class PPSearchPath {

	public interface IConfigurableProvider {
		/**
		 * Configure the search path provider so it knows the container of (distro) pptp, and
		 * root for absolute files.
		 *
		 * @param rootDirectory
		 *            The root for absolute files. Required.
		 * @param path
		 *            The search path. May be <code>null</code>.
		 * @param environment
		 *            The puppet $environment. May be <code>null</code>.
		 * @param manifestDir
		 *            The puppet $manifestdir. May be <code>null</code>.
		 */
		public void configure(URI rootDirectory, String path, String environment, String manifestDir);
	}

	public interface ISearchPathProvider {

		public PPSearchPath get(Resource r);
	}

	/**
	 * Create a new instance of a PPSearchPath
	 *
	 * @param rootDirectory
	 *            The root for absolute files. Required.
	 * @param path
	 *            The search path. May be <code>null</code>.
	 * @param environment
	 *            The puppet $environment. May be <code>null</code>.
	 * @param manifestDir
	 *            The puppet $manifestdir. May be <code>null</code>.
	 * @return The new instance
	 */
	public static PPSearchPath fromString(String path, URI rootDirectory, String environment, String manifestDir) {
		if(rootDirectory == null)
			throw new IllegalArgumentException("root directory must be specified");

		if(!rootDirectory.hasTrailingPathSeparator())
			rootDirectory = rootDirectory.appendSegment("");

		IPath rootPath = Path.fromOSString(rootDirectory.toFileString());

		environment = trimToNull(environment);
		if(environment == null)
			environment = DEFAULT_PUPPET_ENVIRONMENT;

		manifestDir = trimToNull(manifestDir);
		if(manifestDir == null)
			manifestDir = DEFAULT_MANIFEST_DIR;
		else
			manifestDir = manifestDir.replaceAll("\\$environment", environment);

		path = trimToNull(path);
		if(path == null)
			path = "*";
		else
			path = path.replaceAll("\\$environment", environment);

		List<IPath> p = Lists.newArrayList();

		// split the string on ':', and create a path per segment
		String[] paths = path.split(":");
		for(String s : paths) {
			if(s.length() == 0)
				continue; // skip empty segments
			if(s.contains("$manifestdir")) {
				if(s.length() > 12) {
					// Allow suffix '/*' and '\\*' but nothing else
					char sep = s.charAt(12);
					if(!((sep == '/' || sep == '\\') && s.length() == 14 && s.charAt(13) == '*'))
						throw new IllegalArgumentException("$manifestdir can only exist as a separate path segment");
				}
				p.add(Path.fromOSString(URI.createURI(manifestDir).resolve(rootDirectory).toFileString()).makeRelativeTo(rootPath).append(
					"*"));
			}
			else
				p.add(new Path(s));
		}
		return new PPSearchPath(p, rootDirectory);
	}

	public static final String DEFAULT_MANIFEST_DIR = "manifests"; //$NON-NLS-1$

	public static final String DEFAULT_PUPPET_ENVIRONMENT = "production"; //$NON-NLS-1$

	public static final String DEFAULT_PUPPET_PROJECT_PATH = "lib/*:environments/$environment/*:$manifestdir/*:modules/*"; //$NON-NLS-1$

	private final URI rootDirectory;

	private final List<IPath> searchPath;

	private PPSearchPath(List<IPath> p, URI rootDirectory) {
		this.searchPath = p;
		this.rootDirectory = rootDirectory;
	}

	public List<File> getResolvedPath() {
		List<File> resolved = new ArrayList<File>();
		for(IPath p : searchPath)
			resolved.add(new File(URI.createURI(p.toPortableString()).resolve(rootDirectory).toFileString()));
		return resolved;
	}

	public boolean isMatch(IPath candidate, IPath p) {
		final int candidateLimit = candidate.segmentCount();
		final int pLimit = p.segmentCount();
		for(int i = 0; i < pLimit; i++) {
			String s = p.segment(i);
			// * matches any remaining segments of candidate, including no segements
			if("*".equals(s))
				return true;

			// if more segments in candidate required than what it has
			if(i >= candidateLimit)
				return false;

			if(!s.equals(candidate.segment(i)))
				return false;
		}
		// all segments in p matched, there should only be one remaining segment in candidate
		// (if p does not end with *, only paths in p's final directory are matched
		if(candidateLimit - pLimit > 1)
			return false;
		return true;
	}

	public int searchIndexOf(IEObjectDescription d) {
		URI uri = EcoreUtil.getURI(d.getEObjectOrProxy());
		if(uri.isFile())
			uri = uri.deresolve(rootDirectory);
		return searchIndexOf(uri);
	}

	/**
	 * Computes the path position of the given URI, or -1 if not found.
	 * The pptp is always on the search path with index 0. TODO: This is wrong!
	 * It is only the distro path that is on 0, other pptp contributions are subject to filtering (i.e.
	 * ruby code).
	 *
	 * @param uri
	 * @return search path index or -1 if not found
	 */
	public int searchIndexOf(URI uri) {
		String uriPath = uri.path();
		IPath p = new Path(uriPath);
		if("pptp".equals(p.getFileExtension()))
			return 0; // All pptp are searched first - ALWAYS
		if(uri.isPlatformResource())
			p = p.removeFirstSegments(2);
		for(int idx = 0; idx < searchPath.size(); idx++) {
			IPath q = searchPath.get(idx);
			if(isMatch(p, q))
				return idx + 1;

		}
		return -1;
	}

	@Override
	public String toString() {
		int top = searchPath.size();
		if(top == 0)
			return "";

		StringBuilder bld = new StringBuilder(searchPath.get(0).toPortableString());
		for(int idx = 1; idx < top; ++idx) {
			bld.append(':');
			bld.append(searchPath.get(idx).toPortableString());
		}
		return bld.toString();
	}
}
