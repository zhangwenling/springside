package org.springside.modules.security.utils;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.id.UUIDHexGenerator;

/**
 * 
 * @author calvin
 */
public class NonceUtils {

	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//RFC3339

	private static Date lastTime;
	private static int counter = 0;
	private static String ip;
	private static Map<Integer, String> shortips = new HashMap<Integer, String>();
	static {
		try {
			byte[] ipbytes = InetAddress.getLocalHost().getAddress();
			ip = Hex.encodeHexString(ipbytes);
			for (int i = 1; i <= 4; i++) {
				byte[] shortIpBytes = new byte[i];
				System.arraycopy(ipbytes, 4 - i, shortIpBytes, 0, i);
				shortips.put(i, Hex.encodeHexString(shortIpBytes));
			}
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

		if (currentTime.equals(lastTime)) {
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
	public static String getIp() {
		return ip;
	}

	/**
	 * 返回Hex编码的短IP.使用length控制返回的长度.
	 * 如完整为96ec458e的地址, length=1时只返回8e, length=2时返回458e.
	 */
	public static String getShortIp(int length) {
		return shortips.get(length);
	}

	/**
	 * 格式化字符串,固定长度,不足长度在前面补0.
	 */
	public static String format(String string, int length) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < length; i++) {
			buf.append("0");
		}
		buf.replace(length - string.length(), length, string);
		return buf.toString();
	}
}
