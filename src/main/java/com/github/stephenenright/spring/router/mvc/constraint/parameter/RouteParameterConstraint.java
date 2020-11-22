package com.github.stephenenright.spring.router.mvc.constraint.parameter;


import com.github.stephenenright.spring.router.mvc.RouteDetail;
import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface RouteParameterConstraint {
	
	boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue);

}