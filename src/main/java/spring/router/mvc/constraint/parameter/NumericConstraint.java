package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

public class NumericConstraint implements RouteParameterConstraint {

	private static final RouteParameterConstraint INSTANCE = new NumericConstraint();

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.isNumeric(parameterValue);
	}

	public static RouteParameterConstraint numeric() {
		return INSTANCE;
	}

}
