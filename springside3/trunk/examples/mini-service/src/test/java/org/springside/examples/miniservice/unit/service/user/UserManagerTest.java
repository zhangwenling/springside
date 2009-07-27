package org.springside.examples.miniservice.unit.service.user;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.dao.UserDao;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.modules.utils.ReflectionUtils;

public class UserManagerTest extends Assert {

	private UserManager userManager = new UserManager();
	private UserDao userDao = null;

	@Before
	public void setUp() {
		//创建mock对象
		userDao = EasyMock.createNiceMock(UserDao.class);
		ReflectionUtils.setFieldValue(userManager, "userDao", userDao);
	}

	@After
	public void tearDown() {
		//确认脚本都已执行
		EasyMock.verify(userDao);
	}

	@Test
	public void authUser() {
		EasyMock.expect(userDao.findUnique(UserDao.AUTH_HQL, "admin", "admin")).andReturn(1L);
		EasyMock.expect(userDao.findUnique(UserDao.AUTH_HQL, "admin", "errorPasswd")).andReturn(0L);
		EasyMock.replay(userDao);

		assertEquals(true, userManager.authenticate("admin", "admin"));
		assertEquals(false, userManager.authenticate("admin", ""));
		assertEquals(false, userManager.authenticate("admin", "errorPasswd"));
	}
}
