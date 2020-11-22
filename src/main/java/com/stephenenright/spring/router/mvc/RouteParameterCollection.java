package com.stephenenright.spring.router.mvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RouteParameterCollection {

	private final Map<String, String> valuesMap = new HashMap<String, String>();

	public RouteParameterCollection() {

	}

	public RouteParameterCollection(String[] keys, Object[] values) {
		if (keys.length != values.length) {
			throw new IllegalArgumentException(
					"Parameters: Keys and values length must match.");
		}

		for (int i = 0; i < keys.length; i++) {
			add(keys[i], values[i].toString());
		}
	}

	public RouteParameterCollection(Map<String, Object> valuesMap) {
		for (String key : valuesMap.keySet()) {
			add(key, valuesMap.get(key).toString());
		}
	}

	public boolean containsKey(String key) {
		return valuesMap.containsKey(key);
	}

	public void add(String key, String value) {
		boolean exists = valuesMap.containsKey(key);

		if (!exists) {
			this.valuesMap.put(key, value);
		}
	}

	public void add(String key, Object value) {
		add(key, value.toString());
	}

	public void addAll(RouteParameterCollection routeValuesCollection) {

		for (String key : routeValuesCollection.keys()) {
			add(key, routeValuesCollection.getOrDefault(key, ""));
		}
	}

	public Set<String> keys() {
		return valuesMap.keySet();
	}

	public boolean isEmpty() {
		return valuesMap.isEmpty();
	}

	public void addAll(String key, String value) {
		boolean exists = valuesMap.containsKey(key);

		if (!exists) {
			this.valuesMap.put(key, value);
		}

	}

	public Collection<String> values() {
		return valuesMap.values();
	}

	public String getOrDefault(String key, String defaultValue) {
		String value = valuesMap.get(key);
		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	public Map<String, String> getValues() {
		return valuesMap;
	}

}
