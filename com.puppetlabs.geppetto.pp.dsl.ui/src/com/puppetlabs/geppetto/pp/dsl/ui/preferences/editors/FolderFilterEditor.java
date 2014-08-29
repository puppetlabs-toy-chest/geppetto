/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Puppet Labs - edit capability
 *******************************************************************************/
package com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Lists;

/**
 * A field editor to edit directory paths.
 */
public class FolderFilterEditor extends ListEditor {
	public static List<String> parseFolderFilter(String filter) {
		if(filter != null) {
			filter = filter.trim();
			if(filter.length() > 0) {
				StringTokenizer st = new StringTokenizer(filter, ":\n\r");
				ArrayList<String> v = Lists.newArrayList();
				while(st.hasMoreElements())
					v.add((String) st.nextElement());
				return v;
			}
		}
		return Collections.emptyList();
	}

	/**
	 * Creates a folder filter field editor.
	 *
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public FolderFilterEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected String createList(String[] items) {
		if(items.length == 0)
			return "";

		StringBuffer path = new StringBuffer(items[0]);
		for(int i = 1; i < items.length; i++) {
			path.append(':');
			path.append(items[i]);
		}
		return path.toString();
	}

	@Override
	protected String getEditedInput(String input) {
		return getInputObject("Edit Segment Match", input);
	}

	protected String getInputObject(String title, String input) {
		PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET);
		String[] value = new String[] { input };
		int[] okCancel = new int[] { 1 };

		dialog.prompt(title, "Folder path segment pattern (* = any string, ? = any character)", null, new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				switch(event.keyCode) {
					case SWT.BS:
					case SWT.DEL:
					case SWT.HOME:
					case SWT.END:
					case SWT.ARROW_LEFT:
					case SWT.ARROW_RIGHT:
						break;
					default: {
						String txt = event.text;
						if(txt == null)
							break;

						int len = txt.length();
						for(int i = 0; i < len; ++i) {
							char c = txt.charAt(i);
							if(c == '/' || c == '\\') {
								event.doit = false;
								break;
							}
						}
					}
				}
			}
		}, value, null, okCancel);
		if(okCancel[0] == 0)
			return null;
		return value[0].trim();
	}

	@Override
	protected String getNewInputObject() {
		return getInputObject("Add Segment Match", null);
	}

	@Override
	protected String[] parseString(String stringList) {
		List<String> v = parseFolderFilter(stringList);
		return v.toArray(new String[v.size()]);
	}
}
