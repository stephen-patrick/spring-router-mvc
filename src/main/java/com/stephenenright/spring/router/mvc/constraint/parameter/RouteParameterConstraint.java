package com.stephenenright.spring.router.mvc.constraint.parameter;


import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface RouteParameterConstraint {
	
	boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue);

}