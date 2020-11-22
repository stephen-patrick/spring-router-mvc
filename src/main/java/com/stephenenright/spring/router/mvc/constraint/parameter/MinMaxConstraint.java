package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class MinMaxConstraint implements RouteParameterConstraint {

	private long minValue;
	private long maxValue;

	public MinMaxConstraint(long minValue, long maxValue) {
		if (maxValue < minValue) {
			throw new IllegalArgumentException(
					"Max lemngth must be greater than minValue");
		}

		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.min(parameterValue, minValue)
				&& ConstraintUtils.max(parameterValue, maxValue);

	}

}
