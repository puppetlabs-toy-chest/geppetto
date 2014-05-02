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
package com.puppetlabs.geppetto.forge.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puppetlabs.geppetto.forge.client.GsonModule;

/**
 * Super class of all model entitites. Provides basic JSON capability.
 */
public class Entity {
	protected static <T> List<T> asUnmodifiableList(List<T> list) {
		int sz = list == null
				? 0
				: list.size();
		switch(sz) {
			case 0:
				return Collections.emptyList();
			case 1:
				return Collections.singletonList(list.get(0));
			default:
				return Collections.unmodifiableList(new ArrayList<T>(list));
		}
	}

	protected static <K, V> Map<K, V> asUnmodifiableMap(Map<K, V> map) {
		int sz = map == null
				? 0
				: map.size();
		switch(sz) {
			case 0:
				return Collections.emptyMap();
			case 1:
				Map.Entry<K, V> e = map.entrySet().iterator().next();
				return Collections.singletonMap(e.getKey(), e.getValue());
			default:
				return Collections.unmodifiableMap(new HashMap<K, V>(map));
		}
	}

	protected static boolean safeEquals(Object a, Object b) {
		return a == b || a != null && b != null && a.equals(b);
	}

	protected static int safeHash(Object a) {
		return a == null
				? 773
				: a.hashCode();
	}

	protected static String trimToNull(String s) {
		if(s != null) {
			s = s.trim();
			if(s.length() == 0)
				s = null;
		}
		return s;
	}

	/**
	 * Produces a JSON representation of the Entity
	 *
	 * @return The JSON string
	 */
	@Override
	public String toString() {
		return GsonModule.INSTANCE.toJSON(this);
	}
}
