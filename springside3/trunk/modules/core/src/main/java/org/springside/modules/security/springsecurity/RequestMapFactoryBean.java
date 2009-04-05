package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.RequestKey;

public class RequestMapFactoryBean implements FactoryBean {

	RequestMapService requestMapService;

	public void setDefinitionService(RequestMapService requestMapService) {
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
