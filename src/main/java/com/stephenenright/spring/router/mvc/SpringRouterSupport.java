package com.stephenenright.spring.router.mvc;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;
import com.stephenenright.spring.router.mvc.RouterExceptions.RouteConfigException;

class SpringRouterSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(SpringRouterSupport.class);

	private final ConcurrentMap<String, HandlerMethod> 
		handlerMethodCache = new ConcurrentReferenceHashMap<String, HandlerMethod>(
			256);

	private RouteControllers controllers;
	private SpringRouterConfiguration configuration;
	private Router router;

	public SpringRouterSupport(SpringRouterConfiguration configuration) {
		this.configuration = configuration;
	}

	public void configure(ApplicationContext applicationContext) {

		List<RoutesConfig> routesConfigurations = configuration
				.getRouteConfigurations();

		if (routesConfigurations == null) {
			routesConfigurations = new LinkedList<RoutesConfig>();
		}

		setControllers(applicationContext);

		RouteParser routeParser = new RouteParser();
		RouteCollection routesCollection = new RouteCollection();
		router = new RouterMvc(routesCollection);
		RouteHelper.setResolver(router);

	
		int index = 1;
		for (RoutesConfig config : routesConfigurations) {
			if(RouteUtils.isNullOrEmpty(config.name())) {
				throw new RouteConfigException("RoutesConfig with index " + String.valueOf(index -1) + " name is required");
			}
			
			if (logger.isInfoEnabled()) {
				logger.info(String.format(
						"Adding routes for configuration: %s",
						config.name()));
			}
			
			RoutesBuilder builder = new RoutesBuilderImpl(config, routesCollection,
					routeParser, controllers, configuration.isStrict());
			

			config.registerRoutes(builder);
			index++;
		}
	}

	public HandlerMethod handleRequest(HttpServletRequest request) {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
		RouteDetail routeDetail = router.match(requestWrapper);

		if (routeDetail == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Spring Router: no route found for: "
						+ requestWrapper.getPath());
			}
			return null;
		}

		return resolveHandlerForRoute(routeDetail, requestWrapper);
	}

	private final HandlerMethod resolveHandlerForRoute(RouteDetail routeDetail,
			HttpRequestWrapper request) {

		HandlerMethod handlerMethod = handlerMethodCache.get(routeDetail
				.getRoute().getName());

		if (handlerMethod != null) {
			setRequestAttributesFromRoute(routeDetail, request);
			return handlerMethod;
		}

		if (!controllers.contains(routeDetail.getRoute().getController())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Spring Router: No controller found for name: "
						+ routeDetail.getRoute().getController());
			}

			return null;
		}

		Method method = controllers.findAction(routeDetail.getRoute()
				.getController(), routeDetail.getRoute().getAction());

		if (method == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String
						.format("Spring Router: no method found with name: %s for controller: %s",
								routeDetail.getRoute().getAction(), routeDetail
										.getRoute().getController()));
			}

			return null;
		}

		handlerMethod = new RouteHandlerMethod(controllers.get(routeDetail
				.getRoute().getController()), method, routeDetail);
		handlerMethodCache.putIfAbsent(routeDetail.getRoute().getName()
				.toLowerCase(), handlerMethod);

		setRequestAttributesFromRoute(routeDetail, request);

		return handlerMethod;
	}

	private void setRequestAttributesFromRoute(RouteDetail routeDetail, HttpRequestWrapper request) {
		request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
				routeDetail.getParamCollection().getValues());
		request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,
				routeDetail.getRoute().getPath());
	}

	private void setControllers(ApplicationContext applicationContext) {
		controllers = new RouteControllers(
				applicationContext.getBeansWithAnnotation(Controller.class));

	}

}
