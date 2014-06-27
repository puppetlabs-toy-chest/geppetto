package com.puppetlabs.geppetto.module.dsl.ui.quickfix

import com.puppetlabs.geppetto.forge.Forge
import org.eclipse.core.resources.IMarker
import org.eclipse.ui.IMarkerResolution
import org.eclipse.ui.IMarkerResolutionGenerator2

class ModuleProblemResolutionGenerator implements IMarkerResolutionGenerator2 {
	private static val IMarkerResolution[] NO_RESOLUTIONS = newArrayOfSize(0)
	
	override hasResolutions(IMarker marker) {
		Forge.MODULEFILE_NAME == marker.resource.name
	}

	override getResolutions(IMarker marker) {
		if(Forge.MODULEFILE_NAME == marker.resource.name)
			#[new RemoveModuleFileQuickFix]
		else
			NO_RESOLUTIONS
	}
}