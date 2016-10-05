package spring.router.mvc.constraint.parameter;

import org.junit.Assert;
import org.junit.Test;

import spring.router.mvc.constraint.BaseConstraintTest;

public class MinConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {

		MinConstraint min = new MinConstraint(5);

		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", null));
		
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", " "));

	
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", "fdfd"));
		
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", "4fdfd"));
		
		
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", "4"));
		
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", "0"));
		
		Assert.assertFalse("Expected constraint to fail",
				min.match(mockedRequest, mockedRoute, "value", "-2"));
	
		Assert.assertTrue("Expected constraint to match",
				min.match(mockedRequest, mockedRoute, "value", "16"));
		
	}

}
