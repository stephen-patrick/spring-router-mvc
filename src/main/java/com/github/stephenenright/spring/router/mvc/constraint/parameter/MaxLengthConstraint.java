package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import com.github.stephenenright.spring.router.mvc.RouteDetail;
import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class MaxLengthConstraint implements RouteParameterConstraint {

	private int maxLength;

	private static final RouteParameterConstraint INSTANCE_255 = new MaxLengthConstraint(
			255);

	public MaxLengthConstraint(int maxLength) {
		if (maxLength <= 0) {
			throw new IllegalArgumentException(
					"Max length must be greater than zero");
		}

		this.maxLength = maxLength;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {

		return ConstraintUtils.maxLength(parameterValue, maxLength);
	}

	public static RouteParameterConstraint string255() {
		return INSTANCE_255;
	}
}
