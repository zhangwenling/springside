package org.springside.modules.security.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NonceUtils {

	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//RFC3339

	private static String previousTimestamp = "";
	private static int timestampCounter = 0;

	/**
	 * 返回Internate标准格式的当前时间戳字符串.
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z
	 */
	public static String getCurrentTimestamp() {
		Date now = new Date();
		return internateDateFormat.format(now);
	}

	public synchronized static String nextTimestampNonce() {
		String currentTimestamp = getCurrentTimestamp();

		if (previousTimestamp.equals(currentTimestamp)) {
			timestampCounter++;
		} else {
			previousTimestamp = currentTimestamp;
			timestampCounter = 0;
		}
		return currentTimestamp + Integer.toString(timestampCounter);
	}

	public static String nextRandomNonce() {
		try {
			SecureRandom nonceGenerator = SecureRandom.getInstance("SHA1PRNG");
			byte[] nonce = new byte[16];
			nonceGenerator.nextBytes(nonce);
			return CryptoUtils.hexEncode(nonce);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UUID.randomUUID().toString();
	}
}
