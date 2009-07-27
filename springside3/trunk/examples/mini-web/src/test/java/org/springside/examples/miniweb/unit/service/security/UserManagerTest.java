package org.springside.examples.miniweb.unit.service.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniweb.dao.security.UserDao;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.examples.miniweb.service.security.UserManager;
import org.springside.modules.utils.ReflectionUtils;

public class UserManagerTest extends Assert {

	private UserManager userManager = new UserManager();
	private UserDao userDao = null;
	
	@Before
	public void setUp() {
		//创建mock对象
		userDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(userManager, "userDao", userDao);
	}
	
	@After
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userDao);
	}

	@Test
	public void testDetelteUser() {
		userManager.deleteUser(2L);
	}

	@Test(expected = ServiceException.class)
	public void testDeleteAdmin(){
		userManager.deleteRole(1L);
	}
}
