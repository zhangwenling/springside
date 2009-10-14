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
import org.apache.commons.codec.binary.Hex;

/**
 * 单向SHA-1散列与DES对称加密的Utils.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class SecurityUtils {

	private static final String DES = "DES";

	//-- SHA1 --//
	/**
	 * 对输入字符串进行sha1散列,并进行Hex编码.
	 */
	public static String sha1ToHex(String input) {

		byte[] digest = sha1(input);
		return Hex.encodeHexString(digest);
	}

	/**
	 * 对输入字符串进行sha1散列,并进行Base64编码.
	 */
	public static String sha1ToBase64(String input) {
		byte[] digest = sha1(input);
		return Base64.encodeBase64String(digest);
	}

	/**
	 * 对输入字符串进行sha1散列,返回字节数组.
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

	//-- DES --//
	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串.
	 * @param hexKey 由desGenerateHexKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desEncryptToHex(String input, String hexKey) throws Exception {
		byte[] encryptResult = des(input.getBytes(), hexKey, Cipher.ENCRYPT_MODE);
		return Hex.encodeHexString(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串.
	 * @param hexKey 由desGenerateHexKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desEncryptToBase64(String input, String hexKey) throws Exception {
		byte[] encryptResult = des(input.getBytes(), hexKey, Cipher.ENCRYPT_MODE);
		return Base64.encodeBase64String(encryptResult);
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串.
	 * @param hexKey 由desGenerateHexKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desDecryptFromHex(String input, String hexKey) throws Exception {
		byte[] decryptResult = des(Hex.decodeHex(input.toCharArray()), hexKey, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Base64编码的加密字符串.
	 * @param hexKey 由desGenerateHexKey()生成的, 使用Hex编码的密钥.
	 */
	public static String desDecryptFromBase64(String input, String hexKey) throws Exception {
		byte[] decryptResult = des(Base64.decodeBase64(input), hexKey, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 无编码的原始字或加密字符串
	 * @param key 由desGenerateHexKey()生成的, 使用Hex编码的密钥.
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

	/**
	 * 生成符合DES规范的密钥, 返回Hex编码的结果.
	 */
	public static String desGenerateHexKey() throws Exception {
		byte[] key = desGenerateKey();
		return Hex.encodeHexString(key);
	}

	/**
	 * 生成符合DES规范的密钥, 返回字节数组.
	 */
	private static byte[] desGenerateKey() throws Exception {
		SecureRandom secureRandom = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance(DES);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
}
