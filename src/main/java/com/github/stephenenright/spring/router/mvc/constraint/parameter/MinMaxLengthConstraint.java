package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import com.github.stephenenright.spring.router.mvc.RouteDetail;
import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;


public class MinMaxLengthConstraint implements RouteParameterConstraint {

	private int minLength;
	private int maxLength;

	public MinMaxLengthConstraint(int minLength, int maxLength) {
		if (maxLength < minLength || (minLength <=0 || maxLength <=0)) {
			throw new IllegalArgumentException(
					"Max lemngth must be greater than minLength");
		}

		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.minLength(parameterValue, minLength)
				&& ConstraintUtils.maxLength(parameterValue, maxLength);

	}

}
