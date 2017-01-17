package spring.router.mvc.constraint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.junit.Assert;
import org.junit.Test;

public class DomainEqualsConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {

		DomainEqualsConstraint domain = new DomainEqualsConstraint(
				"testing.com", "test.com");

		expect(mockedRequest.getHost()).andReturn("testing.com");
		replay(mockedRequest);

		Assert.assertTrue("Expected constraint to match",
				domain.match(mockedRequest, mockedRoute));

	}

	@Test
	public void match_failed() {

		DomainEqualsConstraint domain = new DomainEqualsConstraint(
				"testing.com", "test.com");

		expect(mockedRequest.getHost()).andReturn("tests.com").times(2);
		replay(mockedRequest);

		Assert.assertFalse("Expected constraint to fail",
				domain.match(mockedRequest, mockedRoute));

	}

}
