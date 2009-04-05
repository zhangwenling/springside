package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.RequestKey;

/**
 * RequestMap工厂.
 * 
 * 为DefaultFilterInvocationDefinitionSource提供存放于数据库或其它地方的URL-授权关系定义.
 * 根据用户的RequestMapService类返回的LinkedHashMap<String, String>生成LinkedHashMap<RequestKey, ConfigAttributeDefinition>RequestMap.
 * 
 * @see org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource
 * @see RequestMapService
 * 
 * @author calvin
 */
public class RequestMapFactoryBean implements FactoryBean {

	RequestMapService requestMapService;

	public void setRequestMapService(RequestMapService requestMapService) {
		this.requestMapService = requestMapService;
	}

	public Object getObject() throws Exception {

		LinkedHashMap<String, String> srcMap = requestMapService.getRequestMap();
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		ConfigAttributeEditor editor = new ConfigAttributeEditor();

		Set<String> paths = srcMap.keySet();
		for (String path : paths) {
			RequestKey key = new RequestKey(path, null);
			editor.setAsText(srcMap.get(path));
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
