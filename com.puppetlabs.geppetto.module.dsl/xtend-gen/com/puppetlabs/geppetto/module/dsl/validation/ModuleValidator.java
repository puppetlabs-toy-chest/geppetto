package com.puppetlabs.geppetto.module.dsl.validation;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonOS;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonTag;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage;
import com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue;
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair;
import com.puppetlabs.geppetto.module.dsl.metadata.Value;
import com.puppetlabs.geppetto.module.dsl.validation.AbstractModuleValidator;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleDiagnostics;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@Singleton
@SuppressWarnings("all")
public class ModuleValidator extends AbstractModuleValidator implements ModuleDiagnostics {
  @Inject
  @Extension
  private ModuleUtil _moduleUtil;
  
  @Inject
  private IModuleValidationAdvisor validationAdvisor;
  
  private String missingAttributeMessage(final String key) {
    return (("Missing required attribute \"" + key) + "\"");
  }
  
  private String emptyAttributeMessage(final String key) {
    return (("Attribute \"" + key) + "\" cannot be empty");
  }
  
  private void missingAttribute(final JsonObject obj, final String key, final ValidationPreference pref) {
    String _missingAttributeMessage = this.missingAttributeMessage(key);
    this.warningOrError(pref, obj, MetadataPackage.Literals.JSON_OBJECT__PAIRS, ModuleDiagnostics.ISSUE__MISSING_REQUIRED_ATTRIBUTE, _missingAttributeMessage, key);
  }
  
  private void emptyAttributeError(final JsonValue value, final String key, final ValidationPreference pref) {
    String _emptyAttributeMessage = this.emptyAttributeMessage(key);
    this.warningOrError(pref, value, MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__EMPTY_ATTRIBUTE, _emptyAttributeMessage, key);
  }
  
  private void checkRequiredFields(final JsonObject obj, final ValidationPreference pref, final String... requiredFields) {
    boolean _tripleNotEquals = (pref != ValidationPreference.IGNORE);
    if (_tripleNotEquals) {
      final Map<String, Value> attrs = this._moduleUtil.getAttributes(obj);
      for (final String key : requiredFields) {
        {
          final Value a = attrs.get(key);
          boolean _tripleEquals = (a == null);
          if (_tripleEquals) {
            this.missingAttribute(obj, key, pref);
          } else {
            if ((a instanceof JsonValue)) {
              final Object v = ((JsonValue)a).getValue();
              boolean _or = false;
              boolean _tripleEquals_1 = (v == null);
              if (_tripleEquals_1) {
                _or = true;
              } else {
                boolean _and = false;
                if (!(v instanceof String)) {
                  _and = false;
                } else {
                  boolean _isEmpty = ((String) v).isEmpty();
                  _and = _isEmpty;
                }
                _or = _and;
              }
              if (_or) {
                this.emptyAttributeError(((JsonValue)a), key, pref);
              }
            }
          }
        }
      }
    }
  }
  
  @Check
  public void checkMetadata(final JsonMetadata metadata) {
    this.checkRequiredFields(metadata, ValidationPreference.ERROR, "name", "version");
    ValidationPreference _missingForgeRequiredFields = this.validationAdvisor.getMissingForgeRequiredFields();
    this.checkRequiredFields(metadata, _missingForgeRequiredFields, "author", "license", "source", 
      "summary");
  }
  
  @Check
  public void checkRequirement(final JsonRequirement requirement) {
    this.checkRequiredFields(requirement, ValidationPreference.ERROR, "name", "version_requirement");
  }
  
  @Check
  public void checkOS(final JsonOS os) {
    this.checkRequiredFields(os, ValidationPreference.ERROR, "operatingsystem");
  }
  
  @Check
  public void checkDependency(final JsonDependency dependency) {
    final JsonMetadata ref = this._moduleUtil.getReferencedModule(dependency);
    final VersionRange range = this._moduleUtil.getRange(dependency);
    boolean _tripleEquals = (ref == null);
    if (_tripleEquals) {
      this.missingAttribute(dependency, "name", ValidationPreference.ERROR);
    }
    boolean _tripleEquals_1 = (range == null);
    if (_tripleEquals_1) {
      String _missingAttributeMessage = this.missingAttributeMessage("version_requirement");
      String _plus = (_missingAttributeMessage + ". All versions will be considered a match");
      this.warning(_plus, dependency, MetadataPackage.Literals.JSON_OBJECT__PAIRS, ModuleDiagnostics.ISSUE__MISSING_REQUIRED_ATTRIBUTE);
    } else {
      boolean _and = false;
      boolean _isResolved = this._moduleUtil.isResolved(ref);
      if (!_isResolved) {
        _and = false;
      } else {
        ValidationPreference _circularDependency = this.validationAdvisor.getCircularDependency();
        boolean _tripleNotEquals = (_circularDependency != ValidationPreference.IGNORE);
        _and = _tripleNotEquals;
      }
      if (_and) {
        HashSet<ModuleName> _newHashSet = CollectionLiterals.<ModuleName>newHashSet();
        JsonMetadata _ownerMetadata = this._moduleUtil.getOwnerMetadata(dependency);
        ModuleName _name = this._moduleUtil.getName(_ownerMetadata);
        LinkedList<ModuleName> _newLinkedList = CollectionLiterals.<ModuleName>newLinkedList(_name);
        this.checkCircularDependencies(ref, dependency, _newHashSet, _newLinkedList);
      }
    }
  }
  
  @Check
  public void checkRequirementName(final RequirementNameValue rqNameValue) {
    Object _value = rqNameValue.getValue();
    final String name = ((String) _value);
    boolean _or = false;
    boolean _equals = "puppet".equals(name);
    if (_equals) {
      _or = true;
    } else {
      boolean _equals_1 = "pe".equals(name);
      _or = _equals_1;
    }
    boolean _not = (!_or);
    if (_not) {
      this.warning((("\"" + name) + "\" is not a known name for a requirement"), rqNameValue, MetadataPackage.Literals.JSON_VALUE__VALUE, 
        ModuleDiagnostics.ISSUE__UNKNOWN_REQUIREMENT_NAME);
    }
  }
  
  @Check
  public void checkVersion(final JsonVersion versionValue) {
    try {
      Object _value = versionValue.getValue();
      Version _create = Version.create(((String) _value));
      boolean _tripleEquals = (_create == null);
      if (_tripleEquals) {
        this.emptyAttributeError(versionValue, "version", ValidationPreference.ERROR);
      }
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        String _message = e.getMessage();
        this.error(_message, versionValue, MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__INVALID_VERSION);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Check
  public void checkVersionRange(final JsonVersionRange versionRangeValue) {
    try {
      Object _value = versionRangeValue.getValue();
      final VersionRange range = VersionRange.create(((String) _value));
      boolean _tripleEquals = (range == null);
      if (_tripleEquals) {
        this.emptyAttributeError(versionRangeValue, "version_requirement", ValidationPreference.ERROR);
      } else {
        EObject _eContainer = versionRangeValue.eContainer();
        final EObject obj = _eContainer.eContainer();
        if ((obj instanceof JsonDependency)) {
          ValidationPreference _dependencyVersionMismatch = this.validationAdvisor.getDependencyVersionMismatch();
          boolean _tripleNotEquals = (_dependencyVersionMismatch != ValidationPreference.IGNORE);
          if (_tripleNotEquals) {
            try {
              final JsonMetadata ref = this._moduleUtil.getReferencedModule(((JsonDependency)obj));
              boolean _isResolved = this._moduleUtil.isResolved(ref);
              if (_isResolved) {
                final Version ver = this._moduleUtil.getVersion(ref);
                boolean _and = false;
                boolean _tripleNotEquals_1 = (ver != null);
                if (!_tripleNotEquals_1) {
                  _and = false;
                } else {
                  boolean _isIncluded = range.isIncluded(ver);
                  boolean _not = (!_isIncluded);
                  _and = _not;
                }
                if (_and) {
                  ValidationPreference _dependencyVersionMismatch_1 = this.validationAdvisor.getDependencyVersionMismatch();
                  String _format = String.format("Version requirement \"%s\" does not match version \"%s\"", range, ver);
                  this.warningOrError(_dependencyVersionMismatch_1, versionRangeValue, 
                    MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__MODULE_VERSION_RANGE_MISMATCH, _format);
                }
              }
            } catch (final Throwable _t) {
              if (_t instanceof IllegalArgumentException) {
                final IllegalArgumentException e = (IllegalArgumentException)_t;
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
        }
      }
    } catch (final Throwable _t_1) {
      if (_t_1 instanceof IllegalArgumentException) {
        final IllegalArgumentException e_1 = (IllegalArgumentException)_t_1;
        String _message = e_1.getMessage();
        this.error(_message, versionRangeValue, MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__INVALID_VERSION_RANGE);
      } else {
        throw Exceptions.sneakyThrow(_t_1);
      }
    }
  }
  
  @Check
  public void checkModuleName(final JsonModuleName moduleNameValue) {
    try {
      Object _value = moduleNameValue.getValue();
      ModuleName _create = ModuleName.create(((String) _value), true);
      this.checkBothNames(_create);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException w = (IllegalArgumentException)_t;
        ValidationPreference _moduleNameNotStrict = this.validationAdvisor.getModuleNameNotStrict();
        boolean _tripleEquals = (_moduleNameNotStrict == ValidationPreference.ERROR);
        if (_tripleEquals) {
          String _message = w.getMessage();
          this.error(_message, moduleNameValue, MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__INVALID_MODULE_NAME);
        } else {
          try {
            Object _value_1 = moduleNameValue.getValue();
            ModuleName _create_1 = ModuleName.create(((String) _value_1), false);
            this.checkBothNames(_create_1);
            ValidationPreference _moduleNameNotStrict_1 = this.validationAdvisor.getModuleNameNotStrict();
            boolean _tripleEquals_1 = (_moduleNameNotStrict_1 == ValidationPreference.WARNING);
            if (_tripleEquals_1) {
              String _message_1 = w.getMessage();
              this.warning(_message_1, moduleNameValue, MetadataPackage.Literals.JSON_VALUE__VALUE, 
                ModuleDiagnostics.ISSUE__INVALID_MODULE_NAME);
            }
          } catch (final Throwable _t_1) {
            if (_t_1 instanceof IllegalArgumentException) {
              final IllegalArgumentException e = (IllegalArgumentException)_t_1;
              String _message_2 = e.getMessage();
              this.error(_message_2, moduleNameValue, MetadataPackage.Literals.JSON_VALUE__VALUE, ModuleDiagnostics.ISSUE__INVALID_MODULE_NAME);
            } else {
              throw Exceptions.sneakyThrow(_t_1);
            }
          }
        }
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Check
  public void checkTag(final JsonTag value) {
    Object _value = value.getValue();
    final String tag = ((String) _value);
    final int len = tag.length();
    if ((len == 0)) {
      this.emptyAttributeError(value, "tag", ValidationPreference.ERROR);
    } else {
      for (int i = 0; (i < len); i++) {
        char _charAt = tag.charAt(i);
        boolean _isWhitespace = Character.isWhitespace(_charAt);
        if (_isWhitespace) {
          this.warning("A tag cannot contain whitespace", value, MetadataPackage.Literals.JSON_VALUE__VALUE, 
            ModuleDiagnostics.ISSUE__INVALID_TAG);
          i = len;
        }
      }
    }
  }
  
  @Check
  public void checkUnrecognizedPair(final UnrecognizedPair pair) {
    EObject _eContainer = pair.eContainer();
    final JsonObject owner = ((JsonObject) _eContainer);
    final String key = pair.getName();
    boolean _isDeprecatedKey = this._moduleUtil.isDeprecatedKey(owner, key);
    if (_isDeprecatedKey) {
      ValidationPreference _deprecatedKey = this.validationAdvisor.getDeprecatedKey();
      String _format = String.format("\'%s\' is a deprecated metadata key", key);
      this.warningOrError(_deprecatedKey, pair, MetadataPackage.Literals.PAIR__NAME, ModuleDiagnostics.ISSUE__DEPRECATED_KEY, _format);
    } else {
      ValidationPreference _unrecognizedKey = this.validationAdvisor.getUnrecognizedKey();
      String _format_1 = String.format("\'%s\' is not recognized as a valid metadata key", key);
      this.warningOrError(_unrecognizedKey, pair, MetadataPackage.Literals.PAIR__NAME, ModuleDiagnostics.ISSUE__UNRECOGNIZED_KEY, _format_1);
    }
  }
  
  private void checkBothNames(final ModuleName moduleName) {
    boolean _or = false;
    String _owner = moduleName.getOwner();
    boolean _isEmpty = _owner.isEmpty();
    if (_isEmpty) {
      _or = true;
    } else {
      String _name = moduleName.getName();
      boolean _isEmpty_1 = _name.isEmpty();
      _or = _isEmpty_1;
    }
    if (_or) {
      throw new ModuleName.BadNameSyntaxException();
    }
  }
  
  private void checkCircularDependencies(final JsonMetadata ref, final JsonDependency origin, final Set<ModuleName> visited, final LinkedList<ModuleName> chain) {
    final ModuleName refName = this._moduleUtil.getName(ref);
    ModuleName _first = chain.getFirst();
    boolean _equals = Objects.equal(_first, refName);
    if (_equals) {
      this.circularDependencyError(origin, chain);
    } else {
      boolean _add = visited.add(refName);
      if (_add) {
        chain.addLast(refName);
        List<JsonMetadata> _resolvedDependencies = this._moduleUtil.getResolvedDependencies(ref);
        for (final JsonMetadata refMd : _resolvedDependencies) {
          this.checkCircularDependencies(refMd, origin, visited, chain);
        }
        chain.removeLast();
      }
    }
  }
  
  private void circularDependencyError(final JsonDependency dependency, final LinkedList<ModuleName> chain) {
    final StringBuilder buf = new StringBuilder("Circular dependency: [");
    for (final ModuleName name : chain) {
      {
        name.toString(buf);
        buf.append(" -> ");
      }
    }
    ModuleName _first = chain.getFirst();
    _first.toString(buf);
    buf.append("]");
    ValidationPreference _circularDependency = this.validationAdvisor.getCircularDependency();
    String _string = buf.toString();
    this.warningOrError(_circularDependency, dependency, MetadataPackage.Literals.JSON_OBJECT__PAIRS, 
      ModuleDiagnostics.ISSUE__CIRCULAR_DEPENDENCY, _string);
  }
  
  private void warningOrError(final ValidationPreference pref, final EObject element, final EStructuralFeature feature, final String issue, final String msg, final String... issueData) {
    boolean _tripleNotEquals = (pref != ValidationPreference.IGNORE);
    if (_tripleNotEquals) {
      boolean _tripleEquals = (pref == ValidationPreference.WARNING);
      if (_tripleEquals) {
        this.warning(msg, element, feature, issue, issueData);
      } else {
        this.error(msg, element, feature, issue, issueData);
      }
    }
  }
}
