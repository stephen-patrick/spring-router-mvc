package spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import spring.router.mvc.Http;
import spring.router.mvc.RouteConstants;
import spring.router.mvc.RouteParameterCollection;
import spring.router.mvc.RouteParsed;
import spring.router.mvc.RouteParser;
import spring.router.mvc.RouteSegments.LiteralSegment;
import spring.router.mvc.RouteSegments.ParameterSegment;
import spring.router.mvc.RouteSegments.PathCompositeSegment;
import spring.router.mvc.RouteSegments.PathSegment;
import spring.router.mvc.RouteSegments.PathSeparatorSegment;
import spring.router.mvc.RouterExceptions.RouteParseException;

public class RouteParserTest {

	private RouteParser routeParser;

	@Before
	public void setup() {
		routeParser = new RouteParser();
	}

	@Test
	public void parseRoute_literalRoute() {
		RouteParsed parsedRoute = routeParser.parseRoute("part1/part2/part3");

		Assert.assertNotNull("Expected route to be parsed", parsedRoute);

		List<PathSegment> segments = parsedRoute.getPathSegments();

		Assert.assertTrue("Expected five segments", segments.size() == 5);
		Assert.assertTrue("Expected Literal Segment",
				segments.get(0) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: part1",
				((LiteralSegment) segments.get(0)).getLiteral(), "part1");
		Assert.assertTrue("Expected Path Segment",
				segments.get(1) instanceof PathSeparatorSegment);
		Assert.assertTrue("Expected Literal Segment",
				segments.get(2) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: part2",
				((LiteralSegment) segments.get(2)).getLiteral(), "part2");
		Assert.assertTrue("Expected Path Segment",
				segments.get(3) instanceof PathSeparatorSegment);
		Assert.assertTrue("Expected Literal Segment",
				segments.get(4) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: part3",
				((LiteralSegment) segments.get(4)).getLiteral(), "part3");

		parsedRoute = routeParser.parseRoute("part1");
		Assert.assertNotNull("Expected route to be parsed", parsedRoute);

		segments = parsedRoute.getPathSegments();

		Assert.assertTrue("Expected one segments", segments.size() == 1);
		Assert.assertTrue("Expected Literal Segment",
				segments.get(0) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: part1",
				((LiteralSegment) segments.get(0)).getLiteral(), "part1");

	}

	@Test
	public void parseRoute_ParamterRoute() {
		RouteParsed parsedRoute = routeParser.parseRoute("{param1}");

		Assert.assertNotNull("Expected route to be parsed", parsedRoute);

		List<PathSegment> segments = parsedRoute.getPathSegments();

		Assert.assertTrue("Expected one segments", segments.size() == 1);
		Assert.assertTrue("Expected Parameter Segment",
				segments.get(0) instanceof ParameterSegment);
		Assert.assertEquals(
				"Expected Parameter Segment to have parameter name: param1",
				((ParameterSegment) segments.get(0)).getParameterName(),
				"param1");

		parsedRoute = routeParser.parseRoute("{param1}/{param2}");
		Assert.assertNotNull("Expected route to be parsed", parsedRoute);

		segments = parsedRoute.getPathSegments();

		Assert.assertTrue("Expected three segments", segments.size() == 3);
		Assert.assertTrue("Expected Parameter Segment",
				segments.get(0) instanceof ParameterSegment);
		Assert.assertEquals(
				"Expected Parameter Segment to have parameter name: param1",
				((ParameterSegment) segments.get(0)).getParameterName(),
				"param1");
		Assert.assertTrue("Expected Path Segment",
				segments.get(1) instanceof PathSeparatorSegment);
		Assert.assertTrue("Expected Parameter Segment",
				segments.get(2) instanceof ParameterSegment);
		Assert.assertEquals(
				"Expected Parameter Segment to have parameter name: param2",
				((ParameterSegment) segments.get(2)).getParameterName(),
				"param2");

	}

	@Test
	public void parseRoute_compositeRoute() {
		RouteParsed parsedRoute = routeParser
				.parseRoute("path1/{param1}/path2/{param2}");

		Assert.assertNotNull("Expected route to be parsed", parsedRoute);

		List<PathSegment> segments = parsedRoute.getPathSegments();
		Assert.assertTrue("Expected one segments", segments.size() == 7);

		Assert.assertTrue("Expected Literal Segment",
				segments.get(0) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: path1",
				((LiteralSegment) segments.get(0)).getLiteral(), "path1");

		Assert.assertTrue("Expected Path Segment",
				segments.get(1) instanceof PathSeparatorSegment);

		Assert.assertTrue("Expected Parameter Segment",
				segments.get(2) instanceof ParameterSegment);
		Assert.assertEquals(
				"Expected Parameter Segment to have parameter name: param1",
				((ParameterSegment) segments.get(2)).getParameterName(),
				"param1");

		Assert.assertTrue("Expected Path Segment",
				segments.get(3) instanceof PathSeparatorSegment);

		Assert.assertTrue("Expected Literal Segment",
				segments.get(4) instanceof LiteralSegment);
		Assert.assertEquals("Expected Literal Segment to have value: path2",
				((LiteralSegment) segments.get(4)).getLiteral(), "path2");

		Assert.assertTrue("Expected Path Segment",
				segments.get(5) instanceof PathSeparatorSegment);

		Assert.assertTrue("Expected Parameter Segment",
				segments.get(6) instanceof ParameterSegment);
		Assert.assertEquals(
				"Expected Parameter Segment to have parameter name: param2",
				((ParameterSegment) segments.get(6)).getParameterName(),
				"param2");

	}
	
	@Test
	public void reverseRoute_Root() {
		List<PathSegment> segments = new LinkedList<PathSegment>();

		Assert.assertEquals("Expected route to be reversed", new RouteParsed(
				segments)
				.reverse(RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION),
				Http.PATH_SEPARATOR);
	}
	
	@Test
	public void reverseRoute_compositeRoute() {
		List<PathSegment> segments = new LinkedList<PathSegment>();

		segments.add(new LiteralSegment("segment1"));
		segments.add(new PathSeparatorSegment());

		List<PathSegment> subSegments = new LinkedList<PathSegment>();

		subSegments.add(new LiteralSegment("prefix-"));
		subSegments.add(new ParameterSegment("param1"));
		segments.add(new PathCompositeSegment(subSegments));

		RouteParsed routeParsed = new RouteParsed(segments);

		Assert.assertEquals("Expected route to be reversed", routeParsed
				.reverse(new RouteParameterCollection(
						new String[] { "param1" },
						new Object[] { "paramValue" })),
				"/segment1/prefix-paramValue");
	}

	

	@Test
	public void parseRoute_errorRepeatingParameters() {
		try {
			routeParser.parseRoute("part1/{param1}{param2}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");
	}

	@Test
	public void parseRoute_errorInvalidParamName() {
		try {
			routeParser.parseRoute("part1/{fdsf/}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");

		try {
			routeParser.parseRoute("part1/{fd{sf}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");

		try {
			routeParser.parseRoute("part1/{fd}sf}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");
	}

	@Test
	public void parseRoute_errorEmptyParamName() {
		try {
			routeParser.parseRoute("part1/{}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");
	}

	@Test
	public void parseRoute_errorUniqueParamName() {
		try {
			routeParser.parseRoute("part1/{param1}/{param1}");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");
	}

	@Test
	public void parseRoute_errorRepeatingPathSeparator() {
		try {
			routeParser.parseRoute("part1//part2");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");
	}

	@Test
	public void parseRoute_errorInvalidRoute() {
		try {
			routeParser.parseRoute("part1/part2?sfdsf");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");

		try {
			routeParser.parseRoute("?");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");

		try {
			routeParser.parseRoute(">");
		} catch (RouteParseException rpe) {
			return;
		}

		Assert.fail("Expected RouteParseException to be caught");

	}

	@Test
	public void parseRoute_isRoot() {
		RouteParsed parsedRoute = routeParser.parseRoute("");
		Assert.assertNotNull("Expected route to be parsed", parsedRoute);
		Assert.assertTrue("Expected to be isRoot", parsedRoute.isRoot());
	}

}
