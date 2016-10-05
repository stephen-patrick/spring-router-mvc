/**
Copyright [2010] [Stephen Enright and contributors]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 **/

package spring.router.mvc;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import spring.router.mvc.RouterExceptions.RouteResolveException;

class RouteSegments {

	private static final String FORMAT_REVERSE_PARAM_PLACEHOLDER = "${%s}";

	public static interface PathSegment {
		boolean match(String segmentToMatch, RouteParameterCollection params);

		String reverse(RouteParameterCollection params, boolean strict);

		void collectParameterNames(Set<String> parameterNameSet);
	}

	public static interface PathSubSegment extends PathSegment {

	}

	public abstract static class BaseSegment implements PathSegment {

		@Override
		public void collectParameterNames(Set<String> parameterNameSet) {

		}
	}

	public static class PathSeparatorSegment extends BaseSegment implements
			PathSegment {
		public boolean match(String segmentToMatch,
				RouteParameterCollection params) {
			return RouteUtils.isPathSeparator(segmentToMatch);
		}

		public String reverse(RouteParameterCollection params, boolean strict) {
			return Http.PATH_SEPARATOR;
		}

	}

	public static class LiteralSegment extends BaseSegment implements
			PathSubSegment {
		private String literal;

		public LiteralSegment(String literal) {
			this.literal = literal;
		}

		public boolean match(String segmentToMatch,
				RouteParameterCollection params) {
			return literal.equals(segmentToMatch);
		}

		public String reverse(RouteParameterCollection params, boolean strict) {
			return literal;
		}

		public String getLiteral() {
			return literal;
		}
	}

	public static class ParameterSegment implements PathSubSegment {
		private String parameterName;

		public ParameterSegment(String parameterName) {
			this.parameterName = parameterName;
		}

		public boolean match(String segmentToMatch,
				RouteParameterCollection params) {
			if (RouteUtils.isNullOrEmpty(segmentToMatch)) {
				return false;
			}

			params.add(parameterName, segmentToMatch);
			return true;
		}

		public String reverse(RouteParameterCollection params, boolean strict) {
			String paramValue = params.getOrDefault(parameterName, null);

			if (RouteUtils.isNullOrEmpty(paramValue) && strict) {
				throw new RouteResolveException(String.format(
						"Parameter: %s required", parameterName));
			} else if (RouteUtils.isNullOrEmpty(paramValue)) {
				return String.format(FORMAT_REVERSE_PARAM_PLACEHOLDER,
						parameterName);
			}

			return paramValue;
		}

		@Override
		public void collectParameterNames(Set<String> parameterNameSet) {
			parameterNameSet.add(getParameterName());

		}

		public String getParameterName() {
			return parameterName;
		}
	}

	public static class PathCompositeSegment implements PathSegment {

		private List<PathSegment> segments = new LinkedList<PathSegment>();

		public PathCompositeSegment(List<PathSegment> segments) {
			this.segments = segments;
		}

		public boolean match(String segmentToMatch,
				RouteParameterCollection params) {

			RouteParameterCollection localParams = new RouteParameterCollection();

			if (matchSegment(segmentToMatch, localParams)) {
				params.addAll(localParams);
				return true;
			}

			return false;
		}

		@Override
		public void collectParameterNames(Set<String> parameterNameSet) {
			for (PathSegment segment : segments) {
				segment.collectParameterNames(parameterNameSet);
			}
		}

		public boolean matchSegment(String segmentToMatch,
				RouteParameterCollection params) {

			return new CompositeMatcher(segmentToMatch, segments).match(params);
		}

		public String reverse(RouteParameterCollection params, boolean strict) {
			StringWriter writer = new StringWriter();

			for (PathSegment segment : segments) {
				writer.append(segment.reverse(params, strict));
			}

			return writer.toString();
		}

		private static class CompositeMatcher {
			private PathSegment current;
			private ParameterSegment previousParameter;
			private LiteralSegment previousLiteral;
			private int curentIndex;
			private int lastMatchedIndex;
			private String matchStr;
			private List<PathSegment> segmentsToMatch;

			public CompositeMatcher(String matchStr,
					List<PathSegment> segmentsToMatch) {
				this.matchStr = matchStr;
				this.curentIndex = matchStr.length();
				this.segmentsToMatch = segmentsToMatch;
			}

			public boolean match(RouteParameterCollection params) {

				for (int currentSegmentIndex = segmentsToMatch.size() - 1; currentSegmentIndex >= 0; currentSegmentIndex--) {

					if (!isValid(segmentsToMatch.get(currentSegmentIndex))) {
						return false;
					}

					lastMatchedIndex = curentIndex;

					setCurrent(segmentsToMatch.get(currentSegmentIndex));

					if (!isParameterSegment() && isLiteralSegment()) {

						if (!matchLiteral(currentSegmentIndex)) {
							return false;
						}
					}

					if (isPreviousParameterMatched()
							&& ((isPreviousLiteralMatched() && !isParameterSegment()) || currentSegmentIndex == 0)) {

						if (!matchParameter(params, currentSegmentIndex)) {
							return false;
						}

					}

					curentIndex = lastMatchedIndex;
				}

				return curentIndex == 0
						|| (this.segmentsToMatch.get(0) instanceof ParameterSegment);
			}

			private boolean isValid(PathSegment current) {
				return !((current instanceof ParameterSegment) && isPreviousParameterMatched());
			}

			private void setCurrent(PathSegment current) {
				this.current = current;

				if (isParameterSegment()) {
					previousParameter = (ParameterSegment) current;
				} else if (isLiteralSegment()) {
					previousLiteral = (LiteralSegment) current;
				}
			}

			private boolean matchLiteral(int currentSegmentIndex) {
				int startIndex = curentIndex;

				if (isPreviousParameterMatched()) {
					startIndex--;
				}

				if (startIndex < 0) {
					return false;
				}

				int literalIndex = RouteUtils.lastIndexFrom(matchStr,
						getCurrentLiteral().getLiteral(), startIndex);

				if (literalIndex == -1) {
					return false;
				}

				if ((currentSegmentIndex == segmentsToMatch.size() - 1)
						&& (literalIndex
								+ getCurrentLiteral().getLiteral().length() != matchStr
								.length())) {
					return false;
				}

				this.lastMatchedIndex = literalIndex;
				return true;

			}

			private boolean matchParameter(RouteParameterCollection params,
					int currentSegmentIndex) {

				int paramStartIndex = 0;
				int paramEndIndex = curentIndex;

				if (!isPreviousLiteralMatched()
						&& !isFirstSegment(currentSegmentIndex)) {
					paramStartIndex = lastMatchedIndex;

				} else if (isPreviousLiteralMatched()
						&& !isFirstParameter(currentSegmentIndex)) {
					paramStartIndex = lastMatchedIndex
							+ getCurrentLiteral().getLiteral().length();
				}

				String paramValue = matchStr.substring(paramStartIndex,
						paramEndIndex);

				if (RouteUtils.isNullOrEmpty(paramValue)) {
					return false;
				}

				params.add(previousParameter.getParameterName(), paramValue);
				previousParameter = null;
				previousLiteral = null;

				return true;
			}

			private boolean isFirstParameter(int currentSegmentIndex) {
				return isFirstSegment(currentSegmentIndex)
						&& isParameterSegment();
			}

			private boolean isPreviousParameterMatched() {
				return previousParameter != null;
			}

			private boolean isPreviousLiteralMatched() {
				return previousLiteral != null;
			}

			private boolean isParameterSegment() {
				return current instanceof ParameterSegment;
			}

			private boolean isLiteralSegment() {
				return current instanceof LiteralSegment;
			}

			private LiteralSegment getCurrentLiteral() {
				return (LiteralSegment) current;
			}

			private boolean isFirstSegment(int currentSegmentIndex) {
				return currentSegmentIndex == 0;
			}
		}

	}
}
