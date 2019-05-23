package com.hll.test.common;

public class StringUtil {

	private static String[] ZEROS;

	static {
		int max = 15;
		int i = 0;
		ZEROS = new String[max + 1];
		ZEROS[0] = "";
		for (; i++ < max;) {
			ZEROS[i] = ZEROS[i - 1] + "0";
		}
	}

	public static boolean isNullOrBlank(String value) {

		return value == null || value.length() == 0;
	}

	public static boolean hasLength(String text) {

		return text != null && text.length() > 0;
	}

	public static boolean hasText(String text) {
		return text != null && text.trim().length() > 0;
	}

	public static String getZeroString(int length) {
		return ZEROS[length];
	}

	public static String padZero(String str, int len) {
		return ZEROS[len - str.length()] + str;
	}

}
