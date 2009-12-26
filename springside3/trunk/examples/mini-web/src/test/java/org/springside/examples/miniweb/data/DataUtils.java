package org.springside.examples.miniweb.data;

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

	public static <T> T randomFromList(List<T> list) {
		return list.get(randomInt(list.size()));
	}

	public static int randomInt(){
		return random.nextInt();
	}

	public static int randomInt(int max) {
		return random.nextInt(max);
	}
}
