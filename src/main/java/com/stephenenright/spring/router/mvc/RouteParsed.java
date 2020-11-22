package com.stephenenright.spring.router.mvc;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.stephenenright.spring.router.mvc.RouteSegments.PathSegment;

class RouteParsed {

	private List<PathSegment> pathSegments = new LinkedList<PathSegment>();

	public RouteParsed(List<PathSegment> pathSegments) {
		this.pathSegments = pathSegments;
	}

	public String reverse(RouteParameterCollection params) {
		return reverse(params, true);
	}

	public String pattern(RouteParameterCollection params) {
		return reverse(params, false);
	}

	public boolean matchParameters(Set<String> parameterNames) {
		Set<String> collectedNameSet = new HashSet<String>();

		for (PathSegment segment : pathSegments) {
			segment.collectParameterNames(collectedNameSet);
		}

		if (collectedNameSet.size() != parameterNames.size()) {
			return false;
		}

		return collectedNameSet.containsAll(parameterNames);
	}

	public RouteMatchResult match(String requestPath) {
		if (RouteUtils.isNullOrEmpty(requestPath)
				|| requestPath.trim().equals(Http.PATH_SEPARATOR)) {
			if (isRoot()) {
				return new RouteMatchResult(true);
			}

			return new RouteMatchResult(false);
		}

		boolean parsed = false;
		RouteParameterCollection params = new RouteParameterCollection();
		List<String> requestPartsList = RouteParseUtils
				.parseUrlToSegmentStrings(requestPath);

		for (int index = 0; index < pathSegments.size(); index++) {
			PathSegment segment = pathSegments.get(index);

			if (requestPartsList.size() <= index) {
				parsed = true;
			}

			String partToMatch = "";

			if (!parsed) {
				partToMatch = requestPartsList.get(index);
			}

			if (!segment.match(partToMatch, params)) {
				return new RouteMatchResult(false);
			}

		}

		int segmentLen = pathSegments.size();
		int partsLen = requestPartsList.size();

		if (segmentLen < partsLen) {
			for (int i = segmentLen; i < partsLen; i++) {
				if (!RouteUtils.isPathSeparator(requestPartsList.get(i))) {
					return new RouteMatchResult(false);
				}
			}
		}

		return new RouteMatchResult(true, params);

	}

	private String reverse(RouteParameterCollection params, boolean strict) {
		if (isRoot()) {
			return Http.PATH_SEPARATOR;
		}

		StringWriter writer = new StringWriter();
		writer.append(Http.PATH_SEPARATOR);

		for (PathSegment segment : pathSegments) {
			writer.append(segment.reverse(params, strict));
		}

		return writer.toString();
	}

	public boolean isRoot() {
		return pathSegments.size() == 0;
	}

	public List<PathSegment> getPathSegments() {
		return pathSegments;
	}

	public static class RouteMatchResult {
		private RouteParameterCollection params = new RouteParameterCollection();
		private boolean matched = false;

		public RouteMatchResult(boolean matched) {
			this.matched = matched;
		}

		public RouteMatchResult(boolean matched, RouteParameterCollection params) {
			this.matched = matched;
			this.params = params;
		}

		public RouteParameterCollection getParams() {
			return params;
		}

		public boolean isMatched() {
			return matched;
		}

	}

}
