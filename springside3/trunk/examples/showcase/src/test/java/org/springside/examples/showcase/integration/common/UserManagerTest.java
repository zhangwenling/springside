package org.springside.examples.showcase.integration.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.ServiceException;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * UserManager的集成测试用例,测试Service层的业务逻辑.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTxTestCase {

	@Autowired
	private UserManager userManager;

	@Test(expected = ServiceException.class)
	public void saveAdminUser() {
		User user = userManager.getUser(1L);
		userManager.saveUser(user);
	}
}
