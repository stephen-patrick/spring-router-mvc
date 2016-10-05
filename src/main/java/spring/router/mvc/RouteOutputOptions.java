package spring.router.mvc;


public class RouteOutputOptions {

	private static final int PORT_NOT_SET_VALUE = -1;

	public static class UrlOuputOptions {
		private boolean forceHttps = false;
		private String host;
		private int port = PORT_NOT_SET_VALUE;
		private String contextPath;

		public UrlOuputOptions(String host, String contextPath) {
			this(host, PORT_NOT_SET_VALUE, contextPath, false);
		}

		
		public UrlOuputOptions(String host, boolean forceHttps) {
			this(host, PORT_NOT_SET_VALUE, forceHttps);
		}
		
		
		public UrlOuputOptions(String host, String contextPath, boolean forceHttps) {
			this(host, PORT_NOT_SET_VALUE, contextPath, forceHttps);
		}
		
		
		
		public UrlOuputOptions(String host, int port, boolean forceHttps) {
			this(host,port,null,forceHttps);
		}
		

		public UrlOuputOptions(String host, int port,  String contextPath, boolean forceHttps) {
			this.forceHttps = forceHttps;
			this.port = port;
			this.host = host;
			this.contextPath = contextPath;
		}


		public boolean isForceHttps() {
			return forceHttps;
		}

		public String getHost() {
			return host;
		}

		
		public int getPort() {
			return port;
		}

		public String getContextPath() {
			return contextPath;
		}
	}

	public static class UrlOverrideOptions extends UrlOuputOptions {

		private static final UrlOverrideOptions OPTIONS_ABSOLUTE;
		private static final UrlOverrideOptions OPTIONS_ABSOLUTE_FORCE_HTTPS;

		static {
			OPTIONS_ABSOLUTE = new UrlOverrideOptions();
			OPTIONS_ABSOLUTE_FORCE_HTTPS = new UrlOverrideOptions(true);

		}

		private UrlOverrideOptions() {
			this(false);
		}

		private UrlOverrideOptions(boolean forceHttps) {
			this(forceHttps,null, null, PORT_NOT_SET_VALUE);
		}
		
		private UrlOverrideOptions(boolean forceHttps, String contextPath) {
			this(forceHttps, contextPath, null, PORT_NOT_SET_VALUE);
		}
		
		

		private UrlOverrideOptions(boolean forceHttps, String contextPath, String host, int port) {
			super(host, port, contextPath, forceHttps);
		}

		public static UrlOverrideOptions absoluteUrl() {
			return OPTIONS_ABSOLUTE;
		}

		public static UrlOverrideOptions absoluteUrlWithHttps() {
			return OPTIONS_ABSOLUTE_FORCE_HTTPS;
		}

		public static UrlOverrideOptions absoluteUrl(String hostOverride,
				boolean forceHttps) {
			return absoluteUrl(hostOverride, PORT_NOT_SET_VALUE, forceHttps);
		}

		public static UrlOverrideOptions absoluteUrl(int portOverride,
				boolean forceHttps) {
			return absoluteUrl(null, portOverride, forceHttps);
		}

		public static UrlOverrideOptions absoluteUrl(String hostOverride,
				int portOverride, boolean forceHttps) {
			return new UrlOverrideOptions(forceHttps,"", hostOverride,
					portOverride);
		}
		
		public boolean isHostOverridden() {
			return RouteUtils.isNullOrEmpty(getHost());
		}
		
		
		public boolean isContextPathOverridden() {
			return RouteUtils.isNullOrEmpty(getContextPath());
		}

		public boolean isPortOverridden() {
			return getPort() == PORT_NOT_SET_VALUE;
		}
	}

}
