package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.constraint.BaseConstraintTest;
import org.junit.Assert;
import org.junit.Test;

public class MinMaxLengthConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {
		MinMaxLengthConstraint minMaxLen = new MinMaxLengthConstraint(2, 5);

		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", null));

		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", ""));

		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", " "));
		
		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "  "));

		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "v"));

		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "values"));

	
		Assert.assertTrue("Expected constraint to match",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "va"));

		Assert.assertTrue("Expected constraint to match",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "value"));
		
		
		
		minMaxLen = new MinMaxLengthConstraint(5, 5);
		
		Assert.assertTrue("Expected constraint to match",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "value"));
		
		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "values"));
		
		Assert.assertFalse("Expected constraint to fail",
				minMaxLen.match(mockedRequest, mockedRoute, "name", "valu"));

	}
	
	
	@Test
	public void create_invalidLength() {

		try {
			new MinMaxLengthConstraint(5,4);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");
		
		
		try {
			new MinMaxLengthConstraint(0, 5);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");

		try {
			new MinMaxLengthConstraint(5, 0);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");
		
		
		try {
			new MinMaxLengthConstraint(0, 0);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail("Expected IllegalArgumentException to be thrown");
		
	}

}
