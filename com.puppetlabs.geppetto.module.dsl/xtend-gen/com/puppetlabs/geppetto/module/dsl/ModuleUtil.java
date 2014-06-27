package com.puppetlabs.geppetto.module.dsl;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.Requirement;
import com.puppetlabs.geppetto.forge.model.SupportedOS;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonArray;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonOS;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair;
import com.puppetlabs.geppetto.module.dsl.metadata.Pair;
import com.puppetlabs.geppetto.module.dsl.metadata.Value;
import com.puppetlabs.geppetto.module.dsl.model.MetadataUtil;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ModuleUtil {
  @Inject
  private LazyURIEncoder lazyURIEncoder;
  
  public Map<String, Value> getAttributes(final JsonObject object) {
    Map<String, Value> _xifexpression = null;
    boolean _tripleEquals = (object == null);
    if (_tripleEquals) {
      _xifexpression = CollectionLiterals.<String, Value>emptyMap();
    } else {
      HashMap<String, Value> _xblockexpression = null;
      {
        final HashMap<String, Value> attrs = new HashMap<String, Value>();
        EList<Pair> _pairs = object.getPairs();
        for (final Pair pair : _pairs) {
          String _name = pair.getName();
          Value _value = pair.getValue();
          attrs.put(_name, _value);
        }
        _xblockexpression = attrs;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  public String getString(final Value value) {
    if ((value instanceof JsonValue)) {
      Object v = ((JsonValue)value).getValue();
      if ((v instanceof String)) {
        return ((String)v);
      }
    }
    return null;
  }
  
  public String getString(final JsonObject object, final String name) {
    Value _value = this.getValue(object, name);
    return this.getString(_value);
  }
  
  public Value getValue(final JsonObject object, final String name) {
    boolean _tripleNotEquals = (object != null);
    if (_tripleNotEquals) {
      EList<Pair> _pairs = object.getPairs();
      for (final Pair pair : _pairs) {
        String _name = pair.getName();
        boolean _equals = Objects.equal(name, _name);
        if (_equals) {
          return pair.getValue();
        }
      }
    }
    return null;
  }
  
  public JsonMetadata getOwnerMetadata(final JsonDependency dependency) {
    EObject _eContainer = dependency.eContainer();
    EObject _eContainer_1 = _eContainer.eContainer();
    EObject _eContainer_2 = _eContainer_1.eContainer();
    return ((JsonMetadata) _eContainer_2);
  }
  
  public JsonMetadata getReferencedModule(final JsonDependency dependency) {
    boolean _tripleNotEquals = (dependency != null);
    if (_tripleNotEquals) {
      EList<Pair> _pairs = dependency.getPairs();
      for (final Pair pair : _pairs) {
        if ((pair instanceof MetadataRefPair)) {
          return ((MetadataRefPair)pair).getRef();
        }
      }
    }
    return null;
  }
  
  public boolean isResolved(final EObject obj) {
    boolean _or = false;
    boolean _tripleEquals = (obj == null);
    if (_tripleEquals) {
      _or = true;
    } else {
      boolean _eIsProxy = obj.eIsProxy();
      _or = _eIsProxy;
    }
    return (!_or);
  }
  
  public boolean isDeprecatedKey(final JsonObject obj, final String key) {
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (obj instanceof JsonMetadata) {
        _matched=true;
        _switchResult = MetadataUtil.isDeprecatedMetadataKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonDependency) {
        _matched=true;
        _switchResult = MetadataUtil.isDeprecatedDependencyKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonOS) {
        _matched=true;
        _switchResult = MetadataUtil.isDeprecatedOSKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonRequirement) {
        _matched=true;
        _switchResult = MetadataUtil.isDeprecatedRequirementKey(key);
      }
    }
    return _switchResult;
  }
  
  public boolean isKnownKey(final JsonObject obj, final String key) {
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (obj instanceof JsonMetadata) {
        _matched=true;
        _switchResult = MetadataUtil.isMetadataKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonDependency) {
        _matched=true;
        _switchResult = MetadataUtil.isDependencyKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonOS) {
        _matched=true;
        _switchResult = MetadataUtil.isOSKey(key);
      }
    }
    if (!_matched) {
      if (obj instanceof JsonRequirement) {
        _matched=true;
        _switchResult = MetadataUtil.isRequirementKey(key);
      }
    }
    return _switchResult;
  }
  
  public ModuleName getName(final JsonMetadata metadata) {
    ModuleName _xtrycatchfinallyexpression = null;
    try {
      String _string = this.getString(metadata, "name");
      _xtrycatchfinallyexpression = ModuleName.create(_string, false);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public String getName(final JsonRequirement requirement) {
    return this.getString(requirement, "name");
  }
  
  public Metadata getApiMetadata(final JsonMetadata metadata) {
    Metadata _xblockexpression = null;
    {
      final Metadata apiMd = new Metadata();
      EList<Pair> _pairs = metadata.getPairs();
      for (final Pair pair : _pairs) {
        String _name = pair.getName();
        boolean _matched = false;
        if (!_matched) {
          if (Objects.equal(_name, "author")) {
            _matched=true;
            Value _value = pair.getValue();
            String _string = this.getString(_value);
            apiMd.setAuthor(_string);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "dependencies")) {
            _matched=true;
            List<Dependency> _apiDependencies = this.getApiDependencies(metadata);
            apiMd.setDependencies(_apiDependencies);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "description")) {
            _matched=true;
            Value _value_1 = pair.getValue();
            String _string_1 = this.getString(_value_1);
            apiMd.setDescription(_string_1);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "license")) {
            _matched=true;
            Value _value_2 = pair.getValue();
            String _string_2 = this.getString(_value_2);
            apiMd.setLicense(_string_2);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "name")) {
            _matched=true;
            Value _value_3 = pair.getValue();
            String _string_3 = this.getString(_value_3);
            ModuleName _create = ModuleName.create(_string_3, false);
            apiMd.setName(_create);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "project_page")) {
            _matched=true;
            Value _value_4 = pair.getValue();
            String _string_4 = this.getString(_value_4);
            apiMd.setProjectPage(_string_4);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "operatingsystem_support")) {
            _matched=true;
            List<SupportedOS> _apiOsSupport = this.getApiOsSupport(metadata);
            apiMd.setOperatingSystemSupport(_apiOsSupport);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "requirements")) {
            _matched=true;
            List<Requirement> _apiRequirements = this.getApiRequirements(metadata);
            apiMd.setRequirements(_apiRequirements);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "summary")) {
            _matched=true;
            Value _value_5 = pair.getValue();
            String _string_5 = this.getString(_value_5);
            apiMd.setSummary(_string_5);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "source")) {
            _matched=true;
            Value _value_6 = pair.getValue();
            String _string_6 = this.getString(_value_6);
            apiMd.setSource(_string_6);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "tags")) {
            _matched=true;
            List<String> _apiTags = this.getApiTags(metadata);
            apiMd.setTags(_apiTags);
          }
        }
        if (!_matched) {
          if (Objects.equal(_name, "version")) {
            _matched=true;
            Value _value_7 = pair.getValue();
            String _string_7 = this.getString(_value_7);
            Version _create_1 = Version.create(_string_7);
            apiMd.setVersion(_create_1);
          }
        }
        if (!_matched) {
          String _name_1 = pair.getName();
          Value _value_8 = pair.getValue();
          Object _apiValue = this.getApiValue(_value_8);
          apiMd.addDynamicAttribute(_name_1, _apiValue);
        }
      }
      _xblockexpression = apiMd;
    }
    return _xblockexpression;
  }
  
  public List<SupportedOS> getApiOsSupport(final JsonMetadata metadata) {
    List<SupportedOS> _xblockexpression = null;
    {
      final Value ossVal = this.getValue(metadata, "operatingsystem_support");
      List<SupportedOS> _xifexpression = null;
      if ((ossVal instanceof JsonArray)) {
        ArrayList<SupportedOS> _xblockexpression_1 = null;
        {
          final ArrayList<SupportedOS> apiOss = CollectionLiterals.<SupportedOS>newArrayList();
          EList<Value> _value = ((JsonArray)ossVal).getValue();
          for (final Value os : _value) {
            if ((os instanceof JsonObject)) {
              final SupportedOS apiOs = new SupportedOS();
              String _string = this.getString(((JsonObject)os), "operatingsystem");
              apiOs.setOperatingSystem(_string);
              final Value releases = this.getValue(((JsonObject)os), "operatingsystemrelease");
              if ((releases instanceof JsonArray)) {
                ArrayList<String> _strings = this.getStrings(((JsonArray)releases));
                apiOs.setOperatingSystemRelease(_strings);
              }
              apiOss.add(apiOs);
            }
          }
          _xblockexpression_1 = apiOss;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = Collections.<SupportedOS>emptyList();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public List<Requirement> getApiRequirements(final JsonMetadata metadata) {
    List<Requirement> _xblockexpression = null;
    {
      final Value reqsVal = this.getValue(metadata, "requirements");
      List<Requirement> _xifexpression = null;
      if ((reqsVal instanceof JsonArray)) {
        ArrayList<Requirement> _xblockexpression_1 = null;
        {
          final ArrayList<Requirement> apiReqs = CollectionLiterals.<Requirement>newArrayList();
          EList<Value> _value = ((JsonArray)reqsVal).getValue();
          for (final Value req : _value) {
            Requirement _apiRequirement = this.getApiRequirement(((JsonRequirement) req));
            apiReqs.add(_apiRequirement);
          }
          _xblockexpression_1 = apiReqs;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = Collections.<Requirement>emptyList();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public List<String> getApiTags(final JsonMetadata metadata) {
    List<String> _xblockexpression = null;
    {
      final Value tags = this.getValue(metadata, "tags");
      List<String> _xifexpression = null;
      if ((tags instanceof JsonArray)) {
        _xifexpression = this.getStrings(((JsonArray)tags));
      } else {
        _xifexpression = Collections.<String>emptyList();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public Object getApiValue(final Value value) {
    Object _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof JsonObject) {
        _matched=true;
        _switchResult = this.getMap(((JsonObject)value));
      }
    }
    if (!_matched) {
      if (value instanceof JsonArray) {
        _matched=true;
        _switchResult = this.getList(((JsonArray)value));
      }
    }
    if (!_matched) {
      if (value instanceof JsonValue) {
        _matched=true;
        _switchResult = ((JsonValue)value).getValue();
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  public ArrayList<String> getStrings(final JsonArray array) {
    ArrayList<String> _xblockexpression = null;
    {
      final ArrayList<String> list = CollectionLiterals.<String>newArrayList();
      EList<Value> _value = array.getValue();
      for (final Value value : _value) {
        String _string = this.getString(value);
        list.add(_string);
      }
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  public ArrayList<Object> getList(final JsonArray array) {
    ArrayList<Object> _xblockexpression = null;
    {
      final ArrayList<Object> list = CollectionLiterals.<Object>newArrayList();
      EList<Value> _value = array.getValue();
      for (final Value value : _value) {
        Object _apiValue = this.getApiValue(value);
        list.add(_apiValue);
      }
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  public HashMap<String, Object> getMap(final JsonObject object) {
    HashMap<String, Object> _xblockexpression = null;
    {
      final HashMap<String, Object> map = CollectionLiterals.<String, Object>newHashMap();
      EList<Pair> _pairs = object.getPairs();
      for (final Pair pair : _pairs) {
        String _name = pair.getName();
        Value _value = pair.getValue();
        Object _apiValue = this.getApiValue(_value);
        map.put(_name, _apiValue);
      }
      _xblockexpression = map;
    }
    return _xblockexpression;
  }
  
  public Dependency getApiDependency(final JsonDependency dependency) {
    Dependency _xblockexpression = null;
    {
      final Dependency apiDep = new Dependency();
      String _rawName = this.getRawName(dependency);
      ModuleName _create = ModuleName.create(_rawName, false);
      apiDep.setName(_create);
      VersionRange _range = this.getRange(dependency);
      apiDep.setVersionRequirement(_range);
      _xblockexpression = apiDep;
    }
    return _xblockexpression;
  }
  
  public Requirement getApiRequirement(final JsonRequirement requirement) {
    Requirement _xblockexpression = null;
    {
      final Requirement apiReq = new Requirement();
      String _name = this.getName(requirement);
      apiReq.setName(_name);
      VersionRange _range = this.getRange(requirement);
      apiReq.setVersionRequirement(_range);
      _xblockexpression = apiReq;
    }
    return _xblockexpression;
  }
  
  public List<Dependency> getApiDependencies(final JsonMetadata metadata) {
    List<Dependency> _xblockexpression = null;
    {
      final Value depsVal = this.getValue(metadata, "dependencies");
      List<Dependency> _xifexpression = null;
      if ((depsVal instanceof JsonArray)) {
        ArrayList<Dependency> _xblockexpression_1 = null;
        {
          final ArrayList<Dependency> apiDeps = CollectionLiterals.<Dependency>newArrayList();
          EList<Value> _value = ((JsonArray)depsVal).getValue();
          for (final Value dep : _value) {
            Dependency _apiDependency = this.getApiDependency(((JsonDependency) dep));
            apiDeps.add(_apiDependency);
          }
          _xblockexpression_1 = apiDeps;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = Collections.<Dependency>emptyList();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public Version getVersion(final JsonMetadata metadata) {
    Version _xtrycatchfinallyexpression = null;
    try {
      String _string = this.getString(metadata, "version");
      _xtrycatchfinallyexpression = Version.create(_string);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public String getRawName(final JsonDependency dependency) {
    String _xblockexpression = null;
    {
      JsonMetadata _referencedModule = null;
      if (dependency!=null) {
        _referencedModule=this.getReferencedModule(dependency);
      }
      final JsonMetadata ref = _referencedModule;
      String _xifexpression = null;
      boolean _tripleNotEquals = (ref != null);
      if (_tripleNotEquals) {
        String _xifexpression_1 = null;
        boolean _isResolved = this.isResolved(ref);
        boolean _not = (!_isResolved);
        if (_not) {
          String _xblockexpression_1 = null;
          {
            Resource _eResource = dependency.eResource();
            URI _eProxyURI = ((InternalEObject) ref).eProxyURI();
            String _fragment = _eProxyURI.fragment();
            final Triple<EObject, EReference, INode> d = this.lazyURIEncoder.decode(_eResource, _fragment);
            String _xifexpression_2 = null;
            boolean _tripleNotEquals_1 = (d != null);
            if (_tripleNotEquals_1) {
              String _xblockexpression_2 = null;
              {
                INode _third = d.getThird();
                final String string = _third.getText();
                String _xifexpression_3 = null;
                boolean _and = false;
                boolean _tripleNotEquals_2 = (string != null);
                if (!_tripleNotEquals_2) {
                  _and = false;
                } else {
                  int _length = string.length();
                  boolean _greaterEqualsThan = (_length >= 2);
                  _and = _greaterEqualsThan;
                }
                if (_and) {
                  int _length_1 = string.length();
                  int _minus = (_length_1 - 1);
                  String _substring = string.substring(1, _minus);
                  _xifexpression_3 = Strings.convertFromJavaString(_substring, true);
                }
                _xblockexpression_2 = _xifexpression_3;
              }
              _xifexpression_2 = _xblockexpression_2;
            }
            _xblockexpression_1 = _xifexpression_2;
          }
          _xifexpression_1 = _xblockexpression_1;
        } else {
          _xifexpression_1 = this.getString(ref, "name");
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public ModuleName getName(final JsonDependency dependency) {
    ModuleName _xtrycatchfinallyexpression = null;
    try {
      String _rawName = this.getRawName(dependency);
      _xtrycatchfinallyexpression = ModuleName.create(_rawName, false);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public VersionRange getRange(final JsonDependency dependency) {
    VersionRange _xtrycatchfinallyexpression = null;
    try {
      String _string = this.getString(dependency, "version_requirement");
      _xtrycatchfinallyexpression = VersionRange.create(_string);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public VersionRange getRange(final JsonRequirement requirement) {
    VersionRange _xtrycatchfinallyexpression = null;
    try {
      String _string = this.getString(requirement, "version_requirement");
      _xtrycatchfinallyexpression = VersionRange.create(_string);
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        final IllegalArgumentException e = (IllegalArgumentException)_t;
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public List<JsonMetadata> getResolvedDependencies(final JsonMetadata metadata) {
    List<JsonMetadata> _xblockexpression = null;
    {
      final Value depsVal = this.getValue(metadata, "dependencies");
      List<JsonMetadata> _xifexpression = null;
      if ((depsVal instanceof JsonArray)) {
        ArrayList<JsonMetadata> _xblockexpression_1 = null;
        {
          final ArrayList<JsonMetadata> resolved = CollectionLiterals.<JsonMetadata>newArrayList();
          EList<Value> _value = ((JsonArray)depsVal).getValue();
          for (final Value dep : _value) {
            {
              JsonMetadata refMd = this.getReferencedModule(((JsonDependency) dep));
              boolean _isResolved = this.isResolved(refMd);
              if (_isResolved) {
                resolved.add(refMd);
              }
            }
          }
          _xblockexpression_1 = resolved;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = Collections.<JsonMetadata>emptyList();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
