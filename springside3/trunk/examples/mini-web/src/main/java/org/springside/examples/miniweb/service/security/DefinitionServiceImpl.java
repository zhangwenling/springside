package org.springside.examples.miniweb.service.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.ResourceDao;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.security.springsecurity.DefinitionService;

@Transactional(readOnly = true)
public class DefinitionServiceImpl implements DefinitionService {

	private static Logger logger = LoggerFactory.getLogger(DefinitionServiceImpl.class);

	@Autowired
	private ResourceDao resourceDao;

	public LinkedHashMap<String, String> getDefinitionMap() {
		LinkedHashMap<String, String> definitionMap = new LinkedHashMap<String, String>();
		List<Resource> resourceList = resourceDao.getAll();
		try {
			for (Resource resource : resourceList) {
				definitionMap.put(resource.getValue(), resource.getAuthNames());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return definitionMap;
	}
}
