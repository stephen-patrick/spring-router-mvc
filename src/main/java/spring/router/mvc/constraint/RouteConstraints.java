package spring.router.mvc.constraint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteDetail;
import spring.router.mvc.constraint.parameter.ConstraintUtils;
import spring.router.mvc.constraint.parameter.EmailConstraint;
import spring.router.mvc.constraint.parameter.MaxConstraint;
import spring.router.mvc.constraint.parameter.MaxLengthConstraint;
import spring.router.mvc.constraint.parameter.MinConstraint;
import spring.router.mvc.constraint.parameter.MinLengthConstraint;
import spring.router.mvc.constraint.parameter.MinMaxConstraint;
import spring.router.mvc.constraint.parameter.MinMaxLengthConstraint;
import spring.router.mvc.constraint.parameter.NumericConstraint;
import spring.router.mvc.constraint.parameter.RegexConstraint;
import spring.router.mvc.constraint.parameter.RouteParameterConstraint;
import spring.router.mvc.constraint.parameter.SlugConstraint;

public class RouteConstraints {

	private final List<RouteConstraint> routeContraints;
	private final Map<String, List<RouteParameterConstraint>> parameterConstraintsMap;

	public RouteConstraints() {
		routeContraints = new LinkedList<RouteConstraint>();
		parameterConstraintsMap = new HashMap<String, List<RouteParameterConstraint>>();
	}

	public RouteConstraints forRoute(RouteConstraint constraint) {
		if (constraint == null) {
			throw new IllegalArgumentException("Constraint required");
		}

		routeContraints.add(constraint);
		return this;
	}

	public RouteConstraints forParam(String paramName,
			RouteParameterConstraint... constraints) {

		if (ConstraintUtils.isNullOrEmpty(paramName)) {
			throw new IllegalArgumentException(
					"A valid parameter name is required");
		}

		if (constraints.length == 0) {
			throw new IllegalArgumentException("Parameter constraint required");
		}

		List<RouteParameterConstraint> paramConstraints = parameterConstraintsMap
				.get(paramName);

		if (paramConstraints == null) {
			paramConstraints = new LinkedList<RouteParameterConstraint>();
			parameterConstraintsMap.put(paramName, paramConstraints);
		}

		paramConstraints.addAll(Arrays.asList(constraints));
		return this;
	}

	public RouteConstraints forParamAddEmail(String paramName) {
		return forParam(paramName, EmailConstraint.email());

	}

	public RouteConstraints forParamAddSlug(String paramName) {
		return forParam(paramName, SlugConstraint.slug());

	}

	public RouteConstraints forParamAddNumeric(String paramName) {
		return forParam(paramName, NumericConstraint.numeric());
	}

	public RouteConstraints forParamAddMax(String paramName, long maxValue) {
		return forParam(paramName, new MaxConstraint(maxValue));
	}

	public RouteConstraints forParamAddMin(String paramName, long minValue) {
		return forParam(paramName, new MinConstraint(minValue));
	}

	public RouteConstraints forParamAddMinMax(String paramName, long minValue,
			long maxValue) {
		return forParam(paramName, new MinMaxConstraint(minValue, maxValue));
	}

	public RouteConstraints forParamAddMinLen(String paramName, int minLength) {
		return forParam(paramName, new MinLengthConstraint(minLength));
	}

	public RouteConstraints forParamAddMaxLen(String paramName, int maxLength) {
		return forParam(paramName, new MaxLengthConstraint(maxLength));
	}

	public RouteConstraints forParamAddMinMaxLen(String paramName,
			int minLength, int maxLength) {
		return forParam(paramName, new MinMaxLengthConstraint(minLength,
				maxLength));
	}

	public RouteConstraints forParamAddRegex(String paramName, String regex) {
		return forParam(paramName, new RegexConstraint(regex));
	}

	public boolean matchConstraints(HttpRequestWrapper request,
			RouteDetail routeDetail) {
		return matchRouteConstraints(request, routeDetail)
				&& matchParameterConstraints(request, routeDetail);

	}

	public boolean hasParamConstraints() {
		return parameterConstraintsMap.size() > 0;
	}

	public Set<String> paramNamesAsSet() {
		Set<String> paramNameSet = new HashSet<String>();

		for (String key : parameterConstraintsMap.keySet()) {
			paramNameSet.add(key);
		}

		return paramNameSet;

	}

	private boolean matchRouteConstraints(HttpRequestWrapper request,
			RouteDetail routeDetail) {

		for (RouteConstraint constraint : routeContraints) {
			if (!constraint.match(request, routeDetail)) {
				return false;
			}
		}

		return true;
	}

	private boolean matchParameterConstraints(HttpRequestWrapper request,
			RouteDetail routeDetail) {

		for (String paramName : parameterConstraintsMap.keySet()) {
			if (routeDetail.getParamCollection().containsKey(paramName)) {
				List<RouteParameterConstraint> constraints = parameterConstraintsMap
						.get(paramName);

				for (RouteParameterConstraint constraint : constraints) {
					if (!constraint.match(
							request,
							routeDetail,
							paramName,
							routeDetail.getParamCollection().getOrDefault(
									paramName, null))) {
						return false;
					}
				}
			}

		}

		return true;
	}

}
