package org.springside.examples.miniservice.integration.dao.user;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.dao.RoleDao;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.Role;
import org.springside.modules.test.spring.SpringTxTestCase;

public class RoleDaoTest extends SpringTxTestCase  {
	@Autowired
	private RoleDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Role entity = UserData.getRandomRole();
		entityDao.save(entity);
		flush();

		//find entity.	
		Role entityFromDB = entityDao.get(entity.getId());
		assertReflectionEquals(entity, entityFromDB);
		
		//modify entity.
		entity.setName("new value");
		entityDao.save(entity);
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertEquals("new value", entity.getName());
		
		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}
}

