package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

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
