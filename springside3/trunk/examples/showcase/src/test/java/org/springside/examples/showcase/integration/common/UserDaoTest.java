package org.springside.examples.showcase.integration.common;

import java.util.ArrayList;
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

import com.google.common.collect.Lists;

/**
 * UserDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * 同时演示分组执行TestCase功能.
 * 
 * @author calvin
 */
//分组执行TestCase
@RunWith(SpringGroupsTestRunner.class)
public class UserDaoTest extends SpringTxTestCase {
	@Autowired
	private UserDao userDao;

	@Test
	public void eagerFetchCollection() {
		int userCount = countRowsInTable("SS_USER");
		//init by hql
		List<User> userList1 = userDao.getAllUserWithRoleByDistinctHql();
		assertEquals(userCount, userList1.size());
		assertTrue(Hibernate.isInitialized(userList1.get(0).getRoleList()));

		//init by criteria
		List<User> userList2 = userDao.getAllUserWithRolesByDistinctCriteria();
		assertEquals(userCount, userList2.size());
		assertTrue(Hibernate.isInitialized(userList2.get(0).getRoleList()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void sampleDialect() {
		//select about 50% record from database.
		List<String> values = userDao.createQuery("select u.name from User u where sample()<50").list();
		for (String name : values) {
			System.out.println(name);
		}
	}

	//只在-Dtest.groups=xxxx或application.test.properties的test.groups中包含MAJOR时执行本测试方法.
	@Test
	@Groups("MAJOR")
	public void upDialect() {
		Object value = userDao.createQuery("select u.name from User u where up(u.name)='ADMIN'").uniqueResult();
		assertEquals("Admin", value);
	}

	@Test
	public void batchDisableUser() {
		List<Long> ids = Lists.newArrayList(1L,2L);

		userDao.disableUsers(ids);

		assertEquals("disabled", userDao.get(1L).getStatus());
		assertEquals("disabled", userDao.get(2L).getStatus());
	}
}
