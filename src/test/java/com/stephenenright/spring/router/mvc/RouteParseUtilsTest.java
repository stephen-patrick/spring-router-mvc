package com.stephenenright.spring.router.mvc;

import org.junit.Assert;
import org.junit.Test;

public class RouteParseUtilsTest {

	@Test
	public void indexOfFirstParameter() {
		Assert.assertEquals(
				RouteParseUtils.indexOfFirstParameter("t{param1}", 0), 1);
		Assert.assertEquals(
				RouteParseUtils.indexOfFirstParameter("tparam1}", 0), -1);
		Assert.assertEquals(RouteParseUtils.indexOfFirstParameter("param1", 0),
				-1);
	}

	@Test
	public void sanitizeLiteralSegment() {
		Assert.assertEquals(RouteParseUtils.sanitizeLiteralSegment("{{test}}"),
				"test");
		Assert.assertEquals(RouteParseUtils.sanitizeLiteralSegment("{{test}"),
				"");
	}

}
