package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;


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
