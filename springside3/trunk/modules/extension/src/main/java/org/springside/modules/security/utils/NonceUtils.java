package org.springside.modules.security.utils;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.id.UUIDHexGenerator;

/**
 * 唯一数生成类,提供纯Random与UUID两种风格的生成函数.
 * 
 * @author calvin
 */
public class NonceUtils {
	//RFC3339 日期标准格式
	private static final SimpleDateFormat internateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	//定长格式化所用字符串, 含1,2,4,8,16位的字符串.
	private static final String[] SPACES = { "0", "00", "0000", "00000000", "0000000000000000" }; //

	//UUID风格同一毫秒内请求的计数器.
	private static Date lastTime;
	private static int counter = 0;

	//UUID风格Hex编码的IP地址
	private static String ip;
	static {
		try {
			byte[] ipbytes = InetAddress.getLocalHost().getAddress();
			ip = Hex.encodeHexString(ipbytes);
		} catch (Exception e) {
			ip = "0";
		}
	}

	//-- random nonce generator --//
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

	//-- UUID nonce generator --//
	/**
	 * 使用Hibernate的实现生成Timebase的Hex编码的UUID Nonce.
	 */
	public static String nextUuidNonce() {
		return (String) new UUIDHexGenerator().generate(null, null);
	}

	//-- UUID nonce support function --//
	/**
	 * 返回Internate标准格式的当前毫秒级时间戳字符串.
	 * 
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z.
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

	/**
	 * 返回Hex编码的同一毫秒内的Counter.
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
	 * 返回Hex编码的IP, 生成自定义UUID的辅助函数.
	 */
	public static String getIp() {
		return ip;
	}

	/**
	 * 格式化字符串, 固定字串长度, 不足长度在前面补0, 生成自定义UUID的辅助函数.
	 */
	public static String format(String hexString, int length) {
		int spaceLength = length - hexString.length();

		StringBuilder buf = new StringBuilder();

		while (spaceLength >= 16) {
			buf.append(SPACES[4]);
			spaceLength -= 16;
		}

		for (int i = 3; i >= 0; i--) {
			if ((spaceLength & (1 << i)) != 0) {
				buf.append(SPACES[i]);
			}
		}

		buf.append(hexString);
		return buf.toString();
	}
}
