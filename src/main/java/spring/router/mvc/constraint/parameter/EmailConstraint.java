package spring.router.mvc.constraint.parameter;

import java.util.regex.Pattern;

public class EmailConstraint extends RegexConstraint {

	private static Pattern EMAIL_PATTERN = Pattern
			.compile("\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b");

	private static final EmailConstraint CONSTRAINT = new EmailConstraint();

	public EmailConstraint() {
		super(EMAIL_PATTERN);
	}

	public static EmailConstraint email() {
		return CONSTRAINT;
	}

}
