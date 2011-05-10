package org.springside.modules.utils;

public class ExceptionUtils {

	/**
	 * 將CheckedException 轉換為UnCheckedException拋出.
	 */
	public static void throwUnchecked(Exception e) {
		throw new RuntimeException(e.getMessage(), e);
	}
}
