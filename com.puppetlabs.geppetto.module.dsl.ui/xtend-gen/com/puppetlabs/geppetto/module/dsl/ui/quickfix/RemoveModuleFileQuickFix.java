package com.puppetlabs.geppetto.module.dsl.ui.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class RemoveModuleFileQuickFix implements IMarkerResolution {
  public String getLabel() {
    return "Remove deprecated Modulefile";
  }
  
  public void run(final IMarker marker) {
    try {
      IResource _resource = marker.getResource();
      NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
      _resource.delete(true, _nullProgressMonitor);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
