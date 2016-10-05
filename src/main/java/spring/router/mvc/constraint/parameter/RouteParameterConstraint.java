package spring.router.mvc.constraint.parameter;


import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteDetail;

public interface RouteParameterConstraint {
	
	boolean match(HttpRequestWrapper request, RouteDetail route,
			String parameterName, String parameterValue);

}