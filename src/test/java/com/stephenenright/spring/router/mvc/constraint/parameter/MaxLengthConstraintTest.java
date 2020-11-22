package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.constraint.BaseConstraintTest;
import org.junit.Assert;
import org.junit.Test;

public class MaxLengthConstraintTest extends BaseConstraintTest {

	
	@Test
	public void match() {
		MaxLengthConstraint maxLen = new MaxLengthConstraint(5);

		Assert.assertFalse("Expected constraint to fail",
				maxLen.match(mockedRequest, mockedRoute, "name", null));
		
		Assert.assertFalse("Expected constraint to fail",
				maxLen.match(mockedRequest, mockedRoute, "name", ""));
		
		Assert.assertFalse("Expected constraint to fail",
				maxLen.match(mockedRequest, mockedRoute, "name", "      "));
		
		Assert.assertFalse("Expected constraint to fail",
				maxLen.match(mockedRequest, mockedRoute, "name", "values"));
		
	
		Assert.assertTrue("Expected constraint to match",
				maxLen.match(mockedRequest, mockedRoute, "name", "v"));

		Assert.assertTrue("Expected constraint to match",
				maxLen.match(mockedRequest, mockedRoute, "name", "val"));

		Assert.assertTrue("Expected constraint to match",
				maxLen.match(mockedRequest, mockedRoute, "name", "value"));
		
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
