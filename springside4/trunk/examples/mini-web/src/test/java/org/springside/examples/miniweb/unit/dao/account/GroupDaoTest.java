package org.springside.examples.miniweb.unit.dao.account;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniweb.dao.account.GroupDao;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * GroupDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class GroupDaoTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UserDao userDao;

	@Before
	public void loadSampleData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/sample-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanSampleData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/sample-data.xml");
		dataSourceHolder = null;
	}

	/**
	 * 测试删除權限組时删除用户-權限組的中间表.
	 */
	@Test
	public void deleteGroup() {
		//新增测试權限組并与admin用户绑定.
		Group group = AccountData.getRandomGroup();
		groupDao.save(group);

		User user = userDao.get(1L);
		user.getGroupList().add(group);
		userDao.save(user);
		userDao.flush();

		int oldJoinTableCount = countRowsInTable("ACCT_USER_GROUP");
		int oldUserTableCount = countRowsInTable("ACCT_USER");

		//删除用户權限組, 中间表将减少1条记录,而用户表应该不受影响.
		groupDao.delete(group.getId());
		groupDao.flush();

		int newJoinTableCount = countRowsInTable("ACCT_USER_GROUP");
		int newUserTableCount = countRowsInTable("ACCT_USER");
		assertEquals(1, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}

	@Test
	public void crudEntityWithGroup() {
		//新建并保存带權限組的用户
		Group group = AccountData.getRandomGroupWithPermissions();
		groupDao.save(group);
		//强制执行sql语句
		groupDao.flush();
		//获取用户
		group = groupDao.findUniqueBy("id", group.getId());
		assertTrue(group.getPermissionList().size() > 0);
	}
}