/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.v2.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A qualified name that is case insensitive and also separator insensitive when performing comparisons
 * and hash code calculations. The instance does however preserve both case and the separator. The
 * created instance is immutable and suitable for use as key in hash tables and trees.
 */
public class ModuleName implements Serializable, Comparable<ModuleName> {
	public static class JsonAdapter implements JsonDeserializer<ModuleName>, JsonSerializer<ModuleName> {
		@Override
		public ModuleName deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return new ModuleName(json.getAsString().toLowerCase());
		}

		@Override
		public JsonElement serialize(ModuleName src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	private static final long serialVersionUID = 1L;

	private final char separator;

	private final String owner;

	private final String name;

	private static final String NO_VALUE = "";

	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*$");

	/**
	 * Checks that the given name only contains lowercase letters, numbers and underscores and that it begins with a
	 * letter
	 * 
	 * @param name
	 *            The name to check
	 * @return The checked name
	 * @throws IllegalArgumentException
	 *             if the name is illegal
	 */
	public static String checkName(String name) throws IllegalArgumentException {
		Matcher m = NAME_PATTERN.matcher(name);
		if(m.matches())
			return name;
		throw new IllegalArgumentException(
			"Module names should only contain lowercase letters, numbers, and underscores, and should begin with a letter");
	}

	/**
	 * Creates a name from a string with a separator. The separator can be either '-' (dash) or '/' (slash). If more
	 * than
	 * one separator is present, then one placed last wins.
	 * last in the string will be considered the separator. Thus<br/>
	 * &quot;foo-bar-baz&quot; yields owner = &quot;foo-bar&quot;, name = &quot;baz&quot;, separator '-'<br/>
	 * &quot;foo/bar-baz&quot; yields owner = &quot;foo/bar&quot;, name = &quot;baz&quot;, separator '-'<br/>
	 * &quot;foo/bar/baz&quot; yields owner = &quot;foo/bar&quot;, name = &quot;baz&quot;, separator '/'<br/>
	 * &quot;foo-bar/baz&quot; yields owner = &quot;foo-bar&quot;, name = &quot;baz&quot;, separator '/'<br/>
	 * 
	 * @param fullName
	 */
	public ModuleName(String fullName) {
		int dashIdx = fullName.lastIndexOf('-');
		int idx = fullName.lastIndexOf('/');
		if(dashIdx > idx) {
			separator = '-';
			idx = dashIdx;
		}
		else
			separator = '/';

		if(!(idx > 0 && idx < fullName.length() - 1))
			throw new IllegalArgumentException("Name should be in the form <owner>-<name> or <owner>/<name>");

		this.owner = checkName(fullName.substring(0, idx));
		this.name = checkName(fullName.substring(idx + 1));
	}

	/**
	 * Creates a name using specified owner, name, and separator.
	 * 
	 * @param owner
	 * @param separator
	 * @param name
	 */
	public ModuleName(String owner, char separator, String name) {
		this.owner = owner == null
				? NO_VALUE
				: checkName(owner);

		if(!(separator == '-' || separator == '/'))
			throw new IllegalArgumentException("Name should be in the form <owner>-<name> or <owner>/<name>");

		this.separator = separator;
		this.name = name == null
				? NO_VALUE
				: checkName(name);
	}

	public ModuleName(String qualifier, String name) {
		this(qualifier, '/', name);
	}

	/**
	 * <p>
	 * Compare this name to <tt>other</tt> for lexical magnitude using case insensitive comparisons. The separator is
	 * considered but only after both owner and names are equal.
	 * </p>
	 * 
	 * @param other
	 *            The name to compare this name to.
	 * @return a positive integer to indicate that this name is lexicographically greater than <tt>other</tt>.
	 */
	@Override
	public int compareTo(ModuleName other) {
		int cmp = owner.compareTo(other.owner);
		if(cmp == 0) {
			cmp = name.compareTo(other.name);
			if(cmp == 0)
				cmp = separator - other.separator;
		}
		return cmp;
	}

	/**
	 * Compares the two names for equality. Names can have different separators or different case and still be equal.
	 * 
	 * @return The result of the comparison.
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof ModuleName))
			return false;
		ModuleName qo = (ModuleName) o;
		return owner.equals(qo.owner) && name.equals(qo.name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the separator
	 */
	public char getSeparator() {
		return separator;
	}

	/**
	 * Computes the hash value for this qualified name. The separator is excluded from the computation
	 * 
	 * @return The computed hash code.
	 */
	@Override
	public int hashCode() {
		return owner.hashCode() * 31 + name.hashCode();
	}

	/**
	 * Returns the string representation fo this instance.
	 */
	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld);
		return bld.toString();
	}

	/**
	 * Present this object as a string onto the given builder.
	 * 
	 * @param builder
	 */
	public void toString(StringBuilder builder) {
		builder.append(owner);
		builder.append(separator);
		builder.append(name);
	}

	/**
	 * Returns an instance that is guaranteed to have the given
	 * separator. The returned instance might be this instance or
	 * a new instance depending on if this instance already has the
	 * given separator.
	 * 
	 * @param separator
	 * @return A name with the given separator, possibly this instance
	 */
	public ModuleName withSeparator(char separator) {
		return this.separator == separator
				? this
				: new ModuleName(this.owner, separator, this.name);
	}
}
