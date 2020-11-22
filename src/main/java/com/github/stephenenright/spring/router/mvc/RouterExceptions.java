package com.github.stephenenright.spring.router.mvc;

public class RouterExceptions {
	
	@SuppressWarnings("serial")
	public static class RouteConfigException extends RuntimeException {

		public RouteConfigException(String message) {
			this(message, null);
		}

		public RouteConfigException(String message, Throwable t) {
			super(message, t);
		}
	}
	
	@SuppressWarnings("serial")
	public static class RouteException extends RuntimeException {

		public RouteException(String message) {
			this(message, null);
		}

		public RouteException(String message, Throwable t) {
			super(message, t);
		}
	}
	
	@SuppressWarnings("serial")
	public static class RouteParseException extends RuntimeException {

		public RouteParseException(String message) {
			this(message, null);
		}

		public RouteParseException(String message, Throwable t) {
			super(message, t);
		}
	}
	
	
	@SuppressWarnings("serial")
	public static class RouteResolveException extends RuntimeException {

		public RouteResolveException(String message) {
			this(message, null);
		}

		public RouteResolveException(String message, Throwable t) {
			super(message, t);
		}
	}
	
	
	
	

	
	
	
	

	
	
	
}
