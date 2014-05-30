package com.puppetlabs.geppetto.module.dsl.scoping;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Qualified names conforming to the puppet module name syntax. Accepts both
 * '-' and '/' as separators
 */
@SuppressWarnings("all")
public class ModuleQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
  public String getDelimiter() {
    return "-";
  }
  
  public QualifiedName toQualifiedName(final String qualifiedNameAsString) {
    QualifiedName _xblockexpression = null;
    {
      try {
        final ModuleName mn = ModuleName.create(qualifiedNameAsString, false);
        boolean _tripleNotEquals = (mn != null);
        if (_tripleNotEquals) {
          String _owner = mn.getOwner();
          String _name = mn.getName();
          return QualifiedName.create(_owner, _name);
        }
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException e = (IllegalArgumentException)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = null;
    }
    return _xblockexpression;
  }
}
