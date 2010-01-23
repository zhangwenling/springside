package org.springside.examples.showcase.integration.common;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.UserJdbcDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DataUtils;

import com.google.common.collect.Lists;

/**
 * UserJdbcDao的集成测试用例.
 * 
 * @author calvin
 */
public class UserJdbcDaoTest extends SpringTxTestCase {
	@Autowired
	private UserJdbcDao userJdbcDao;

	@Test
	public void queryObject() {
		User user = userJdbcDao.queryObject(1L);
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void queryObjectList() {
		List<User> resultlist = userJdbcDao.queryObjectList();
		assertEquals("admin", resultlist.get(0).getLoginName());
	}

	@Test
	public void queryMap() {
		Map<String, Object> resultMap = userJdbcDao.queryMap(1L);
		assertEquals("admin", resultMap.get("login_name"));
	}

	@Test
	public void queryMapList() {
		List<Map<String, Object>> resultList = userJdbcDao.queryMapList();
		assertEquals("admin", resultList.get(0).get("login_name"));
	}

	@Test
	public void queryBySingleNamedParameter() {
		User user = userJdbcDao.queryBySingleNamedParameter(1L);
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void queryByMultiNamedParameter() {
		User user = userJdbcDao.queryByMultiNamedParameter("admin", "Admin");
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void createObject() {
		Long id = DataUtils.randomId();
		User user = new User();
		user.setId(id);
		user.setLoginName(DataUtils.randomName("user"));
		user.setName(DataUtils.randomName("User"));
		userJdbcDao.createObject(user);

		User newUser = userJdbcDao.queryObject(id);
		assertEquals(user.getLoginName(), newUser.getLoginName());
	}

	@Test
	public void batchCreateObject() {
		Long id1 = DataUtils.randomId();
		User user1 = new User();
		user1.setId(id1);
		user1.setLoginName(DataUtils.randomName("user"));
		user1.setName(DataUtils.randomName("User"));

		Long id2 = DataUtils.randomId();
		User user2 = new User();
		user2.setId(id2);
		user2.setLoginName(DataUtils.randomName("user"));
		user2.setName(DataUtils.randomName("User"));

		List<User> list = Lists.newArrayList(user1, user2);

		userJdbcDao.batchCreateObject(list);

		User newUser1 = userJdbcDao.queryObject(id1);
		assertEquals(user1.getLoginName(), newUser1.getLoginName());

		User newUser2 = userJdbcDao.queryObject(id2);
		assertEquals(user2.getLoginName(), newUser2.getLoginName());
	}
}
