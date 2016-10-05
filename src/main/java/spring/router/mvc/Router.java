package spring.router.mvc;

import spring.router.mvc.Http.HttpRequestWrapper;

public interface Router extends RouteResolver {

	public RouteDetail match(HttpRequestWrapper request);

}
