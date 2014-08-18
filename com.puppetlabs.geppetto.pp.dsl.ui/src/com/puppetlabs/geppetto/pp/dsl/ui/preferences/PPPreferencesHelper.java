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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.ui.pptp.PptpTargetProjectHandler;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.FolderFilterEditor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

/**
 * A facade that helps with preference checking.
 * (The idea is to not litter the code with specifics about how preferences are stated, where they are stored etc.)
 *
 */
public class PPPreferencesHelper implements IPreferenceStoreInitializer, IPropertyChangeListener {
	public interface IPreferenceListener {
		void onChange(String key, String newValue);
	}

	private int autoInsertOverrides = 0;

	private final static String OVERRIDE_AUTO_INSERT = "com.puppetlabs.geppetto.override.autoinsert";

	public final static int AUTO_INSERT_BRACKETS = 0x01;

	public final static int AUTO_INSERT_BRACES = 0x02;

	public final static int AUTO_INSERT_PARENTHESES = 0x04;

	public final static int AUTO_INSERT_COMMENT = 0x08;

	public final static int AUTO_INSERT_SQ = 0x10;

	public final static int AUTO_INSERT_DQ = 0x20;

	private IPreferenceStore store;

	private static final String defaultProjectPath = "lib/*:environments/$environment/*:manifests/*:modules/*"; //$NON-NLS-1$

	private static final String defaultFolderFilter = "pkg:spec"; //$NON-NLS-1$

	private static final String defaultPuppetEnvironment = "production"; //$NON-NLS-1$

	private final PptpTargetProjectHandler pptpHandler;

	private final RebuildChecker backgroundRebuildChecker;

	private final Multimap<String, IPropertyChangeListener> listeners = ArrayListMultimap.create();

	/**
	 * IMPORTANT:
	 * Add all preference that requires a rebuild when their value change.
	 */
	private final List<String> requiresRebuild = Lists.newArrayList(//
		PPPreferenceConstants.PUPPET_TARGET_VERSION, //
		PPPreferenceConstants.PUPPET_ENVIRONMENT, //
		PPPreferenceConstants.PUPPET_PROJECT_PATH, //
		PPPreferenceConstants.PUPPET_FOLDER_FILTER, //
		PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, //
		PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, //
		PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, //
		PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST, //
		PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST, //

		PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE, //
		PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED, //
		PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR, //
		PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION, //
		PPPreferenceConstants.PROBLEM_ML_COMMENTS, //
		PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP, //
		PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_STRING, //
		PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_TRUSTED, //
		PPPreferenceConstants.PROBLEM_ENSURE_NOT_FIRST, //
		PPPreferenceConstants.PROBLEM_VALIDITY_ASSERTED_AT_RUNTIME, //
		PPPreferenceConstants.PROBLEM_IMPORT_IS_DEPRECATED //
	);

	private IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	public PPPreferencesHelper(PptpTargetProjectHandler pptpHandler, RebuildChecker backgroundRebuildChecker) {
		this.pptpHandler = pptpHandler;
		this.backgroundRebuildChecker = backgroundRebuildChecker;
		configureAutoInsertOverride();
	}

	public void addPreferenceListener(String key, IPropertyChangeListener listener) {
		listeners.put(key, listener);
	}

	private void configureAutoInsertOverride() {
		String propValue = System.getProperty(OVERRIDE_AUTO_INSERT, "0");

		try {
			autoInsertOverrides = Integer.parseInt(propValue);
		}
		catch(NumberFormatException e) {
			autoInsertOverrides = 0;
		}
	}

	public ValidationPreference getAssignmentToVariableNamedString() {
		return getPreference(PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_STRING);
	}

	public ValidationPreference getAssignmentToVariableNamedTrusted() {
		return getPreference(PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_TRUSTED);
	}

	public ValidationPreference getBooleansInStringForm() {
		return getPreference(PPPreferenceConstants.PROBLEM_BOOLEAN_STRING);
	}

	public ValidationPreference getCaseDefaultShouldAppearLast() {
		return getPreference(PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST);
	}

	public List<String> getDefaultFolderFilters() {
		return FolderFilterEditor.parseFolderFilter(defaultFolderFilter);
	}

	public ValidationPreference getDqStringNotRequired() {
		return getPreference(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED);
	}

	public ValidationPreference getDqStringNotRequiredVar() {
		return getPreference(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR);
	}

	public ValidationPreference getEnsureShouldAppearFirst() {
		return getPreference(PPPreferenceConstants.PROBLEM_ENSURE_NOT_FIRST);
	}

	public List<String> getFolderFilters() {
		return FolderFilterEditor.parseFolderFilter(store.getString(PPPreferenceConstants.PUPPET_FOLDER_FILTER));
	}

	public ValidationPreference getImportIsDeprecated() {
		return getPreference(PPPreferenceConstants.PROBLEM_IMPORT_IS_DEPRECATED);
	}

	public ValidationPreference getInterpolatedNonBraceEnclosedHypens() {
		return getPreference(PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN);
	}

	public ValidationPreference getMissingDefaultInSwitch() {
		return getPreference(PPPreferenceConstants.PROBLEM_MISSING_DEFAULT);
	}

	public ValidationPreference getMLCommentsValidationPreference() {
		return getPreference(PPPreferenceConstants.PROBLEM_ML_COMMENTS);
	}

	private ValidationPreference getPreference(String prefId) {
		return ValidationPreference.fromString(store.getString(prefId));
	}

	public PuppetTarget getPuppetTarget() {
		String targetLiteral = store.getString(PPPreferenceConstants.PUPPET_TARGET_VERSION);

		// there never was a 2.8, but older preferences settings may still have this string, use 3.0 instead
		if("2.8".equals(targetLiteral))
			targetLiteral = "3.0";

		try {
			return PuppetTarget.forLiteral(targetLiteral);
		}
		catch(IllegalArgumentException e) {
			return PuppetTarget.getDefault();
		}
	}

	private boolean getResourceSpecificBoolean(IResource r, String property) {
		// get project specific preference and use them if they are enabled
		IPreferenceStore store = preferenceStoreAccess.getContextPreferenceStore(r.getProject());
		return store.getBoolean(property);

	}

	public ValidationPreference getRightToLeftRelationships() {
		return getPreference(PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP);
	}

	public boolean getSaveActionEnsureEndsWithNewLine() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL);
	}

	public boolean getSaveActionEnsureEndsWithNewLine(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL)
				: getSaveActionEnsureEndsWithNewLine();
	}

	public boolean getSaveActionFormat() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_FORMAT);
	}

	public boolean getSaveActionFormat(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_FORMAT)
				: getSaveActionFormat();
	}

	public boolean getSaveActionReplaceFunkySpaces() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES);
	}

	public boolean getSaveActionReplaceFunkySpaces(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES)
				: getSaveActionReplaceFunkySpaces();
	}

	public boolean getSaveActionTrimLines() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_TRIM_LINES);
	}

	public boolean getSaveActionTrimLines(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_TRIM_LINES)
				: getSaveActionTrimLines();
	}

	public ValidationPreference getSelectorDefaultShouldAppearLast() {
		return getPreference(PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST);
	}

	public ValidationPreference getUnbracedInterpolation() {
		return getPreference(PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION);
	}

	public ValidationPreference getUnquotedResourceTitles() {
		return getPreference(PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE);
	}

	synchronized public IValidationAdvisor.ComplianceLevel getValidationComplianceLevel() {
		return getPuppetTarget().getComplianceLevel();
	}

	public ValidationPreference getValidityAssertedAtRuntime() {
		return getPreference(PPPreferenceConstants.PROBLEM_VALIDITY_ASSERTED_AT_RUNTIME);
	}

	@Override
	synchronized public void initialize(IPreferenceStoreAccess access) {
		// if already initialized
		if(store != null)
			return;
		preferenceStoreAccess = access;
		store = preferenceStoreAccess.getWritablePreferenceStore();
		store.setDefault(PPPreferenceConstants.AUTO_EDIT_STRATEGY, 0);
		store.setDefault(PPPreferenceConstants.AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS, true);
		store.setDefault(PPPreferenceConstants.PUPPET_TARGET_VERSION, PuppetTarget.getDefault().getLiteral());
		store.setDefault(PPPreferenceConstants.PUPPET_PROJECT_PATH, defaultProjectPath);
		store.setDefault(PPPreferenceConstants.PUPPET_FOLDER_FILTER, defaultFolderFilter);
		store.setDefault(PPPreferenceConstants.PUPPET_ENVIRONMENT, defaultPuppetEnvironment);

		store.setDefault(PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, ValidationPreference.WARNING.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, ValidationPreference.WARNING.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, ValidationPreference.WARNING.toString());
		store.setDefault(
			PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_STRING, ValidationPreference.WARNING.toString());
		store.setDefault(
			PPPreferenceConstants.PROBLEM_ASSIGNMENT_TO_VAR_NAMED_TRUSTED, ValidationPreference.WARNING.toString());

		// stylistic
		store.setDefault(PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST, ValidationPreference.IGNORE.toString());

		store.setDefault(PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED, ValidationPreference.IGNORE.toString());
		store.setDefault(
			PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_ML_COMMENTS, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_ENSURE_NOT_FIRST, ValidationPreference.IGNORE.toString());
		store.setDefault(
			PPPreferenceConstants.PROBLEM_VALIDITY_ASSERTED_AT_RUNTIME, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_IMPORT_IS_DEPRECATED, ValidationPreference.WARNING.toString());

		// save actions
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_TRIM_LINES, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_FORMAT, false);

		autoInsertOverrides = (int) store.getLong(PPPreferenceConstants.AUTO_EDIT_STRATEGY);

		// Schedule the background job that makes rebuild after property changes more efficient
		// (Removes the need to run one rebuild per changing preference).
		store.addPropertyChangeListener(this);

	}

	public boolean isAutoBraceInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_BRACES) == 0;
	}

	public boolean isAutoBracketInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_BRACKETS) == 0;
	}

	public boolean isAutoCompleteBlockWanted() {
		return store.getBoolean(PPPreferenceConstants.AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS);
	}

	public boolean isAutoDqStringInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_DQ) == 0;
	}

	public boolean isAutoMLCommentInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_COMMENT) == 0;
	}

	public boolean isAutoParenthesisInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_PARENTHESES) == 0;
	}

	public boolean isAutoSqStringInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_SQ) == 0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(PPPreferenceConstants.AUTO_EDIT_STRATEGY.equals(event.getProperty()))
			autoInsertOverrides = Integer.valueOf((String) event.getNewValue());

		for(IPropertyChangeListener listener : listeners.get(event.getProperty()))
			listener.propertyChange(event);

		// If pptp changes, recheck the workspace
		if(PPPreferenceConstants.PUPPET_TARGET_VERSION.equals(event.getProperty()))
			pptpHandler.initializePuppetTargetProject();

		if(requiresRebuild.contains(event.getProperty()))
			backgroundRebuildChecker.triggerBuild();
	}

	/**
	 * Stops the helper from checking for preference store changes and scheduling rebuilds.
	 */
	public void stop() {
		store.removePropertyChangeListener(this);
		if(backgroundRebuildChecker == null)
			return;
		if(!backgroundRebuildChecker.cancel()) {
			// if not in cancelable state, poke it harder
			Thread t = backgroundRebuildChecker.getThread();
			t.interrupt();
			try {
				// must wait for *job* to die
				backgroundRebuildChecker.join();
			}
			catch(InterruptedException e) {
				// ok ok, I was interrupted when stopping... no need to make it worse...
				System.err.println("Interrupted");
			}
		}
	}

}
