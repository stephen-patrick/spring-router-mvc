package com.github.stephenenright.spring.router.mvc;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import com.github.stephenenright.spring.router.mvc.RouteSegments.LiteralSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.ParameterSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathCompositeSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathSegment;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathSeparatorSegment;

public class RouteSegmentsTest {

	@Test
	public void match_literalSegment() {
		LiteralSegment ls = new LiteralSegment("path1");

		Assert.assertTrue("Expected literal to match",
				ls.match("path1", new RouteParameterCollection()));

		Assert.assertFalse("Expected literal not to match",
				ls.match("path11", new RouteParameterCollection()));

		Assert.assertFalse("Expected literal not to match",
				ls.match("", new RouteParameterCollection()));

		Assert.assertFalse("Expected literal not to match",
				ls.match("path1/path2", new RouteParameterCollection()));

	}

	@Test
	public void reverse_literalSegment() {
		LiteralSegment ls = new LiteralSegment("path1");

		Assert.assertEquals("Expected literal to be returned",
				ls.reverse(new RouteParameterCollection(), true), "path1");
	}

	@Test
	public void match_pathSeparatorSegment() {
		PathSeparatorSegment ps = new PathSeparatorSegment();

		Assert.assertTrue("Expected segment to match",
				ps.match(Http.PATH_SEPARATOR, new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match(" ", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("sep", new RouteParameterCollection()));

	}

	@Test
	public void reverse_pathSeparatorSegment() {
		PathSeparatorSegment ps = new PathSeparatorSegment();

		Assert.assertEquals("Expected path separtor to be returned",
				ps.reverse(new RouteParameterCollection(), true),
				Http.PATH_SEPARATOR);

	}

	@Test
	public void match_ParameterSegment() {
		ParameterSegment ps = new ParameterSegment("param1");

		RouteParameterCollection paramCollection = new RouteParameterCollection();

		Assert.assertTrue("Expected segment to match",
				ps.match("value1", paramCollection));

		Assert.assertTrue("Expected param to exist",
				paramCollection.containsKey("param1"));

		Assert.assertEquals("Expected param value to match",
				paramCollection.getOrDefault("param1", ""), "value1");

		Assert.assertFalse("Expected segment not to match",
				ps.match("", new RouteParameterCollection()));

	}

	@Test
	public void reverse_ParameterSegment() {
		ParameterSegment ps = new ParameterSegment("param1");

		Assert.assertEquals("Expected expanded parameter value to be returned",
				ps.reverse(new RouteParameterCollection(
						new String[] { "param1" },
						new Object[] { "paramValue" }), true), "paramValue");

	}

	@Test
	public void pattern_ParameterSegment() {
		ParameterSegment ps = new ParameterSegment("param1");

		Assert.assertEquals("Expected expanded parameter value to be returned",
				ps.reverse(new RouteParameterCollection(), false), "${param1}");

	}

	@Test
	public void reverse_ParameterSegment_MissingParameter() {
		ParameterSegment ps = new ParameterSegment("param1");

		try {
			Assert.assertEquals(
					"Expected expanded parameter value to be returned",
					ps.reverse(new RouteParameterCollection(), true),
					"paramValue");
		} catch (RouterExceptions.RouteResolveException rre) {
			return;
		}

		Assert.fail("Expected RouteReverseException to be thrown");
	}

	@Test
	public void match_pathCompositeSegment() {
		List<PathSegment> subSegments = new LinkedList<PathSegment>();

		subSegments.add(new LiteralSegment("prefix-"));
		subSegments.add(new ParameterSegment("param1"));
		subSegments.add(new LiteralSegment("-suffix"));

		PathCompositeSegment ps = new PathCompositeSegment(subSegments);

		RouteParameterCollection paramCollection = new RouteParameterCollection();

		Assert.assertTrue("Expected segment to match",
				ps.match("prefix-param-value1-suffix", paramCollection));

		Assert.assertTrue("Expected param to exist",
				paramCollection.containsKey("param1"));

		Assert.assertEquals("Expected param value to match",
				paramCollection.getOrDefault("param1", ""), "param-value1");

		Assert.assertFalse("Expected segment not to match",
				ps.match("", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("prefixvalue1-suffix", new RouteParameterCollection()));

		subSegments = new LinkedList<PathSegment>();
		subSegments.add(new LiteralSegment("prefix-"));
		subSegments.add(new ParameterSegment("param1"));

		ps = new PathCompositeSegment(subSegments);
		paramCollection = new RouteParameterCollection();

		Assert.assertTrue("Expected segment to match",
				ps.match("prefix-param-value1", paramCollection));

		Assert.assertTrue("Expected param to exist",
				paramCollection.containsKey("param1"));

		Assert.assertEquals("Expected param value to match",
				paramCollection.getOrDefault("param1", ""), "param-value1");

		Assert.assertFalse("Expected segment not to match",
				ps.match("", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("pre-value1-suffix", new RouteParameterCollection()));

		subSegments = new LinkedList<PathSegment>();
		subSegments.add(new ParameterSegment("param1"));
		subSegments.add(new LiteralSegment("-suffix"));

		ps = new PathCompositeSegment(subSegments);
		paramCollection = new RouteParameterCollection();

		Assert.assertTrue("Expected segment to match",
				ps.match("param-value1-suffix", paramCollection));

		Assert.assertTrue("Expected param to exist",
				paramCollection.containsKey("param1"));

		Assert.assertEquals("Expected param value to match",
				paramCollection.getOrDefault("param1", ""), "param-value1");

		Assert.assertFalse("Expected segment not to match",
				ps.match("", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("param-value1", new RouteParameterCollection()));

		Assert.assertFalse("Expected segment not to match",
				ps.match("param-value1-suffiX", new RouteParameterCollection()));
	}

	@Test
	public void reverse_pathCompositeSegment() {
		List<PathSegment> subSegments = new LinkedList<PathSegment>();

		subSegments.add(new LiteralSegment("prefix-"));
		subSegments.add(new ParameterSegment("param1"));
		subSegments.add(new LiteralSegment("-suffix"));

		PathCompositeSegment ps = new PathCompositeSegment(subSegments);

		Assert.assertEquals("Expected expanded composite to match", ps.reverse(
				new RouteParameterCollection(new String[] { "param1" },
						new Object[] { "paramValue" }), true),
				"prefix-paramValue-suffix");
	}

}
