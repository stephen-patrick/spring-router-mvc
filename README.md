# Spring MVC Router  

Spring MVC handles routing of requests to controllers using the RequestMappingHandlerMapping and RequestMappingHandlerAdapter classes.   In a standard Spring MVC configuration, the Spring run-time allows us to configure routing in XML, or using annotations such as the  RequestMapping annotation. More information can be found in the [reference documentation](http://docs.spring.io/spring/docs/3.2.17.RELEASE/spring-framework-reference/htmlsingle/#mvc-controller)


This project provides an alternative to the default routing mechanism.  This project provides an API that supports adding routes and routing constraints to your application.   

* Routes can be defined in a single configuration, or in multiple configurations – supporting a plugin architecture
* Constraints can be added on a route using a fluent API.  For example, a constraint can be added to restrict a route to a particular domain.
* Constraints can be added on route parameters.  For example, to only match a route if a parameter contains a valid email address.


## Defining Routes
Routes are defined by creating a class that implements the `RoutesConfig` interface.  For example 


		public class AppRouteConfig implements RoutesConfig {
	
			@Override
			public void registerRoutes(RoutesBuilder routes) {
				routes.add("home", "/", "homeController", "index", HttpMethod.GET, HttpMethod.HEAD);
				routes.add("viewPlace", "places/{slug}", "placeController",
					"welcomeNumber", new RouteConstraints().forParamAddSlug("slug")
							.forParamAddMaxLen("slug", 255), HttpMethod.GET);
			}
		
			@Override
			public String name() {
				return "Application Routes Configuration";
			}
		}
		

## Route Parameter Constraints
Parameter constraints can be added using the `RouteConstraints` builder.  These constraints will be used during the route matching process.  If the constraint does not hold, the route will not be matched.  The following constraints are included:

		RouteConstraints forParamAddEmail(String paramName)
		RouteConstraints forParamAddSlug(String paramName)
		RouteConstraints forParamAddNumeric(String paramName)
		RouteConstraints forParamAddMax(String paramName, long maxValue)
		RouteConstraints forParamAddMin(String paramName, long minValue)
		RouteConstraints forParamAddMinMax(String paramName, long minValue,long maxValue)
		RouteConstraints forParamAddMinLen(String paramName, int minLength)
		RouteConstraints forParamAddMaxLen(String paramName, int maxLength)
		RouteConstraints forParamAddMinMaxLen(String paramName, int minLength, int maxLength)
		RouteConstraints forParamAddRegex(String paramName, String regex)
		

You can also create your own custom parameter constraints by implementing the `RouteParameterConstraint` interface.

		RouteConstraints forParam(String paramName,RouteParameterConstraint... constraints)
		


## Route Constraints
These are constraints that are applied to the route.  These are useful if there is a need to apply a custom constraint, such as: the existence of a value, the request having a specific host etc.  These constraints are applied as part of the route matching process.  The constraints must be valid for the route to match.  Custom route constraints can be created by implementing the `RouteConstraint` interface.

		RouteConstraints forRoute(RouteConstraint constraint)	
	


## Route Helper
A `RouteHelper` class is provided.  This class can be used to reverse routes or to return the pattern string for a particular route.  The route pattern can also be partially expanded by supplying route parameters.

		String reverse(String routeName)
		String reverse(String routeName,RouteParameterCollection params)
		String reverse(String routeName, HttpServletRequest request) 
		String reverse(String routeName, HttpServletRequest request,RouteParameterCollection params)
		String reverse(String controller, String action) 
		String reverse(String controller, String action,RouteParameterCollection params)
		String reverse(String controller, String action,HttpServletRequest request)
		String reverse(String controller, String action,
				HttpServletRequest request,  RouteParameterCollection params)
		
		
		String pattern(String routeName)
		String pattern(String routeName, RouteParameterCollection params )
		String pattern(String routeName, HttpServletRequest request)
		String pattern(String routeName, HttpServletRequest request, RouteParameterCollection params)
		String pattern(String controller, String action)
		String pattern(String controller, String action, RouteParameterCollection params)
		String pattern(String controller, String action, HttpServletRequest request)
		String pattern(String controller, String action,
				HttpServletRequest request, RouteParameterCollection params) 


An API is also provided for reversing routes and generating patterns outside of the request pipeline, for example as part of background task.

		String reverse(String routeName, UrlOuputOptions outputOptions)
		String reverse(String routeName, RouteParameterCollection params, 
			UrlOuputOptions outputOptions)
		String reverse(String controller, String action,
			UrlOuputOptions outputOptions)
		String reverse(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions)
			
		String pattern(String routeName, UrlOuputOptions outputOptions)
		String pattern(String routeName, RouteParameterCollection params, 
			UrlOuputOptions outputOptions)
		String pattern(String controller, String action,
			UrlOuputOptions outputOptions)
		String pattern(String controller, String action,
			RouteParameterCollection params, UrlOuputOptions outputOptions)
			




		
## JSP View Integration
A tag library is provided for use with JSP pages. 

To use this library include the taglib declaration at the top of the JSP page

		<%@ taglib prefix="route" uri="/springrouter-mvc" %> 	


To reverse a route by name:

		<a href="<route:reverseByName name="home" />">Home</a>	


To reverse a route by controller and action.
		
		<a href="<route:reverse controller="homeController" action="index" greeting="Hello" />">Home</a>

To obtain a route pattern by name:

		<a href="<route:patternByName name="home" />">Home</a>	
	

## Thymeleaf View Integration
Custom Thymeleaf expressions are provided for reversing routes
To use these expressions add the dialect to the template engine.

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addDialect(new RouterExpressionDialect());
		
To reverse a route by name:

		<a th:href="${#router.reverseByName('login')}">Login</a>	


To reverse a route by controller and action.
		
		<a th:href="${#router.reverse(controller,action)}" />">Login</a>
		
Note the above expressions also take an additional Map<String,Object> parameter to set the route parameters.

The above expressions also take a csv string parameter which represents route parameters.

		<a class="on" th:href="${#router.reverseByName(routeName, 'paramName=' + paramValue)}">Login</a>



## JavaScript Client Routes Generation
The router provides support for generating JavaScript route files.  
By default it will generate a routes file containing a routes configuration object for each RoutesConfig.
The generated JavaScript route files can be used to configure a client side JavaScript router / Route Resolver for use in client side applications.  The Routes generation is configurable and could be used to output routes file when running in development mode.   The following is an example of configuring the Client Routes Generation

```
	JsRouterConfiguration jsRouterConfig = new JsRouterConfiguration();
	jsRouterConfig.setOutputDirectory(properties.getClientRoutesOutputDirectory());
	jsRouterConfig.setWriteRoutes(properties.isWriteClientRoutes());
	
	new JsRoutesFileWriterImpl(jsRouterConfig).writeRoutes();

```

For each RoutesConfig a JavaScript file will be produced containing an object of the form:

```
	var routesConfig = {
		name: '<RoutesConfig_Name>',
		routes: {
			'<ROUTE_NAME':'ROUTE_PATTERN',
			...
		}
	};

```




## Configuration
The router can be configured using the following XML configuration:

		<bean id="handlerMapping"
		          class="SpringRouterHandlerMapping">
		          	<property name="configuration">
						<bean class="SpringRouterConfiguration" >
							<property name="enabled" value="true" />
							<property name="strict" value="true" />
							<property name="routeConfigurations">
								<list>
		                    		<bean class="web.routes.AppRouteConfig" />
		                		</list>
							</property>
						</bean>
					</property>
		</bean>
		
Java Configuration Example:
		
		@Configuration
		@EnableWebMvc
		@ComponentScan(includeFilters = {
			@org.springframework.context.annotation.ComponentScan.Filter(type = FilterType.ANNOTATION, value 				= org.springframework.stereotype.Controller.class)})
		public static class AppWebConfiguration extends WebMvcConfigurerAdapter {
			
			@Bean
			public HandlerMapping handlerMapping() {
				SpringRouterHandlerMapping handlerMapping = new SpringRouterHandlerMapping();
				List<RoutesConfig> routesConfig = new LinkedList<RoutesConfig>();
				routesConfig.add(new AppMvcConfig());
	
				SpringRouterConfiguration configuration = new SpringRouterConfiguration();
				configuration.setEnabled(true);
				configuration.setStrict(true);
				configuration.setRouteConfigurations(routesConfig);
	
				handlerMapping.setConfiguration(configuration);
				handlerMapping.setInterceptors(appInterceptor());
	
				return handlerMapping;
			}
		}

There is no limit to the number of route configuration classes you can add.  To add a configuration add a bean to the routeConfigurations property as shown above.

The strict property if set to true, will perform some extra validations and throw an exception during initialization under the following conditions:

* if a route is added and no matching controller is found for the given controller name. 
* if no matching public method is found with the name of the given action.
* if parameter constraints are added for paramters that do not exist in the route pattern.  


## Installation

```
<dependency>
  <groupId>com.github.stephenenright</groupId>
  <artifactId>spring-router</artifactId>
  <version>1.1</version>
</dependency>
```