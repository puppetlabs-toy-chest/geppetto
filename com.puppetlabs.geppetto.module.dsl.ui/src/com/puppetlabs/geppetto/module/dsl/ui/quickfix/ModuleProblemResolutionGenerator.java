package com.puppetlabs.geppetto.module.dsl.ui.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

import com.puppetlabs.geppetto.forge.Forge;

public class ModuleProblemResolutionGenerator implements IMarkerResolutionGenerator2 {
	private final static IMarkerResolution[] NO_RESOLUTIONS = new IMarkerResolution[0];

	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {
		return Forge.MODULEFILE_NAME.equals(marker.getResource().getName())
				? new IMarkerResolution[] { new RemoveModuleFileQuickFix() }
		: ModuleProblemResolutionGenerator.NO_RESOLUTIONS;
	}

	@Override
	public boolean hasResolutions(final IMarker marker) {
		return Forge.MODULEFILE_NAME.equals(marker.getResource().getName());
	}
}
