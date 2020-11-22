package com.github.stephenenright.spring.router.mvc;

import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface Router extends RouteResolver {

	public RouteDetail match(HttpRequestWrapper request);

}
