package com.puppetlabs.geppetto.module.dsl.validation;

import static com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference.*;
import static java.lang.String.format;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.Check;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.*;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage.Literals;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

@Singleton
public class ModuleJavaValidator extends AbstractModuleJavaValidator implements ModuleDiagnostics {
	@Inject
	private ModuleUtil moduleUtil;

	@Inject
	private IModuleValidationAdvisor validationAdvisor;

	private String missingAttributeMessage(String key) {
		return (("Missing required attribute \"" + key) + "\"");
	}

	private String emptyAttributeMessage(String key) {
		return (("Attribute \"" + key) + "\" cannot be empty");
	}

	private void missingAttribute(JsonObject obj, String key, ValidationPreference pref) {
		warningOrError(pref, obj, Literals.JSON_OBJECT__PAIRS, ISSUE__MISSING_REQUIRED_ATTRIBUTE, missingAttributeMessage(key), key);
	}

	private void emptyAttributeError(JsonValue value, String key, ValidationPreference pref) {
		warningOrError(pref, value, Literals.JSON_VALUE__VALUE, ISSUE__EMPTY_ATTRIBUTE, emptyAttributeMessage(key), key);
	}

	private void checkRequiredFields(JsonObject obj, ValidationPreference pref, String... requiredFields) {
		if(pref != IGNORE) {
			Map<String, Value> attrs = moduleUtil.getAttributes(obj);
			for(String key : requiredFields) {
				Value a = attrs.get(key);
				if(a == null)
					missingAttribute(obj, key, pref);
				else {
					if((a instanceof JsonValue)) {
						Object v = ((JsonValue) a).getValue();
						if(v == null || v instanceof String && ((String) v).isEmpty())
							emptyAttributeError((JsonValue) a, key, pref);
					}
				}
			}
		}
	}

	@Check
	public void checkMetadata(JsonMetadata metadata) {
		checkRequiredFields(metadata, ERROR, "name", "version");
		checkRequiredFields(metadata, validationAdvisor.getMissingForgeRequiredFields(), "author", "license", "source", "summary");
	}

	@Check
	public void checkRequirement(JsonRequirement requirement) {
		checkRequiredFields(requirement, ERROR, "name", "version_requirement");
	}

	@Check
	public void checkOS(JsonOS os) {
		checkRequiredFields(os, ERROR, "operatingsystem");
	}

	@Check
	public void checkDependency(JsonDependency dependency) {
		JsonMetadata ref = moduleUtil.getReferencedModule(dependency);
		VersionRange range = moduleUtil.getRange(dependency);
		if(ref == null)
			missingAttribute(dependency, "name", ValidationPreference.ERROR);

		if(range == null)
			warning(
				missingAttributeMessage("version_requirement") + ". All versions will be considered a match", dependency,
				Literals.JSON_OBJECT__PAIRS, ISSUE__MISSING_REQUIRED_ATTRIBUTE);
		else if(moduleUtil.isResolved(ref) && validationAdvisor.getCircularDependency() != IGNORE) {
			LinkedList<ModuleName> chain = Lists.newLinkedList();
			chain.add(moduleUtil.getName(moduleUtil.getOwnerMetadata(dependency)));
			checkCircularDependencies(ref, dependency, new HashSet<ModuleName>(), chain);
		}
	}

	@Check
	public void checkRequirementName(RequirementNameValue rqNameValue) {
		Object _value = rqNameValue.getValue();
		String name = ((String) _value);
		if(!("puppet".equals(name) || "pe".equals(name)))
			warning(
				(("\"" + name) + "\" is not a known name for a requirement"), rqNameValue, Literals.JSON_VALUE__VALUE,
				ISSUE__UNKNOWN_REQUIREMENT_NAME);
	}

	@Check
	public void checkVersion(JsonVersion versionValue) {
		try {
			if(Version.create((String) versionValue.getValue()) == null)
				emptyAttributeError(versionValue, "version", ERROR);
		}
		catch(IllegalArgumentException e) {
			error(e.getMessage(), versionValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_VERSION);
		}
	}

	@Check
	public void checkVersionRange(JsonVersionRange versionRangeValue) {
		try {
			VersionRange range = VersionRange.create((String) versionRangeValue.getValue());
			if(range == null)
				emptyAttributeError(versionRangeValue, "version_requirement", ERROR);
			else {
				EObject obj = versionRangeValue.eContainer().eContainer();
				if(obj instanceof JsonDependency) {
					if(validationAdvisor.getDependencyVersionMismatch() != IGNORE)
						// Check that the given range appoints the referenced metadata
						try {
							JsonMetadata ref = moduleUtil.getReferencedModule((JsonDependency) obj);
							if(moduleUtil.isResolved(ref)) {
								Version ver = moduleUtil.getVersion(ref);
								if(ver != null && !range.isIncluded(ver))
									warningOrError(
										validationAdvisor.getDependencyVersionMismatch(), versionRangeValue, Literals.JSON_VALUE__VALUE,
										ISSUE__MODULE_VERSION_RANGE_MISMATCH,
										String.format("Version requirement \"%s\" does not match version \"%s\"", range, ver));
							}
						}
					catch(IllegalArgumentException e) {
						// Warnings have been issued elsewhere. Just ignore
					}
				}
			}
		}
		catch(IllegalArgumentException e) {
			error(e.getMessage(), versionRangeValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_VERSION_RANGE);
		}
	}

	@Check
	public void checkModuleName(JsonModuleName moduleNameValue) {
		try {
			checkBothNames(ModuleName.create((String) moduleNameValue.getValue(), true));
		}
		catch(IllegalArgumentException w) {
			if(validationAdvisor.getModuleNameNotStrict() == ERROR)
				error(w.getMessage(), moduleNameValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_MODULE_NAME);
			else
				try {
					checkBothNames(ModuleName.create((String) moduleNameValue.getValue(), false));
					if(validationAdvisor.getModuleNameNotStrict() == ValidationPreference.WARNING)
						warning(w.getMessage(), moduleNameValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_MODULE_NAME);
				}
			catch(IllegalArgumentException e) {
				error(e.getMessage(), moduleNameValue, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_MODULE_NAME);
			}
		}
	}

	@Check
	public void checkTag(JsonTag value) {
		String tag = (String) value.getValue();
		int len = tag.length();
		if(len == 0)
			emptyAttributeError(value, "tag", ERROR);
		else {
			for(int i = 0; i < len; i++)
				if(Character.isWhitespace(tag.charAt(i))) {
					warning("A tag cannot contain whitespace", value, Literals.JSON_VALUE__VALUE, ISSUE__INVALID_TAG);
					i = len;
				}
		}
	}

	@Check
	public void checkUnrecognizedPair(UnrecognizedPair pair) {
		JsonObject owner = (JsonObject) pair.eContainer();
		String key = pair.getName();
		if(moduleUtil.isDeprecatedKey(owner, key))
			warningOrError(
				validationAdvisor.getDeprecatedKey(), pair, Literals.PAIR__NAME, ISSUE__DEPRECATED_KEY,
				format("'%s' is a deprecated metadata key", key));
		else
			warningOrError(
				validationAdvisor.getUnrecognizedKey(), pair, Literals.PAIR__NAME, ISSUE__UNRECOGNIZED_KEY,
				format("'%s' is not recognized as a valid metadata key", key));
	}

	private void checkBothNames(ModuleName moduleName) {
		if(moduleName.getOwner().isEmpty() || moduleName.getName().isEmpty())
			throw new ModuleName.BadNameSyntaxException();
	}

	private void checkCircularDependencies(JsonMetadata ref, JsonDependency origin, Set<ModuleName> visited, LinkedList<ModuleName> chain) {
		ModuleName refName = moduleUtil.getName(ref);
		if(chain.getFirst().equals(refName))
			circularDependencyError(origin, chain);
		else {
			if(visited.add(refName)) {
				chain.addLast(refName);
				for(JsonMetadata refMd : moduleUtil.getResolvedDependencies(ref))
					checkCircularDependencies(refMd, origin, visited, chain);
				chain.removeLast();
			}
		}
	}

	private void circularDependencyError(JsonDependency dependency, LinkedList<ModuleName> chain) {
		StringBuilder buf = new StringBuilder("Circular dependency: [");
		for(ModuleName name : chain) {
			name.toString(buf);
			buf.append(" -> ");
		}
		chain.getFirst().toString(buf);
		buf.append("]");
		warningOrError(
			validationAdvisor.getCircularDependency(), dependency, Literals.JSON_OBJECT__PAIRS, ISSUE__CIRCULAR_DEPENDENCY, buf.toString());
	}

	private void warningOrError(ValidationPreference pref, EObject element, EStructuralFeature feature, String issue, String msg,
			String... issueData) {
		if(pref != IGNORE) {
			if(pref == WARNING)
				warning(msg, element, feature, issue, issueData);
			else
				error(msg, element, feature, issue, issueData);
		}
	}
}
