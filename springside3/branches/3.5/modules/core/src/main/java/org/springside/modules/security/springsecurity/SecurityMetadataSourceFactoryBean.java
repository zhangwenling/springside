/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: DefinitionSourceFactoryBean.java 764 2009-12-27 19:13:59Z calvinxiu $
 */
package org.springside.modules.security.springsecurity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.RequestKey;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.google.common.collect.Maps;

/**
 * DefinitionSource工厂.
 * 
 * 由注入的resourceDetailService读取在数据库或其它地方中定义的URL-授权关系,
 * 提供LinkedHashMap<String, String>形式的URL及授权关系定义，
 * 并最终转为SpringSecurity所需的LinkedHashMap<RequestKey, Collection<ConfigAttribute>>形式的定义.
 * 
 * @see ResourceDetailsService
 * 
 * @author calvin
 */
public class SecurityMetadataSourceFactoryBean implements FactoryBean<FilterInvocationSecurityMetadataSource> {

	private ResourceDetailsService resourceDetailsService;

	public void setResourceDetailsService(ResourceDetailsService resourceDetailsService) {
		this.resourceDetailsService = resourceDetailsService;
	}

	/**
	 * 返回注入了Ant Style的URLMatcher和ResourceDetailService提供的RequestMap的DefaultFilterInvocationDefinitionSource.
	 */
	public FilterInvocationSecurityMetadataSource getObject() throws Exception {
		LinkedHashMap<RequestKey, Collection<ConfigAttribute>> requestMap = buildRequestMap();
		UrlMatcher matcher = getUrlMatcher();
		FilterInvocationSecurityMetadataSource securityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(
				matcher, requestMap);
		return securityMetadataSource;
	}

	public Class<? extends FilterInvocationSecurityMetadataSource> getObjectType() {
		return FilterInvocationSecurityMetadataSource.class;
	}

	/**
	 * @see FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * 提供Ant Style的URLMatcher.
	 */
	protected UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}

	/**
	 * 将resourceDetailService提供LinkedHashMap<String, String>形式的URL及授权关系定义
	 * 转化为DefaultFilterInvocationDefinitionSource需要的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式.
	 */
	@SuppressWarnings("unchecked")
	protected LinkedHashMap<RequestKey, Collection<ConfigAttribute>> buildRequestMap() throws Exception {//NOSONAR
		LinkedHashMap<String, String> srcMap = resourceDetailsService.getRequestMap();
		LinkedHashMap<RequestKey, Collection<ConfigAttribute>> distMap = Maps.newLinkedHashMap();
		for (Map.Entry<String, String> entry : srcMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			String access = entry.getValue();
			if (StringUtils.isNotBlank(access)) {
				distMap.put(key, SecurityConfig.createListFromCommaDelimitedString(access));
			} else {
				distMap.put(key, Collections.EMPTY_LIST);
			}
		}

		return distMap;
	}

}
