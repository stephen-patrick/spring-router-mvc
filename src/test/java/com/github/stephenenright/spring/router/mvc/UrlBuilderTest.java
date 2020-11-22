package com.github.stephenenright.spring.router.mvc;

import org.junit.Assert;

import org.junit.Test;

import com.github.stephenenright.spring.router.mvc.Http.Protocol;

public class UrlBuilderTest {

	@Test
	public void buildUrl() {
		UrlBuilder builder = new UrlBuilder("test.com", "/", "/tests");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/tests");

		builder = new UrlBuilder("test.com", "", "tests");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/tests");
	}

	@Test
	public void buildUrl_rootUrl() {
		UrlBuilder builder = new UrlBuilder("test.com", "/", "");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com");

		builder = new UrlBuilder("test.com", "", "/");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com");
		
		builder = new UrlBuilder("test.com", "/", "/");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com");

	}

	@Test
	public void buildUrl_queryStr() {
		UrlBuilder builder = new UrlBuilder("test.com", "/", "path1?testing=1");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/path1?testing=1");

		builder.addQueryStrParam("param2", "value2");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/path1?testing=1&param2=value2");

		builder = new UrlBuilder("test.com","", "path1");
		builder.addQueryStrParam("param1", "value1");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/path1?param1=value1");

		builder.addQueryStrParam("param2", "value2");

		Assert.assertEquals("Expected url to match", builder.buildUrl(),
				"http://test.com/path1?param1=value1&param2=value2");
	}

	@Test
	public void buildUrl_noHost() {
		try {
			new UrlBuilder("", "/", "path1?testing=1");
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");
	}

	@Test
	public void buildUrl_invalidPort() {
		try {
			new UrlBuilder(Protocol.HTTP,  "test.com", -2, "/","path1?testing=1");
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");

	}

	@Test
	public void buildUrl_invalidProtocol() {
		try {
			new UrlBuilder(null, "test.com", -2, "/", "path1?testing=1");
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");

	}

}
