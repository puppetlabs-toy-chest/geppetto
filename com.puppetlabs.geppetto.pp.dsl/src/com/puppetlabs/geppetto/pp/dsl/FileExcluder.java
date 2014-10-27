package com.puppetlabs.geppetto.pp.dsl;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.common.os.FileUtils;

@Singleton
public class FileExcluder implements IFileExcluder {
	private PathMatcher pathMatcher;

	/**
	 * UI module might override this to provide custom patterns
	 *
	 * @return Pattern used when excluding
	 */
	protected Set<String> getExcludeGlobs() {
		return Collections.emptySet();
	}

	protected synchronized PathMatcher getPathMatcher() {
		if(pathMatcher == null)
			pathMatcher = FileUtils.createGlobMatcher(Sets.union(getExcludeGlobs(), Sets.newHashSet(FileUtils.DEFAULT_EXCLUDES)));
		return pathMatcher;
	}

	protected Path getRelativePath(Path path) {
		return path;
	}

	protected Path getRelativePath(URI uri) {
		if(uri != null && uri.isPlatform() && uri.segmentCount() > 1 && "resource".equals(uri.segment(0))) {
			String[] segments = uri.segments();
			StringBuilder path = new StringBuilder(segments[1]);
			for(int idx = 2; idx < segments.length; ++idx) {
				path.append('/');
				path.append(segments[idx]);
			}
			return Paths.get(path.toString());
		}
		return null;
	}

	@Override
	public boolean isExcluded(Path path) {
		path = getRelativePath(path);
		return path == null
			? true
			: getPathMatcher().matches(path);
	}

	/**
	 * <p>
	 * Checks if the given URI contains a path segment that is matched by the exclude pattern and returns <code>true</code> if that is the
	 * case.
	 * </p>
	 * <p>
	 * The last path segment is considered to be a file name unless the URI has a trailing separator. File names do not participate in the
	 * check.
	 * </p>
	 *
	 * @param uri
	 *            The URI to check.
	 * @return <code>true</code> if the URI path contains a folder that is excluded by the pattern
	 */
	@Override
	public synchronized boolean isExcluded(URI uri) {
		Path path = getRelativePath(uri);
		return path == null
			? true
			: getPathMatcher().matches(path);
	}

	protected synchronized void reset() {
		pathMatcher = null;
	}
}
