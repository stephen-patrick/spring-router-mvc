package com.stephenenright.spring.router.mvc;

import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;
import com.stephenenright.spring.router.mvc.constraint.RouteConstraints;

public interface Route {
	RouteDetail match(HttpRequestWrapper request);

	String reverse();

	String reverse(RouteParameterCollection params);

	String pattern();

	String pattern(RouteParameterCollection params);

	String getPath();

	String getController();

	String getAction();

	String getName();
	
	RouteConstraints getConstraints();

	boolean hasMethod(HttpMethod method);

	boolean hasMethod(String httpMethod);
}
