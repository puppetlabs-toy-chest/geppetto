package com.puppetlabs.geppetto.pp.dsl.ui.builder;

import java.util.Set;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.FileExcluder;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelperProvider;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;

@Singleton
public class PreferenceBasedFileExcluder extends FileExcluder implements IPropertyChangeListener {
	@Inject
	private PPPreferencesHelperProvider preferencesProvider;

	@Inject
	private RebuildChecker backgroundRebuildChecker;

	private PPPreferencesHelper helper;

	@Override
	protected synchronized Set<String> getExcludeGlobs() {
		if(helper == null) {
			helper = preferencesProvider.get();
			helper.addPreferenceListener(PPPreferenceConstants.PUPPET_EXCLUDE_GLOBS, this);
		}

		Set<String> patterns = super.getExcludeGlobs();
		// Remove the defaults since user might have removed them
		patterns.removeAll(helper.getDefaultFolderFilters());

		// Add user specific entries
		patterns.addAll(helper.getExcludeGlobs());
		return patterns;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		reset();

		// Changes in folder structure must be followed by a clean build in order
		// to remove potentially redundant markers on resources that are no longer
		// considered part of the delta
		backgroundRebuildChecker.triggerClean();
	}
}
