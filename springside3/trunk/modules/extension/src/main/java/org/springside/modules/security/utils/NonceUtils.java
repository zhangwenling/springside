package org.springside.modules.security.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NonceUtils {

	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//RFC3339

	/**
	 * 返回Internate标准格式的当前时间戳字符串.
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		return internateDateFormat.format(now);
	}

	/**
	 * 返回从1970到现在的毫秒数.
	 */
	public static String getCurrentTimpestamp() {
		return Long.toString(new Date().getTime());
	}
}
