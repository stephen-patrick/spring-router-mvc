package spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import spring.router.mvc.constraint.RouteConstraints;

public class RouteConfigSpec {

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

	public RouteConfigSpec setController(String controller) {
		this.controller = controller;
		return this;
	}

	public String getName() {
		return name;
	}

	public RouteConfigSpec setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public RouteConfigSpec setPath(String path) {
		this.path = path;
		return this;
	}

	public String getAction() {
		return action;
	}

	public RouteConfigSpec setAction(String action) {
		this.action = action;
		return this;
	}

	public List<HttpMethod> getMethods() {
		return methods;
	}

	public RouteConfigSpec setMethods(List<HttpMethod> methods) {
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

	public RouteConfigSpec setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public RouteConfigSpec addHttpMethod(HttpMethod... methods) {
		for (HttpMethod method : methods) {
			this.methods.add(method);
		}

		return this;
	}

}
