package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import com.github.stephenenright.spring.router.mvc.constraint.BaseConstraintTest;
import org.junit.Assert;
import org.junit.Test;

public class MinMaxConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {
		MinMaxConstraint minMax = new MinMaxConstraint(2, 5);

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", null));

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", ""));

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", " "));

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", "v"));

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", "1"));

		Assert.assertFalse("Expected constraint to fail",
				minMax.match(mockedRequest, mockedRoute, "value", "6"));

		Assert.assertTrue("Expected constraint to match",
				minMax.match(mockedRequest, mockedRoute, "value", "2"));

		Assert.assertTrue("Expected constraint to match",
				minMax.match(mockedRequest, mockedRoute, "value", "3"));

		Assert.assertTrue("Expected constraint to match",
				minMax.match(mockedRequest, mockedRoute, "value", "5"));

		minMax = new MinMaxConstraint(5, 5);

		Assert.assertTrue("Expected constraint to match",
				minMax.match(mockedRequest, mockedRoute, "value", "5"));

	}

}
