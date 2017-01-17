package spring.router.mvc.constraint;



import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteDetail;

public interface RouteConstraint {
	
	boolean match (HttpRequestWrapper request, RouteDetail route);

}
