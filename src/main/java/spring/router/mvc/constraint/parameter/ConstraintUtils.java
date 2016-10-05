package spring.router.mvc.constraint.parameter;

public class ConstraintUtils {

	public static boolean isNullOrEmpty(String value) {
		return (value == null || value.trim().length() <= 0);
	}

	public static boolean required(String value) {
		return !isNullOrEmpty(value);
	}

	public static boolean isNumeric(final String value, Long maxValue) {
		if (isNullOrEmpty(value)) {
			return false;
		}

		try {
			long longValue = Long.parseLong(value);

			if (longValue > maxValue) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean isNumeric(final String value) {
		return isNumeric(value, Long.MAX_VALUE);
	}

	public static boolean maxLength(String value, int maxLength) {
		if (isNullOrEmpty(value)) {
			return false;
		}

		return value.length() <= maxLength;
	}

	public static boolean minLength(String value, int minLength) {
		if (isNullOrEmpty(value)) {
			return false;
		}

		return value.length() >= minLength;
	}

	public static boolean min(String value, long minValue) {
		if (isNullOrEmpty(value) || !isNumeric(value)) {
			return false;
		}

		long longValue = Long.valueOf(value);

		return longValue >= minValue;
	}

	public static boolean max(String value, long maxValue) {
		if (isNullOrEmpty(value) || !isNumeric(value)) {
			return false;
		}

		long longValue = Long.valueOf(value);

		return longValue <= maxValue;
	}

}
