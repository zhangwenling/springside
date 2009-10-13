package org.springside.modules.unit.security.utils;

import org.junit.Test;
import org.springside.modules.security.utils.SecurityUtils;

public class SecurityUtilsTest {

	@Test
	public void digest() {
		String input = "adkluqkn";
		System.out.println(input + " sha1 base64 result:" + SecurityUtils.sha1Base64(input));
	}
}
