package org.springside.examples.miniweb.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.dao.security.RoleDao;
import org.springside.examples.miniweb.data.SecurityData;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.modules.test.spring.SpringTxTestCase;

public class RoleDaoTest extends SpringTxTestCase {
	@Autowired
	private RoleDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Role entity = SecurityData.getRandomRole();
		entityDao.save(entity);
		flush();

		//find entity.
		Role entityFromDB = entityDao.findUniqueBy("id", entity.getId());
		assertReflectionEquals(entity, entityFromDB);

		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}
}
