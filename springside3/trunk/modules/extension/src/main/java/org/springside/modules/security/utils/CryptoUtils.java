package org.springside.modules.security.utils;

import java.net.URLEncoder;
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
public class CryptoUtils {

	private static final String DES = "DES";
	private static final String SHA1 = "SHA-1";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int HMACSHA1_KEY_SIZE = 160;
	private static final String URL_ENCODING = "UTF-8";

	//-- SHA1 function --//
	/**
	 * 对输入字符串进行sha1散列, 返回Hex编码的结果.
	 */
	public static String sha1ToHex(String input) {
		byte[] digestResult = sha1(input);
		return hexEncode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的结果.
	 */
	public static String sha1ToBase64(String input) {
		byte[] digestResult = sha1(input);
		return base64Encode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的URL安全的结果.
	 */
	public static String sha1ToBase64Url(String input) {
		byte[] digestResult = sha1(input);
		return base64UrlEncode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回字节数组.
	 */
	private static byte[] sha1(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
			return messageDigest.digest(input.getBytes());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	//-- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Hex编码的结果.
	 *
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static String hmacSha1ToHex(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return hexEncode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static String hmacSha1ToBase64(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return base64Encode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的URL安全的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static String hmacSha1ToBase64Url(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return base64UrlEncode(macResult);
	}

	/**
	 * 校验Hex编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param hexMac Hex编码的签名
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static boolean isHexMacValid(String hexMac, String input, byte[] keyBytes) {
		byte[] expected = hexDecode(hexMac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 校验Base64编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param base64Mac Base64编码的签名
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	public static boolean isBase64MacValid(String base64Mac, String input, byte[] keyBytes) {
		byte[] expected = base64Decode(base64Mac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 建议最少160位的密钥
	 */
	private static byte[] hmacSha1(String input, byte[] keyBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(keyBytes, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input.getBytes());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组.
	 * HMAC-SHA1算法(RFC2401)建议最少长度为160位(20字节).
	 */
	public static byte[] generateMacSha1Key() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(HMACSHA1);
			keyGen.init(HMACSHA1_KEY_SIZE);
			SecretKey secretKey = keyGen.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 生成HMAC-SHA1密钥, 返回Hex编码的结果.
	 * HMAC-SHA1算法可使用任意长度的密钥, 默认长度为160字节.
	 */
	public static String generateMacSha1HexKey() {
		return hexEncode(generateMacSha1Key());
	}

	/**
	 * 生成HMAC-SHA1密钥, 返回Base64编码的结果.
	 * HMAC-SHA1算法可使用任意长度的密钥, 默认长度为160字节.
	 */
	public static String generateMacSha1Base64Key() {
		return base64Encode(generateMacSha1Key());
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
		return hexEncode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToBase64(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return base64Encode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的URL安全的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToBase64Url(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return base64UrlEncode(encryptResult);
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] keyBytes) {
		byte[] decryptResult = des(hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Base64编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromBase64(String input, byte[] keyBytes) {
		byte[] decryptResult = des(base64Decode(input), keyBytes, Cipher.DECRYPT_MODE);
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
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
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
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * 生成符合DES规范的Hex编码的密钥.
	 */
	public static String generateDesHexKey() {
		return hexEncode(generateDesKey());
	}

	/**
	 * 生成符合DES规范的Base64编码的密钥.
	 */
	public static String generateDesBase64Key() {
		return base64Encode(generateDesKey());
	}

	//-- Support function--// 
	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(不含URL中不支持的字符, RFC3548).
	 */
	public static String base64UrlEncode(byte[] input) {
		try {
			return URLEncoder.encode(Base64.encodeBase64String(input), URL_ENCODING);
		} catch (Exception e) {
			handleSecurityException(e);
			return null;
		}

	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * 统一处理异常, 将全部异常转化为Unchecked Exception后重新抛出.
	 */
	private static void handleSecurityException(Exception e) {
		if (e instanceof GeneralSecurityException) {
			throw new IllegalStateException("Security exception", e);
		} else if (e instanceof DecoderException) {
			throw new IllegalStateException("Hex Decoder exception", e);
		} else {
			throw new IllegalStateException("Unexpected exception", e);
		}
	}
}
