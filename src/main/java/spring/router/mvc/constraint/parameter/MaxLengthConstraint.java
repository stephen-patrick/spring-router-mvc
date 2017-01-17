package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

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
