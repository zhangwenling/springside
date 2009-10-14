package org.springside.modules.security.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 支持SHA-1消息摘要, HMAC-SHA1消息签名 及 DES对称加密的方法集合.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class SecurityUtils {

	private static final String DES = "DES";
	private static final String SHA1 = "SHA-1";
	private static final String HMACSHA1 = "HmacSHA1";

	//-- Hex/Base64 encode and decode function --//
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	public static String base64Encode(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	//-- SHA1 function --//
	/**
	 * 对输入字符串进行sha1散列, 返回字节数组.
	 */
	public static byte[] sha1(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
			return messageDigest.digest(input.getBytes());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Hex编码的结果.
	 */
	public static String sha1ToHex(String input) {
		byte[] digest = sha1(input);
		return hexEncode(digest);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的结果.
	 */
	public static String sha1ToBase64(String input) {
		byte[] digest = sha1(input);
		return base64Encode(digest);
	}

	//-- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组.
	 * 
	 * @param input 原始输入字符串
	 * @param key 任意长度的密钥
	 */
	public static byte[] hmacSha1(String input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input.getBytes());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Hex编码的结果.
	 *
	 * @param input 原始输入字符串
	 * @param key 任意长度的密钥
	 */
	public static String hmacSha1ToHex(String input, byte[] key) {
		byte[] signature = hmacSha1(input, key);
		return hexEncode(signature);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param key 任意长度的密钥
	 */
	public static String hmacSha1ToBase64(String input, byte[] key) {
		byte[] signature = hmacSha1(input, key);
		return base64Encode(signature);
	}

	/**
	 * 校验Hex编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param hexMac Hex编码的签名
	 * @param input 原始输入字符串
	 * @param key 任意长度的密钥
	 */
	public static boolean isHexMacValid(String hexMac, String input, byte[] key) {
		byte[] expected = hexDecode(hexMac);
		byte[] actual = hmacSha1(input, key);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 校验Base64编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param base64Mac Base64编码的签名
	 * @param input 原始输入字符串
	 * @param key 任意长度的密钥
	 */
	public static boolean isBase64MacValid(String base64Mac, String input, byte[] key) {
		byte[] expected = base64Decode(base64Mac);
		byte[] actual = hmacSha1(input, key);

		return Arrays.equals(expected, actual);
	}

	//-- DES function --//
	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 无编码的原始字或加密字符串
	 * @param key 符合DES规范的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	public static byte[] des(byte[] input, byte[] key, int mode) {
		try {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}

	}

	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param key 符合DES规范的密钥
	 */
	public static String desEncryptToHex(String input, byte[] key) {
		byte[] encryptResult = des(input.getBytes(), key, Cipher.ENCRYPT_MODE);
		return hexEncode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param key 符合DES规范的密钥
	 */
	public static String desEncryptToBase64(String input, byte[] key) {
		byte[] encryptResult = des(input.getBytes(), key, Cipher.ENCRYPT_MODE);
		return base64Encode(encryptResult);
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合DES规范的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] key) {
		byte[] decryptResult = des(hexDecode(input), key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Base64编码的加密字符串
	 * @param key 符合DES规范的密钥
	 */
	public static String desDecryptFromBase64(String input, byte[] key) {
		byte[] decryptResult = des(base64Decode(input), key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 生成符合DES规范的密钥, 返回字节数组.
	 */
	public static byte[] desGenerateKey() {
		try {
			SecureRandom secureRandom = new SecureRandom();
			KeyGenerator kg = KeyGenerator.getInstance(DES);
			kg.init(secureRandom);
			SecretKey secretKey = kg.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 生成符合DES规范的密钥, 返回Hex编码的结果.
	 */
	public static String desGenerateHexKey() {
		byte[] key = desGenerateKey();
		return hexEncode(key);
	}

	/**
	 * 生成符合DES规范的密钥, 返回Base65编码的结果.
	 */
	public static String desGenerateBase64Key() {
		byte[] key = desGenerateKey();
		return base64Encode(key);
	}

	public static void handleSecurityException(Exception e) {
		if (e instanceof GeneralSecurityException) {
			throw new IllegalStateException("Security exception", e);
		} else if (e instanceof DecoderException) {
			throw new IllegalStateException("Hex Decoder exception", e);
		} else {
			throw new IllegalStateException("Unexpected exception", e);
		}
	}
}
