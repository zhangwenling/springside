package org.springside.examples.miniservice.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class UserDao extends HibernateDao<User, Long> {
	// 统一定义所有以用户为主体的HQL.
	public static final String AUTH_HQL = "select count(u) from User u where u.loginName=? and u.password=?";
}
