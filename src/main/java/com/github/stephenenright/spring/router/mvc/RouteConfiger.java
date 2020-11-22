package com.github.stephenenright.spring.router.mvc;

import java.util.List;

/**
 * This interface supports the implementation of a custom class that can
 * configure one or more routes within a router that follows a pattern. For example different
 * applications can have common URL patterns or patterns that vary but repeat. 
 * 
 * As an example API routes @see {@link RouteApiConfiger} sometimes follow a CRUD pattern and have routes for add, update, list (search), get etc.
 *
 */
public interface RouteConfiger {
	public List<RouteConfigSpec> getRouteConfigs();
}
