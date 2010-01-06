/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringSecurityUtils.java 763 2009-12-27 18:36:21Z calvinxiu $
 */
package org.springside.modules.security.springsecurity;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * SpringSecurity的工具类.
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
	 * 使用数据库存储URL-授权关系时, 改变URL或授权后需要执行本函数.
	 */
	@SuppressWarnings("unchecked")
	public static void refreshDefinitionSource(ApplicationContext ctx,String filterSecurityInterceptorName,String securityMetadataSourceName) throws Exception{
		FilterSecurityInterceptor interceptor = (FilterSecurityInterceptor)ctx.getBean(filterSecurityInterceptorName);
		FactoryBean<FilterInvocationSecurityMetadataSource> factoryBean =  (FactoryBean<FilterInvocationSecurityMetadataSource>)ctx.getBean("&"+securityMetadataSourceName);
		FilterInvocationSecurityMetadataSource newSource = (FilterInvocationSecurityMetadataSource)factoryBean.getObject();
		interceptor.setSecurityMetadataSource(newSource);
	}
}
