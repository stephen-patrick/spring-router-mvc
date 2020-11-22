package com.stephenenright.spring.router.mvc.constraint;



import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface RouteConstraint {
	
	boolean match (HttpRequestWrapper request, RouteDetail route);

}
