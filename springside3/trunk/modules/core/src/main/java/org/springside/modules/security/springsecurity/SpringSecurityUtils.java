/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.springsecurity;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

/**
 * SpringSecurity的工具类.
 * 
 * @author calvin
 */
public class SpringSecurityUtils {

	/**
	 * 取得当前用户的登录名,如果当前用户未登录则返回null.
	 */
	public static String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return null;
		return auth.getName();
	}

	/**
	 * 取得当前用户,返回值为SpringSecurity的User类及其子类, 如果当前用户未登录则返回null.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null)
			return null;
		return (T) principal;
	}
}
