package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

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
