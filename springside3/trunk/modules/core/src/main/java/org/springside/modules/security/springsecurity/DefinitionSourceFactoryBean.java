package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

/**
 * DefinitionSource工厂,可在数据库或其它地方定义URL-授权关系.
 * 
 * 由注入的requestMapService提供LinkedHashMap<String, String>形式的URL及授权关系定义.
 * 
 * @see org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource
 * @see RequestMapService
 * 
 * @author calvin
 */
public class DefinitionSourceFactoryBean implements FactoryBean {

	private RequestMapService requestMapService;

	public void setRequestMapService(RequestMapService requestMapService) {
		this.requestMapService = requestMapService;
	}

	public Object getObject() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = getRequestMap();
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

	private UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}

	private LinkedHashMap<RequestKey, ConfigAttributeDefinition> getRequestMap() throws Exception {
		LinkedHashMap<String, String> srcMap = requestMapService.getRequestMap();
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		ConfigAttributeEditor editor = new ConfigAttributeEditor();

		for (Map.Entry<String, String> entry : srcMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			editor.setAsText(entry.getValue());
			requestMap.put(key, (ConfigAttributeDefinition) editor.getValue());
		}
		return requestMap;
	}
}
