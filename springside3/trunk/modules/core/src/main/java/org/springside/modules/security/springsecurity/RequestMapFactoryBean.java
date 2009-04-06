package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.RequestKey;

/**
 * RequestMap工厂,提供存放于数据库或其它地方的URL-授权关系定义.
 * 
 * 要实现数据库存放URL-授权关系定义,只需向DefaultFilterInvocationDefinitionSource的构造函数新的RequestMapService提供的RequestMap即可,无需重写该类.
 * requestMapService将提供LinkedHashMap<String, String>形式的关系定义,由本类组装成SpringSecurity要求的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式.
 * 
 * @see org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource
 * @see RequestMapService
 * 
 * @author calvin
 */
public class RequestMapFactoryBean implements FactoryBean {

	private RequestMapService requestMapService;

	public RequestMapFactoryBean(RequestMapService requestMapService) {
		this.requestMapService = requestMapService;
	}

	public Object getObject() throws Exception {

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

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return LinkedHashMap.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
