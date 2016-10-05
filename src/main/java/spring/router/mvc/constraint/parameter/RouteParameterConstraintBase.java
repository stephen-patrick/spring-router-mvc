package spring.router.mvc.constraint.parameter;

public abstract class RouteParameterConstraintBase implements
		RouteParameterConstraint {

	//TODO REMOVE THIS
	private String parameterName;

	public RouteParameterConstraintBase(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

}