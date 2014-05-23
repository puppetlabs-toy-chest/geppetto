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
package com.puppetlabs.geppetto.ui.dialog;

import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.model.AbbrevRelease;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.util.ModuleUtil;

public class ReleaseListSelectionDialog extends FilteredItemsSelectionDialog {

	public static class Elem implements Comparable<Elem> {
		public static Comparator<Elem> comparator = new Comparator<Elem>() {
			@Override
			public int compare(Elem o1, Elem o2) {
				return o1.compareTo(o2);
			}
		};

		private final VersionedName versionedName;

		private final boolean supported;

		public Elem(AbbrevRelease release) {
			this.versionedName = ModuleUtil.getVersionedName(release);
			this.supported = release.isSupported();
		}

		public Elem(VersionedName versionedName, boolean supported) {
			this.versionedName = versionedName;
			this.supported = supported;
		}

		@Override
		public int compareTo(Elem o) {
			// Supported first, then sort by name ASC, version DESC
			if(isSupported()) {
				if(!o.isSupported())
					return -1;
			}
			else {
				if(o.isSupported())
					return 1;
			}
			VersionedName vn1 = getVersionedName();
			VersionedName vn2 = o.getVersionedName();
			int cmp = vn1.getModuleName().compareTo(vn2.getModuleName());
			if(cmp == 0)
				cmp = vn2.getVersion().compareTo(vn1.getVersion());
			return cmp;
		}

		/**
		 * @return the versionedName
		 */
		public VersionedName getVersionedName() {
			return versionedName;
		}

		/**
		 * @return the supported
		 */
		public boolean isSupported() {
			return supported;
		}

		@Override
		public String toString() {
			StringBuilder bld = new StringBuilder();
			getVersionedName().toString(bld, ' ');
			if(isSupported())
				bld.append(" Supported");
			return bld.toString();
		}
	}

	public class ElemsFilter extends ItemsFilter {
		public ElemsFilter() {
			patternMatcher.setPattern(((Text) getPatternControl()).getText());
		}

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			return matches(item.toString());
		}
	}

	private static final String DIALOG_SETTINGS = "ReleaseListSelectionDialogSettings";

	private final List<Elem> elements;

	public ReleaseListSelectionDialog(Shell parent, List<Elem> elements) {
		super(parent);
		this.elements = elements;

		setInitialPattern("*");
		setMessage(UIPlugin.getLocalString("_UI_SelectModule")); //$NON-NLS-1$
		setTitle(UIPlugin.getLocalString("_UI_ModuleSelection_title")); //$NON-NLS-1$
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ElemsFilter();
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException {
		for(Elem elem : elements)
			contentProvider.add(elem, itemsFilter);
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = UIPlugin.getInstance().getPluginResourceLocator().getDialogSettings().getSection(
			DIALOG_SETTINGS);
		if(settings == null)
			settings = UIPlugin.getInstance().getPluginResourceLocator().getDialogSettings().addNewSection(
				DIALOG_SETTINGS);
		return settings;
	}

	@Override
	public String getElementName(Object item) {
		return item.toString();
	}

	@Override
	protected Comparator<Elem> getItemsComparator() {
		return Elem.comparator;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}
}
