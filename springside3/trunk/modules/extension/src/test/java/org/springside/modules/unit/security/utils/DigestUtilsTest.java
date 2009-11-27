package org.springside.modules.unit.security.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.DigestUtils;

public class DigestUtilsTest extends Assert {

	@Test
	public void digest() {
		String input = "foo message";

		System.out.println("sha1 in hex result              :" + DigestUtils.sha1ToHex(input));
		System.out.print("sha1 in base64 result           :" + DigestUtils.sha1ToBase64(input));
		System.out.println("sha1 in base64 url result       :" + DigestUtils.sha1ToBase64Url(input));
	}
}
