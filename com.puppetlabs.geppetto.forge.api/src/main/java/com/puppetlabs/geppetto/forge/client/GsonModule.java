/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.client;

import static com.puppetlabs.geppetto.forge.model.Dependency.DEPENDENCY_ADAPTER;
import static com.puppetlabs.geppetto.forge.model.ModuleName.MODULE_NAME_ADAPTER;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.Requirement;
import com.puppetlabs.geppetto.forge.model.SupportedOS;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 */
public class GsonModule extends AbstractModule {

	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class DateJsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		public static String dateToString(Date src) {
			String target;
			synchronized(ISO_8601_TZ) {
				target = ISO_8601_TZ.format(src);
			}
			Matcher m = RFC_822_PTRN.matcher(target);
			if(m.matches()) {
				String tz = m.group(2);
				if("+0000".equals(tz))
					tz = "Z";
				else
					tz = tz.substring(0, 3) + ':' + tz.substring(3, 5);
				target = m.group(1) + tz;
			}
			return target;
		}

		public static Date stringToDate(String source) {
			Matcher m = ISO_8601_PTRN.matcher(source);
			if(m.matches()) {
				String tz = m.group(2);
				if("Z".equals(tz))
					tz = "+0000";
				else
					tz = tz.substring(0, 3) + tz.substring(4, 6);
				source = m.group(1) + tz;
			}
			synchronized(ISO_8601_TZ) {
				try {
					return ISO_8601_TZ.parse(source);
				}
				catch(ParseException e) {
					try {
						return HR_8601_TZ.parse(source);
					}
					catch(ParseException e2) {
						throw new JsonParseException(e);
					}
				}
			}
		}

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return stringToDate(json.getAsString());
		}

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(dateToString(src));
		}
	}

	/**
	 * A json adapter capable of serializing/deserializing the metadata structure.
	 */
	public static class MetadataJsonAdapter implements JsonSerializer<Metadata>, JsonDeserializer<Metadata> {
		// @fmtOff
		public static final Type STRINGS_TYPE = new TypeToken<List<String>>() {}.getType();
		public static final Type TYPES_TYPE = new TypeToken<List<com.puppetlabs.geppetto.forge.model.Type>>() {}.getType();
		public static final Type DEPENDENCIES_TYPE = new TypeToken<List<Dependency>>() {}.getType();
		public static final Type REQUIREMENTS_TYPE = new TypeToken<List<Requirement>>() {}.getType();
		public static final Type SUPPORTEDOS_TYPE = new TypeToken<List<SupportedOS>>() {}.getType();
		// @fmtOn

		private static void addProperty(JsonObject obj, String key, String value) {
			obj.addProperty(key, value == null
					? ""
					: value);
		}

		private static void appendHex(StringBuilder bld, byte b) {
			bld.append(hexChars[(b & 0xf0) >> 4]);
			bld.append(hexChars[b & 0x0f]);
		}

		private static Map<String, byte[]> deserializeChecksums(JsonElement json) throws JsonParseException {

			Set<Map.Entry<String, JsonElement>> entrySet = json.getAsJsonObject().entrySet();
			Map<String, byte[]> result = new HashMap<String, byte[]>();
			for(Map.Entry<String, JsonElement> entry : entrySet) {
				String hexString = entry.getValue().getAsString();
				int top = hexString.length() / 2;
				byte[] bytes = new byte[top];
				for(int idx = 0, cidx = 0; idx < top; ++idx) {
					int val = hexToByte(hexString.charAt(cidx++)) << 4;
					val |= hexToByte(hexString.charAt(cidx++));
					bytes[idx] = (byte) (val & 0xff);
				}
				result.put(entry.getKey(), bytes);
			}
			return result;
		}

		private static int hexToByte(char c) {
			return c >= 'a'
					? c - ('a' - 10)
					: c - '0';
		}

		private static JsonElement serializeChecksums(Map<String, byte[]> src) {
			JsonObject result = new JsonObject();
			StringBuilder hexBuilder = new StringBuilder(32);
			for(Map.Entry<String, byte[]> entry : src.entrySet()) {
				hexBuilder.setLength(0);
				byte[] bytes = entry.getValue();
				for(int idx = 0; idx < bytes.length; ++idx)
					appendHex(hexBuilder, bytes[idx]);
				result.addProperty(entry.getKey(), hexBuilder.toString());
			}
			return result;
		}

		@Override
		public Metadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			if(!json.isJsonObject())
				throw new JsonParseException("Expected JSON object");

			Metadata md = new Metadata();
			for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
				String key = entry.getKey();
				JsonElement val = entry.getValue();
				if("author".equals(key))
					md.setAuthor(val.getAsString());
				else if("checksums".equals(key))
					md.setChecksums(deserializeChecksums(val));
				else if("dependencies".equals(key))
					md.setDependencies(context.<List<Dependency>> deserialize(val, DEPENDENCIES_TYPE));
				else if("description".equals(key))
					md.setDescription(val.getAsString());
				else if("license".equals(key))
					md.setLicense(val.getAsString());
				else if("name".equals(key))
					md.setName(context.<ModuleName> deserialize(val, ModuleName.class));
				else if("operatingsystem_support".equals(key))
					md.setOperatingSystemSupport(context.<List<SupportedOS>> deserialize(val, SUPPORTEDOS_TYPE));
				else if("project_page".equals(key))
					md.setProjectPage(val.getAsString());
				else if("requirements".equals(key))
					md.setRequirements(context.<List<Requirement>> deserialize(val, REQUIREMENTS_TYPE));
				else if("source".equals(key))
					md.setSource(val.getAsString());
				else if("summary".equals(key))
					md.setSummary(val.getAsString());
				else if("tags".equals(key))
					md.setTags(context.<List<String>> deserialize(val, STRINGS_TYPE));
				else if("types".equals(key))
					md.setTypes(context.<List<com.puppetlabs.geppetto.forge.model.Type>> deserialize(val, TYPES_TYPE));
				else if("version".equals(key))
					md.setVersion(context.<Version> deserialize(val, Version.class));
				else
					// We don't know what this is. Parse and assign dynamically
					md.addDynamicAttribute(key, context.deserialize(val, Object.class));
			}
			return md;
		}

		@Override
		public JsonElement serialize(Metadata src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			addProperty(json, "author", src.getAuthor());
			json.add("checksums", serializeChecksums(src.getChecksums()));
			json.add("dependencies", context.serialize(src.getDependencies(), DEPENDENCIES_TYPE));
			addProperty(json, "description", src.getDescription());
			addProperty(json, "license", src.getLicense());
			json.addProperty("name", src.getName() == null
					? ""
					: src.getName().toString());
			json.add("operatingsystem_support", context.serialize(src.getOperatingSystemSupport(), SUPPORTEDOS_TYPE));
			addProperty(json, "project_page", src.getProjectPage());
			json.add("requirements", context.serialize(src.getRequirements(), REQUIREMENTS_TYPE));
			addProperty(json, "source", src.getSource());
			addProperty(json, "summary", src.getSummary());
			json.add("tags", context.serialize(src.getTags(), STRINGS_TYPE));
			json.add("types", context.serialize(src.getTypes(), TYPES_TYPE));
			json.addProperty("version", src.getVersion() == null
					? ""
					: src.getVersion().toString());
			for(Map.Entry<String, Object> dynAttr : src.getDynamicAttributes().entrySet())
				json.add(dynAttr.getKey(), context.serialize(dynAttr.getValue()));
			return json;
		}
	}

	public static class VersionJsonAdapter implements JsonSerializer<Version>, JsonDeserializer<Version> {
		@Override
		public Version deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Version.fromString(json.getAsString());
		}

		@Override
		public JsonElement serialize(Version src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class VersionRangeJsonAdapter implements JsonSerializer<VersionRange>, JsonDeserializer<VersionRange> {
		@Override
		public VersionRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				return VersionRange.create(json.getAsString());
			}
			catch(IllegalArgumentException e) {
				return null;
			}
		}

		@Override
		public JsonElement serialize(VersionRange src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	public static final MetadataJsonAdapter METADATA_ADAPTER = new MetadataJsonAdapter();

	public static final VersionJsonAdapter VERSION_ADAPTER = new VersionJsonAdapter();

	public static final VersionRangeJsonAdapter VERSION_RANGE_ADAPTER = new VersionRangeJsonAdapter();

	public static GsonModule INSTANCE = new GsonModule();

	private static final Pattern ISO_8601_PTRN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d)(Z|(?:[+-]\\d\\d:\\d\\d))$");

	private static final Pattern RFC_822_PTRN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d)([+-]\\d\\d\\d\\d)$");

	private static final SimpleDateFormat ISO_8601_TZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	private static final SimpleDateFormat HR_8601_TZ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

	private static char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private final GsonBuilder gsonBuilder;

	private final GsonBuilder gsonV3Builder;

	private final Gson gson;

	private GsonModule() {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(VersionRange.class, VERSION_RANGE_ADAPTER);
		gsonBuilder.registerTypeAdapter(Version.class, VERSION_ADAPTER);
		gsonBuilder.registerTypeAdapter(Dependency.class, DEPENDENCY_ADAPTER);
		gsonBuilder.registerTypeAdapter(ModuleName.class, MODULE_NAME_ADAPTER);
		gsonBuilder.registerTypeAdapter(Metadata.class, METADATA_ADAPTER);
		gsonBuilder.registerTypeAdapter(Date.class, new DateJsonAdapter());

		gson = gsonBuilder.create();

		gsonV3Builder = new GsonBuilder();
		gsonV3Builder.excludeFieldsWithoutExposeAnnotation();
		gsonV3Builder.serializeNulls();
		gsonV3Builder.registerTypeAdapter(VersionRange.class, VERSION_RANGE_ADAPTER);
		gsonV3Builder.registerTypeAdapter(Version.class, VERSION_ADAPTER);
		gsonV3Builder.registerTypeAdapter(Dependency.class, DEPENDENCY_ADAPTER);
		gsonV3Builder.registerTypeAdapter(ModuleName.class, MODULE_NAME_ADAPTER);
		gsonV3Builder.registerTypeAdapter(Metadata.class, METADATA_ADAPTER);
		gsonV3Builder.setDateFormat("yyyy-MM-dd HH:mm:ss Z");
	}

	@Override
	protected void configure() {
	}

	@Provides
	public Gson provideGson() {
		return gsonBuilder.create();
	}

	@Provides
	public GsonBuilder provideGsonBuilder() {
		return gsonBuilder;
	}

	@Provides
	@Named("gson-v3")
	public Gson provideV3Gson() {
		return gsonV3Builder.create();
	}

	@Provides
	@Named("gson-v3")
	public GsonBuilder provideV3GsonBuilder() {
		return gsonV3Builder;
	}

	/**
	 * Creates a JSON representation for the given object using an internal
	 * synchronized {@link Gson} instance.
	 * 
	 * @param object
	 *            The object to produce JSON for
	 * @return JSON representation of the given <code>object</code>
	 */
	public String toJSON(Object object) {
		synchronized(gson) {
			return gson.toJson(object);
		}
	}
}
