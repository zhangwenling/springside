package org.springside.examples.miniservice.unit.dao.account;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniservice.dao.account.AccountMyBatisDao;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

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
	private AccountMyBatisDao accountDao;

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
	public void getDepartmentList() {
		List<Department> departmentList = accountDao.getDepartmentList();
		assertEquals(2, departmentList.size());
		assertEquals("Development", departmentList.get(0).getName());
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
	public void saveUser() {
		User user = AccountData.getRandomUser();
		Long id = accountDao.saveUser(user);
		assertEquals(new Long(5L), id);

		assertEquals(4 + 1, this.countRowsInTable("acct_user"));
		assertEquals(2 + 1, accountDao.getDepartmentDetail(1L).getUserList().size());
	}
}
