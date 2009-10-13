package org.springside.modules.security.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SecurityUtils {

	private static final String DES = "DES";

	/**
	 * 对输入字符串进行Sha-1散列并进行Base64编码.
	 */
	public static String sha1Base64(String input) {
		byte[] digest = sha1(input);
		return base64(digest);
	}

	/**
	 * 对输入字符串进行Sha-1散列并进行Base64编码,编码时针对URL中的字符合法性进行特殊处理.
	 */
	public static String sha1Base64UrlSafe(String input) {
		byte[] digest = sha1(input);
		return base64UrlSafe(digest);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	private static byte[] sha1(String input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unexpected exception.", e);
		}
		return messageDigest.digest(input.getBytes());
	}

	public static String desEncrypt(String input, String key) throws Exception {
		byte[] encryptResult = des(input.getBytes(), key, Cipher.ENCRYPT_MODE);
		return base64(encryptResult);
	}

	public static String desDecrypt(String input, String key) throws Exception {
		byte[] decryptResult = des(Base64.decodeBase64(input), key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	private static byte[] des(byte[] input, String key, int mode) throws Exception {
		DESKeySpec dks = new DESKeySpec(Base64.decodeBase64(key));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		Key k = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(mode, k);
		return cipher.doFinal(input);
	}

	public static String generateDesKey() throws Exception {
		SecureRandom secureRandom = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance(DES);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return base64(secretKey.getEncoded());
	}

	/**
	 * Base64编码.
	 */
	private static String base64(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	/**
	 * Base64编码, 编码时针对URL中的字符合法性进行特殊处理.
	 * eg. '+' -> '-' , '/' -> '_'
	 */
	private static String base64UrlSafe(byte[] bytes) {
		return new String(Base64.encodeBase64URLSafe(bytes));
	}
}
