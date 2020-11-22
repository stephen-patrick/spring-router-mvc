package com.stephenenright.spring.router.mvc;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.niceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.strictMock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;
import com.stephenenright.spring.router.mvc.constraint.DomainEqualsConstraint;
import com.stephenenright.spring.router.mvc.constraint.RouteConstraints;
import com.stephenenright.spring.router.mvc.constraint.parameter.EmailConstraint;

public class RouteMvcTest {

	private Route route;
	private RouteParsed mockParsedRoute;
	private HttpRequestWrapper mockRequest;

	@Before
	public void setup() {
		mockParsedRoute = strictMock(RouteParsed.class);
		mockRequest = niceMock(HttpRequestWrapper.class);

		route = new RouteMvc(mockParsedRoute, "route1", "segment1",
				"TestController", "list", HttpMethod.GET);

	}

	@Test
	public void match() {

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1");

		expect(mockParsedRoute.match("segment1")).andReturn(
				new RouteParsed.RouteMatchResult(true));

		replay(mockRequest);
		replay(mockParsedRoute);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNotNull("Expected routeDetail not to be null", routeDetail);
		Assert.assertNotNull("Expected route not to be null",
				routeDetail.getRoute());
		Assert.assertEquals("Expected route name to equal: route1", routeDetail
				.getRoute().getName(), "route1");
		Assert.assertTrue("Expected parameters to be empty", routeDetail
				.getParamCollection().isEmpty());

	}

	@Test
	public void match_withRouteConstraints() {
		DomainEqualsConstraint mockDomainConstraint = strictMock(DomainEqualsConstraint.class);

		RouteConstraints routeConstraints = new RouteConstraints();
		routeConstraints.forRoute(mockDomainConstraint);

		Route route = new RouteMvc(mockParsedRoute, "route1", "segment1",
				"TestController", "list", routeConstraints, HttpMethod.GET);

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1");

		expect(mockParsedRoute.match("segment1")).andReturn(
				new RouteParsed.RouteMatchResult(true));

		expect(
				mockDomainConstraint.match(eq(mockRequest),
						anyObject(RouteDetail.class))).andReturn(true);

		replay(mockRequest);
		replay(mockParsedRoute);
		replay(mockDomainConstraint);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNotNull("Expected routeDetail not to be null", routeDetail);
		Assert.assertNotNull("Expected route not to be null",
				routeDetail.getRoute());
		Assert.assertEquals("Expected route name to equal: route1", routeDetail
				.getRoute().getName(), "route1");
		Assert.assertTrue("Expected parameters to be empty", routeDetail
				.getParamCollection().isEmpty());

	}

	@Test
	public void match_withRouteConstraints_fails() {
		DomainEqualsConstraint mockDomainConstraint = strictMock(DomainEqualsConstraint.class);

		RouteConstraints routeConstraints = new RouteConstraints();
		routeConstraints.forRoute(mockDomainConstraint);

		Route route = new RouteMvc(mockParsedRoute, "route1", "segment1",
				"TestController", "list", routeConstraints, HttpMethod.GET);

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1");

		expect(mockParsedRoute.match("segment1")).andReturn(
				new RouteParsed.RouteMatchResult(true));

		expect(
				mockDomainConstraint.match(eq(mockRequest),
						anyObject(RouteDetail.class))).andReturn(false);

		replay(mockRequest);
		replay(mockParsedRoute);
		replay(mockDomainConstraint);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNull("Expected routeDetail to be null", routeDetail);
	}

	@Test
	public void match_withParameterConstraints() {
		EmailConstraint emailConstraint = strictMock(EmailConstraint.class);

		RouteConstraints routeConstraints = new RouteConstraints();
		routeConstraints.forParam("email", emailConstraint);

		Route route = new RouteMvc(mockParsedRoute, "route1",
				"segment1/{email}", "TestController", "list", routeConstraints,
				HttpMethod.GET);

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1/test@testing.com");

		expect(mockParsedRoute.match("segment1/test@testing.com")).andReturn(
				new RouteParsed.RouteMatchResult(true, new RouteParameterCollection(
						new String[] { "email" },
						new Object[] { "test@testing.com" })));

		expect(
				emailConstraint.match(eq(mockRequest),
						anyObject(RouteDetail.class), eq("email"),
						eq("test@testing.com"))).andReturn(true);

		replay(mockRequest);
		replay(mockParsedRoute);
		replay(emailConstraint);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNotNull("Expected routeDetail not to be null", routeDetail);
		Assert.assertNotNull("Expected route not to be null",
				routeDetail.getRoute());
		Assert.assertEquals("Expected route name to equal: route1", routeDetail
				.getRoute().getName(), "route1");
		Assert.assertFalse("Expected parameters not to be empty", routeDetail
				.getParamCollection().isEmpty());

	}

	@Test
	public void match_withParameterConstraints_fails() {
		EmailConstraint emailConstraint = strictMock(EmailConstraint.class);

		RouteConstraints routeConstraints = new RouteConstraints();
		routeConstraints.forParam("email", emailConstraint);

		Route route = new RouteMvc(mockParsedRoute, "route1",
				"segment1/{email}", "TestController", "list", routeConstraints,
				HttpMethod.GET);

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1/testing.com");

		expect(mockParsedRoute.match("segment1/testing.com")).andReturn(
				new RouteParsed.RouteMatchResult(true, new RouteParameterCollection(
						new String[] { "email" },
						new Object[] { "testing.com" })));

		expect(
				emailConstraint.match(eq(mockRequest),
						anyObject(RouteDetail.class), eq("email"),
						eq("testing.com"))).andReturn(false);

		replay(mockRequest);
		replay(mockParsedRoute);
		replay(emailConstraint);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNull("Expected routeDetail to be null", routeDetail);
	}

	public void match_anyHttpMethod() {

		RouteMvc route = new RouteMvc(mockParsedRoute, "route1", "segment1",
				"TestController", "list", HttpMethod.ANY);

		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1");

		expect(mockParsedRoute.match("segment1")).andReturn(
				new RouteParsed.RouteMatchResult(true));

		replay(mockRequest);
		replay(mockParsedRoute);

		RouteDetail routeDetail = route.match(mockRequest);
		Assert.assertNotNull("Expected routeDetail not to be null", routeDetail);
		Assert.assertNotNull("Expected route not to be null",
				routeDetail.getRoute());
		Assert.assertEquals("Expected route name to equal: route1", routeDetail
				.getRoute().getName(), "route1");
		Assert.assertTrue("Expected parameters to be empty", routeDetail
				.getParamCollection().isEmpty());
	}

	public void match_noMatchHttpMethod() {
		expect(mockRequest.getMethod()).andReturn(HttpMethod.POST);
		replay(mockRequest);

		Assert.assertNull("Expected routeDetail to be null",
				route.match(mockRequest));
	}

	public void match_noMatchReturnsNull() {
		expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
		expect(mockRequest.getPath()).andReturn("segment1");

		expect(mockParsedRoute.match("segment1")).andReturn(
				new RouteParsed.RouteMatchResult(false));

		replay(mockRequest);
		replay(mockParsedRoute);

		Assert.assertNull("Expected routeDetail to be null",
				route.match(mockRequest));

	}

}
