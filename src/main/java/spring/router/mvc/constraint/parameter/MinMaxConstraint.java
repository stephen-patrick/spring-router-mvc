package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

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
