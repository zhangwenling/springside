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
	public void testEagerInitLazyCollection() {
		//init by hql
		List<User> userList1 = userDao.getAllUserWithRoleByDistinctHql();
		assertEquals(6, userList1.size());

		User user1 = userList1.get(0);
		userDao.getSession().evict(user1);
		assertEquals("管理员, 用户", user1.getRoleNames());

		//init by criteria
		List<User> userList2 = userDao.getAllUserWithRolesByDistinctCriteria();
		assertEquals(6, userList2.size());

		User user2 = userList2.get(0);
		userDao.getSession().evict(user2);
		assertEquals("管理员, 用户", user2.getRoleNames());

		//distinct by set
		List<User> userList3 = userDao.getAllUserWithRoleByHqlDistinctBySet();
		assertEquals(6, userList3.size());

		//distinct by set
		List<User> userList4 = userDao.getAllUserWithRolesByCriteriaDistinctBySet();
		assertEquals(6, userList4.size());
	}

	@Test
	public void testNativeSql() {
		User user = userDao.getUserByNativeSql("admin");
		assertEquals("admin", user.getLoginName());
		assertEquals("管理员, 用户", user.getRoleNames());
	}

	@Test
	public void testClob() {
		User user = userDao.get(1L);
		assertEquals("a good guy", user.getDescription());
	}
}
