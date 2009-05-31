package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class UserDao extends HibernateDao<User, Long> {
	/**
	 * hibernate native sql 演示.
	 * 
	 * 因为hibernate当前版本中SQLQueryReturnProcessor不能eager fetch many to many的bug, 因此仅加载User对象.
	 */
	public User findUserByNativeSQL(String loginName) {

		return (User) getSession().createSQLQuery("select  {u.*} from USERS u  where u.LOGIN_NAME=:loginName")
				.addEntity("u", User.class).setParameter("loginName", loginName).uniqueResult();

	}

}
