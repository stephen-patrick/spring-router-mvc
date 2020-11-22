package com.github.stephenenright.spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

class RouteParseUtils {

	public static List<String> parseUrlToSegmentStrings(String routeUrl) {
		List<String> segments = new LinkedList<String>();
		int urlLength = routeUrl.length();

		for (int currentIndex = 0; currentIndex < urlLength;) {
			int indexNextStep = routeUrl.indexOf(Http.PATH_SEPARATOR,
					currentIndex);

			if (indexNextStep == -1) {
				String lastSegment = routeUrl
						.substring(currentIndex, urlLength);
				if (!RouteUtils.isNullOrEmpty(lastSegment)) {
					addSegmentToBack(segments, lastSegment);
				}

				break;
			}

			String nextSegment = routeUrl
					.substring(currentIndex, indexNextStep);
			if (nextSegment.length() > 0) {
				addSegmentToBack(segments, nextSegment);
			}

			addSegmentToBack(segments, Http.PATH_SEPARATOR);
			currentIndex = indexNextStep + 1;
		}

		return segments;
	}

	public static <T> void addSegmentToBack(List<T> segments, T segment) {
		int segmentsSize = segments.size();
		if (segmentsSize > 0) {
			segments.add(segmentsSize, segment);
			return;
		}

		segments.add(segment);
	}

	public static int indexOfFirstParameter(String segment, int startIndex) {

		while (startIndex <= segment.length()) {
			startIndex = segment.indexOf("{", startIndex);
			if (startIndex == -1) {
				return -1;
			}

			if (startIndex + 1 == segment.length()
					|| ((startIndex + 1 < segment.length()) && (segment
							.substring(startIndex + 1) != "{"))) {
				return startIndex;
			}
			startIndex += 2;
		}

		return -1;
	}

	public static String sanitizeLiteralSegment(String segment) {
		String literalStr = segment.replaceAll("\\{\\{", "");
		literalStr = literalStr.replaceAll("}}", "");

		if (literalStr.contains("{") || literalStr.contains("}")) {
			return "";
		}

		String literal = literalStr.replaceAll("\\{\\{", "\\{");
		literal = literal.replaceAll("}}", "}");
		return literal;
	}

	public static boolean isRouteParamNameValid(String name) {
		if (RouteUtils.containsAny(name, "}", "{", "/")) {
			return false;
		}

		return true;
	}

}
