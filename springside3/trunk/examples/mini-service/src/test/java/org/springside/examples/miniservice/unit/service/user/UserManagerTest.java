package org.springside.examples.miniservice.unit.service.user;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.dao.UserDao;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.modules.utils.ReflectionUtils;

/**
 * UserManager的单元测试用例,测试Service层的业务逻辑.
 * 
 * @author calvin
 */
public class UserManagerTest extends Assert {

	private UserManager userManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		userManager = new UserManager();
		mockUserDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(userManager, "userDao", mockUserDao);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockUserDao);
	}

	/**
	 * 用户认证测试.
	 * 
	 * 分别测试正确的用户与正确,空,错误的密码三种情况.
	 */
	@Test
	public void authUser() {
		EasyMock.expect(mockUserDao.countUser("admin", "admin")).andReturn(1L);
		EasyMock.expect(mockUserDao.countUser("admin", "errorPasswd")).andReturn(0L);
		EasyMock.replay(mockUserDao);

		assertEquals(true, userManager.authenticate("admin", "admin"));
		assertEquals(false, userManager.authenticate("admin", ""));
		assertEquals(false, userManager.authenticate("admin", "errorPasswd"));
	}
}
