/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import org.springside.modules.utils.EncodeUtils;

/**
 * 支持SHA-1消息摘要的Util方法集合.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class DigestUtils {

	private static final String SHA1 = "SHA-1";

	//-- SHA1 function --//
	/**
	 * 对输入字符串进行sha1散列, 返回Hex编码的结果.
	 */
	public static String sha1ToHex(String input) {
		byte[] digestResult = sha1(input);
		return EncodeUtils.hexEncode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的结果.
	 */
	public static String sha1ToBase64(String input) {
		byte[] digestResult = sha1(input);
		return EncodeUtils.base64Encode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的URL安全的结果.
	 */
	public static String sha1ToBase64Url(String input) {
		byte[] digestResult = sha1(input);
		return EncodeUtils.base64UrlEncode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回字节数组.
	 */
	private static byte[] sha1(String input) {
		return sha1(input.getBytes());
	}

	/**
	 * 对输入字节数组进行sha1散列, 返回字节数组.
	 */
	private static byte[] sha1(byte[] input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
			return messageDigest.digest(input);
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}
}
