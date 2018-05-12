package spring.router.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.router.mvc.RouterExceptions.RouteException;
import spring.router.mvc.constraint.RouteConstraints;

class RoutesBuilderImpl implements RoutesBuilder {

	private static final Logger logger = LoggerFactory
			.getLogger(RoutesBuilderImpl.class);

	private RoutesConfig routeConfig;
	private RouteCollection routesCollection;
	private RouteParser routeParser;
	private RouteControllers controllers;
	private boolean validate;

	public RoutesBuilderImpl(RoutesConfig config, RouteCollection routesCollection,
			RouteParser parser, RouteControllers controllers) {
		this(config,routesCollection, parser, controllers, false);

	}

	public RoutesBuilderImpl(RoutesConfig config, RouteCollection routesCollection,
			RouteParser parser, RouteControllers controllers, boolean validating) {
		this.routeConfig = config;
		this.routesCollection = routesCollection;
		this.routeParser = parser;
		this.controllers = controllers;
		this.validate = validating;

	}

	public Route add(String name, String path, String controller, String action) {
		return add(name, path, controller, action, HttpMethod.ANY);
	}

	public Route add(String name, String path, String controller,
			String action, HttpMethod... methods) {

		return add(name, path, controller, action, new RouteConstraints(),
				methods);

	}

	@Override
	public void add(RouteConfiger configer) {
		addRoutesWithConfiger(configer);
	}

	@Override
	public void addApi(RouteConfiger routesApi) {
		addRoutesWithConfiger(routesApi);
		
	}
	
	
	private void addRoutesWithConfiger(RouteConfiger configer) {
		for(RouteConfigSpec spec : configer.getRouteConfigs()) {
			addRouteFromConfig(spec);
		}
	}
	
	private void addRouteFromConfig(RouteConfigSpec config) {
		if(!config.isEnabled()) {
			return;
		}
		
		add(config.getName(), config.getPath(),
				config.getController(), config.getAction(), config.getConstriants(), 
				config.getMethods().toArray(new HttpMethod[0]));
		
	}
	
	@Override
	public Route add(String name, String path, String controller,
			String action, RouteConstraints constraints, HttpMethod... methods) {

		if (routesCollection.containsName(name)) {
			throw new RouteException(
					"Unable to Add route. A route exists with the given name: "
							+ name);
		}

		RouteParsed parsedRoute = parseRoute(path);

		Route route = new RouteMvc(parsedRoute, name, path, controller, action,
				constraints, methods);

		validateRouteForAdd(route);
		validateAction(route);
		validateParameterConstraints(route, parsedRoute);

		routesCollection.add(routeConfig.name(),route);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Router: Added %s ", route.toString()));
		}

		return route;

	}

	public RouteCollection getRoutesCollection() {
		return routesCollection;
	}

	public RouteParser getRouteParser() {
		return routeParser;
	}

	private RouteParsed parseRoute(String path) {
		return routeParser.parseRoute(path);
	}

	private void validateRouteForAdd(Route route) {
		if (RouteUtils.isNullOrEmpty(route.getName())) {
			throw new RouteException(
					"Error Adding Route, route name is required");
		}

		if (RouteUtils.isNullOrEmpty(route.getAction())) {
			throw new RouteException(
					"Error Adding Route, route action is required");
		}

		if (RouteUtils.isNullOrEmpty(route.getPath())) {
			throw new RouteException(
					"Error Adding Route, route path is required");
		}

		if (RouteUtils.isNullOrEmpty(route.getController())) {
			throw new RouteException(
					"Error Adding Route, route controller is required");
		}

		if (routesCollection.containsName(route.getName())) {
			throw new RouteException(
					"Unable to Add route. A route exists with the given name: "
							+ route.getName());
		}

	}

	private void validateController(Route r) {
		if (!validate) {
			return;
		}

		if (!controllers.contains(r.getController())) {
			throw new RouteException(
					String.format(
							"Unable to add route: %s, no controller found with name: %s",
							r.toString(), r.getController()));
		}

		return;
	}

	private void validateAction(Route r) {
		if (!validate) {
			return;
		}

		validateController(r);

		if (controllers.findAction(r.getController(), r.getAction()) == null) {
			throw new RouteException(String.format(
					"Unable to add route: %s, no action found with name: %s",
					r.toString(), r.getAction()));
		}

		return;
	}

	private void validateParameterConstraints(Route r, RouteParsed rp) {
		if (!validate) {
			return;
		}

		if (!r.getConstraints().hasParamConstraints()) {
			return;
		}

		if (!rp.matchParameters(r.getConstraints().paramNamesAsSet())) {
			throw new RouteException(
					String.format(
							"Unable to add route: %s, parameter constraints don't match route parameters",
							r.toString()));

		}
	}

}
