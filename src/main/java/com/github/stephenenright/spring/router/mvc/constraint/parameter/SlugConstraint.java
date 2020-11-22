package com.github.stephenenright.spring.router.mvc.constraint.parameter;

import java.util.regex.Pattern;

public class SlugConstraint extends RegexConstraint {

	private static Pattern SLUG_PATTERN = Pattern
			.compile("^[\\w-]+$");

	private static final SlugConstraint CONSTRAINT = new SlugConstraint();

	public SlugConstraint() {
		super(SLUG_PATTERN);
	}

	public static SlugConstraint slug() {
		return CONSTRAINT;
	}

}
