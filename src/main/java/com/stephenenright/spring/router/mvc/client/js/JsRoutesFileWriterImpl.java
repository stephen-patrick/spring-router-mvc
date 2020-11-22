package com.stephenenright.spring.router.mvc.client.js;

import java.io.File;
import java.util.Map;

import com.stephenenright.spring.router.mvc.Route;
import com.stephenenright.spring.router.mvc.RouteHelper;
import com.stephenenright.spring.router.mvc.RouterExceptions;
import org.springframework.util.StringUtils;

public class JsRoutesFileWriterImpl implements JsRoutesFileWriter {
	
	private JsRouterConfiguration config;
	
	public JsRoutesFileWriterImpl(JsRouterConfiguration config) {
		this.config = config;
	}
	
	@Override
	public void writeRoutes() {
		if(!config.isWriteRoutes()) {
			return;
		}
		
		if(config.getJavacriptFileWriter() ==null) {
			throw new RouterExceptions.RouteConfigException("Configuration error: javacriptFileWriter is required");
		}
		
		
		File outputDirectory = null;
		
		if(!new File(config.getOutputDirectory()).isAbsolute()) {
			outputDirectory = new File(System.getProperty("user.dir"), config.getOutputDirectory());
		}
		else {
			outputDirectory = new File(config.getOutputDirectory());
		}

		Map<String, Map<String, Route>> routes = RouteHelper.getConfigRouteMappingsByName();
		
		
		for(String configName: routes.keySet()) {
			String routesBaseName = config.getRouteFileBaseName();
			
			if(routesBaseName ==null) {
				routesBaseName = "";
			}
			
			final String routesFileName = routesBaseName.trim()  + StringUtils.capitalize(configName)  + ".js";
			config.getJavacriptFileWriter().writeRoutes(outputDirectory, routesFileName, configName, routes.get(configName));
		}
	}
}
