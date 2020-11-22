package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import com.github.stephenenright.spring.router.mvc.constraint.BaseConstraintTest;
import org.junit.Assert;
import org.junit.Test;

public class MinLengthConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {
		MinLengthConstraint minLen = new MinLengthConstraint(5);

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", null));

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", ""));

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", "  "));

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", "     "));

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", "v"));

		Assert.assertFalse("Expected constraint to fail",
				minLen.match(mockedRequest, mockedRoute, "email", "val"));

		Assert.assertTrue("Expected constraint to match",
				minLen.match(mockedRequest, mockedRoute, "email", "value"));

		Assert.assertTrue("Expected constraint to match",
				minLen.match(mockedRequest, mockedRoute, "email", "values"));
	}

	@Test
	public void create_invalidLength() {

		try {
			new MinLengthConstraint(-1);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");

		try {
			new MinLengthConstraint(0);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");

	}

}
