package com.github.stephenenright.spring.router.mvc;

import org.junit.Test;

import org.junit.Assert;

public class RouteUtilsTest {

	@Test
	public void lastIndexFrom() {
		String testStr = "part1/part2/part3";

		int index = RouteUtils
				.lastIndexFrom(testStr, "part3", testStr.length());

		Assert.assertTrue(String.format(
				"Expected index to be found at 12 but was: %s", index),
				index == 12);

		Assert.assertEquals("Expected substring to match",
				testStr.substring(index), "part3");

		Assert.assertEquals("Expected not to be found",
				RouteUtils.lastIndexFrom(testStr, "p1", testStr.length()), -1);

	}

	@Test
	public void containsAny() {
		Assert.assertTrue("Expected to contain '{'",
				RouteUtils.containsAny("paramStr{name}", "}"));

		Assert.assertTrue("Expected to contain '{'",
				RouteUtils.containsAny("paramStr{name}", "}", "{", "/"));

		Assert.assertFalse("Expected not to contain ','",
				RouteUtils.containsAny("paramStr{name}", ","));

	}

	@Test
	public void isNullOrEmpty() {
		Assert.assertTrue("Expected to be null or empty",
				RouteUtils.isNullOrEmpty(" "));
		Assert.assertTrue("Expected to be null or empty",
				RouteUtils.isNullOrEmpty(""));
		Assert.assertTrue("Expected to be null or empty",
				RouteUtils.isNullOrEmpty(null));
		Assert.assertFalse("Expected not to be true",
				RouteUtils.isNullOrEmpty("str"));
	}

	@Test
	public void escapeRegex() {
		Assert.assertEquals("Expected to be escaped",
				RouteUtils.escapeRegex("testing.com"), "testing\\.com");

	}

	@Test
	public void isPathSeparator() {
		Assert.assertTrue("Expected to be true",
				RouteUtils.isPathSeparator(Http.PATH_SEPARATOR));

		Assert.assertFalse("Expected to be false",
				RouteUtils.isPathSeparator("str"));

	}

	@Test
	public void joinContextWithPathAsRootRelative() {
		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("", ""),
				Http.PATH_SEPARATOR);

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("/", "/"),
				Http.PATH_SEPARATOR);

		Assert.assertEquals("Expected to be match", RouteUtils
				.joinContextWithPathAsRootRelative("/context/", "/path1"),
				"/context/path1");

		Assert.assertEquals("Expected to be match", RouteUtils
				.joinContextWithPathAsRootRelative("/context/", "/path1/"),
				"/context/path1/");

		Assert.assertEquals("Expected to be match", RouteUtils
				.joinContextWithPathAsRootRelative("/context/////", "/path1/"),
				"/context/path1/");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("/", "/path1/"),
				"/path1/");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("/", "/path1"),
				"/path1");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("", "/path1"),
				"/path1");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative(null, "/path1"),
				"/path1");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative(null, "path1"),
				"/path1");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative(null, null), "/");

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("/context", null),
				"/context");
		

		Assert.assertEquals("Expected to be match",
				RouteUtils.joinContextWithPathAsRootRelative("/context", "/"),
				"/context");

	}

}
