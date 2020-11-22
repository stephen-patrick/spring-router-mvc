package com.stephenenright.spring.router.mvc.tags.thymeleaf;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class RouterExpressionDialects {

	public static class RouterExpressionFactory implements IExpressionObjectFactory {

		public static final String ROUTER_EXPRESSION_OBJECT_NAME = "router";

		private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList(ROUTER_EXPRESSION_OBJECT_NAME)));

		@Override
		public Object buildObject(IExpressionContext context, String expressionObjectName) {
			if (ROUTER_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
				return new RouterExpression();
			}

			return null;
		}

		@Override
		public Set<String> getAllExpressionObjectNames() {
			return ALL_EXPRESSION_OBJECT_NAMES;
		}

		@Override
		public boolean isCacheable(String expressionObjectName) {
			return expressionObjectName != null;
		}
	}

	public static class RouterExpressionDialect extends AbstractDialect implements IExpressionObjectDialect {

		private final IExpressionObjectFactory ROUTER_EXPRESSION_FACTORY = new RouterExpressionFactory();

		public RouterExpressionDialect() {
			super("router");
		}

		@Override
		public IExpressionObjectFactory getExpressionObjectFactory() {
			return ROUTER_EXPRESSION_FACTORY;
		}
	}
}
