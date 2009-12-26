package org.springside.examples.miniservice.data;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataUtils {


	private static Random random = new Random();

	public static long randomId() {
		return random.nextLong();
	}

	public static String randomName(String prefix) {
		return prefix + randomInt(10000);
	}

	public static <T> T randomFrom(List<T> list) {
		return randomFromList(list, 1).get(0);
	}

	public static <T> List<T> randomFromList(List<T> list, int count) {
		Collections.shuffle(list);
		return list.subList(0, count);
	}

	public static int randomInt() {
		return random.nextInt();
	}

	public static int randomInt(int max) {
		return random.nextInt(max);
	}
}
