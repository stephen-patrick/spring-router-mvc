package spring.router.mvc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import spring.router.mvc.RouteParameterCollection;

public class RouteParameterCollectionTest {

	private RouteParameterCollection paramCollection;

	@Before
	public void setup() {
		paramCollection = new RouteParameterCollection();
		paramCollection.add("param1", "value1");
		paramCollection.add("param2", "value2");
	}

	@Test
	public void containsKey() {
		Assert.assertTrue("Expected to contain param1",
				paramCollection.containsKey("param1"));

		Assert.assertFalse("Expected not to contain param78",
				paramCollection.containsKey("param78"));

	}

	@Test
	public void isEmpty() {

		Assert.assertTrue("Expected to be empty",
				new RouteParameterCollection().isEmpty());

		Assert.assertFalse("Expected not to be empty",
				paramCollection.isEmpty());

	}

	@Test
	public void getOrDefault() {

		Assert.assertEquals("Expected value to equal value2",
				paramCollection.getOrDefault("param2", ""), "value2");

		Assert.assertEquals("Expected returned value to be default",
				paramCollection.getOrDefault("param78", ""), "");

	}

}
