package org.springside.modules.security.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.id.UUIDHexGenerator;

/**
 * 
 * @author calvin
 */
public class NonceUtils {

	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//RFC3339

	private static String previousTimestamp = "";
	private static int timestampCounter = 0;

	/**
	 * 返回Internate标准格式的当前毫秒级时间戳字符串.
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z
	 */
	public static String getCurrentTimestamp() {
		Date now = new Date();
		return internateDateFormat.format(now);
	}

	/**
	 * 生成可读时间戳字符串+计数器的Nonce.
	 */
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

	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce, 默认长度为16字节.
	 */
	public static byte[] nextRandomNonce() {
		return nextRandomNonce(16);
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce.
	 */
	public static byte[] nextRandomNonce(int length) {
		try {
			SecureRandom nonceGenerator = SecureRandom.getInstance("SHA1PRNG");
			byte[] nonce = new byte[length];
			nonceGenerator.nextBytes(nonce);
			return nonce;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 生成Timebase的UUID Nonce.
	 */
	public static String nextUuidNonce() {
		return (String) new UUIDHexGenerator().generate(null, null);
	}
}
