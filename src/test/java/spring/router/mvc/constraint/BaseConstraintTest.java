package spring.router.mvc.constraint;

import static org.easymock.EasyMock.mock;

import org.junit.Before;

import spring.router.mvc.Http.HttpRequestWrapper;
import spring.router.mvc.RouteDetail;

public class BaseConstraintTest {

	protected HttpRequestWrapper mockedRequest;
	protected RouteDetail mockedRoute;

	@Before
	public void setup() {
		mockedRequest = mock(HttpRequestWrapper.class);
		mockedRoute = mock(RouteDetail.class);
	}

	public HttpRequestWrapper getMockedRequest() {
		return mockedRequest;
	}

	public RouteDetail getMockedRoute() {
		return mockedRoute;
	}

}
