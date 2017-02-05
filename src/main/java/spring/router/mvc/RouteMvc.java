package spring.router.mvc;

import java.util.HashMap;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteParsed.RouteMatchResult;
import spring.router.mvc.constraint.RouteConstraints;

class RouteMvc implements Route {

	private HashMap<String, HttpMethod> methodMap = new HashMap<String, HttpMethod>();
	private String path;
	private String controller;
	private String action;
	private String name;
	private RouteParsed parsedRoute;
	private RouteConstraints constraints;

	
	public RouteMvc(RouteParsed parsedRoute, String name, String path,
			String controller, String action, HttpMethod... httpMethods) {

		this(parsedRoute, name, path, controller, action,
				new RouteConstraints(), httpMethods);
	}

	public RouteMvc(RouteParsed parsedRoute, String name, String path,
			String controller, String action, RouteConstraints constraints,
			HttpMethod... httpMethods) {

		this.name = name;
		this.path = path;
		this.controller = controller;
		this.action = action;
		this.parsedRoute = parsedRoute;
		this.constraints = constraints;

		setMethods(httpMethods);

	}

	public RouteDetail match(HttpRequestWrapper request) {
		if (!matchHttpMethod(request)) {
			return null;
		}

		RouteMatchResult matchResult = parsedRoute.match(request.getPath());

		if (!matchResult.isMatched()) {
			return null;
		}

		RouteDetail routeDetail = new RouteDetail(this, matchResult.getParams());

		if (constraints.matchConstraints(request, routeDetail)) {
			return routeDetail;
		}

		return null;

	}

	public String reverse() {
		return reverse(RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String reverse(RouteParameterCollection params) {
		return parsedRoute.reverse(params);
	}

	public String pattern() {
		return pattern(RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String pattern(RouteParameterCollection params) {
		return parsedRoute.pattern(params);
	}

	private boolean matchHttpMethod(HttpRequestWrapper request) {
		return hasMethod(request.getMethod());

	}

	public String getPath() {
		return path;
	}

	public String getController() {
		return controller;
	}

	public String getAction() {
		return action;
	}

	public String getName() {
		return name;
	}

	public RouteConstraints getConstraints() {
		return constraints;
	}

	protected RouteParsed getParsedRoute() {
		return parsedRoute;
	}

	public boolean hasMethod(HttpMethod method) {
		if (methodMap.containsKey(HttpMethod.ANY.toString())) {
			return true;
		}

		return methodMap.containsKey(method.toString());
	}

	public boolean hasMethod(String httpMethod) {
		try {
			return hasMethod(HttpMethod.valueOf(httpMethod.toUpperCase()));

		} catch (Exception e) {
			return false;
		}
	}

	private void setMethods(HttpMethod... methods) {
		if (methods.length == 0) {
			methodMap.put(HttpMethod.ANY.toString(), HttpMethod.ANY);
			return;
		}

		for (HttpMethod method : methods) {
			if (!methodMap.containsKey(method.toString())) {
				methodMap.put(method.toString(), method);
			}
		}
	}

	public String toString() {
		return String.format(
				"Route: Name: %s, Controller: %s, Action: %s, Path: %s",
				RouteUtils.isNullOrEmpty(name) ? "" : name,
				RouteUtils.isNullOrEmpty(controller) ? "" : controller,
				RouteUtils.isNullOrEmpty(action) ? "" : action,
				RouteUtils.isNullOrEmpty(path) ? "" : path);

	}
}
