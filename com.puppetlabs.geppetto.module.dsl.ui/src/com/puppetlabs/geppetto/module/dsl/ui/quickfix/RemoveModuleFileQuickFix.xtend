package com.puppetlabs.geppetto.module.dsl.ui.quickfix

import org.eclipse.ui.IMarkerResolution
import org.eclipse.core.resources.IMarker
import org.eclipse.core.runtime.NullProgressMonitor

class RemoveModuleFileQuickFix implements IMarkerResolution {
	
	override getLabel() {
		'Remove deprecated Modulefile'
	}
	
	override run(IMarker marker) {
		marker.resource.delete(true, new NullProgressMonitor)
	}
}