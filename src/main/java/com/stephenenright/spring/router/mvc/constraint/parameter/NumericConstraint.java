package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class NumericConstraint implements RouteParameterConstraint {

	private static final RouteParameterConstraint INSTANCE = new NumericConstraint();

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.isNumeric(parameterValue);
	}

	public static RouteParameterConstraint numeric() {
		return INSTANCE;
	}

}
