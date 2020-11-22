package com.stephenenright.spring.router.mvc;

import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface Router extends RouteResolver {

	public RouteDetail match(HttpRequestWrapper request);

}
