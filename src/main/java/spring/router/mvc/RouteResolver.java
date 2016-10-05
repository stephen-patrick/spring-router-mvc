package spring.router.mvc;

import javax.servlet.http.HttpServletRequest;

import spring.router.mvc.RouteOutputOptions.UrlOuputOptions;

public interface RouteResolver {

	public String reverse(String routeName, HttpServletRequest request);

	public String reverse(String routeName, HttpServletRequest request, RouteParameterCollection params);

	public String reverse(String controller, String action, HttpServletRequest request);

	public String reverse(String controller, String action, HttpServletRequest request,
			RouteParameterCollection params);

	public String reverse(String routeName, UrlOuputOptions outputOptions);

	public String reverse(String routeName, RouteParameterCollection params,
			UrlOuputOptions outputOptions);

	public String reverse(String controller, String action,
			UrlOuputOptions outputOptions);

	public String reverse(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions);

	public String pattern(String routeName, HttpServletRequest request);

	public String pattern(String routeName, HttpServletRequest request, RouteParameterCollection params);

	public String pattern(String controller, String action, HttpServletRequest request);

	public String pattern(String controller, String action, HttpServletRequest request,
			RouteParameterCollection params);

	public String pattern(String routeName, UrlOuputOptions outputOptions);

	public String pattern(String routeName, RouteParameterCollection params,
			UrlOuputOptions outputOptions);

	public String pattern(String controller, String action,
			UrlOuputOptions outputOptions);

	public String pattern(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions);
}
