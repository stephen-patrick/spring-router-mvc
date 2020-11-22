package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import com.github.stephenenright.spring.router.mvc.RouteDetail;
import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class MaxConstraint implements RouteParameterConstraint {

	private long maxValue;

	public MaxConstraint(long maxValue) {
		this.maxValue = maxValue;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {

		return ConstraintUtils.max(parameterValue, maxValue);

	}

}
