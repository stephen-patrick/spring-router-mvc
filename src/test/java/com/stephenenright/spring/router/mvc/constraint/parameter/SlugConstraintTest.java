package com.stephenenright.spring.router.mvc.constraint.parameter;

import com.stephenenright.spring.router.mvc.constraint.BaseConstraintTest;
import org.junit.Assert;
import org.junit.Test;

public class SlugConstraintTest extends BaseConstraintTest {

	@Test
	public void match() {
		SlugConstraint slug = SlugConstraint.slug();

		Assert.assertFalse("Expected constraint to fail",
				slug.match(mockedRequest, mockedRoute, "slug", null));

		Assert.assertFalse("Expected constraint to fail",
				slug.match(mockedRequest, mockedRoute, "slug", " "));
		
		
		Assert.assertFalse("Expected constraint to fail",
				slug.match(mockedRequest, mockedRoute, "slug", "1$"));
		
		Assert.assertFalse("Expected constraint to fail",
				slug.match(mockedRequest, mockedRoute, "slug", "1/"));

		Assert.assertFalse("Expected constraint to fail",
				slug.match(mockedRequest, mockedRoute, "slug", "fdsf/$."));

		Assert.assertTrue("Expected constraint to pass",
				slug.match(mockedRequest, mockedRoute, "slug", "name1-name2"));

		Assert.assertTrue("Expected constraint to pass",
				slug.match(mockedRequest, mockedRoute, "slug", "name1"));

		Assert.assertTrue("Expected constraint to pass",
				slug.match(mockedRequest, mockedRoute, "slug", "1"));
		
		Assert.assertTrue("Expected constraint to pass",
				slug.match(mockedRequest, mockedRoute, "slug", "1-value"));

	}

}
