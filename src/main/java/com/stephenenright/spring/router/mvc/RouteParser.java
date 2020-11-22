package com.stephenenright.spring.router.mvc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.stephenenright.spring.router.mvc.RouteSegments.LiteralSegment;
import com.stephenenright.spring.router.mvc.RouteSegments.ParameterSegment;
import com.stephenenright.spring.router.mvc.RouteSegments.PathCompositeSegment;
import com.stephenenright.spring.router.mvc.RouteSegments.PathSegment;
import com.stephenenright.spring.router.mvc.RouteSegments.PathSeparatorSegment;

class RouteParser {

	public RouteParsed parseRoute(String routeUrl) {
		if (RouteUtils.isNullOrEmpty(routeUrl) || routeUrl.trim().equals(Http.PATH_SEPARATOR)) {
			routeUrl = "";
		}

		if (!validateRouteForParse(routeUrl)) {
			;
			throw new RouterExceptions.RouteParseException("Unable to parse invalid route "
					+ routeUrl);
		}

		List<String> segmentPartList = RouteParseUtils
				.parseUrlToSegmentStrings(routeUrl);

		if (!validateSegments(segmentPartList, routeUrl)) {
			throw new RouterExceptions.RouteParseException("Unable to parse route: " + routeUrl);
		}

		List<PathSegment> pathSegments = parseToSegments(segmentPartList,
				routeUrl);

		return new RouteParsed(pathSegments);

	}

	private List<PathSegment> parseToSegments(List<String> segmentStrList,
			String routeUrl) {
		List<PathSegment> pathSegmentsList = new LinkedList<PathSegment>();

		for (int i = 0; i < segmentStrList.size(); i++) {
			String currentSegmentStr = segmentStrList.get(i);

			if (RouteUtils.isPathSeparator(currentSegmentStr)) {
				pathSegmentsList.add(new PathSeparatorSegment());
			} else {
				List<PathSegment> subSegments = parseUrlSegment(
						currentSegmentStr, routeUrl);

				if (subSegments.size() == 0) {
					pathSegmentsList.add(new PathSeparatorSegment());
				} else if (subSegments.size() == 1) {
					pathSegmentsList.add(subSegments.get(0));
				} else {
					pathSegmentsList.add(new PathCompositeSegment(subSegments));
				}
			}
		}

		return pathSegmentsList;
	}

	private List<PathSegment> parseUrlSegment(String segment, String routeUrl) {
		List<PathSegment> segments = new LinkedList<PathSegment>();

		for (int index = 0; index < segment.length(); index++) {
			int nextStart = RouteParseUtils.indexOfFirstParameter(segment,
					index);

			if (nextStart == -1) {
				String lastPart = RouteParseUtils
						.sanitizeLiteralSegment(segment.substring(index));

				if (RouteUtils.isNullOrEmpty(lastPart)) {
					throw new RouterExceptions.RouteParseException(String.format(
							"Invalid Route url parameter syntax for route: %s",
							routeUrl));
				}
				if (lastPart.length() > 0) {
					RouteParseUtils.addSegmentToBack(segments,
							new LiteralSegment(lastPart));
				}
				break;
			}

			int nextEnd = segment.indexOf("}", nextStart + 1);

			if (nextEnd == -1) {
				throw new RouterExceptions.RouteParseException(String.format(
						"Invalid Route url parameter syntax for route: %s",
						routeUrl));
			}

			String literalSegment = RouteParseUtils
					.sanitizeLiteralSegment(segment.substring(index, nextStart
							- index));
			if (!RouteUtils.isNullOrEmpty(literalSegment)) {
				RouteParseUtils.addSegmentToBack(segments, new LiteralSegment(
						literalSegment));
			}

			String parameterName = segment.substring(nextStart + 1, nextEnd);
			RouteParseUtils.addSegmentToBack(segments, new ParameterSegment(
					parameterName));

			index = nextEnd + 1;
		}

		return segments;
	}

	public boolean validateUrlSegment(List<PathSegment> segments,
			Map<String, String> nameMap, String segment) {

		String previousType = "";
		for (PathSegment subSegment : segments) {
			if ((previousType != "")
					&& (previousType.equals(subSegment.getClass().getName()))) {
				throw new RouterExceptions.RouteParseException(
						"Repeating route params are not allowed");
			}

			previousType = subSegment.getClass().getName();

			if (subSegment instanceof ParameterSegment) {
				ParameterSegment paramSegment = (ParameterSegment) subSegment;
				if (!RouteParseUtils.isRouteParamNameValid(paramSegment
						.getParameterName())) {
					throw new RouterExceptions.RouteParseException("Route Parameter Name "
							+ paramSegment.getParameterName() + " is not valid");
				} else if (paramSegment.getParameterName().equals(
						RouteConstants.EMPTY_STRING)) {
					throw new RouterExceptions.RouteParseException("Route Parameter Name "
							+ paramSegment.getParameterName() + " is not valid");
				}

				if (nameMap.containsKey(paramSegment.getParameterName()
						.toLowerCase())) {
					throw new RouterExceptions.RouteParseException("Route Parameter Name "
							+ paramSegment.getParameterName()
							+ " must be unique");
				} else {
					nameMap.put(paramSegment.getParameterName().toLowerCase(),
							paramSegment.getParameterName());
				}
			}
		}


		return true;
	}

	private boolean validateSegments(List<String> segmentsLists, String routeUrl) {
		Map<String, String> nameMap = new HashMap<String, String>();

		boolean isLastSeparator = false;

		for (String segment : segmentsLists) {
			boolean isCurrentSeparator = false;

			if (!isLastSeparator) {
				isLastSeparator = RouteUtils.isPathSeparator(segment);
				isCurrentSeparator = isLastSeparator;
			} else {
				isCurrentSeparator = RouteUtils.isPathSeparator(segment);

				if (isCurrentSeparator && isLastSeparator) {
					throw new RouterExceptions.RouteParseException(String.format(
							"Route url: %s contains repeating path separator",
							routeUrl));
				}

				isLastSeparator = isCurrentSeparator;
			}

			if (!isCurrentSeparator) {
				List<PathSegment> segments = parseUrlSegment(segment, routeUrl);

				if (!validateUrlSegment(segments, nameMap, segment)) {
					return false;
				}

			}
		}

		return true;
	}

	private boolean validateRouteForParse(String routeUrl) {
		if (routeUrl.indexOf("?") != -1) {
			return false;
		}

		return true;
	}

}
