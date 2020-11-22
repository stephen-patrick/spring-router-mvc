package com.github.stephenenright.spring.router.mvc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.stephenenright.spring.router.mvc.RouteSegments.LiteralSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.ParameterSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathSeparatorSegment;

public class RouteParsedTest {

	@Test
	public void match_emptyRoute() {
		RouteParsed parsedRoute = new RouteParsed(new LinkedList<PathSegment>());
		RouteParsed.RouteMatchResult matchResult = parsedRoute.match("/");

		Assert.assertTrue("Expected empty route to match",
				matchResult.isMatched());

		Assert.assertTrue("Expected no matched parameters", matchResult
				.getParams().isEmpty());

		Assert.assertFalse("Expected not to match empty route", parsedRoute
				.match("path").isMatched());

		matchResult = parsedRoute.match("");

		Assert.assertTrue("Expected empty route to match",
				matchResult.isMatched());

		Assert.assertTrue("Expected no matched parameters", matchResult
				.getParams().isEmpty());

		Assert.assertFalse("Expected not to match empty route", parsedRoute
				.match("path").isMatched());

	}

	@Test
	public void match_literalRoute() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part3"));

		RouteParsed parsedRoute = new RouteParsed(pathSegments);
		RouteParsed.RouteMatchResult matchResult = parsedRoute.match("part1/part2/part3");

		Assert.assertTrue("Expected route to match", matchResult.isMatched());

		Assert.assertTrue("Expected no matched parameters", matchResult
				.getParams().isEmpty());

		Assert.assertFalse("Expected not to match empty route", parsedRoute
				.match("").isMatched());

		Assert.assertFalse("Expected not to match",
				parsedRoute.match("part1/part2/part31").isMatched());

		Assert.assertFalse("Expected not to match",
				parsedRoute.match("part1/part2/par3").isMatched());

		Assert.assertFalse("Expected not to match",
				parsedRoute.match("part1/part3/part2").isMatched());

		Assert.assertFalse("Expected not to match",
				parsedRoute.match("part1/part3/part3/part4").isMatched());

		Assert.assertFalse("Expected not to match", parsedRoute.match("part1")
				.isMatched());

	}

	@Test
	public void match_compositeRoute() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));

		RouteParsed parsedRoute = new RouteParsed(pathSegments);
		RouteParsed.RouteMatchResult matchResult = parsedRoute
				.match("part1/paramValue1/part2");
		assertRouteParameters(matchResult, new String[] { "param1" },
				new String[] { "paramValue1" });

		Assert.assertTrue("Expected route to match", matchResult.isMatched());

		Assert.assertFalse("Expected not to match empty route", parsedRoute
				.match("").isMatched());

		Assert.assertFalse("Expected not to match route",
				parsedRoute.match("part/paramValue1/part2").isMatched());

		Assert.assertFalse("Expected not to match route",
				parsedRoute.match("part1").isMatched());

		Assert.assertFalse("Expected not to match route",
				parsedRoute.match("part1/part2").isMatched());

		Assert.assertFalse("Expected not to match route",
				parsedRoute.match("part1/paramValue1/part2/part3").isMatched());
	}

	@Test
	public void pattern_compositeRoute() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));

		RouteParsed parsedRoute = new RouteParsed(pathSegments);

		Assert.assertEquals("Expected pattern to match", parsedRoute
				.pattern(RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION),
				"/part1/${param1}/part2");

		Assert.assertEquals("Expected pattern to match", parsedRoute
				.pattern(new RouteParameterCollection(
						new String[] { "param1" }, new String[] { "value1" })),
				"/part1/value1/part2");

	}

	@Test
	public void matchParameters_compositeRoute() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param2"));

		RouteParsed parsedRoute = new RouteParsed(pathSegments);

		Set<String> expectedParamters = new HashSet<String>();
		expectedParamters.add("param1");
		expectedParamters.add("param2");

		Assert.assertTrue("Expected parameters to match",
				parsedRoute.matchParameters(expectedParamters));
		
	

		expectedParamters = new HashSet<String>();
		expectedParamters.add("param1");
		expectedParamters.add("param2");
		expectedParamters.add("param3");
		
		Assert.assertFalse("Expected parameters not to match",
				parsedRoute.matchParameters(expectedParamters));
		
		
		expectedParamters = new HashSet<String>();
		expectedParamters.add("param1");

		Assert.assertFalse("Expected parameters not to match",
				parsedRoute.matchParameters(expectedParamters));

		expectedParamters = new HashSet<String>();

		Assert.assertFalse("Expected parameters not to match",
				parsedRoute.matchParameters(expectedParamters));

	}

	@Test
	public void reverse_compositeRoute() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));

		RouteParsed parsedRoute = new RouteParsed(pathSegments);

		Assert.assertEquals("Expected pattern to match", parsedRoute
				.reverse(new RouteParameterCollection(
						new String[] { "param1" }, new String[] { "value1" })),
				"/part1/value1/part2");
	}

	@Test
	public void reverse_compositeRoute_missingParameter() {
		List<PathSegment> pathSegments = new LinkedList<PathSegment>();

		pathSegments.add(new LiteralSegment("part1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new ParameterSegment("param1"));
		pathSegments.add(new PathSeparatorSegment());
		pathSegments.add(new LiteralSegment("part2"));

		try {
			Assert.assertEquals("Expected pattern to match", new RouteParsed(
					pathSegments)
					.reverse(RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION),
					"/part1/value1/part2");
		} catch (RouterExceptions.RouteResolveException e) {
			return;
		}

		Assert.fail("Expected RouteReverseException to be thrown");

	}

	protected void assertRouteParameters(RouteParsed.RouteMatchResult result,
                                         String[] expectedParamNames, String[] expectedParamValues) {

		if (expectedParamValues.length != expectedParamNames.length) {
			Assert.fail("Parameter name and parameter values should match");
		}

		Set<String> matchParams = new HashSet<String>();

		for (int i = 0; i < expectedParamNames.length; i++) {
			final String paramName = expectedParamNames[i];

			Assert.assertTrue("Expected parameter: " + paramName
					+ "to be found", result.getParams().containsKey(paramName));

			Assert.assertEquals("Expected parameter value to match for name: "
					+ paramName,
					result.getParams().getOrDefault(paramName, ""),
					expectedParamValues[i]);

			matchParams.add(paramName);
		}

		Assert.assertEquals("Expected all expected parameters to match",
				matchParams.size(), expectedParamNames.length);
	}

}
