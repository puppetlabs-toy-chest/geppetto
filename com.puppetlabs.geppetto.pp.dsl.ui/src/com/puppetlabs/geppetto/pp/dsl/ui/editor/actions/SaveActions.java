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
package com.puppetlabs.geppetto.pp.dsl.ui.editor.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.reconciler.ReplaceRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.ui.linked.ISaveActions;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.validation.PPValidationUtils;
import com.puppetlabs.xtext.ui.editor.formatting.DummyReadOnly;

/**
 * Implementation of save actions
 *
 */
public class SaveActions implements ISaveActions {

	// case '\u00A0': // NBSP
	// case '\u1680': // OGHAM SPACE MARK");
	// case '\u2000': // EN QUAD");
	// case '\u2001': // EM QUAD");
	// case '\u2002': // EN SPACE");
	// case '\u2003': // EM SPACE");
	// case '\u2004': // THREE-PER-EM SPACE");
	// case '\u2005': // FOUR-PER-EM SPACE");
	// case '\u2006': // SIX-PER-EM SPACE");
	// case '\u2007': // FIGURE SPACE");
	// case '\u2008': // PUNCTUATION SPACE");
	// case '\u2009': // THIN SPACE");
	// case '\u200A': // HAIR SPACE");
	// case '\u200B': // ZERO WIDTH SPACE");
	// case '\u202F': // NARROW NO-BREAK SPACE");
	// case '\u3000': // IDEOGRAPHIC SPACE");

	public static String funkySpaces = "\\u00A0\\u1680\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u200B\\u202F\\u3000";

	private static Pattern trimPattern = Pattern.compile("[ \\t\\f\\x0B" + funkySpaces + "]+(\\r\\n|\\n)");

	private static Pattern funkySpacePattern = Pattern.compile("[\\f\\x0B" + funkySpaces + "]");

	@Inject
	private PPPreferencesHelper preferenceHelper;

	@Inject
	private IContentFormatterFactory contentFormatterFactory;

	@Override
	public void perform(SourceViewerConfiguration viewerConfig, ISourceViewer viewer, IResource r,
			final IXtextDocument document) {
		final boolean ensureNl = preferenceHelper.getSaveActionEnsureEndsWithNewLine(r);
		final boolean replaceFunkySpace = preferenceHelper.getSaveActionReplaceFunkySpaces(r);
		final boolean fullFormat = preferenceHelper.getSaveActionFormat(r);
		final boolean trimLines = preferenceHelper.getSaveActionTrimLines(r);

		if(!(ensureNl || replaceFunkySpace || trimLines || fullFormat))
			return;

		// Xtext issue, a dummy read only is needed before all modify operations.
		document.readOnly(DummyReadOnly.Instance);

		if(ensureNl || replaceFunkySpace || trimLines)
			document.modify(new IUnitOfWork<ReplaceRegion, XtextResource>() {
				@Override
				public ReplaceRegion exec(XtextResource state) throws Exception {
					// No action if there are hard syntax errors
					if(!PPValidationUtils.hasSyntaxErrors(state) && process(state))
						return new ReplaceRegion(0, document.getLength(), document.get());
					return null;
				}

				private boolean process(XtextResource state) throws Exception {
					// Do any semantic changes here
					boolean changed = false;
					String content = document.get();
					if(ensureNl)
						if(!content.endsWith("\n")) {
							content = content + "\n";
							try {
								document.replace(content.length() - 1, 0, "\n");
								content = document.get();
								changed = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					if(trimLines) {
						Matcher matcher = trimPattern.matcher(content);
						boolean mustRefetch = false;

						int lengthAdjustment = 0;
						while(matcher.find()) {
							int offset = matcher.start();
							int length = matcher.end() - offset;
							try {
								String replacement = matcher.group(1);
								document.replace(offset - lengthAdjustment, length, replacement);
								lengthAdjustment += (length - replacement.length());
								mustRefetch = true;
								changed = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
						if(mustRefetch)
							content = document.get();
					}
					if(replaceFunkySpace) {
						Matcher matcher = funkySpacePattern.matcher(content);
						int lengthAdjustment = 0;
						while(matcher.find()) {
							int offset = matcher.start();
							int length = matcher.end() - offset;
							try {
								document.replace(offset - lengthAdjustment, length, " ");
								lengthAdjustment += length - 1;
								changed = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					}
					return changed; // no change
				}
			});

		if(fullFormat)
			// Needs to be executed in a separate modify operation since it uses the state rather than
			// the document itself. The state is modified by the document.modify operation.
			contentFormatterFactory.createConfiguredFormatter(viewerConfig, viewer).format(
				document, new Region(0, document.getLength()));
	}
}
