package com.puppetlabs.geppetto.module.dsl.ui.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IMarkerResolution;

public class RemoveModuleFileQuickFix implements IMarkerResolution {
	@Override
	public String getLabel() {
		return "Remove deprecated Modulefile";
	}

	@Override
	public void run(IMarker marker) {
		try {
			marker.getResource().delete(true, new NullProgressMonitor());
		}
		catch(CoreException e) {
		}
	}
}
