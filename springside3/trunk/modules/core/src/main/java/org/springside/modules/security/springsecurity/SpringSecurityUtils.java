package org.springside.modules.security.springsecurity;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Spring Security的工具类.
 * 
 * @author calvin
 */
public class SpringSecurityUtils {

	private SpringSecurityUtils() {
	}

	/**
	 * 取得当前用户的登录名,如果无已登录用户则返回null.
	 */
	public static String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			return null;
		return auth.getName();
	}
}
