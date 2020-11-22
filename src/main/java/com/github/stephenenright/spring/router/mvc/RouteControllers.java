package com.github.stephenenright.spring.router.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class RouteControllers {

	private final Map<String, Object> controllersMap;

	public RouteControllers(Map<String, Object> controllers) {
		controllersMap = new HashMap<String, Object>();

		for (String key : controllers.keySet()) {
			if (!controllersMap.containsKey(key.toLowerCase())) {
				controllersMap.put(key.toLowerCase(), controllers.get(key));
			}

		}
	}

	public boolean contains(String controllerName) {
		return controllersMap.containsKey(controllerName.toLowerCase());
	}

	public Object get(String controllerName) {
		return controllersMap.get(controllerName.toLowerCase());
	}

	public Method findAction(String controllerName, String actionName) {
		Object controller = get(controllerName);

		if (controller == null) {
			return null;
		}

		return RouteUtils.findPublicMethod(controller, actionName);

	}

}
