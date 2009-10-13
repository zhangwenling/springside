package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.SecurityUtils;

public class SecurityUtilsTest extends Assert {

	@Test
	public void digest() {
		String input = "adkluqkddn";
		System.out.println(input + " sha1 in hex result    :" + SecurityUtils.sha1(input));
		System.out.println(input + " sha1 in base64 result :" + SecurityUtils.sha1Base64(input));
	}

	@Test
	public void encrypt() throws Exception {
		String key = SecurityUtils.desGenerateKey();
		System.out.println("des key in hex:" + key);

		String input = "adfadfadca";

		String encryptResult = SecurityUtils.desEncrypt(input, key);
		String descryptResult = SecurityUtils.desDecrypt(encryptResult, key);

		String encryptBase64Result = SecurityUtils.desEncryptBase64(input, key);
		String descryptBase64Result = SecurityUtils.desDecryptBase64(encryptBase64Result, key);

		System.out.println(input + "des encrypt in hex result   :" + encryptResult);
		System.out.println(input + "des encrypt in base64 result:" + encryptBase64Result);

		assertEquals(input, descryptResult);
		assertEquals(input, descryptBase64Result);

	}
}
