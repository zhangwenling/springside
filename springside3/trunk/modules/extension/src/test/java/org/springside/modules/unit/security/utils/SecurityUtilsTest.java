package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.SecurityUtils;

public class SecurityUtilsTest extends Assert {

	@Test
	public void digest() throws Exception {
		String input = "adkluqkddn";
		System.out.println(input + " sha1 in hex result    :" + SecurityUtils.sha1ToHex(input));
		System.out.println(input + " sha1 in base64 result :" + SecurityUtils.sha1ToBase64(input));
	}

	@Test
	public void encrypt() throws Exception {
		String hexKey = SecurityUtils.desGenerateHexKey();
		byte[] key = SecurityUtils.hexDecode(hexKey);
		System.out.println("des key in hex:" + hexKey);

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
	public void mac() throws Exception {
		String input = "adfadfadca";
		String key = "abcdefgdd";
		byte[] keyBytes = key.getBytes();
		String macHexResult = SecurityUtils.hmacSha1ToHex(input, keyBytes);
		String macBase64Result = SecurityUtils.hmacSha1ToBase64(input, keyBytes);
		System.out.println(input + " hmac-sha1 in hex result   :" + macHexResult);
		System.out.println(input + " hmac-sha1 in base64 result:" + macBase64Result);

		assertTrue(SecurityUtils.isHexMacValid(macHexResult, input, keyBytes));
		assertTrue(SecurityUtils.isBase64MacValid(macBase64Result, input, keyBytes));
	}
}
