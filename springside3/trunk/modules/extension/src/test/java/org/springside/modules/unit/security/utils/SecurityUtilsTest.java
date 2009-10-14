package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.SecurityUtils;

public class SecurityUtilsTest extends Assert {

	@Test
	public void digest() {
		String input = "adkluqkddn";
		System.out.println(input + " sha1 in hex result    :" + SecurityUtils.sha1ToHex(input));
		System.out.println(input + " sha1 in base64 result :" + SecurityUtils.sha1ToBase64(input));
	}

	@Test
	public void encrypt() {
		String key = SecurityUtils.desGenerateKey();
		System.out.println("des key in hex:" + key);

		String input = "adfadfadca";

		String encryptHexResult = SecurityUtils.desEncryptToHex(input, key);
		String descryptHexResult = SecurityUtils.desDecryptFromHex(encryptHexResult, key);

		String encryptBase64Result = SecurityUtils.desEncryptToBase64(input, key);
		String descryptBase64Result = SecurityUtils.desDecryptFromBase64(encryptBase64Result, key);

		System.out.println(input + " des encrypt in hex result   :" + encryptHexResult);
		System.out.println(input + " des encrypt in base64 result:" + encryptBase64Result);

		assertEquals(input, descryptHexResult);
		assertEquals(input, descryptBase64Result);
	}

	@Test
	public void mac() {
		String input = "adfadfadca";
		String key = "abcdefgdd";
		String macHexResult = SecurityUtils.hmacSha1ToHex(input, key);
		String macBase64Result = SecurityUtils.hmacSha1ToBase64(input, key);
		System.out.println(input + " hmac-sha1 in hex result   :" + macHexResult);
		System.out.println(input + " hmac-sha1 in base64 result:" + macBase64Result);

		assertTrue(SecurityUtils.isHexMacValid(macHexResult, input, key));
		assertTrue(SecurityUtils.isBase64MacValid(macBase64Result, input, key));
	}
}
