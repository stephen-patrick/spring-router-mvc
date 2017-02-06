package spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import spring.router.mvc.constraint.RouteConstraints;

public class RouteConfiger {

	private String controller;
	private String name;
	private String path;
	private String action;
	private List<HttpMethod> methods = new LinkedList<HttpMethod>();
	private RouteConstraints constriants = new RouteConstraints();
	private boolean enabled = true;

	public String getController() {
		return controller;
	}

	public RouteConfiger setController(String controller) {
		this.controller = controller;
		return this;
	}

	public String getName() {
		return name;
	}

	public RouteConfiger setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public RouteConfiger setPath(String path) {
		this.path = path;
		return this;
	}

	public String getAction() {
		return action;
	}

	public RouteConfiger setAction(String action) {
		this.action = action;
		return this;
	}

	public List<HttpMethod> getMethods() {
		return methods;
	}

	public RouteConfiger setMethods(List<HttpMethod> methods) {
		this.methods = methods;
		return this;
	}

	public RouteConstraints getConstriants() {
		return constriants;
	}

	public void setConstriants(RouteConstraints constriants) {
		this.constriants = constriants;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public RouteConfiger setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public RouteConfiger addHttpMethod(HttpMethod... methods) {
		for (HttpMethod method : methods) {
			this.methods.add(method);
		}

		return this;
	}

}
