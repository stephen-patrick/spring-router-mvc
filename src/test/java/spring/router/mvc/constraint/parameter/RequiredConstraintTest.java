package spring.router.mvc.constraint.parameter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import spring.router.mvc.constraint.BaseConstraintTest;

public class RequiredConstraintTest extends BaseConstraintTest {

	private RequiredConstraint required;

	@Before
	public void setup() {
		required = new RequiredConstraint();
	}

	@Test
	public void match() {
		Assert.assertFalse("Expected constraint to fail",
				required.match(mockedRequest, mockedRoute, "param1", null));

		Assert.assertFalse("Expected constraint to fail",
				required.match(mockedRequest, mockedRoute, "param1", " "));

		Assert.assertFalse("Expected constraint to fail",
				required.match(mockedRequest, mockedRoute, "param1", ""));

		Assert.assertTrue("Expected constraint to match",
				required.match(mockedRequest, mockedRoute, "param1", "value"));

	}

}
