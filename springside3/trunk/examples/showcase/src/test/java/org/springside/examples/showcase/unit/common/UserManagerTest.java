package org.springside.examples.showcase.unit.common;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.ServiceException;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.utils.ReflectionUtils;

public class UserManagerTest extends Assert {

	private UserManager userManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		userManager = new UserManager();
		mockUserDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(userManager, "userDao", mockUserDao);
	}

	@Test
	public void saveUser() {
		User admin = new User();
		admin.setId(1L);
		User user = new User();
		user.setId(2L);
		//正常保存用户.
		userManager.saveUser(user);
		//保存超级管理用户抛出异常.
		try {
			userManager.saveUser(admin);
			fail("expected ServicExcepton not be be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
	}
}
