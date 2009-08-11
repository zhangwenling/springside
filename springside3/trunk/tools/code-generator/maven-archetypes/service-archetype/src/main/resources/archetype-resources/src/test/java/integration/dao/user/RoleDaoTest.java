#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.user;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.RoleDao;
import ${package}.data.UserData;
import ${package}.entity.user.Role;
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
		entity = entityDao.get(entity.getId());
		assertNotNull(entity);
		
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

