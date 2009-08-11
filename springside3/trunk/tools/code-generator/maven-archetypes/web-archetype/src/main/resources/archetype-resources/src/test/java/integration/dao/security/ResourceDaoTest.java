#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.security.ResourceDao;
import ${package}.data.SecurityData;
import ${package}.entity.security.Resource;
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

		//find entity.
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNotNull(entity);
		
		//modify entity.
		entity.setValue("new value");
		entityDao.save(entity);
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertEquals("new value", entity.getValue());
		
		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}
}