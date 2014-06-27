package com.puppetlabs.geppetto.module.dsl

import com.google.inject.Inject
import com.puppetlabs.geppetto.forge.model.Dependency
import com.puppetlabs.geppetto.forge.model.Metadata
import com.puppetlabs.geppetto.forge.model.ModuleName
import com.puppetlabs.geppetto.forge.model.Requirement
import com.puppetlabs.geppetto.forge.model.SupportedOS
import com.puppetlabs.geppetto.module.dsl.metadata.JsonArray
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
import com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair
import com.puppetlabs.geppetto.module.dsl.metadata.Value
import com.puppetlabs.geppetto.semver.Version
import com.puppetlabs.geppetto.semver.VersionRange
import java.util.Collections
import java.util.HashMap
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.xtext.linking.lazy.LazyURIEncoder
import org.eclipse.xtext.util.Strings
import com.puppetlabs.geppetto.module.dsl.model.MetadataUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonOS

class ModuleUtil {
	@Inject
	LazyURIEncoder lazyURIEncoder;

	def getAttributes(JsonObject object) {
		if (object === null)
			emptyMap
		else {
			val attrs = new HashMap
			for (pair : object.pairs)
				attrs.put(pair.name, pair.value);
			attrs
		}
	}

	def getString(Value value) {
		if (value instanceof JsonValue) {
			var v = value.value
			if (v instanceof String)
				return v
		}
	}

	def getString(JsonObject object, String name) {
		object.getValue(name).string
	}

	def getValue(JsonObject object, String name) {
		if (object !== null)
			for (pair : object.pairs)
				if (name == pair.name)
					return pair.value
	}

	def getOwnerMetadata(JsonDependency dependency) {
		dependency.eContainer.eContainer.eContainer as JsonMetadata
	}

	def getReferencedModule(JsonDependency dependency) {
		if (dependency !== null)
			for (pair : dependency.pairs)
				if (pair instanceof MetadataRefPair)
					return pair.ref
	}

	def isResolved(EObject obj) {
		!(obj === null || obj.eIsProxy)
	}

	def isDeprecatedKey(JsonObject obj, String key) {
		switch (obj) {
			JsonMetadata:
				MetadataUtil.isDeprecatedMetadataKey(key)
			JsonDependency:
				MetadataUtil.isDeprecatedDependencyKey(key)
			JsonOS:
				MetadataUtil.isDeprecatedOSKey(key)
			JsonRequirement:
				MetadataUtil.isDeprecatedRequirementKey(key)
		}
	}

	def isKnownKey(JsonObject obj, String key) {
		switch (obj) {
			JsonMetadata:
				MetadataUtil.isMetadataKey(key)
			JsonDependency:
				MetadataUtil.isDependencyKey(key)
			JsonOS:
				MetadataUtil.isOSKey(key)
			JsonRequirement:
				MetadataUtil.isRequirementKey(key)
		}
	}

	def getName(JsonMetadata metadata) {
		try {
			ModuleName.create(metadata.getString('name'), false)
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def getName(JsonRequirement requirement) {
		requirement.getString('name')
	}

	def getApiMetadata(JsonMetadata metadata) {
		val apiMd = new Metadata
		for (pair : metadata.pairs) {
			switch (pair.name) {
				case 'author':
					apiMd.author = pair.value.string
				case 'dependencies':
					apiMd.dependencies = metadata.apiDependencies
				case 'description':
					apiMd.description = pair.value.string
				case 'license':
					apiMd.license = pair.value.string
				case 'name':
					apiMd.name = ModuleName.create(pair.value.string, false)
				case 'project_page':
					apiMd.projectPage = pair.value.string
				case 'operatingsystem_support':
					apiMd.operatingSystemSupport = metadata.apiOsSupport
				case 'requirements':
					apiMd.requirements = metadata.apiRequirements
				case 'summary':
					apiMd.summary = pair.value.string
				case 'source':
					apiMd.source = pair.value.string
				case 'tags':
					apiMd.tags = metadata.apiTags
				case 'version':
					apiMd.version = Version.create(pair.value.string)
				default:
					apiMd.addDynamicAttribute(pair.name, pair.value.apiValue)
			}
		}
		apiMd
	}

	def List<SupportedOS> getApiOsSupport(JsonMetadata metadata) {
		val ossVal = getValue(metadata, 'operatingsystem_support')
		if (ossVal instanceof JsonArray) {
			val apiOss = newArrayList
			for (os : ossVal.value)
				if (os instanceof JsonObject) {
					val apiOs = new SupportedOS
					apiOs.operatingSystem = os.getString('operatingsystem')
					val releases = os.getValue('operatingsystemrelease')
					if (releases instanceof JsonArray)
						apiOs.operatingSystemRelease = releases.strings
					apiOss.add(apiOs)
				}
			apiOss
		} else
			Collections.emptyList
	}

	def getApiRequirements(JsonMetadata metadata) {
		val reqsVal = getValue(metadata, 'requirements')
		if (reqsVal instanceof JsonArray) {
			val apiReqs = newArrayList
			for (req : reqsVal.value)
				apiReqs.add((req as JsonRequirement).apiRequirement)
			apiReqs
		} else
			Collections.emptyList
	}

	def getApiTags(JsonMetadata metadata) {
		val tags = metadata.getValue('tags')
		if (tags instanceof JsonArray)
			tags.strings
		else
			Collections.emptyList
	}

	def Object getApiValue(Value value) {
		switch (value) {
			JsonObject: value.map
			JsonArray: value.list
			JsonValue: value.value
			default: null
		}
	}

	def getStrings(JsonArray array) {
		val list = newArrayList
		for (value : array.value)
			list.add(value.string)
		list
	}

	def getList(JsonArray array) {
		val list = newArrayList
		for (value : array.value)
			list.add(value.apiValue)
		list
	}

	def getMap(JsonObject object) {
		val map = newHashMap
		for (pair : object.pairs)
			map.put(pair.name, pair.value.apiValue)
		map
	}

	def getApiDependency(JsonDependency dependency) {
		val apiDep = new Dependency
		apiDep.name = ModuleName.create(dependency.rawName, false)
		apiDep.versionRequirement = dependency.range
		apiDep
	}

	def getApiRequirement(JsonRequirement requirement) {
		val apiReq = new Requirement
		apiReq.name = requirement.name
		apiReq.versionRequirement = requirement.range
		apiReq
	}

	def getApiDependencies(JsonMetadata metadata) {
		val depsVal = getValue(metadata, 'dependencies')
		if (depsVal instanceof JsonArray) {
			val apiDeps = newArrayList
			for (dep : depsVal.value)
				apiDeps.add((dep as JsonDependency).apiDependency)
			apiDeps
		} else
			Collections.emptyList
	}

	def getVersion(JsonMetadata metadata) {
		try {
			Version.create(metadata.getString('version'))
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def getRawName(JsonDependency dependency) {
		val ref = dependency?.referencedModule
		if (ref !== null)
			if (!ref.isResolved) {
				val d = lazyURIEncoder.decode(dependency.eResource, (ref as InternalEObject).eProxyURI.fragment)
				if (d !== null) {
					val string = d.third.text
					if (string !== null && string.length >= 2)
						Strings.convertFromJavaString(string.substring(1, string.length() - 1), true)
				}
			} else
				ref.getString('name')
	}

	def getName(JsonDependency dependency) {
		try {
			ModuleName.create(dependency.rawName, false)
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def getRange(JsonDependency dependency) {
		try {
			VersionRange.create(getString(dependency, 'version_requirement'))
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def getRange(JsonRequirement requirement) {
		try {
			VersionRange.create(getString(requirement, 'version_requirement'))
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def getResolvedDependencies(JsonMetadata metadata) {
		val depsVal = getValue(metadata, 'dependencies')
		if (depsVal instanceof JsonArray) {
			val resolved = newArrayList
			for (dep : depsVal.value) {
				var refMd = (dep as JsonDependency).referencedModule
				if (refMd.isResolved)
					resolved.add(refMd)
			}
			resolved
		} else
			Collections.emptyList
	}
}
