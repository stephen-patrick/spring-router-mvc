package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

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
