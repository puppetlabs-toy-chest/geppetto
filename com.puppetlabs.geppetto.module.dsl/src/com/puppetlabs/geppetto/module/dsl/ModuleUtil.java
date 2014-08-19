package com.puppetlabs.geppetto.module.dsl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Triple;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.Requirement;
import com.puppetlabs.geppetto.forge.model.SupportedOS;
import com.puppetlabs.geppetto.module.dsl.metadata.*;
import com.puppetlabs.geppetto.module.dsl.model.MetadataUtil;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

@Singleton
public class ModuleUtil {
	private final LazyURIEncoder lazyURIEncoder;

	@Inject
	public ModuleUtil(LazyURIEncoder lazyURIEncoder) {
		this.lazyURIEncoder = lazyURIEncoder;
	}

	public List<Dependency> getApiDependencies(JsonMetadata metadata) {
		Value depsVal = getValue(metadata, "dependencies");
		if(depsVal instanceof JsonArray) {
			List<Dependency> apiDeps = Lists.newArrayList();
			for(Value dep : ((JsonArray) depsVal).getValue())
				apiDeps.add(getApiDependency(((JsonDependency) dep)));
			return apiDeps;
		}
		return Collections.emptyList();
	}

	public Dependency getApiDependency(JsonDependency dependency) {
		Dependency apiDep = new Dependency();
		apiDep.setName(ModuleName.create(getRawName(dependency), false));
		apiDep.setVersionRequirement(getRange(dependency));
		return apiDep;
	}

	public Metadata getApiMetadata(JsonMetadata metadata) {
		Metadata apiMd = new Metadata();
		EList<Pair> _pairs = metadata.getPairs();
		for(Pair pair : _pairs) {
			String name = pair.getName();
			if(name == null)
				continue;

			Value value = pair.getValue();
			if(name.equals("author"))
				apiMd.setAuthor(getString(value));
			else if(name.equals("dependencies"))
				apiMd.setDependencies(getApiDependencies(metadata));
			else if(name.equals("description"))
				apiMd.setDescription(getString(value));
			else if(name.equals("issues_url"))
				apiMd.setIssuesURL(getString(value));
			else if(name.equals("license"))
				apiMd.setLicense(getString(value));
			else if(name.equals("name"))
				apiMd.setName(ModuleName.create(getString(value), false));
			else if(name.equals("project_page"))
				apiMd.setProjectPage(getString(value));
			else if(name.equals("operatingsystem_support"))
				apiMd.setOperatingSystemSupport(getApiOsSupport(metadata));
			else if(name.equals("requirements"))
				apiMd.setRequirements(getApiRequirements(metadata));
			else if(name.equals("summary"))
				apiMd.setSummary(getString(value));
			else if(name.equals("source"))
				apiMd.setSource(getString(value));
			else if(name.equals("tags"))
				apiMd.setTags(getApiTags(metadata));
			else if(name.equals("version"))
				apiMd.setVersion(Version.create(getString(value)));
			else
				apiMd.addDynamicAttribute(name, getApiValue(value));
		}
		return apiMd;
	}

	public List<SupportedOS> getApiOsSupport(JsonMetadata metadata) {
		Value ossVal = getValue(metadata, "operatingsystem_support");
		if(ossVal instanceof JsonArray) {
			List<SupportedOS> apiOss = Lists.newArrayList();
			for(Value osv : ((JsonArray) ossVal).getValue())
				if(osv instanceof JsonObject) {
					JsonObject os = (JsonObject) osv;
					SupportedOS apiOs = new SupportedOS();
					apiOs.setOperatingSystem(getString(os, "operatingsystem"));
					Value releases = getValue(os, "operatingsystemrelease");
					if(releases instanceof JsonArray)
						apiOs.setOperatingSystemRelease(getStrings((JsonArray) releases));
					apiOss.add(apiOs);
				}
			return apiOss;
		}
		return Collections.emptyList();
	}

	public Requirement getApiRequirement(JsonRequirement requirement) {
		Requirement apiReq = new Requirement();
		apiReq.setName(getName(requirement));
		apiReq.setVersionRequirement(getRange(requirement));
		return apiReq;
	}

	public List<Requirement> getApiRequirements(JsonMetadata metadata) {
		Value reqsVal = getValue(metadata, "requirements");
		if(reqsVal instanceof JsonArray) {
			List<Requirement> apiReqs = Lists.newArrayList();
			for(Value req : ((JsonArray) reqsVal).getValue())
				apiReqs.add(getApiRequirement(((JsonRequirement) req)));
			return apiReqs;
		}
		return Collections.emptyList();
	}

	public List<String> getApiTags(JsonMetadata metadata) {
		Value tags = getValue(metadata, "tags");
		return tags instanceof JsonArray
				? getStrings((JsonArray) tags)
				: Collections.<String> emptyList();
	}

	public Object getApiValue(Value value) {
		if(value instanceof JsonObject)
			return getMap(((JsonObject) value));
		if(value instanceof JsonArray)
			return getList(((JsonArray) value));
		if(value instanceof JsonValue)
			return ((JsonValue) value).getValue();
		return null;
	}

	public Map<String, Value> getAttributes(JsonObject object) {
		if(object == null)
			return Collections.emptyMap();

		Map<String, Value> attrs = new HashMap<String, Value>();
		for(Pair pair : object.getPairs())
			attrs.put(pair.getName(), pair.getValue());
		return attrs;
	}

	public List<Object> getList(JsonArray array) {
		List<Object> list = Lists.newArrayList();
		for(Value value : array.getValue())
			list.add(getApiValue(value));
		return list;
	}

	public Map<String, Object> getMap(JsonObject object) {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Pair pair : object.getPairs())
			map.put(pair.getName(), getApiValue(pair.getValue()));
		return map;
	}

	public ModuleName getName(JsonDependency dependency) {
		try {
			return ModuleName.create(getRawName(dependency), false);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public ModuleName getName(JsonMetadata metadata) {
		try {
			return ModuleName.create(getString(metadata, "name"), false);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public String getName(JsonRequirement requirement) {
		return getString(requirement, "name");
	}

	public JsonMetadata getOwnerMetadata(JsonDependency dependency) {
		return (JsonMetadata) dependency.eContainer().eContainer().eContainer();
	}

	public VersionRange getRange(JsonDependency dependency) {
		try {
			return VersionRange.create(getString(dependency, "version_requirement"));
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public VersionRange getRange(JsonRequirement requirement) {
		try {
			return VersionRange.create(getString(requirement, "version_requirement"));
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public String getRawName(JsonDependency dependency) {
		if(dependency == null)
			return null;

		JsonMetadata ref = getReferencedModule(dependency);
		if(ref != null) {
			if(isResolved(ref))
				return getString(ref, "name");

			Triple<EObject, EReference, INode> d = lazyURIEncoder.decode(
				dependency.eResource(), ((InternalEObject) ref).eProxyURI().fragment());

			if(d != null) {
				String string = d.getThird().getText();
				if(string != null && string.length() >= 2)
					return Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
			}
		}
		return null;
	}

	public JsonMetadata getReferencedModule(JsonDependency dependency) {
		if(dependency != null)
			for(Pair pair : dependency.getPairs())
				if(pair instanceof MetadataRefPair)
					return ((MetadataRefPair) pair).getRef();
		return null;
	}

	public List<JsonMetadata> getResolvedDependencies(JsonMetadata metadata) {
		Value depsVal = getValue(metadata, "dependencies");
		if(depsVal instanceof JsonArray) {
			List<JsonMetadata> resolved = Lists.newArrayList();
			for(Value dep : ((JsonArray) depsVal).getValue()) {
				JsonMetadata refMd = getReferencedModule(((JsonDependency) dep));
				if(isResolved(refMd))
					resolved.add(refMd);
			}
			return resolved;
		}
		return Collections.emptyList();
	}

	public String getString(JsonObject object, String name) {
		return getString(getValue(object, name));
	}

	public String getString(Value value) {
		if(value instanceof JsonValue) {
			Object v = ((JsonValue) value).getValue();
			if(v instanceof String)
				return (String) v;
		}
		return null;
	}

	public List<String> getStrings(JsonArray array) {
		List<String> list = Lists.newArrayList();
		for(Value value : array.getValue())
			list.add(getString(value));
		return list;
	}

	public Value getValue(JsonObject object, String name) {
		if(object != null && name != null)
			for(Pair pair : object.getPairs())
				if(name.equals(pair.getName()))
					return pair.getValue();
		return null;
	}

	public Version getVersion(JsonMetadata metadata) {
		try {
			return Version.create(getString(metadata, "version"));
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public boolean isDeprecatedKey(JsonObject obj, String key) {
		if(obj instanceof JsonMetadata)
			return MetadataUtil.isDeprecatedMetadataKey(key);
		if(obj instanceof JsonDependency)
			return MetadataUtil.isDeprecatedDependencyKey(key);
		if(obj instanceof JsonOS)
			return MetadataUtil.isDeprecatedOSKey(key);
		if(obj instanceof JsonRequirement)
			return MetadataUtil.isDeprecatedRequirementKey(key);
		return false;
	}

	public boolean isKnownKey(JsonObject obj, String key) {
		if(obj instanceof JsonMetadata)
			return MetadataUtil.isMetadataKey(key);
		if(obj instanceof JsonDependency)
			return MetadataUtil.isDependencyKey(key);
		if(obj instanceof JsonOS)
			return MetadataUtil.isOSKey(key);
		if(obj instanceof JsonRequirement)
			return MetadataUtil.isRequirementKey(key);
		return false;
	}

	public boolean isResolved(EObject obj) {
		return !(obj == null || obj.eIsProxy());
	}
}
