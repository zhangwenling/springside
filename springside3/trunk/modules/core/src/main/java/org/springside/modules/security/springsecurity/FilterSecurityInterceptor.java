/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import com.google.common.collect.Maps;

/**
 * 重载FilterSecurityInterceptor, 由注入的resourceDetailService读取在数据库或其它地方中定义的URL-授权关系.
 * 
 * 注意. 本类只支持SpringSecurity 2.0.x.
 * 
 * @author calvin
 */
public class FilterSecurityInterceptor extends org.springframework.security.intercept.web.FilterSecurityInterceptor  {

	private ResourceDetailsService resourceDetailsService;

	public void setResourceDetailsService(ResourceDetailsService resourceDetailsService) {
		this.resourceDetailsService = resourceDetailsService;
	}

	/**
	 * 调用resourceDetailsService获取URL-授权关系，建立DefinitionSource.
	 */
	@PostConstruct
	public void buildDefinitionSource() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = buildRequestMap();
		UrlMatcher matcher = new AntUrlPathMatcher();
		FilterInvocationDefinitionSource definitionSource = new DefaultFilterInvocationDefinitionSource(matcher,
				requestMap);
		
		super.setObjectDefinitionSource(definitionSource);
	}

	/**
	 * 将resourceDetailService提供LinkedHashMap<String, String>形式的URL及授权关系定义
	 * 转化为DefaultFilterInvocationDefinitionSource需要的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式.
	 */
	protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {//NOSONAR
		LinkedHashMap<String, String> srcMap = resourceDetailsService.getRequestMap();
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> distMap = Maps.newLinkedHashMap();
		ConfigAttributeEditor editor = new ConfigAttributeEditor();

		for (Map.Entry<String, String> entry : srcMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			if (StringUtils.isNotBlank(entry.getValue())) {
				editor.setAsText(entry.getValue());
				distMap.put(key, (ConfigAttributeDefinition) editor.getValue());
			} else {
				distMap.put(key, ConfigAttributeDefinition.NO_ATTRIBUTES);
			}
		}

		return distMap;
	}
}
