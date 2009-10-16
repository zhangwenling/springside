package org.springside.modules.security.utils;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.util.BytesHelper;

/**
 * 
 * @author calvin
 */
public class NonceUtils {

	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//RFC3339

	private static Date lastTime;
	private static int counter = 0;
	private static String ip;
	static {
		try {
			ip = Integer.toHexString(BytesHelper.toInt(InetAddress.getLocalHost().getAddress()));
		} catch (Exception e) {
			ip = "0";
		}
	}

	//-- Timestamp function --//
	/**
	 * 返回Internate标准格式的当前毫秒级时间戳字符串.
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z
	 */
	public static String getCurrentTimestamp() {
		Date now = new Date();
		return internateDateFormat.format(now);
	}

	/**
	 * 返回当前毫秒数.
	 */
	public static String getCurrentMills() {
		return Long.toHexString(System.currentTimeMillis());
	}

	//-- random nonce function --//
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
	 * 生成SHA1PRNG算法的SecureRandom Nonce, 默认长度为16字节.
	 */
	public static byte[] nextRandomNonce() {
		return nextRandomNonce(16);
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce, 返回Hex编码的结果.
	 */
	public static String nextRandomHexNonce(int length) {
		return Hex.encodeHexString(nextRandomNonce(length));
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce, 默认长度为16字节,返回Hex编码的结果.
	 */
	public static String nextRandomHexNonce() {
		return Hex.encodeHexString(nextRandomNonce());
	}

	//-- UUID nonce function --//
	/**
	 * 生成Timebase的UUID Nonce, 返回Hex编码的结果.
	 */
	public static String nextUuidNonce() {
		return (String) new UUIDHexGenerator().generate(null, null);
	}

	/**
	 * 返回Hex编码的同一毫秒内的Counter, 生成自定义UUID的辅助函数.
	 */
	public synchronized static String getCounter() {
		Date currentTime = new Date();

		if (lastTime.equals(currentTime)) {
			counter++;
		} else {
			lastTime = currentTime;
			counter = 0;
		}
		return Integer.toHexString(counter);
	}

	/**
	 * 返回Hex编码的IP, 生成自定义UUID的辅助函数
	 */
	public String getIp() {
		return ip;
	}
}
