package spring.router.mvc;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import spring.router.mvc.Http.HttpRequestWrapper;

public class HttpRequestWrapperTest {

	@Test
	public void request() {
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

		expect(mockedRequest.isSecure()).andReturn(false);
		expect(mockedRequest.getHeader("host")).andReturn("test.com");
		expect(mockedRequest.getMethod()).andReturn("GET");
		expect(mockedRequest.getQueryString()).andReturn("?sort=name+");
		expect(mockedRequest.getPathInfo()).andReturn(null);
		expect(mockedRequest.getServletPath()).andReturn("/users");
		expect(mockedRequest.getServletPath()).andReturn("/users");
		expect(mockedRequest.getServletPath()).andReturn("/users");
		expect(mockedRequest.getContextPath()).andReturn("/");
		expect(mockedRequest.getContextPath()).andReturn("/");

		replay(mockedRequest);

		HttpRequestWrapper request = new HttpRequestWrapper(mockedRequest);
		Assert.assertEquals("Expected host to match", request.getHost(),
				"test.com");

		Assert.assertEquals("Expected port to match", request.getPort(), -1);

		Assert.assertFalse("Expected https to be false", request.isHttps());

		Assert.assertEquals("Expected http method to be Get",
				request.getHttpMethod(), HttpMethod.GET);

		Assert.assertEquals("Expected protocol to be http",
				request.getProtocol(), "http");

		Assert.assertEquals("Expected query string to match",
				request.getQueryStr(), "?sort=name+");

		Assert.assertEquals("Expected context path to match",
				request.getContextPath(), "/");

		Assert.assertEquals("Expected path to match", request.getPath(),
				"/users");

		Assert.assertEquals("Expected servlet path to match",
				request.getServletPath(), "/users");

	}

}
