#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.security.AuthorityDao;
import ${package}.entity.security.Authority;
import ${package}.unit.service.security.UserData;
import org.springside.modules.test.spring.SpringTxTestCase;

public class AuthorityDaoTest extends SpringTxTestCase  {
	@Autowired
	private AuthorityDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Authority entity = UserData.getRandomAuthority();
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

