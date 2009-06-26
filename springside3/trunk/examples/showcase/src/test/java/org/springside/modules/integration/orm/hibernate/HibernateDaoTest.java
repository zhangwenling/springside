package org.springside.modules.integration.orm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.test.junit4.SpringTxTestCase;

public class HibernateDaoTest extends SpringTxTestCase {

	private HibernateDao<User, Long> dao;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void setUp() {
		dao = new HibernateDao<User, Long>(sessionFactory, User.class);
	}

	@Test
	public void getAll() {
		//初始化数据中共有6个用户
		Page<User> page = new Page<User>(5);
		dao.getAll(page);
		assertEquals(5, page.getResult().size());

		//自动统计总数
		assertEquals(6L, page.getTotalCount());

		page.setPageNo(2);
		dao.getAll(page);
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void findByHQL() {
		//初始化数据中共有6个email为@springside.org.cn的用户
		Page<User> page = new Page<User>(5);
		dao.find(page, "from User u where email like ?", "%springside.org.cn%");
		assertEquals(5, page.getResult().size());

		//自动统计总数
		assertEquals(6L, page.getTotalCount());

		//翻页
		page.setPageNo(2);
		dao.find(page, "from User u where email like ?", "%springside.org.cn%");
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void findByCriterion() {
		//初始化数据中共有6个email为@springside.org.cn的用户
		Page<User> page = new Page<User>(5);
		Criterion c = Restrictions.like("email", "springside.org.cn", MatchMode.ANYWHERE);
		dao.find(page, c);
		assertEquals(5, page.getResult().size());

		//自动统计总数
		assertEquals(6L, page.getTotalCount());

		//翻页
		page.setPageNo(2);
		dao.find(page, c);
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void findByProperty() {
		List<User> users = dao.findBy("loginName", "admin", PropertyFilter.MatchType.EQ);
		assertEquals(1, users.size());
		assertEquals("admin", users.get(0).getLoginName());

		users = dao.findBy("email", "springside.org.cn", PropertyFilter.MatchType.LIKE);
		assertEquals(6, users.size());
		assertTrue(users.get(0).getEmail().indexOf("springside.org.cn") != -1);
	}

	@Test
	public void findByFilters() {
		PropertyFilter eqFilter = new PropertyFilter("loginName", PropertyFilter.MatchType.EQ, "admin");

		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(eqFilter);

		List<User> users = dao.find(filters);
		assertEquals(1, users.size());
		assertEquals("admin", users.get(0).getLoginName());

		filters.clear();
		PropertyFilter likeFilter = new PropertyFilter("email", PropertyFilter.MatchType.LIKE, "springside.org.cn");
		filters.add(likeFilter);
		users = dao.find(filters);
		assertEquals(6, users.size());
		assertTrue(users.get(0).getEmail().indexOf("springside.org.cn") != -1);

		Page<User> page = new Page<User>(5);

		dao.find(page, filters);
		assertEquals(5, page.getResult().size());
		assertEquals(6L, page.getTotalCount());

		page.setPageNo(2);
		dao.find(page, filters);
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void isPropertyUnique() {
		assertEquals(true, dao.isPropertyUnique("loginName", "admin", "admin"));
		assertEquals(true, dao.isPropertyUnique("loginName", "user6", "admin"));
		assertEquals(false, dao.isPropertyUnique("loginName", "user2", "admin"));
	}

	@Test
	public void findPageByHqlAutoCount() {
		Page<User> page = new Page<User>(5);
		dao.find(page, "from User user");
		assertEquals(6L, page.getTotalCount());

		dao.find(page, "select user from User user");
		assertEquals(6L, page.getTotalCount());

		dao.find(page, "select user from User user order by id");
		assertEquals(6L, page.getTotalCount());
	}
}
