package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.SecurityUtils;

public class SecurityUtilsTest extends Assert {

	@Test
	public void digest() {
		String input = "adkluqkddn";
		System.out.println(input + " sha1 in base64 result         :" + SecurityUtils.sha1Base64(input));
		System.out.println(input + " sha1 in base64 url safe result:" + SecurityUtils.sha1Base64UrlSafe(input));
	}

	@Test
	public void encrypt() throws Exception {
		String key = SecurityUtils.generateDesKey();
		System.out.println("key in base64:" + key);

		String input = "adfadfadca";
		String encryptResult = SecurityUtils.desEncrypt(input, key);
		String descryptResult = SecurityUtils.desDecrypt(encryptResult, key);

		System.out.println(input + "des encrypt in base64 result:" + encryptResult);
		assertEquals(input, descryptResult);
	}
}
