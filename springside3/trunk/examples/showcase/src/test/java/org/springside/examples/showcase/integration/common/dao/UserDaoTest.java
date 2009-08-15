package org.springside.examples.showcase.integration.common.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.spring.SpringGroupsTestRunner;
import org.springside.modules.test.spring.SpringTxTestCase;

@RunWith(SpringGroupsTestRunner.class)
public class UserDaoTest extends SpringTxTestCase {
	@Autowired
	private UserDao userDao;

	@Test
	public void testEagerFetchCollection() {
		//init by hql
		List<User> userList1 = userDao.getAllUserWithRoleByDistinctHql();
		assertEquals(6, userList1.size());

		assertTrue(Hibernate.isInitialized(userList1.get(0).getRoles()));

		//init by criteria
		List<User> userList2 = userDao.getAllUserWithRolesByDistinctCriteria();
		assertEquals(6, userList2.size());
		assertTrue(Hibernate.isInitialized(userList2.get(0).getRoles()));

		//distinct by set
		List<User> userList3 = userDao.getAllUserWithRoleByHqlDistinctBySet();
		assertEquals(6, userList3.size());
	}

	@Test
	public void testNativeSql() {
		User user = userDao.getUserByNativeSql("admin");
		assertEquals("admin", user.getLoginName());
		assertEquals("Admin, User", user.getRoleNames());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSampleDialect() {
		//select about 50% record from database.
		List<String> values = userDao.createQuery("select u.name from User u where sample()<50").list();
		for (String name : values) {
			System.out.println(name);
		}
	}

	//只在-Dtest.groups=xxxx或application.test.properties的test.groups中包含extension时执行本测试方法.
	@Test
	@Groups("extension")
	public void testUpDialect() {
		Object value = userDao.createQuery("select u.name from User u where up(u.name)='ADMIN'").uniqueResult();
		assertEquals("Admin", value);
	}
}
