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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.puppetlabs.geppetto.forge.model.Checksums;
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
	 * A json adapter capable of serializing/deserializing the Checksums structure.
	 */
	public static class ChecksumsJsonAdapter implements JsonSerializer<Checksums>, JsonDeserializer<Checksums> {
		@Override
		public Checksums deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Checksums checksums = new Checksums();
			checksums.setChecksums(deserializeChecksums(json));
			return checksums;
		}

		@Override
		public JsonElement serialize(Checksums src, Type typeOfSrc, JsonSerializationContext context) {
			return serializeChecksums(src.getChecksums());
		}

	}

	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class DateJsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		public static String dateToString(Date src) {
			synchronized(HR_8601_TZ) {
				return HR_8601_TZ.format(src);
			}
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

	public static class InlineJson {
		private final String json;

		public InlineJson(String json) {
			this.json = json;
		}

		public String getJson() {
			return json;
		}
	}

	public class InlineJsonAdapter implements JsonSerializer<InlineJson>, JsonDeserializer<InlineJson> {
		@Override
		public InlineJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Gson gson = getGson();
			synchronized(gson) {
				return new InlineJson(gson.toJson(json));
			}
		}

		@Override
		public JsonElement serialize(InlineJson src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonParser().parse(src.getJson());
		}
	}

	/**
	 * A json adapter capable of serializing/deserializing the metadata structure.
	 */
	public static class MetadataJsonAdapter implements JsonSerializer<Metadata>, JsonDeserializer<Metadata> {
		private static void addProperty(JsonObject obj, String key, String value) {
			obj.addProperty(key, value == null
					? ""
					: value);
		}

		private static List<SupportedOS> deserializeSupportedOs(JsonElement supportedOS,
				JsonDeserializationContext context) {
			if(supportedOS == null || !supportedOS.isJsonArray())
				return Collections.emptyList();

			JsonArray soss = supportedOS.getAsJsonArray();
			List<SupportedOS> result = new ArrayList<SupportedOS>(soss.size());
			for(JsonElement elem : soss) {
				SupportedOS sos;
				if(elem.isJsonPrimitive()) {
					sos = new SupportedOS();
					sos.setOperatingSystem(elem.getAsString());
				}
				else if(elem.isJsonObject())
					sos = context.<SupportedOS> deserialize(elem, SupportedOS.class);
				else
					continue;
				result.add(sos);
			}
			return result;
		}

		// @fmtOff
		public static final Type TYPES_TYPE = new TypeToken<List<com.puppetlabs.geppetto.forge.model.Type>>() {}.getType();
		public static final Type DEPENDENCIES_TYPE = new TypeToken<List<Dependency>>() {}.getType();

		public static final Type REQUIREMENTS_TYPE = new TypeToken<List<Requirement>>() {}.getType();

		public static final Type SUPPORTEDOS_TYPE = new TypeToken<List<SupportedOS>>() {}.getType();
		// @fmtOn

		@Override
		public Metadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			if(!json.isJsonObject())
				throw new JsonParseException("Expected JSON object");

			Metadata md = new Metadata();
			List<Requirement> reqs = new ArrayList<Requirement>();
			for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
				String key = entry.getKey();
				JsonElement val = entry.getValue();
				if("author".equals(key))
					md.setAuthor(getString(val));
				else if("checksums".equals(key))
					md.setChecksums(deserializeChecksums(val));
				else if("dependencies".equals(key))
					md.setDependencies(context.<List<Dependency>> deserialize(val, DEPENDENCIES_TYPE));
				else if("description".equals(key))
					md.setDescription(getString(val));
				else if("issues_url".equals(key))
					md.setIssuesURL(getString(val));
				else if("license".equals(key))
					md.setLicense(getString(val));
				else if("name".equals(key))
					md.setName(context.<ModuleName> deserialize(val, ModuleName.class));
				else if("operatingsystem_support".equals(key))
					md.setOperatingSystemSupport(deserializeSupportedOs(val, context));
				else if("project_page".equals(key))
					md.setProjectPage(getString(val));
				else if("requirements".equals(key))
					reqs.addAll(context.<List<Requirement>> deserialize(val, REQUIREMENTS_TYPE));
				else if("source".equals(key))
					md.setSource(getString(val));
				else if("summary".equals(key))
					md.setSummary(getString(val));
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
			if(!reqs.isEmpty())
				md.setRequirements(reqs);
			return md;
		}

		@Override
		public JsonElement serialize(Metadata src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			addProperty(json, "author", src.getAuthor());

			// Checksums are deprecated (moved to separate file)
			Map<String, byte[]> checksums = src.getChecksums();
			if(!(checksums == null || checksums.isEmpty()))
				json.add("checksums", serializeChecksums(checksums));

			// Mandatory
			json.add("dependencies", context.serialize(src.getDependencies(), DEPENDENCIES_TYPE));

			// Description is deprecated
			String tmp = src.getDescription();
			if(tmp != null)
				addProperty(json, "description", tmp);

			// Types are deprecated
			List<com.puppetlabs.geppetto.forge.model.Type> types = src.getTypes();
			if(!(types == null || types.isEmpty()))
				json.add("types", context.serialize(types, TYPES_TYPE));

			// Optional
			tmp = src.getIssuesURL();
			if(tmp != null)
				addProperty(json, "issues_url", tmp);

			// Mandatory
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

	public static void addProperty(JsonObject json, String key, Boolean value) {
		if(value != null)
			json.addProperty(key, value);
	}

	public static void addProperty(JsonObject json, String key, Date value) {
		if(value != null)
			json.addProperty(key, DateJsonAdapter.dateToString(value));
	}

	public static void addProperty(JsonObject json, String key, Number value) {
		if(value != null)
			json.addProperty(key, value);
	}

	public static void addProperty(JsonObject json, String key, String value) {
		if(value != null)
			json.addProperty(key, value);
	}

	private static void appendHex(StringBuilder bld, byte b) {
		bld.append(hexChars[(b & 0xf0) >> 4]);
		bld.append(hexChars[b & 0x0f]);
	}

	private static Map<String, byte[]> deserializeChecksums(JsonElement json) throws JsonParseException {

		Set<Map.Entry<String, JsonElement>> entrySet = json.getAsJsonObject().entrySet();
		Map<String, byte[]> result = new HashMap<String, byte[]>(entrySet.size());
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

	public static Boolean getBoolean(JsonElement json) {
		return json.isJsonNull()
				? null
				: json.getAsBoolean();
	}

	public static Date getDate(JsonElement json) {
		return json.isJsonNull()
				? null
				: DateJsonAdapter.stringToDate(json.getAsString());
	}

	public static Integer getInteger(JsonElement json) {
		return json.isJsonNull()
				? null
				: json.getAsInt();
	}

	public static Long getLong(JsonElement json) {
		return json.isJsonNull()
				? null
				: json.getAsLong();
	}

	public static String getString(JsonElement json) {
		return json.isJsonNull()
				? null
				: json.getAsString();
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

	public static final Type STRINGS_TYPE = new TypeToken<List<String>>() {
	}.getType();

	public static GsonModule INSTANCE = new GsonModule();

	private static final Pattern ISO_8601_PTRN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d)(Z|(?:[+-]\\d\\d:\\d\\d))$");

	private static final SimpleDateFormat ISO_8601_TZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	private static final SimpleDateFormat HR_8601_TZ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

	private static char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private final GsonBuilder gsonBuilder;

	private final Gson gson;

	private GsonModule() {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Checksums.class, new ChecksumsJsonAdapter());
		gsonBuilder.registerTypeAdapter(VersionRange.class, new VersionRangeJsonAdapter());
		gsonBuilder.registerTypeAdapter(Version.class, new VersionJsonAdapter());
		gsonBuilder.registerTypeAdapter(Dependency.class, DEPENDENCY_ADAPTER);
		gsonBuilder.registerTypeAdapter(ModuleName.class, MODULE_NAME_ADAPTER);
		gsonBuilder.registerTypeAdapter(Metadata.class, new MetadataJsonAdapter());
		gsonBuilder.registerTypeAdapter(Date.class, new DateJsonAdapter());
		gsonBuilder.registerTypeAdapter(InlineJson.class, new InlineJsonAdapter());

		gson = gsonBuilder.create();
	}

	@Override
	protected void configure() {
	}

	/**
	 * @return a predeclared gson instance. Must be synchronized by user
	 */
	public Gson getGson() {
		return gson;
	}

	@Provides
	public Gson provideGson() {
		return gsonBuilder.create();
	}

	@Provides
	public GsonBuilder provideGsonBuilder() {
		return gsonBuilder;
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
