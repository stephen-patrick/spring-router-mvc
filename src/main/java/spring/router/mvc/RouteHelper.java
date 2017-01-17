package spring.router.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteOutputOptions.UrlOuputOptions;

public class RouteHelper {

	private static RouteResolver resolver;

	public static String reverse(String routeName) {
		return reverse(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String reverse(String routeName,
			RouteParameterCollection params) {
		return resolver.reverse(routeName,
				HttpRequestWrapper.getCurrentServletRequest(), params);
	}

	public static String reverse(String routeName, HttpServletRequest request) {
		return reverse(routeName, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String reverse(String routeName, HttpServletRequest request,
			RouteParameterCollection params) {
		return resolver.reverse(routeName, request, params);
	}

	public static String reverse(String controller, String action) {
		return reverse(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String reverse(String controller, String action,
			RouteParameterCollection params) {
		return reverse(controller, action,
				HttpRequestWrapper.getCurrentServletRequest(),
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String reverse(String controller, String action,
			HttpServletRequest request) {
		return reverse(controller, action, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String reverse(String controller, String action,
			HttpServletRequest request, RouteParameterCollection params) {
		return resolver.reverse(controller, action, request, params);
	}

	public static String reverse(String routeName, UrlOuputOptions outputOptions) {
		return reverse(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public static String reverse(String routeName,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {
		return resolver.reverse(routeName, params, outputOptions);
	}

	public static String reverse(String controller, String action,
			UrlOuputOptions outputOptions) {
		return reverse(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public static String reverse(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {
		return resolver.reverse(controller, action, params, outputOptions);
	}

	public static String pattern(String routeName) {
		return pattern(routeName,
				HttpRequestWrapper.getCurrentServletRequest(),
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String pattern(String routeName,
			RouteParameterCollection params) {
		return pattern(routeName,
				HttpRequestWrapper.getCurrentServletRequest(), params);
	}

	public static String pattern(String routeName, HttpServletRequest request) {
		return pattern(routeName, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String pattern(String routeName, HttpServletRequest request,
			RouteParameterCollection params) {
		return resolver.pattern(routeName, request, params);

	}

	public static String pattern(String controller, String action) {
		return pattern(controller, action,
				HttpRequestWrapper.getCurrentServletRequest(),
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String pattern(String controller, String action,
			RouteParameterCollection params) {
		return pattern(controller, action,
				HttpRequestWrapper.getCurrentServletRequest(), params);
	}

	public static String pattern(String controller, String action,
			HttpServletRequest request) {
		return pattern(controller, action, request,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION);
	}

	public static String pattern(String controller, String action,
			HttpServletRequest request, RouteParameterCollection params) {
		return resolver.pattern(controller, action, request, params);

	}

	public static String pattern(String routeName, UrlOuputOptions outputOptions) {
		return pattern(routeName,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);
	}

	public static String pattern(String routeName,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {
		return resolver.pattern(routeName, params, outputOptions);
	}

	public static String pattern(String controller, String action,
			UrlOuputOptions outputOptions) {
		return pattern(controller, action,
				RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION, outputOptions);

	}

	public static String pattern(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions) {
		return resolver.pattern(controller, action, params, outputOptions);
	}
	
	public static Map<String, Route> getRouteMappingsByName() {
		return resolver.getRouteMappingsByName();
	}
	
	static RouteResolver getResolver() {
		return resolver;
	}

	static void setResolver(RouteResolver resolver) {
		RouteHelper.resolver = resolver;
	}

}
