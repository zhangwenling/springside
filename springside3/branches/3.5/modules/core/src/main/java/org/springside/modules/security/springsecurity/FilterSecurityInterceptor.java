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

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.RequestKey;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.google.common.collect.Maps;

/**
 * 重载FilterSecurityInterceptor, 由注入的resourceDetailService读取在数据库或其它地方中定义的URL-授权关系.* 
 * 
 * @see ResourceDetailsService
 * 
 * @author calvin
 */
public class FilterSecurityInterceptor extends org.springframework.security.web.access.intercept.FilterSecurityInterceptor {

	private ResourceDetailsService resourceDetailsService;

	public void setResourceDetailsService(ResourceDetailsService resourceDetailsService) {
		this.resourceDetailsService = resourceDetailsService;
	}

	/**
	 * 调用resourceDetailsService获取URL-授权关系，建立SecurityMetadataSource.
	 */
	@PostConstruct
	public void buildSecurityMetadataSource() throws Exception {
		LinkedHashMap<RequestKey, Collection<ConfigAttribute>> requestMap = buildRequestMap();
		UrlMatcher matcher =  new AntUrlPathMatcher();
		FilterInvocationSecurityMetadataSource securityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(
				matcher, requestMap);
		super.setSecurityMetadataSource(securityMetadataSource);
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
