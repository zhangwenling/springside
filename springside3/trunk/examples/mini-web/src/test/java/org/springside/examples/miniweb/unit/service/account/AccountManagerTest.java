package org.springside.examples.miniweb.unit.service.account;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.examples.miniweb.service.account.SecurityEntityManager;
import org.springside.modules.utils.ReflectionUtils;

/**
 * SecurityEntityManager的测试用例, 测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
public class AccountManagerTest extends Assert {

	private SecurityEntityManager securityEntityManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		securityEntityManager = new SecurityEntityManager();
		mockUserDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(securityEntityManager, "userDao", mockUserDao);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockUserDao);
	}

	@Test
	public void deleteUser() {
		mockUserDao.delete(2L);
		EasyMock.replay(mockUserDao);
		//正常删除用户.
		securityEntityManager.deleteUser(2L);
		//删除超级管理用户抛出异常.
		try {
			securityEntityManager.deleteUser(1L);
			fail("expected ServicExcepton not be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
	}
}
