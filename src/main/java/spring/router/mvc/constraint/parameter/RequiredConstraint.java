package spring.router.mvc.constraint.parameter;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

public class RequiredConstraint implements RouteParameterConstraint {

	private static final RouteParameterConstraint INSTANCE = new RequiredConstraint();

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {
		return ConstraintUtils.required(parameterValue);
	}

	public static RouteParameterConstraint required() {
		return INSTANCE;
	}

}
