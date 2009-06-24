package org.springside.examples.showcase.common.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class UserDao extends HibernateDao<User, Long> {
	/**
	 * 使用 HQL 预加载lazy init的List<Role>.
	 */
	public List<User> getAllUserWithRoleByHql() {
		return find("select distinct  u from User u left join fetch u.roles");
	}

	/**
	 * 使用Criteria 预加载lazy init的List<Role>.
	 */
	public List<User> getAllUserWithRolesByCriteria() {
		Criteria c = createCriteria();
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return c.setFetchMode("roles", FetchMode.JOIN).list();
	}

	/**
	 * 使用NativeSql并装配成User Entity.
	 */
	public User getUserByNativeSql(String loginName) {
		String sql = "select * from USERS u where LOGIN_NAME=:loginName";
		return (User) getSession().createSQLQuery(sql).addEntity(User.class).setString("loginName", loginName)
				.uniqueResult();
	}
}
