#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.service.ServiceException;
import ${package}.service.security.SecurityManager;
import org.springside.modules.test.spring.SpringTxTestCase;

public class SecurityManagerTest extends SpringTxTestCase {
	@Autowired
	private SecurityManager securityManager = new SecurityManager();

	@Test(expected = ServiceException.class)
	public void deleteAdmin() {
		securityManager.deleteUser(1L);
	}

	@Test
	public void isLoginNameUnique() {
		assertEquals(true, securityManager.isLoginNameUnique("admin", "admin"));
		assertEquals(true, securityManager.isLoginNameUnique("foo", "admin"));
		assertEquals(false, securityManager.isLoginNameUnique("admin", ""));
	}
}
