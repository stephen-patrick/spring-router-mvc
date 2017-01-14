package spring.router.mvc.tags.thymeleaf;

import java.util.Map;

import spring.router.mvc.RouteHelper;
import spring.router.mvc.RouteParameterCollection;

public class RouterExpression {

	public String reverse(String controller, String action) {

		return RouteHelper.reverse(controller, action);
	}

	public String reverse(String controller, String action, Map<String, Object> params) {

		return RouteHelper.reverse(controller, action, new RouteParameterCollection(params));
	}

	public String reverseByName(String name) {
		return RouteHelper.reverse(name);
	}

	public String reverseByName(String name, Map<String, Object> params) {
		return RouteHelper.reverse(name, new RouteParameterCollection(params));
	}

}
