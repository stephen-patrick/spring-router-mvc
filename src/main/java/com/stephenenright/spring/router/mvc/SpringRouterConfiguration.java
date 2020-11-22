package com.stephenenright.spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

public class SpringRouterConfiguration {

	private boolean enabled;
	private List<RoutesConfig> 
		routeConfigurations = new LinkedList<RoutesConfig>();

	private boolean strict = false;
	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<RoutesConfig> getRouteConfigurations() {
		return routeConfigurations;
	}

	public void setRouteConfigurations(List<RoutesConfig> routeConfigurations) {
		this.routeConfigurations = routeConfigurations;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

}
