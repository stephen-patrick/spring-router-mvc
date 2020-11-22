package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class RequiredConstraint implements RouteParameterConstraint {

	private static final RouteParameterConstraint INSTANCE = new RequiredConstraint();

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.required(parameterValue);
	}

	public static RouteParameterConstraint required() {
		return INSTANCE;
	}

}
