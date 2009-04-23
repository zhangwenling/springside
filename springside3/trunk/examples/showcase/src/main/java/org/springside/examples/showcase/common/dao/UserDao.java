package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 用户泛型DAO类.
 * 
 * @author calvin
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {

	/**
	 * 因为QuartzBean实在无法声明事务管理,才有这个定义了事务的函数存在.
	 */
	@Transactional(readOnly = true)
	public long getUserCount() {
		return findLong(User.COUNT_USER);
	}
}
