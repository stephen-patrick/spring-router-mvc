package com.github.stephenenright.spring.router.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Http {

	public static String PATH_SEPARATOR = "/";
	public static String HTTP_QUERY_STR_START = "?";
	public static String HTTP_QUERY_STR_PARAM_START = "&";
	public static String HTTP_QUERY_STR_EQUALS = "=";

	public static enum Protocol {
		HTTP, HTTPS,
	}

	public static class HttpRequestWrapper {

		private HttpServletRequest request;
		private String path;
		private HttpMethod httpMethod;
		private String method;
		private String servletPath;
		private String contextPath;
		private String queryStr;
		private boolean httpMethodUnknown = false;
		private String host;
		private int port;
		private String protocol;
		private boolean https = false;

		public HttpRequestWrapper(HttpServletRequest request) {
			this.request = request;
			init();

		}

		private void init() {
			setIsSecure();
			setHostDetails();
			setMethod();
			setQueryString();
			setPath();
			setServletPaths();

		}

		public void setIsSecure() {
			https = request.isSecure();

			if (https) {
				protocol = "https";
			} else {
				protocol = "http";
			}

		}

		private void setMethod() {
			method = request.getMethod().intern();

			try {
				httpMethod = HttpMethod.valueOf(method.toUpperCase());
			} catch (Exception e) {
				httpMethodUnknown = true;
				httpMethod = null;
			}

		}

		private void setPath() {
			String pathInfo = request.getPathInfo();

			if (pathInfo != null) {
				path = pathInfo;
			} else {
				path = request.getServletPath();
			}

			if (RouteUtils.isNullOrEmpty(path)
					&& path.startsWith(PATH_SEPARATOR)) {

				path = path.substring(1);

			}
		}

		private void setQueryString() {
			String queryStr = request.getQueryString();

			this.queryStr = queryStr == null ? "" : queryStr;
		}

		private void setServletPaths() {
			servletPath = request.getServletPath() != null ? request
					.getServletPath() : "";
			contextPath = request.getContextPath() != null ? request
					.getContextPath() : "";
		}

		private void setHostDetails() {
			host = request.getHeader("host");
			if (host != null && host.contains(":")) {
				port = Integer.parseInt(host.split(":")[1]);
				host = host.split(":")[0];
			} else {
				port = -1;
			}

		}

		public void setAttribute(String name, Object value) {
			request.setAttribute(name, value);
		}

		public boolean isHttps() {
			return https;
		}

		public HttpServletRequest getRequest() {
			return request;
		}

		public String getPath() {
			return path;
		}

		public HttpMethod getMethod() {
			return httpMethod;
		}

		public String getServletPath() {
			return servletPath;
		}

		public String getContextPath() {
			return contextPath;
		}

		public String getQueryStr() {
			return queryStr;
		}

		public boolean isHttpMethodUnknown() {
			return httpMethodUnknown;
		}

		public HttpMethod getHttpMethod() {
			return httpMethod;
		}

		public String getHost() {
			return host;
		}

		public int getPort() {
			return port;
		}

		public String getProtocol() {
			return protocol;
		}

		public static HttpServletRequest getCurrentServletRequest() {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			return ((ServletRequestAttributes) requestAttributes).getRequest();

		}

	}

}
