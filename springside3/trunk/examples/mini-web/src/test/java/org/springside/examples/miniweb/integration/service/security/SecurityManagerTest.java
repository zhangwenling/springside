package org.springside.examples.miniweb.integration.service.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.examples.miniweb.service.security.SecurityManager;
import org.springside.modules.test.spring.SpringTxTestCase;

public class SecurityManagerTest extends SpringTxTestCase {
	@Autowired
	private SecurityManager securityManager = new SecurityManager();

	@Test(expected = ServiceException.class)
	public void deleteAdmin(){
		securityManager.deleteUser(1L);
	}
	
	@Test
	public void isLoginNameUnique() {
		assertEquals(true, securityManager.isLoginNameUnique("admin", "admin"));
		assertEquals(true, securityManager.isLoginNameUnique("foo", "admin"));
		assertEquals(false, securityManager.isLoginNameUnique("admin", ""));
	}
}
