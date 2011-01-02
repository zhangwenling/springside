package org.springside.examples.miniservice.unit.dao.account;

import static org.junit.Assert.*;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniservice.dao.account.AccountDao;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.orm.Page;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

import com.google.common.collect.Maps;

/**
 * AccountDao的集成测试用例,测试ORM映射及SQL操作.
 * 
 * 默认在每个测试函数后进行回滚.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class AccountDaoTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private AccountDao accountDao;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	@Test
	public void getDepartmentDetail() {
		Department department = accountDao.getDepartmentDetail(1L);
		assertEquals("Development", department.getName());
		assertEquals("Jack", department.getManager().getName());
		assertEquals(2, department.getUserList().size());
		assertEquals("Jack", department.getUserList().get(0).getName());
	}

	@Test
	public void getUser() {
		User user = accountDao.getUser(1L);
		assertEquals("user1", user.getLoginName());

		user = accountDao.getUser(999L);
		assertNull(user);
	}

	@Test
	public void searchUser() {
		Map<String, Object> parameters = Maps.newHashMap();

		parameters.put("loginName", null);
		parameters.put("name", null);
		Page<User> page = accountDao.searchUser(new Page(1, Integer.MAX_VALUE), parameters);
		assertEquals(4, page.getResult().size());

		parameters.put("loginName", "user1");
		parameters.put("name", null);
		page = accountDao.searchUser(new Page(1, Integer.MAX_VALUE), parameters);
		assertEquals(1, page.getResult().size());

		parameters.clear();
		parameters.put("name", "Jack");
		parameters.put("loginName", null);
		page = accountDao.searchUser(new Page(1, Integer.MAX_VALUE), parameters);
		assertEquals(1, page.getResult().size());

		parameters.clear();
		parameters.put("name", "Jack");
		parameters.put("loginName", "user1");
		page = accountDao.searchUser(new Page(1, Integer.MAX_VALUE), parameters);
		assertEquals(1, page.getResult().size());

		parameters.clear();
		parameters.put("name", "Jack");
		parameters.put("loginName", "errorName");
		page = accountDao.searchUser(new Page(1, Integer.MAX_VALUE), parameters);
		assertEquals(0, page.getResult().size());
	}

	@Test
	public void saveUser() {
		User user = AccountData.getRandomUser();
		Long id = accountDao.saveUser(user);
		assertEquals(new Long(5L), id);

		assertEquals(4 + 1, this.countRowsInTable("acct_user"));
		assertEquals(2 + 1, accountDao.getDepartmentDetail(1L).getUserList().size());
	}

}
