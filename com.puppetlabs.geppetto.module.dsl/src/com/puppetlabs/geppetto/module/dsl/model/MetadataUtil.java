package com.puppetlabs.geppetto.module.dsl.model;

public class MetadataUtil {

	public static boolean isDependencyKey(String key) {
		return isKnownKey(Dependency.ALL_KEYS, key);
	}

	public static boolean isDeprecatedDependencyKey(String key) {
		return isKnownKey(Dependency.DEPRECATED_KEYS, key);
	}

	public static boolean isDeprecatedMetadataKey(String key) {
		return isKnownKey(Metadata.DEPRECATED_KEYS, key);
	}

	public static boolean isDeprecatedOSKey(String key) {
		return false;
	}

	public static boolean isDeprecatedRequirementKey(String key) {
		return false;
	}

	private static boolean isKnownKey(String[] strings, String string) {
		int idx = strings.length;
		while(--idx >= 0)
			if(strings[idx].equals(string))
				return true;
		return false;
	}

	public static boolean isMetadataKey(String key) {
		return isKnownKey(Metadata.ALL_KEYS, key);
	}

	public static boolean isOSKey(String key) {
		return isKnownKey(OS.ALL_KEYS, key);
	}

	public static boolean isRequirementKey(String key) {
		return isKnownKey(Requirement.ALL_KEYS, key);
	}
}
