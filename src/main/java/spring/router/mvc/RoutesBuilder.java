package spring.router.mvc;

import spring.router.mvc.constraint.RouteConstraints;

public interface RoutesBuilder {

	Route add(String name, String path, String controller, String action);

	Route add(String name, String path, String controller, String action,
			HttpMethod... methods);

	Route add(String name, String path, String controller, String action,
			RouteConstraints constraints, HttpMethod... methods);

}
