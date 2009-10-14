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
	public void encrypt() throws Exception {
		String key = SecurityUtils.desGenerateHexKey();
		System.out.println("des key in hex:" + key);

		String input = "adfadfadca";

		String encryptResult = SecurityUtils.desEncryptToHex(input, key);
		String descryptResult = SecurityUtils.desDecryptFromHex(encryptResult, key);

		String encryptBase64Result = SecurityUtils.desEncryptToBase64(input, key);
		String descryptBase64Result = SecurityUtils.desDecryptFromBase64(encryptBase64Result, key);

		System.out.println(input + "des encrypt in hex result   :" + encryptResult);
		System.out.println(input + "des encrypt in base64 result:" + encryptBase64Result);

		assertEquals(input, descryptResult);
		assertEquals(input, descryptBase64Result);

	}
}
