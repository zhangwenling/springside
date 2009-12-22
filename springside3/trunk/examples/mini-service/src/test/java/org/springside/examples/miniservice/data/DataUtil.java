package org.springside.examples.miniservice.data;

import org.apache.commons.lang.RandomStringUtils;

public class DataUtil {

	public static String random(String prefix) {
		return prefix + randomNumber();
	}

	public static String randomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	public static String randomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}
