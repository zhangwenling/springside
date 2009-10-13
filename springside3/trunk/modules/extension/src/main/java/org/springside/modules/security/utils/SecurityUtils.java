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

import org.apache.commons.codec.binary.Hex;

/**
 * 单向SHA-1散列与DES对称加密的Utils.
 * 
 * 使用Base64编码SHA1与DES产生的字节数组.
 * 
 * @author calvin
 */
public class SecurityUtils {

	private static final String DES = "DES";

	//-- SHA1 --//
	/**
	 * 对输入字符串进行sha1散列,并进行Hex编码.
	 */
	public static String sha1(String input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unexpected exception.", e);
		}
		byte[] digest = messageDigest.digest(input.getBytes());
		return Hex.encodeHexString(digest);
	}

	//-- DES --//
	/**
	 * 使用DES加密原始字符串,返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串.
	 * @param key 由desGenerateKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desEncrypt(String input, String key) throws Exception {
		byte[] encryptResult = des(input.getBytes(), key, Cipher.ENCRYPT_MODE);
		return Hex.encodeHexString(encryptResult);
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串.
	 * @param key 由desGenerateKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desDecrypt(String input, String key) throws Exception {
		byte[] decryptResult = des(Hex.decodeHex(input.toCharArray()), key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES加密或解密无编码的原始字节数组,返回无编码的字节数组结果.
	 * 
	 * @param input 无编码的原始字或加密字符串
	 * @param key 由desGenerateKey()生成的, 使用Hex编码的密钥.
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] des(byte[] input, String key, int mode) throws Exception {
		DESKeySpec dks = new DESKeySpec(Hex.decodeHex(key.toCharArray()));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		Key k = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(mode, k);
		return cipher.doFinal(input);
	}

	public static String desGenerateKey() throws Exception {
		SecureRandom secureRandom = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance(DES);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return Hex.encodeHexString(secretKey.getEncoded());
	}

}
