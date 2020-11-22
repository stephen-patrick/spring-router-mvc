package com.stephenenright.spring.router.mvc;

public class RouteDetail {

	private Route route;
	private RouteParameterCollection paramCollection;

	public RouteDetail(Route route, RouteParameterCollection paramCollection) {
		this.route = route;
		this.paramCollection = paramCollection;

	}

	public Route getRoute() {
		return route;
	}

	public RouteParameterCollection getParamCollection() {
		return paramCollection;
	}

}
