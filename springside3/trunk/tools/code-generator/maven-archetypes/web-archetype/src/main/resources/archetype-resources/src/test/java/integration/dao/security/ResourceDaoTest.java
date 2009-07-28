#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.security.ResourceDao;
import ${package}.entity.security.Resource;
import ${package}.unit.service.security.UserData;
import org.springside.modules.test.spring.SpringTxTestCase;

public class ResourceDaoTest extends SpringTxTestCase  {
	@Autowired
	private ResourceDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Resource entity = UserData.getRandomResource();
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

