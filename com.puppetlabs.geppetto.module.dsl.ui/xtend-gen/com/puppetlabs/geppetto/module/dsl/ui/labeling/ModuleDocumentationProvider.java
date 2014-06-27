package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.puppetlabs.geppetto.module.dsl.metadata.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;

@SuppressWarnings("all")
public class ModuleDocumentationProvider implements IEObjectDocumentationProvider {
  public String getDocumentation(final EObject o) {
    String _xifexpression = null;
    if ((o instanceof Pair)) {
      Class<? extends Pair> _class = ((Pair)o).getClass();
      String _name = _class.getName();
      _xifexpression = ("Hello pair of class " + _name);
    }
    return _xifexpression;
  }
}
