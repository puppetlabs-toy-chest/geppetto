package com.puppetlabs.geppetto.module.dsl.validation

import com.google.inject.Inject
import com.google.inject.Singleton
import com.puppetlabs.geppetto.forge.model.ModuleName
import com.puppetlabs.geppetto.forge.model.ModuleName.BadNameSyntaxException
import com.puppetlabs.geppetto.module.dsl.ModuleUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
import com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName
import com.puppetlabs.geppetto.module.dsl.metadata.JsonOS
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
import com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement
import com.puppetlabs.geppetto.module.dsl.metadata.JsonTag
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage.Literals
import com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference
import com.puppetlabs.geppetto.semver.Version
import com.puppetlabs.geppetto.semver.VersionRange
import java.util.LinkedList
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.validation.Check

import static com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference.*

@Singleton
class ModuleValidator extends AbstractModuleValidator implements ModuleDiagnostics {
	@Inject
	extension ModuleUtil

	@Inject
	IModuleValidationAdvisor validationAdvisor

	def private missingAttributeMessage(String key) {
		'Missing required attribute "' + key + '"'
	}

	def private emptyAttributeMessage(String key) {
		'Attribute "' + key + '" cannot be empty'
	}

	def private void missingAttribute(JsonObject obj, String key, ValidationPreference pref) {
		pref.warningOrError(obj, Literals.JSON_OBJECT__PAIRS, ISSUE__MISSING_REQUIRED_ATTRIBUTE,
			key.missingAttributeMessage, key)
	}

	def private void emptyAttributeError(JsonValue value, String key, ValidationPreference pref) {
		pref.warningOrError(value, Literals.JSON_VALUE__VALUE, ISSUE__EMPTY_ATTRIBUTE, key.emptyAttributeMessage, key)
	}

	def private void checkRequiredFields(JsonObject obj, ValidationPreference pref, String... requiredFields) {
		if (pref !== IGNORE) {
			val attrs = obj.attributes
			for (key : requiredFields) {
				val a = attrs.get(key)
				if (a === null)
					obj.missingAttribute(key, pref)
				else if (a instanceof JsonValue) {
					val v = a.value
					if (v === null || v instanceof String && (v as String).empty)
						a.emptyAttributeError(key, pref)
				}
			}
		}
	}

	@Check
	def void checkMetadata(JsonMetadata metadata) {
		checkRequiredFields(metadata, ERROR, 'name', 'version')
		checkRequiredFields(metadata, validationAdvisor.missingForgeRequiredFields, 'author', 'license', 'source',
			'summary')
	}

	@Check
	def void checkRequirement(JsonRequirement requirement) {
		checkRequiredFields(requirement, ERROR, 'name', 'version_requirement')
	}

	@Check
	def void checkOS(JsonOS os) {
		checkRequiredFields(os, ERROR, 'operatingsystem')
	}

	@Check
	def void checkDependency(JsonDependency dependency) {
		val ref = dependency.referencedModule
		val range = dependency.range
		if (ref === null)
			dependency.missingAttribute('name', ERROR)

		if (range === null)
			warning('version_requirement'.missingAttributeMessage + '. All versions will be considered a match',
				dependency, Literals.JSON_OBJECT__PAIRS, ISSUE__MISSING_REQUIRED_ATTRIBUTE)
		else if (ref.isResolved && validationAdvisor.circularDependency !== IGNORE)
			ref.checkCircularDependencies(dependency, newHashSet, newLinkedList(dependency.ownerMetadata.name))
	}

	@Check
	def void checkRequirementName(RequirementNameValue rqNameValue) {
		val name = rqNameValue.value as String
		if (!("puppet".equals(name) || "pe".equals(name)))
			warning('"' + name + '" is not a known name for a requirement', rqNameValue, Literals.JSON_VALUE__VALUE,
				ISSUE__UNKNOWN_REQUIREMENT_NAME)
	}

	@Check
	def void checkVersion(JsonVersion versionValue) {
		try {
			if (Version.create(versionValue.value as String) === null)
				versionValue.emptyAttributeError('version', ERROR)
		} catch (IllegalArgumentException e) {
			error(e.message, versionValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_VERSION)
		}
	}

	@Check
	def void checkVersionRange(JsonVersionRange versionRangeValue) {
		try {
			val range = VersionRange.create(versionRangeValue.value as String)
			if (range === null)
				versionRangeValue.emptyAttributeError('version_requirement', ERROR)
			else {
				val obj = versionRangeValue.eContainer.eContainer
				if (obj instanceof JsonDependency) {
					if (validationAdvisor.dependencyVersionMismatch !== IGNORE)
						// Check that the given range appoints the referenced metadata
						try {
							val ref = obj.referencedModule
							if (ref.resolved) {
								val ver = ref.version
								if (ver !== null && !range.isIncluded(ver))
									validationAdvisor.dependencyVersionMismatch.warningOrError(versionRangeValue,
										Literals.JSON_VALUE__VALUE, ISSUE__MODULE_VERSION_RANGE_MISMATCH,
										String.format('Version requirement "%s" does not match version "%s"', range, ver))
							}
						} catch (IllegalArgumentException e) {
							// Warnings have been issued elsewhere. Just ignore
						}
				}
			}
		} catch (IllegalArgumentException e) {
			error(e.message, versionRangeValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_VERSION_RANGE)
		}
	}

	@Check
	def void checkModuleName(JsonModuleName moduleNameValue) {
		try {
			ModuleName.create(moduleNameValue.value as String, true).checkBothNames
		} catch (IllegalArgumentException w) {
			if (validationAdvisor.moduleNameNotStrict === ERROR)
				error(w.message, moduleNameValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_MODULE_NAME)
			else
				try {
					ModuleName.create(moduleNameValue.value as String, false).checkBothNames
					if (validationAdvisor.moduleNameNotStrict === WARNING)
						warning(w.message, moduleNameValue, Literals.JSON_VALUE__VALUE,
							ISSUE__INVALID_MODULE_NAME)
				} catch (IllegalArgumentException e) {
					error(e.message, moduleNameValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_MODULE_NAME)
				}
		}
	}

	@Check
	def void checkTag(JsonTag value) {
		val tag = value.value as String
		val len = tag.length
		if (len === 0)
			value.emptyAttributeError('tag', ERROR)
		else {
			for (var i = 0; i < len; i++) {
				if (Character.isWhitespace(tag.charAt(i))) {
					warning('A tag cannot contain whitespace', value, Literals.JSON_VALUE__VALUE,
						ISSUE__INVALID_TAG)
					i = len
				}
			}
		}
	}

	@Check
	def void checkUnrecognizedPair(UnrecognizedPair pair) {
		val owner = pair.eContainer as JsonObject
		val key = pair.name
		if (owner.isDeprecatedKey(key))
			validationAdvisor.deprecatedKey.warningOrError(pair, Literals.PAIR__NAME, ISSUE__DEPRECATED_KEY,
				String.format("'%s' is a deprecated metadata key", key))
		else
			validationAdvisor.unrecognizedKey.warningOrError(pair, Literals.PAIR__NAME, ISSUE__UNRECOGNIZED_KEY,
				String.format("'%s' is not recognized as a valid metadata key", key))
	}

	def private void checkBothNames(ModuleName moduleName) {
		if (moduleName.owner.empty || moduleName.name.empty)
			throw new BadNameSyntaxException
	}

	def private void checkCircularDependencies(JsonMetadata ref, JsonDependency origin, Set<ModuleName> visited,
		LinkedList<ModuleName> chain) {
		val refName = ref.name
		if (chain.first == refName)
			origin.circularDependencyError(chain)
		else {
			if (visited.add(refName)) {
				chain.addLast(refName)
				for (refMd : ref.resolvedDependencies)
					refMd.checkCircularDependencies(origin, visited, chain)
				chain.removeLast()
			}
		}
	}

	def private void circularDependencyError(JsonDependency dependency, LinkedList<ModuleName> chain) {
		val buf = new StringBuilder('Circular dependency: [')
		for (name : chain) {
			name.toString(buf)
			buf.append(' -> ')
		}
		chain.first.toString(buf)
		buf.append(']')
		validationAdvisor.circularDependency.warningOrError(dependency, Literals.JSON_OBJECT__PAIRS,
			ISSUE__CIRCULAR_DEPENDENCY, buf.toString)
	}

	def private warningOrError(ValidationPreference pref, EObject element, EStructuralFeature feature, String issue,
		String msg, String... issueData) {
		if (pref !== IGNORE) {
			if (pref === WARNING)
				warning(msg, element, feature, issue, issueData)
			else
				error(msg, element, feature, issue, issueData)
		}
	}
}
