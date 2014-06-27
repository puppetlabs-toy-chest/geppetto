package com.puppetlabs.geppetto.module.dsl.scoping;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ModuleQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
  @Inject
  @Extension
  private ModuleUtil _moduleUtil;
  
  public QualifiedName getFullyQualifiedName(final EObject obj) {
    QualifiedName _xblockexpression = null;
    {
      if ((obj instanceof JsonMetadata)) {
        ModuleName moduleName = this._moduleUtil.getName(((JsonMetadata)obj));
        boolean _notEquals = (!Objects.equal(moduleName, null));
        if (_notEquals) {
          String _owner = moduleName.getOwner();
          String _name = moduleName.getName();
          return QualifiedName.create(_owner, _name);
        }
      }
      _xblockexpression = null;
    }
    return _xblockexpression;
  }
}
