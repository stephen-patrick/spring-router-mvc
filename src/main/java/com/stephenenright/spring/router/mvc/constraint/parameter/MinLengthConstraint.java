package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class MinLengthConstraint implements RouteParameterConstraint {

	private int minLength;

	public MinLengthConstraint(int minLength) {
		if(minLength <= 0) {
			throw new IllegalArgumentException("Min length must be greater than zero");
		}
		
		
		this.minLength = minLength;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {

		return ConstraintUtils.minLength(parameterValue, minLength);

	}

}
