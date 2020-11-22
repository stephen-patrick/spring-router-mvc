package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class MinConstraint implements RouteParameterConstraint {

	private long minValue;

	public MinConstraint(long minValue) {
		this.minValue = minValue;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {

		return ConstraintUtils.min(parameterValue, minValue);

	}

}
