/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Lists;

/**
 * A field editor to edit glob patterns.
 */
public class GlobPatternFieldEditor extends ListEditor {
	private static String mangleGlob(String glob) {
		if(glob != null) {
			glob = glob.trim();
			while(glob.startsWith("/"))
				glob = glob.substring(1).trim();
			if(glob.isEmpty())
				glob = null;
		}
		return glob;
	}

	public static List<String> parseFolderFilter(String filter) {
		if(filter != null) {
			filter = filter.trim();
			if(filter.length() > 0) {
				StringTokenizer st = new StringTokenizer(filter, ":\n\r");
				ArrayList<String> v = Lists.newArrayList();
				while(st.hasMoreElements())
					v.add(((String) st.nextElement()).trim());
				return v;
			}
		}
		return Collections.emptyList();
	}

	/**
	 * Creates a glob pattern field editor.
	 *
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public GlobPatternFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected String createList(String[] items) {
		if(items.length == 0)
			return "";

		StringBuffer path = new StringBuffer();
		for(int i = 0; i < items.length; i++) {
			String glob = mangleGlob(items[i]);
			if(glob == null)
				continue;
			if(path.length() > 0)
				path.append(':');
			path.append(glob);
		}
		return path.toString();
	}

	@Override
	protected String getEditedInput(String input) {
		return getInputObject("Edit Glob Pattern", input);
	}

	protected String getInputObject(String title, String input) {
		PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET);
		String[] value = new String[] { input };
		int[] okCancel = new int[] { 1 };

		dialog.prompt(title, "Glob pattern (* = any string, ? = any character, ** = any path)", null, value, null, okCancel);
		if(okCancel[0] == 0)
			return null;
		return value[0].trim();
	}

	@Override
	protected String getNewInputObject() {
		return getInputObject("Add Glob Pattern", null);
	}

	@Override
	protected String[] parseString(String stringList) {
		List<String> v = parseFolderFilter(stringList);
		return v.toArray(new String[v.size()]);
	}
}
