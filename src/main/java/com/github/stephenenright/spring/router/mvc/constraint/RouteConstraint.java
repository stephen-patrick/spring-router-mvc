package com.github.stephenenright.spring.router.mvc.constraint;



import com.github.stephenenright.spring.router.mvc.RouteDetail;
import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public interface RouteConstraint {
	
	boolean match (HttpRequestWrapper request, RouteDetail route);

}
