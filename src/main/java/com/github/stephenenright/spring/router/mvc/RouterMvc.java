package com.github.stephenenright.spring.router.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;
import com.github.stephenenright.spring.router.mvc.RouteOutputOptions.UrlOuputOptions;

class RouterMvc implements Router {

	private RouteCollection routesCollection;

	public RouterMvc(RouteCollection routesCollection) {
		this.routesCollection = routesCollection;
	}

	public RouteDetail match(HttpRequestWrapper request) {

		if (request.isHttpMethodUnknown()) {
			return null;
		}

		return routesCollection.matchRoute(request);
	}

	public String reverse(String routeName, HttpServletRequest request) {
		return reverse(routeName, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String reverse(String routeName, HttpServletRequest request,
			RouteParameterCollection params) {
		return RouteUtils.joinContextWithPathAsRootRelative(
				request.getContextPath(),
				routesCollection.reverseRoute(routeName, params));
	}

	public String reverse(String controller, String action,
			HttpServletRequest request) {
		return reverse(controller, action, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String reverse(String controller, String action,
			HttpServletRequest request, RouteParameterCollection params) {

		return RouteUtils.joinContextWithPathAsRootRelative(
				request.getContextPath(),
				routesCollection.reverseRoute(controller, action, params));
	}

	public String reverse(String routeName, UrlOuputOptions outputOptions) {
		return reverse(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public String reverse(String routeName, RouteParameterCollection params,
			UrlOuputOptions outputOptions) {

		String path = reverseRoute(routeName, params);

		if (path == null) {
			return null;
		}

		return new UrlBuilder(outputOptions, path).buildUrl();
	}

	public String reverse(String controller, String action,
			UrlOuputOptions outputOptions) {
		return reverse(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public String reverse(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {

		String path = reverseRoute(controller, action, params);

		if (path == null) {
			return null;
		}

		return new UrlBuilder(outputOptions, path).buildUrl();

	}

	public String pattern(String routeName, HttpServletRequest request) {
		return pattern(routeName, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String pattern(String routeName, HttpServletRequest request,
			RouteParameterCollection params) {
		return RouteUtils.joinContextWithPathAsRootRelative(
				request.getContextPath(),
				routesCollection.routePattern(routeName, params));
	}

	public String pattern(String controller, String action,
			HttpServletRequest request) {
		return pattern(controller, action, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public String pattern(String controller, String action,
			HttpServletRequest request, RouteParameterCollection params) {
		return RouteUtils.joinContextWithPathAsRootRelative(
				request.getContextPath(),
				routesCollection.routePattern(controller, action, params));
	}

	public String pattern(String routeName, UrlOuputOptions outputOptions) {
		return pattern(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);

	}

	public String pattern(String routeName, RouteParameterCollection params,
			UrlOuputOptions outputOptions) {

		String path = routePattern(routeName, params);

		if (path == null) {
			return null;
		}

		return new UrlBuilder(outputOptions, path).buildUrl();
	}

	public String pattern(String controller, String action,
			UrlOuputOptions outputOptions) {
		return pattern(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public String pattern(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {

		String path = routePattern(controller, action, params);

		if (path == null) {
			return null;
		}

		return new UrlBuilder(outputOptions, path).buildUrl();

	}

	private String reverseRoute(String routeName,
			RouteParameterCollection params) {
		return routesCollection.reverseRoute(routeName, params);
	}

	private String reverseRoute(String controllerName, String action,
			RouteParameterCollection params) {
		return routesCollection.reverseRoute(controllerName, action, params);
	}

	private String routePattern(String routeName,
			RouteParameterCollection params) {
		return routesCollection.routePattern(routeName, params);
	}

	private String routePattern(String controllerName, String action,
			RouteParameterCollection params) {
		return routesCollection.routePattern(controllerName, action, params);
	}


	public Map<String, Route> getRouteMappingsByName() {
		return routesCollection.getRouteMappingsByName();
	}
	
	@Override
	public Map<String, Map<String, Route>> getConfigRouteMappingsByName() {
		return routesCollection.getConfigRouteMappingsByName();
	}

	public RouteCollection getRoutesCollection() {
		return routesCollection;
	}

	public void setRoutesCollection(RouteCollection routesCollection) {
		this.routesCollection = routesCollection;
	}
}
