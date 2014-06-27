package com.puppetlabs.geppetto.module.dsl.ui.quickfix;

import com.google.common.base.Objects;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.module.dsl.ui.quickfix.RemoveModuleFileQuickFix;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

@SuppressWarnings("all")
public class ModuleProblemResolutionGenerator implements IMarkerResolutionGenerator2 {
  private final static IMarkerResolution[] NO_RESOLUTIONS = new IMarkerResolution[0];
  
  public boolean hasResolutions(final IMarker marker) {
    IResource _resource = marker.getResource();
    String _name = _resource.getName();
    return Objects.equal(Forge.MODULEFILE_NAME, _name);
  }
  
  public IMarkerResolution[] getResolutions(final IMarker marker) {
    IMarkerResolution[] _xifexpression = null;
    IResource _resource = marker.getResource();
    String _name = _resource.getName();
    boolean _equals = Objects.equal(Forge.MODULEFILE_NAME, _name);
    if (_equals) {
      RemoveModuleFileQuickFix _removeModuleFileQuickFix = new RemoveModuleFileQuickFix();
      _xifexpression = new IMarkerResolution[] { _removeModuleFileQuickFix };
    } else {
      _xifexpression = ModuleProblemResolutionGenerator.NO_RESOLUTIONS;
    }
    return _xifexpression;
  }
}
