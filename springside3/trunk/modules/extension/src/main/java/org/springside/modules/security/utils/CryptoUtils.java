/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.utils;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springside.modules.utils.EncodeUtils;

/**
 * 支持HMAC-SHA1消息签名 及 DES对称加密的Util方法集合.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class CryptoUtils {

	private static final String DES = "DES";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;//RFC2401

	//-- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static byte[] hmacSha1(String input, byte[] keyBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(keyBytes, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input.getBytes());
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Hex编码的结果.
	 *	
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToHex(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.hexEncode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的结果.
	 * 
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToBase64(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.base64Encode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的URL安全的结果.
	 * 
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToBase64Url(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.base64UrlEncode(macResult);
	}

	/**
	 * 校验Hex编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param hexMac Hex编码的签名
	 * @param input 原始输入字符串
	 * @param keyBytes 密钥
	 */
	public static boolean isHexMacValid(String hexMac, String input, byte[] keyBytes) {
		byte[] expected = EncodeUtils.hexDecode(hexMac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 校验Base64/Base64URLSafe编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param base64Mac Base64/Base64URLSafe编码的签名
	 * @param input 原始输入字符串
	 * @param keyBytes 密钥
	 */
	public static boolean isBase64MacValid(String base64Mac, String input, byte[] keyBytes) {
		byte[] expected = EncodeUtils.base64Decode(base64Mac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组.
	 * HMAC-SHA1算法可使用任意长度的密钥,RFC2401建议最少长度为160位.
	 */
	public static byte[] generateMacSha1Key() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(HMACSHA1);
			keyGen.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGen.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}

	/**
	 * 生成HMAC-SHA1密钥, 返回Hex编码的结果. 
	 * @see #generateMacSha1Key()
	 */
	public static String generateMacSha1HexKey() {
		return EncodeUtils.hexEncode(generateMacSha1Key());
	}

	/**
	 * 生成HMAC-SHA1密钥, 返回Base64编码的结果.
	 * @see #generateMacSha1Key()
	 */
	public static String generateMacSha1Base64Key() {
		return EncodeUtils.base64Encode(generateMacSha1Key());
	}

	//-- DES function --//
	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToHex(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.hexEncode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToBase64(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.base64Encode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的URL安全的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToBase64Url(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.base64UrlEncode(encryptResult);
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] keyBytes) {
		byte[] decryptResult = des(EncodeUtils.hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Base64编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromBase64(String input, byte[] keyBytes) {
		byte[] decryptResult = des(EncodeUtils.base64Decode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 无编码的原始字或加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] des(byte[] input, byte[] keyBytes, int mode) {
		try {
			DESKeySpec dks = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}

	/**
	 * 生成符合DES要求的密钥.
	 */
	public static byte[] generateDesKey() {
		try {
			SecureRandom secureRandom = new SecureRandom();
			KeyGenerator kg = KeyGenerator.getInstance(DES);
			kg.init(secureRandom);
			SecretKey secretKey = kg.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}

	/**
	 * 生成符合DES要求的Hex编码密钥.
	 */
	public static String generateDesHexKey() {
		return EncodeUtils.hexEncode(generateDesKey());
	}

	/**
	 * 生成符合DES要求的Base64编码密钥.
	 */
	public static String generateDesBase64Key() {
		return EncodeUtils.base64Encode(generateDesKey());
	}
}