package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

/**
 * DefinitionSource工厂.
 * 
 * 由注入的resourceDetailService读取在数据库或其它地方中定义的URL-授权关系,
 * 提供LinkedHashMap<String, String>形式的URL及授权关系定义，
 * 并最终转为SpringSecurity所需的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式的定义.
 * 
 * @see org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource
 * @see ResourceDetailService
 * 
 * @author calvin
 */
public class DefinitionSourceFactoryBean implements FactoryBean {

	private ResourceDetailService resourceDetailService;

	public void setResourceDetailService(ResourceDetailService resourceDetailService) {
		this.resourceDetailService = resourceDetailService;
	}

	/**
	 * 返回注入了Ant Style的URLMatcher和ResourceDetailService提供的RequestMap的DefaultFilterInvocationDefinitionSource.
	 */
	public Object getObject() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = buildRequestMap();
		UrlMatcher matcher = getUrlMatcher();
		DefaultFilterInvocationDefinitionSource definitionSource = new DefaultFilterInvocationDefinitionSource(matcher,
				requestMap);
		return definitionSource;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

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
	protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {
		LinkedHashMap<String, String> srcMap = resourceDetailService.getRequestMap();
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> distMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
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
