package org.springside.examples.miniservice.data;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class DataUtils {

	private static Random random = new Random();

	public static String random(String prefix) {
		return prefix + randomNumber();
	}

	public static String randomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	public static String randomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	public static Long randomId() {
		return random.nextLong();
	}

}
