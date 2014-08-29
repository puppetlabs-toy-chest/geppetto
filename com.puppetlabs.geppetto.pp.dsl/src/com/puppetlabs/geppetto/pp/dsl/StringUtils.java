/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl;

/**
 * Some utilities to efficiently deal with up/down case differences on initial
 * character.
 */
public class StringUtils {

	public static void appendCount(int count, int otherCount, StringBuilder bld) {
		if(otherCount >= 0 && otherCount < 2) {
			if(count == 0)
				bld.append("zero");
			else if(count == 1 && otherCount < 2)
				bld.append("one");
			else if(count == 2 && otherCount < 2)
				bld.append("two");
			else
				bld.append(count);
		}
		else
			bld.append(count);
	}

	public static void appendInitialUpperCase(String s, StringBuilder bld) {
		if(s != null) {
			int len = s.length();
			if(len > 0) {
				bld.append(Character.toUpperCase(s.charAt(0)));
				if(len > 1)
					bld.append(s, 1, len);
			}
		}
	}

	public static boolean equalsIgnoreInitialCase(String a, String b) {
		if(a == b)
			return true;
		if(a == null || b == null)
			return false;

		int len = a.length();
		if(len != b.length())
			return false;
		if(len == 0)
			return true;

		char ac = a.charAt(0);
		char bc = a.charAt(0);
		if(ac != bc) {
			char lac = Character.toLowerCase(ac);
			if(lac != bc) {
				if(lac != ac)
					// We lower cased ac so neither upper or lower case ac == bc
					return false;

				// ac was already lower case, Try lower case bc as well
				if(ac != Character.toLowerCase(bc))
					return false;
			}
		}
		for(int i = 1; i < len; ++i)
			if(a.charAt(i) != b.charAt(i))
				return false;
		return true;
	}

	public static boolean isWovel(char c) {
		return "AEIOUYaeiouy".indexOf(c) >= 0;
	}

	public static boolean startsWithWovel(String s) {
		return s != null && s.length() > 0 && isWovel(s.charAt(0));
	}

	public static String toInitialLowerCase(String s) {
		if(!(s == null || s.isEmpty())) {
			char c = s.charAt(0);
			char l = Character.toLowerCase(c);
			if(c != l) {
				StringBuilder buf = new StringBuilder(s);
				buf.setCharAt(0, l);
				s = buf.toString();
			}
		}
		return s;
	}

	public static String toInitialUpperCase(String s) {
		if(!(s == null || s.isEmpty())) {
			char c = s.charAt(0);
			char l = Character.toUpperCase(c);
			if(c != l) {
				StringBuilder buf = new StringBuilder(s);
				buf.setCharAt(0, l);
				s = buf.toString();
			}
		}
		return s;
	}
}
