package spring.router.mvc.constraint.parameter;

import org.junit.Assert;
import org.junit.Test;

import spring.router.mvc.constraint.BaseConstraintTest;

public class RegexConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {
		RegexConstraint regex = new RegexConstraint("[\\d+]");

		Assert.assertFalse("Expected constraint to fail",
				regex.match(mockedRequest, mockedRoute, "value", null));

		Assert.assertFalse("Expected constraint to fail",
				regex.match(mockedRequest, mockedRoute, "value", ""));

		Assert.assertFalse("Expected constraint to fail",
				regex.match(mockedRequest, mockedRoute, "value", " "));

		Assert.assertFalse("Expected constraint to fail",
				regex.match(mockedRequest, mockedRoute, "value", "f"));

		Assert.assertTrue("Expected constraint to match",
				regex.match(mockedRequest, mockedRoute, "value", "3"));

	}
}
