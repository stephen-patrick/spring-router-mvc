package spring.router.mvc.constraint.parameter;

import org.junit.Assert;
import org.junit.Test;

import spring.router.mvc.constraint.BaseConstraintTest;

public class MaxConstraintTest extends BaseConstraintTest {

	
	@Test
	public void match() {
		MaxConstraint max = new MaxConstraint(5);
		
		Assert.assertFalse("Expected constraint to fail",
				max.match(mockedRequest, mockedRoute, "name", null));
		
		Assert.assertFalse("Expected constraint to fail",
				max.match(mockedRequest, mockedRoute, "name", ""));
		
		Assert.assertFalse("Expected constraint to fail",
				max.match(mockedRequest, mockedRoute, "name", "6"));
		
		Assert.assertTrue("Expected constraint to match",
				max.match(mockedRequest, mockedRoute, "name", "-1"));
		
		Assert.assertTrue("Expected constraint to match",
				max.match(mockedRequest, mockedRoute, "name", "5"));
		
		
	}
	
}
