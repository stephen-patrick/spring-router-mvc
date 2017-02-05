package spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import spring.router.mvc.constraint.RouteConstraint;

public class RouteConfig {

	private String controller;
	private String name;
	private String path;
	private String action;
	private List<HttpMethod> methods = new LinkedList<HttpMethod>();
	private List<RouteConstraint> constriants = new LinkedList<RouteConstraint>();
	private boolean enabled = true;

	public String getController() {
		return controller;
	}

	public RouteConfig setController(String controller) {
		this.controller = controller;
		return this;
	}

	public String getName() {
		return name;
	}

	public RouteConfig setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public RouteConfig setPath(String path) {
		this.path = path;
		return this;
	}

	public String getAction() {
		return action;
	}

	public RouteConfig setAction(String action) {
		this.action = action;
		return this;
	}

	public List<HttpMethod> getMethods() {
		return methods;
	}

	public RouteConfig setMethods(List<HttpMethod> methods) {
		this.methods = methods;
		return this;
	}

	public List<RouteConstraint> getConstriants() {
		return constriants;
	}

	public RouteConfig setConstriants(List<RouteConstraint> constriants) {
		this.constriants = constriants;
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public RouteConfig setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public RouteConfig addHttpMethod(HttpMethod... methods) {
		for (HttpMethod method : methods) {
			this.methods.add(method);
		}

		return this;
	}

	public RouteConfig addConstraint(RouteConstraint... constraints) {

		for (RouteConstraint c : constraints) {
			this.constriants.add(c);
		}

		return this;

	}
}
