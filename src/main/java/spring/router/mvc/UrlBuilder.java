package spring.router.mvc;

import java.io.StringWriter;
import java.util.Map;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.Http.Protocol;
import spring.router.mvc.RouteOutputOptions.UrlOuputOptions;
import spring.router.mvc.RouteOutputOptions.UrlOverrideOptions;

class UrlBuilder {

	private Protocol protocol;
	private int port = -1;
	private String host;
	private String contextPath;
	private String path;
	private StringWriter queryStr;

	public UrlBuilder(String host, String contextPath, String path) {
		this(Protocol.HTTP, host, contextPath, path);
	}

	public UrlBuilder(Protocol protocol, String host, String contextPath,
			String path) {
		this(protocol, host, -1, contextPath, path);
	}

	public UrlBuilder(Protocol protocol, String host, int port,
			String contextPath, String path) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.contextPath = contextPath;
		this.path = path;

		validate();
		parsePath();
	}

	public UrlBuilder(UrlOuputOptions options, String path) {
		this(options.isForceHttps() ? Protocol.HTTPS : Protocol.HTTP, options
				.getHost(), options.getPort(), options.getContextPath(), path);

	}

	public UrlBuilder(UrlOverrideOptions options, HttpRequestWrapper request,
			String path) {
		this(options.isForceHttps() || request.isHttps() ? Protocol.HTTPS
				: Protocol.HTTP, options.isHostOverridden() ? options.getHost()
				: request.getHost(), options.isPortOverridden() ? options
				.getPort() : request.getPort(), options
				.isContextPathOverridden() ? options.getContextPath() : request
				.getContextPath(), path);
	}

	private void validate() {
		if (RouteUtils.isNullOrEmpty(host)) {
			throw new IllegalArgumentException("Host is required");
		}

		if (port != -1 && port < 0) {
			throw new IllegalArgumentException("Port is required");
		}

		if (protocol == null) {
			throw new IllegalArgumentException("Protocol is required");
		}
	}

	private void parsePath() {
		if (RouteUtils.isNullOrEmpty(contextPath)) {
			contextPath = Http.PATH_SEPARATOR;
		}

		if (RouteUtils.isNullOrEmpty(path)
				|| path.trim().equals(Http.PATH_SEPARATOR)) {
			path = RouteConstants.EMPTY_STRING;
			return;
		}

		int index = path.lastIndexOf(Http.HTTP_QUERY_STR_START);

		if (index == -1) {
			return;
		}

		queryStr = new StringWriter(path.length());
		queryStr.append(path.substring(index));
		path = path.substring(0, index).trim();
	}

	public String buildUrl() {
		StringWriter urlWriter = new StringWriter();

		if (hasQueryString()) {
			urlWriter = new StringWriter(path.length()
					+ queryStr.getBuffer().length() + 15);
		} else {
			urlWriter = new StringWriter(path.length() + 15);
		}

		urlWriter.append(protocol.toString().toLowerCase());
		urlWriter.append("://");
		urlWriter.append(host);

		if (port != -1) {
			urlWriter.append(":");
			urlWriter.append(String.valueOf(port));
		}

		String requestPath = RouteUtils.joinContextWithPathAsRootRelative(
				contextPath, path);

		if (!requestPath.equals(Http.PATH_SEPARATOR)) {
			urlWriter.append(requestPath);
		}

		if (hasQueryString()) {
			urlWriter.append(queryStr.toString());
		}

		return urlWriter.toString();

	}

	public void addQueryStrParam(String name, String value) {
		if (RouteUtils.isNullOrEmpty(name)) {
			throw new IllegalArgumentException("Parameter name is required");
		}

		if (!hasQueryString()) {
			queryStr = new StringWriter();
			queryStr.append(Http.HTTP_QUERY_STR_START);
		} else {
			queryStr.append(Http.HTTP_QUERY_STR_PARAM_START);
		}

		queryStr.append(name);
		queryStr.append(Http.HTTP_QUERY_STR_EQUALS);
		queryStr.append(value == null ? RouteConstants.EMPTY_STRING : value);
	}

	public void addQueryStrParam(String[] names, String[] values) {
		if (names.length != values.length) {
			throw new IllegalArgumentException(
					"UrlBuilder: "
							+ "Query string parameter names and values must have same length");
		}

		for (int i = 0; i < names.length; i++) {
			addQueryStrParam(names[i], values[i]);
		}

	}

	public void addQueryStrParam(Map<String, String> parameters) {
		if (parameters == null) {
			return;
		}

		for (String key : parameters.keySet()) {
			addQueryStrParam(key, parameters.get(key));
		}

	}

	private boolean hasQueryString() {
		return queryStr != null;
	}

}
