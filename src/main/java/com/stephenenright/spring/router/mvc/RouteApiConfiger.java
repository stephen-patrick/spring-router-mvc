package com.stephenenright.spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.StringUtils;

public class RouteApiConfiger implements RouteConfiger {

	private RouteConfigSpec listApi;
	private RouteConfigSpec getApi;
	private RouteConfigSpec updateApi;
	private RouteConfigSpec addApi;

	public RouteApiConfiger(Class<?> controller, String collectionPath) {
		init(parseBaseNameFromCls(controller), controller.getSimpleName(), collectionPath);
	}
	
	
	
	public RouteApiConfiger(String baseName, String controller, String collectionPath) {
		init(baseName, controller, collectionPath);
	}

	private void init(String baseName, String controller, String collectionPath) {
		initRoutes(baseName, controller, collectionPath);
	}

	private String parseBaseNameFromCls(Class<?> controller) {
		String name = controller.getSimpleName();
		
		if(name.toLowerCase().indexOf("api") != -1) {
			return StringUtils.uncapitalize(name.substring(0, name.toLowerCase().indexOf("api")));
		}
		
		return StringUtils.uncapitalize(name);
		
		
	}
	
	
	private void initRoutes(String baseName, String controller, String collectionPath) {
		listApi = initRouteConfig(baseName, "list", controller, "list", collectionPath, HttpMethod.GET);
		getApi = initRouteConfig(baseName, "get", controller, "get", appendPath(collectionPath, "/{id}"),
				HttpMethod.GET);
		updateApi = initRouteConfig(baseName, "update", controller, "update", appendPath(collectionPath, "/{id}"),
				HttpMethod.POST);
		addApi = initRouteConfig(baseName, "add", controller, "add", collectionPath, HttpMethod.POST);

	}

	private RouteConfigSpec initRouteConfig(String baseName, String nameSuffix, String controller, String action,
			String path, HttpMethod... method) {
		return new RouteConfigSpec().setName(createRouteName(baseName, nameSuffix)).setAction(action)
				.addHttpMethod(method).setController(controller).setPath(path);

	}

	private String appendPath(String base, String path) {
		if(!base.endsWith("/")) {
			base += "/";
		}
		
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		
		return base + path;

	}

	public RouteApiConfiger enableListApi(boolean enabled) {
		listApi.setEnabled(enabled);
		return this;
	}
	
	public RouteApiConfiger enableUpdateApi(boolean enabled) {
		updateApi.setEnabled(enabled);
		return this;
	}
	
	public RouteApiConfiger enableAddApi(boolean enabled) {
		addApi.setEnabled(enabled);
		return this;
	}

	public RouteApiConfiger enableGetApi(boolean enabled) {
		getApi.setEnabled(enabled);
		return this;
	}

	public RouteConfigSpec getListApi() {
		return listApi;
	}

	public RouteConfigSpec getGetApi() {
		return getApi;
	}

	public RouteConfigSpec getUpdateApi() {
		return updateApi;
	}

	public RouteConfigSpec getAddApi() {
		return addApi;
	}

	private String createRouteName(String base, String suffix) {
		return base + StringUtils.capitalize(suffix) + "Api";
	}

	@Override
	public List<RouteConfigSpec> getRouteConfigs() {
		List<RouteConfigSpec> specs = new LinkedList<RouteConfigSpec>();
		specs.add(listApi);
		specs.add(getApi);
		specs.add(addApi);
		specs.add(updateApi);
		return specs;
	}
}
