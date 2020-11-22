package com.github.stephenenright.spring.router.mvc;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

public class RouteHandlerMethod extends HandlerMethod {

	private final RouteDetail route;

	public RouteHandlerMethod(Object controller, Method method,
			RouteDetail route) {
		super(controller, method);
		this.route = route;

	}

	public RouteDetail getRoute() {
		return route;
	}

}
