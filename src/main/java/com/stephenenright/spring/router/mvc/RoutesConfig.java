package com.stephenenright.spring.router.mvc;

public interface RoutesConfig {

	public void registerRoutes(RoutesBuilder routes);
	public String name();

}
