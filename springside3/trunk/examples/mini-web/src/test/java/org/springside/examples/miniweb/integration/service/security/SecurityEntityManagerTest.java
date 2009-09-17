package org.springside.examples.miniweb.integration.service.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.examples.miniweb.service.security.SecurityEntityManager;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * SecurityEntityManager的集成测试用例,测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
public class SecurityEntityManagerTest extends SpringTxTestCase {

	@Autowired
	private SecurityEntityManager securityEntityManager;

	@Test
	public void deleteUser() {
		securityEntityManager.deleteUser(2L);
	}

	@Test(expected = ServiceException.class)
	public void deleteAdmin() {
		securityEntityManager.deleteUser(1L);
	}
}
