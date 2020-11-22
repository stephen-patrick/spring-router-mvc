package com.stephenenright.spring.router.mvc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Pattern;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;

class RouteUtils {

	public static boolean isPathSeparator(String strToTest) {
		if (!RouteUtils.isNullOrEmpty(strToTest)
				&& strToTest.equals(Http.PATH_SEPARATOR)) {
			return true;
		}

		return false;
	}

	public static boolean isNullOrEmpty(String strToTest) {
		return strToTest == null || strToTest.trim().length() <= 0;
	}

	public static boolean isAnyNullOrEmpty(String... strsToTest) {
		for (String str : strsToTest) {
			if (isNullOrEmpty(str)) {
				return true;
			}
		}

		return false;
	}

	public static boolean containsAny(String strToTest, String... strsToTest) {
		for (String str : strsToTest) {
			if (strToTest.contains(str)) {
				return true;
			}
		}

		return false;
	}

	public static int lastIndexFrom(String str, String sep, int startIndex) {
		if (isNullOrEmpty(str)) {
			return -1;
		}

		if (startIndex < 0 || startIndex > str.length()) {
			return -1;
		}

		int index = str.substring(0, startIndex).lastIndexOf(sep);

		if (index == -1) {
			return -1;
		}

		return index;
	}

	public static String escapeRegex(String str) {
		return str.replaceAll("([^a-zA-Z0-9])", "\\\\$1");
	}

	public static Method findPublicMethod(Object instance, String methodName) {
		Class<?> clazz = AopUtils.getTargetClass(instance);

		Class<?> searchFor = clazz;
		while (searchFor != null && !searchFor.isInterface()
				&& !searchFor.getName().equals("java.lang.Object")) {
			Method[] methods = searchFor.getDeclaredMethods();
			for (Method method : methods) {
				if (methodName.equalsIgnoreCase(method.getName())
						&& Modifier.isPublic(method.getModifiers())) {
					return BridgeMethodResolver.findBridgedMethod(method);
				}
			}
			searchFor = searchFor.getSuperclass();
		}
		return null;
	}

	private static final Pattern PATTERN_REPEATING_PATH_CHARS = Pattern
			.compile("/{2,}");

	public static String joinContextWithPathAsRootRelative(String path1,
			String path2) {
		if (RouteUtils.isNullOrEmpty(path1)) {
			path1 = RouteConstants.EMPTY_STRING;
		}

		if (RouteUtils.isNullOrEmpty(path2)
				|| path2.trim().equals(Http.PATH_SEPARATOR)) {
			path2 = RouteConstants.EMPTY_STRING;
		}

		path1 = path1.trim();
		path2 = path2.trim();

		String joinedPath = PATTERN_REPEATING_PATH_CHARS.matcher(
				(path1 + path2)).replaceAll("/");

		if (joinedPath.equals(RouteConstants.EMPTY_STRING)) {
			return Http.PATH_SEPARATOR;
		} else if (!joinedPath.startsWith(Http.PATH_SEPARATOR)) {
			return Http.PATH_SEPARATOR + joinedPath;
		}

		return joinedPath;

	}

}
