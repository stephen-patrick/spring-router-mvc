package spring.router.mvc;

import org.springframework.util.StringUtils;

public class RouteApiBuilder {

	private RouteConfig listApi;
	private RouteConfig getApi;
	private RouteConfig editApi;
	private RouteConfig addApi;

	public RouteApiBuilder(String baseName, String controller, String collectionPath) {
		init(baseName, controller, collectionPath);
	}

	private void init(String baseName, String controller, String collectionPath) {
		initRoutes(baseName, controller, collectionPath);
	}

	private void initRoutes(String baseName, String controller, String collectionPath) {
		listApi = initRouteConfig(baseName, "list", controller, "list", collectionPath, HttpMethod.GET);
		getApi = initRouteConfig(baseName, "get", controller, "get", appendPath(collectionPath, "/{id}"),
				HttpMethod.GET);
		editApi = initRouteConfig(baseName, "edit", controller, "edit", appendPath(collectionPath, "/{id}"),
				HttpMethod.POST);
		addApi = initRouteConfig(baseName, "add", controller, "add", collectionPath, HttpMethod.POST);

	}

	private RouteConfig initRouteConfig(String baseName, String nameSuffix, String controller, String action,
			String path, HttpMethod... method) {
		return new RouteConfig().setName(createRouteName(baseName, nameSuffix)).setAction(action)
				.addHttpMethod(HttpMethod.GET).setController(controller).setPath(path);

	}

	private String appendPath(String base, String path) {
		return base;

	}

	public RouteConfig getListApi() {
		return listApi;
	}

	public RouteConfig getGetApi() {
		return getApi;
	}

	public RouteConfig getEditApi() {
		return editApi;
	}

	public RouteConfig getAddApi() {
		return addApi;
	}

	private String createRouteName(String base, String suffix) {
		return base + StringUtils.capitalize(suffix) + "Api";
	}

}
