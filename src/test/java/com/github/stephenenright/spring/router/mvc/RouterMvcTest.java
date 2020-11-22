package com.github.stephenenright.spring.router.mvc;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.niceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.strictMock;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;
import com.github.stephenenright.spring.router.mvc.RouteOutputOptions.UrlOuputOptions;
import com.github.stephenenright.spring.router.mvc.RouteSegments.PathSegment;

public class RouterMvcTest {

	private HttpRequestWrapper mockRequest;
	private HttpServletRequest mockServletRequest;
	private RouteCollection mockRoutesCollection;
	private RouterMvc routerMvc;

	@Before
	public void setup() {
		mockRoutesCollection = strictMock(RouteCollection.class);
		mockRequest = niceMock(HttpRequestWrapper.class);
		mockServletRequest = niceMock(HttpServletRequest.class);
		routerMvc = new RouterMvc(mockRoutesCollection);

	}

	@Test
	public void match() {

		Route route = new RouteMvc(new RouteParsed(
				new LinkedList<PathSegment>()), "route1", "segment",
				"TestController", "List");

		expect(mockRequest.isHttpMethodUnknown()).andReturn(false);
		expect(mockRoutesCollection.matchRoute(mockRequest)).andReturn(
				new RouteDetail(route, new RouteParameterCollection()));

		replay(mockRequest);
		replay(mockRoutesCollection);

		RouteDetail detail = routerMvc.match(mockRequest);
		Assert.assertNotNull("Expected route to be matched", detail);
		Assert.assertEquals("Expected route name to match route1", detail
				.getRoute().getName(), "route1");
	}

	@Test
	public void reverse_byName() {

		expect(
				mockRoutesCollection.reverseRoute("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("part1/part");

		expect(mockRequest.isHttpMethodUnknown()).andReturn(false);
		expect(mockServletRequest.getContextPath()).andReturn("/");

		replay(mockRoutesCollection, mockServletRequest);

		Assert.assertEquals("Expected reversed to match",
				routerMvc.reverse("route1", mockServletRequest), "/part1/part");

	}

	@Test
	public void reverse_byNameWithOptions() {

		expect(
				mockRoutesCollection.reverseRoute("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("part1/part");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected reversed to match", routerMvc.reverse(
				"route1", new UrlOuputOptions("test.com", true)),
				"https://test.com/part1/part");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.reverseRoute("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected reversed to match", routerMvc.reverse(
				"route1", new UrlOuputOptions("test.com", true)),
				"https://test.com");
	}

	@Test
	public void pattern_byName() {

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("part1/${param1}");

		expect(mockServletRequest.getContextPath()).andReturn("/");

		replay(mockRoutesCollection);
		replay(mockServletRequest);

		Assert.assertEquals("Expected pattern to match",
				routerMvc.pattern("route1", mockServletRequest),
				"/part1/${param1}");
	}

	@Test
	public void pattern_byNameWithOptions() {

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("part1/${param1}");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", true)),
				"https://test.com/part1/${param1}");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", true)),
				"https://test.com");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", "/", true)),
				"https://test.com");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", "/context", true)),
				"https://test.com/context");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", "context", true)),
				"https://test.com/context");

		reset(mockRoutesCollection);

		expect(
				mockRoutesCollection.routePattern("route1",
						RouteConstants.EMPTY_ROUTE_PARAMETER_COLLECTION))
				.andReturn("/path1");

		replay(mockRoutesCollection);

		Assert.assertEquals("Expected pattern to match", routerMvc.pattern(
				"route1", new UrlOuputOptions("test.com", "context", true)),
				"https://test.com/context/path1");

	}

	@Test
	public void match_noMatchOnUnknownMethod() {

		expect(mockRequest.isHttpMethodUnknown()).andReturn(true);

		replay(mockRequest);

		RouteDetail detail = routerMvc.match(mockRequest);
		Assert.assertNull("Expected no match", detail);

	}

}
