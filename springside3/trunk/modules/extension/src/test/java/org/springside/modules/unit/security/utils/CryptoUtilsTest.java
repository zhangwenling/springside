package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.CryptoUtils;

public class CryptoUtilsTest extends Assert {

	@Test
	public void digest() {
		String input = "foo message";

		System.out.println("sha1 in hex result              :" + CryptoUtils.sha1ToHex(input));
		System.out.print("sha1 in base64 result           :" + CryptoUtils.sha1ToBase64(input));
		System.out.println("sha1 in base64 url result       :" + CryptoUtils.sha1ToBase64Url(input));
	}

	@Test
	public void encrypt() {
		byte[] key = CryptoUtils.generateDesKey();
		String input = "foo message";

		String encryptHexResult = CryptoUtils.desEncryptToHex(input, key);
		String descryptHexResult = CryptoUtils.desDecryptFromHex(encryptHexResult, key);

		String encryptBase64Result = CryptoUtils.desEncryptToBase64(input, key);
		String descryptBase64Result = CryptoUtils.desDecryptFromBase64(encryptBase64Result, key);
		String encryptBase64UrlResult = CryptoUtils.desEncryptToBase64Url(input, key);

		System.out.println("des encrypt in hex result       :" + encryptHexResult);
		System.out.print("des encrypt in base64 result    :" + encryptBase64Result);
		System.out.println("des encrypt in base64 url result:" + encryptBase64UrlResult);

		assertEquals(input, descryptHexResult);
		assertEquals(input, descryptBase64Result);
	}

	@Test
	public void mac() {
		String input = "foo message";

		byte[] key = CryptoUtils.generateMacSha1Key();
		//hmac-sha1 密钥可为任意长度的字符串, 建议最少20字节.
		//byte[] key = "a foo key".getBytes();

		String macHexResult = CryptoUtils.hmacSha1ToHex(input, key);
		String macBase64Result = CryptoUtils.hmacSha1ToBase64(input, key);
		String macBase64UrlResult = CryptoUtils.hmacSha1ToBase64Url(input, key);

		System.out.println("hmac-sha1 in hex result         :" + macHexResult);
		System.out.print("hmac-sha1 in base64 result      :" + macBase64Result);
		System.out.println("hmac-sha1 in base64 url result  :" + macBase64UrlResult);

		assertTrue(CryptoUtils.isHexMacValid(macHexResult, input, key));
		assertTrue(CryptoUtils.isBase64MacValid(macBase64Result, input, key));
	}

}
