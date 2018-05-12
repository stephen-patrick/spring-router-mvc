package spring.router.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouterExceptions.RouteResolveException;

class RouteCollection {

	private final Map<String, Route> routesNameMapping;
	private final Map<String, Route> routesActionMapping;

	private final Map<String, Map<String, Route>> configRoutesNameMapping;
	private final List<Route> routesList = new LinkedList<Route>();

	public RouteCollection() {
		configRoutesNameMapping = new HashMap<>();
		routesNameMapping = new HashMap<String, Route>();
		routesActionMapping = new HashMap<String, Route>();
	}

	public boolean containsName(String name) {
		if (RouteUtils.isNullOrEmpty(name)) {
			return false;
		}

		if (routesNameMapping.containsKey(createRouteKey(name))) {
			return true;
		}

		return false;
	}

	public Route get(String name) {
		if (RouteUtils.isNullOrEmpty(name)) {
			return null;
		}

		return routesNameMapping.get(createRouteKey(name));
	}

	public Route get(String controller, String action) {
		if (RouteUtils.isAnyNullOrEmpty(controller, action)) {
			return null;
		}

		return routesActionMapping.get(createRouteKey(controller, action));
	}

	
	public void add(String configName, Route route) {
		if (!containsName(route.getName())) {
			final String routeKey = createRouteKey(route.getName());
			addRouteNameMappingForConfig(configName, routeKey, route);
			routesNameMapping.put(routeKey, route);
			routesActionMapping.put(
					createRouteKey(route.getController(), route.getAction()),
					route);
			routesList.add(route);
		}
	}
	
	private void addRouteNameMappingForConfig(String configName, String routeKey, Route route) {
		Map<String, Route> routesNameMapping = configRoutesNameMapping.get(configName);
		
		if(routesNameMapping==null) {
			routesNameMapping = new HashMap<>();
			configRoutesNameMapping.put(configName, routesNameMapping);
		}
		
		routesNameMapping.put(routeKey, route);
	}
	
	public RouteDetail matchRoute(HttpRequestWrapper request) {
		for (Route route : routesList) {
			RouteDetail routeDetail = route.match(request);

			if (routeDetail != null) {
				return routeDetail;
			}
		}

		return null;
	}

	public String reverseRoute(String routeName) {
		return reverseRoute(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String reverseRoute(String routeName, RouteParameterCollection params) {
		Route route = get(routeName);

		if (route == null) {
			throw new RouteResolveException(String.format(
					"Route not found for name: %s", routeName));
		}

		return route.reverse(params);
	}

	public String reverseRoute(String controller, String action) {
		return reverseRoute(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String reverseRoute(String controller, String action,
			RouteParameterCollection params) {

		Route route = get(controller, action);

		if (route == null) {
			throw new RouteResolveException(String.format(
					"Route not found for Controller: %s, Action: %s",
					controller, action));
		}

		return route.reverse(params);

	}

	public String routePattern(String routeName) {
		return routePattern(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String routePattern(String routeName, RouteParameterCollection params) {
		Route route = get(routeName);

		if (route == null) {
			throw new RouteResolveException(String.format(
					"Route not found for name: %s", routeName));
		}

		return route.pattern(params);
	}

	public String routePattern(String controller, String action) {
		return routePattern(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String routePattern(String controller, String action,
			RouteParameterCollection params) {

		Route route = get(controller, action);

		if (route == null) {
			throw new RouteResolveException(String.format(
					"Route not found for Controller: %s, Action: %s",
					controller, action));
		}

		return route.pattern(params);
	}

	private String createRouteKey(String name) {
		return name;
	}

	private String createRouteKey(String controller, String action) {
		return controller + "." + action;
	}
	
	public Map<String, Route> getRouteMappingsByName() {
		return Collections.unmodifiableMap(routesNameMapping);
	}
	
	public Map<String,Map<String, Route>> getConfigRouteMappingsByName() {
		return Collections.unmodifiableMap(configRoutesNameMapping);
	}
}
