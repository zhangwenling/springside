#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.user;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.RoleDao;
import ${package}.entity.user.Role;
import ${package}.unit.service.user.UserData;
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

