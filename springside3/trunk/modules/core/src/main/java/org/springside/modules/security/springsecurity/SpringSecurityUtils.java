/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.springsecurity;

import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

/**
 * SpringSecurity的工具类.
 * 
 * 注意. 本类只支持SpringSecurity 2.0.x.
 * 
 * @author calvin
 */
public class SpringSecurityUtils {

	/**
	 * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "";
		}
		return auth.getName();
	}

	/**
	 * 取得当前用户, 返回值为SpringSecurity的User类及其子类, 如果当前用户未登录则返回null.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			return null;
		}
		return (T) principal;
	}
	
	/**
	 * 刷新FilterSecurityInterceptor中的URL-授权定义.
	 * 使用数据库存储URL-授权关系时, 改变守保护URL资源或授权后需要执行本函数.
	 */
	public static void refreshDefinitionSource(ApplicationContext ctx,String filterSecurityInterceptorName) throws Exception{
		FilterSecurityInterceptor interceptor = (FilterSecurityInterceptor)ctx.getBean(filterSecurityInterceptorName);
		interceptor.buildDefinitionSource();
	}
}
