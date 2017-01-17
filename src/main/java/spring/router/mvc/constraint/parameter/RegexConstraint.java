package spring.router.mvc.constraint.parameter;

import java.util.regex.Pattern;

import spring.router.mvc.Http.HttpRequestWrapper;

import spring.router.mvc.RouteDetail;

public class RegexConstraint implements RouteParameterConstraint {

	private Pattern pattern;

	public RegexConstraint(String regex) {
		this(Pattern.compile(regex));
	}

	public RegexConstraint(Pattern regex) {
		pattern = regex;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue) {

		if (!ConstraintUtils.required(parameterValue)) {
			return false;
		}

		return pattern.matcher(parameterValue).matches();

	}

	public static RouteParameterConstraint regex(String regex) {
		return new RegexConstraint(regex);
	}
}
