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

	//-- random nonce generator --//
	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce.
	 * 
	 * @param length byte数组长度(单位为字节).
	 */
	public static byte[] nextRandomNonce(int length) {
		SecureRandom nonceGenerator = new SecureRandom();
		byte[] nonce = new byte[length];
		nonceGenerator.nextBytes(nonce);
		return nonce;
	}

	/**
	 * 生成SHA1PRNG算法的SecureRandom Nonce, 返回Hex编码结果.
	 * @param length 内部byte数组长度(单位为字节), 返回字符串长度为length*2.
	 */
	public static String nextRandomHexNonce(int length) {
		return Hex.encodeHexString(nextRandomNonce(length));
	}

	/**
	 * 使用较低强度的java.util.Random(),生成带字母与数字字符串.
	 * 
	 * @param length 返回字符串长度(单位为字符)
	 */
	public static String nextRandomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	//-- UUID nonce support function --//
	/**
	 * 返回Hex编码的8字符唯一值, 可用于标识唯一的机器+JVM, 生成自定义UUID的辅助函数.
	 */
	public static String getUnique() {
		int unique = new SecureRandom().nextInt();
		return Integer.toHexString(unique);
	}

	/**
	 * 返回Internate标准格式的当前毫秒级时间戳字符串, 生成自定义UUID的辅助函数.
	 * 
	 * 标准格式为yyyy-MM-dd'T'HH:mm:ss.SSS'Z', 如2009-10-15T14:24:50.316Z.
	 */
	public static String getCurrentTimestamp() {
		Date now = new Date();
		return INTERNATE_DATE_FORMAT.format(now);
	}

	/**
	 * 返回当前距离1970年的毫秒数, 生成自定义UUID的辅助函数.
	 */
	public static String getCurrentMills() {
		return Long.toHexString(System.currentTimeMillis());
	}

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

	/**
	 * 格式化字符串, 固定字符串长度, 不足长度在前面补0, 生成自定义UUID的辅助函数.
	 */
	public static String format(String hexString, int length) {
		int spaceLength = length - hexString.length();
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

		buf.append(hexString);
		return buf.toString();
	}
}
