package org.springside.examples.showcase.integration.common.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.test.junit4.SpringTxTestCase;

public class UserDaoTest extends SpringTxTestCase {
	@Autowired
	private UserDao userDao;

	@Test
	public void testInitLazy() {
		//init by hql
		List<User> userList1 = userDao.getAllUserWithRoleByHql();
		assertEquals(6, userList1.size());

		User user1 = userList1.get(0);
		userDao.getSession().evict(user1);
		assertEquals("管理员, 用户", user1.getRoleNames());

		//init by criteria
		List<User> userList2 = userDao.getAllUserWithRolesByCriteria();
		assertEquals(6, userList2.size());

		User user2 = userList2.get(0);
		userDao.getSession().evict(user2);
		assertEquals("管理员, 用户", user2.getRoleNames());
	}

	@Test
	public void testNativeSql() {
		User user = userDao.getUserByNativeSql("admin");
		assertEquals("admin", user.getLoginName());
		assertEquals("管理员, 用户", user.getRoleNames());
	}
}
