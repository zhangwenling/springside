package org.springside.examples.miniweb.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.dao.security.AuthorityDao;
import org.springside.examples.miniweb.data.SecurityData;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.modules.test.spring.SpringTxTestCase;

public class AuthorityDaoTest extends SpringTxTestCase  {
	@Autowired
	private AuthorityDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Authority entity = SecurityData.getRandomAuthority();
		entityDao.save(entity);
		flush();

		//get entity by id.	
		entity = entityDao.get(entity.getId());
		
		//modify entity.
		entity.setName("new name");
		entityDao.save(entity);
		flush();
		
		//delete entity.
		entityDao.delete(entity.getId());
		flush();
	}
}

