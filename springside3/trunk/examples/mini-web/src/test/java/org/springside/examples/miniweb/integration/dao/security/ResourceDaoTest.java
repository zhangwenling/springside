package org.springside.examples.miniweb.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.dao.security.ResourceDao;
import org.springside.examples.miniweb.data.SecurityData;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.test.spring.SpringTxTestCase;

public class ResourceDaoTest extends SpringTxTestCase  {
	@Autowired
	private ResourceDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Resource entity = SecurityData.getRandomResource();
		entityDao.save(entity);
		flush();

		//get entity by id.	
		entity = entityDao.get(entity.getId());
		
		//modify entity.
		entity.setValue("new value");
		entityDao.save(entity);
		flush();
		
		//delete entity.
		entityDao.delete(entity.getId());
		flush();
	}
}

