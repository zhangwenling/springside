package org.springside.examples.miniservice.unit.service.account;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.dao.account.AccountMyBatisDao;
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
	private AccountMyBatisDao mockAccountDao;

	@Before
	public void setUp() {
		accountManager = new AccountManager();
		mockAccountDao = control.createMock(AccountMyBatisDao.class);
		accountManager.setAccountDao(mockAccountDao);
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
		EasyMock.expect(mockAccountDao.countByLoginNamePassword("admin", "admin")).andReturn(1);
		EasyMock.expect(mockAccountDao.countByLoginNamePassword("admin", "errorPasswd")).andReturn(0);
		control.replay();

		assertEquals(true, accountManager.authenticate("admin", "admin"));
		assertEquals(false, accountManager.authenticate("admin", ""));
		assertEquals(false, accountManager.authenticate("admin", "errorPasswd"));
	}
}
