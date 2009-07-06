package org.springside.modules.integration.orm.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.SimpleHibernateDao;
import org.springside.modules.test.junit4.SpringTxTestCase;

public class SimpleHibernateDaoTest extends SpringTxTestCase {

	private static final String LOGIN_NAME = "admin";
	private SimpleHibernateDao<User, Long> dao;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void setUp() {
		dao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	}

	@Test
	public void crud() {
		User user = new User();
		user.setName("foo");
		user.setLoginName("foo");
		dao.save(user);
		user.setName("boo");
		dao.save(user);
		dao.delete(user);
	}

	@Test
	public void findByProperty() {
		List<User> users = dao.findBy("loginName", LOGIN_NAME);
		assertEquals(1, users.size());
		assertEquals(LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findByUnique("loginName", LOGIN_NAME);
		assertEquals(LOGIN_NAME, user.getLoginName());
	}

	@Test
	public void findByHQL() {

		List<User> users = dao.find("from User u where loginName=?", LOGIN_NAME);
		assertEquals(1, users.size());
		assertEquals(LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findUnique("from User u where loginName=?", LOGIN_NAME);
		assertEquals(LOGIN_NAME, user.getLoginName());

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("loginName", LOGIN_NAME);
		users = dao.find("from User u where loginName=:loginName", values);
		assertEquals(1, users.size());
		assertEquals(LOGIN_NAME, users.get(0).getLoginName());

		user = dao.findUnique("from User u where loginName=:loginName", values);
		assertEquals(LOGIN_NAME, user.getLoginName());
	}

	@Test
	public void findByCriterion() {
		Criterion c = Restrictions.eq("loginName", LOGIN_NAME);
		List<User> users = dao.find(c);
		assertEquals(1, users.size());
		assertEquals(LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findUnique(c);
		assertEquals(LOGIN_NAME, user.getLoginName());

		dao.findUnique(c);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBatchUpdate() {
		Map map = new HashMap();
		map.put("ids", new Long[] { 1L, 23L });

		dao.batchExecute("update User u set u.status='disabled' where id in(:ids)", map);
		User u1 = dao.get(1L);
		assertEquals("disabled", u1.getStatus());
		User u3 = dao.get(3L);
		assertEquals("enabled", u3.getStatus());
	}

	@Test
	public void getIdName() {
		assertEquals("id", dao.getIdName());
	}

}
