package com.stephenenright.spring.router.mvc.constraint.parameter;

import java.util.regex.Pattern;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

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
