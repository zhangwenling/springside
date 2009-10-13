package org.springside.modules.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class SecurityUtils {

	/**
	 * 对输入字符串进行Sha-1散列并进行Base64编码.
	 */
	public static String sha1Base64(String input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unexpected exception.", e);
		}
		byte[] digest = messageDigest.digest(input.getBytes());

		return new String(Base64.encodeBase64(digest));
	}
}
