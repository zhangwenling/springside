package org.springside.examples.miniservice.unit.service.account;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.dao.account.UserMyBatisDao;
import org.springside.examples.miniservice.service.account.AccountManager;

/**
 * AccountManager的单元测试用例, 测试Service层的业务逻辑.
 * 
 * 使用EasyMock对UserDao进行模拟.
 * 
 * @author calvin
 */
public class AccountManagerTest {

	private IMocksControl control = EasyMock.createControl();

	private AccountManager accountManager;
	private UserMyBatisDao mockUserDao;

	@Before
	public void setUp() {
		accountManager = new AccountManager();
		mockUserDao = control.createMock(UserMyBatisDao.class);
		accountManager.setUserDao(mockUserDao);
	}

	@After
	public void tearDown() {
		control.verify();
	}

	/**
	 * 用户认证测试.
	 * 
	 * 分别测试正确的用户与正确,空,错误的密码三种情况.
	 */
	@Test
	public void authUser() {
		EasyMock.expect(mockUserDao.countByLoginNamePassword("admin", "admin")).andReturn(1L);
		EasyMock.expect(mockUserDao.countByLoginNamePassword("admin", "errorPasswd")).andReturn(0L);
		control.replay();

		assertEquals(true, accountManager.authenticate("admin", "admin"));
		assertEquals(false, accountManager.authenticate("admin", ""));
		assertEquals(false, accountManager.authenticate("admin", "errorPasswd"));
	}
}
