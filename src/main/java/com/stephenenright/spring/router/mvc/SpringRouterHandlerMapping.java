package com.stephenenright.spring.router.mvc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

public class SpringRouterHandlerMapping extends AbstractHandlerMapping {

	private static final Logger logger = LoggerFactory
			.getLogger(SpringRouterHandlerMapping.class);

	private SpringRouterSupport routerSupport;
	private SpringRouterConfiguration configuration;

	public SpringRouterHandlerMapping() {

	}

	@Override
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();
		configure();
	}

	private void configure() {
		if (logger.isInfoEnabled()) {
			logger.info("Spring Router: RouterHanderMapping init");
		}

		if (configuration == null) {
			throw new IllegalArgumentException(
					"Spring Router: SpringRouterHandlerMapping requires spring.router.mvc.SpringRouterConfiguration bean is required.");
		}

		if (!configuration.isEnabled()) {
			if (logger.isInfoEnabled()) {
				logger.info("Spring Router: is not enabled.");
				return;
			}
		}
		
		routerSupport = new SpringRouterSupport(configuration);
		routerSupport.configure(getApplicationContext());

		if (logger.isInfoEnabled()) {
			logger.info("Spring Router: RouterHanderMapping init complete");
		}
	}
	
	
	

	@Override
	protected Object getHandlerInternal(HttpServletRequest request)
			throws Exception {

		return routerSupport.handleRequest(request);

	}

	public SpringRouterConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(SpringRouterConfiguration configuration) {
		this.configuration = configuration;
	}

}
