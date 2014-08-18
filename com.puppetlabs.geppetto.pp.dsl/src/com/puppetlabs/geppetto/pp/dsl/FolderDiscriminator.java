package com.puppetlabs.geppetto.pp.dsl;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.common.os.FileUtils;

@Singleton
public class FolderDiscriminator implements IFolderDiscriminator {
	private final Map<String, Boolean> matchCache = Maps.newHashMap();

	private Pattern excludePattern;

	protected synchronized Pattern getExcludePattern() {
		if(excludePattern == null)
			excludePattern = FileUtils.compileExcludePattern(Lists.newArrayList(getExcludePatterns()));
		return excludePattern;
	}

	/**
	 * UI module might override this to provide custom patterns
	 *
	 * @return Pattern used when excluding
	 */
	protected Set<String> getExcludePatterns() {
		Set<String> patterns = Sets.newLinkedHashSet(Lists.newArrayList(FileUtils.DEFAULT_EXCLUDES));
		patterns.add("spec");
		return patterns;
	}

	/**
	 * <p>
	 * Checks if the given URI contains a path segment that is matched by the exclude pattern and returns
	 * <code>true</code> if that is the case.
	 * </p>
	 * <p>
	 * The last path segment is considered to be a file name unless the URI has a trailing separator. File names do not
	 * participate in the check.
	 * </p>
	 *
	 * @param uri
	 *            The URI to check.
	 * @return <code>true</code> if the URI path contains a folder that is excluded by the pattern
	 */
	public synchronized boolean isExcluded(URI uri) {
		if(uri == null)
			return true;

		String[] segments = uri.segments();
		int t = segments.length;
		if(!uri.hasTrailingPathSeparator())
			// We don't exclude files, just folders
			--t;

		for(int i = 0; i < t; ++i)
			if(isMatch(segments[i]))
				return true;
		return false;
	}

	private boolean isMatch(String segment) {
		// The exclude pattern may be a fairly long list of ored expressions and this method
		// is called numerous times during a build. So instead of just matching using the
		// pattern each time we also keep a map of known matches.
		Boolean excluded = matchCache.get(segment);
		if(excluded == null) {
			excluded = Boolean.valueOf(getExcludePattern().matcher(segment).matches());
			matchCache.put(segment, excluded);
		}
		return excluded.booleanValue();
	}

	@Override
	public synchronized boolean isSegmentExcluded(String segment) {
		return isMatch(segment);
	}

	protected synchronized void reset() {
		excludePattern = null;
		matchCache.clear();
	}
}
