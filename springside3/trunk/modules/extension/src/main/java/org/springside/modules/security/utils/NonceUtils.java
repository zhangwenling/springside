/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;

/**
 * 唯一数生成类, 提供纯Random与UUID两种风格的生成函数.
 * 
 * @author calvin
 */
public class NonceUtils {
	//RFC3339 日期标准格式,
	private static final SimpleDateFormat INTERNATE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	//定长格式化所用字符串, 含1,2,4,8位的字符串.
	private static final String[] SPACES = { "0", "00", "0000", "00000000" };

	//UUID风格同一JVM同一毫秒内请求的计数器.
	private static Date lastTime;
	private static int counter = 0;

	//-- Random function --//
	/**
	 * 使用较低强度的java.util.Random(),生成含所有字母与数字的字符串.
	 * 
	 * @param length 返回字符串长度(单位为字符)
	 */
	public static String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Int.
	 */
	public static int randomInt() {
		return new SecureRandom().nextInt();
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Int, 返回长度为8的Hex编码结果 .
	 */
	public static String randomHexInt() {
		return Integer.toHexString(randomInt());
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Long, 返回长度为16的Hex编码结果.
	 */
	public static long randomLong() {
		return new SecureRandom().nextLong();
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Long, 返回长度为16的Hex编码结果.
	 */
	public static String randomHexLong() {
		return Long.toHexString(randomLong());
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Bytes, 返回Hex编码结果.
	 * 
	 * @param length 内部byte数组长度(单位为字节), 返回字符串长度为length*2.
	 */
	public static String randomHexBytes(int length) {
		SecureRandom nonceGenerator = new SecureRandom();
		byte[] nonce = new byte[length];
		nonceGenerator.nextBytes(nonce);
		return Hex.encodeHexString(nonce);
	}

	//-- Timestamp function --//
	/**
	 * 返回Internate标准格式的当前毫秒级时间戳字符串, 生成自定义UUID的辅助函数.
	 * 
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z.
	 */
	public static String currentTimestamp() {
		Date now = new Date();
		return INTERNATE_DATE_FORMAT.format(now);
	}

	/**
	 * 返回当前距离1970年的毫秒数,生成自定义UUID的辅助函数.
	 */
	public static long currentMills() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 返回当前距离1970年的毫秒数, 返回Hex编码的, 生成自定义UUID的辅助函数.
	 */
	public static String currentHexMills() {
		return Long.toHexString(currentMills());
	}

	//-- Counter function --//
	/**
	 * 返回Hex编码的同一毫秒内的Counter, 生成自定义UUID的辅助函数.
	 */
	public static synchronized String getCounter() {
		Date currentTime = new Date();

		if (currentTime.equals(lastTime)) {
			counter++;
		} else {
			lastTime = currentTime;
			counter = 0;
		}
		return Integer.toHexString(counter);
	}

	//-- Helper function --//
	/**
	 * 格式化字符串, 固定字符串长度, 不足长度在前面补0, 生成自定义UUID的辅助函数.
	 */
	public static String format(String source, int length) {
		int spaceLength = length - source.length();
		StringBuilder buf = new StringBuilder();

		while (spaceLength >= 8) {
			buf.append(SPACES[3]);
			spaceLength -= 8;
		}

		for (int i = 2; i >= 0; i--) {
			if ((spaceLength & (1 << i)) != 0) {
				buf.append(SPACES[i]);
			}
		}

		buf.append(source);
		return buf.toString();
	}
}
