package org.springside.examples.showcase.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class UserDao extends HibernateDao<User, Long> {

	public static final String COUNT_USERS = "select count(u) from User u";
	public static final String DISABLE_USERS = "update User u set u.status='disabled' where id in(:ids)";

	/**
	 * 批量修改用户状态.
	 */
	public void disableUsers(List<Long> ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		batchExecute(UserDao.DISABLE_USERS, map);
	}

	/**
	 * 使用NativeSql并装配成User entity.
	 * 
	 * 因目前Native SQL预装载多对多关系还必须用hbm.xml来表达,暂时不演示.
	 */
	public User getUserByNativeSql(String loginName) {
		String sql = "select {u.*} from users u where u.LOGIN_NAME=:loginName";
		return (User) getSession().createSQLQuery(sql).addEntity("u", User.class).setString("loginName", loginName)
		.uniqueResult();
	}

	/**
	 * 使用 HQL 预加载lazy init的List<Role>,用DISTINCE_ROOT_ENTITY排除重复数据.
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUserWithRoleByDistinctHql() {
		return distinct(createQuery("from User u left join fetch u.roles")).list();
	}

	/**
	 * 使用 HQL 预加载lazy init的List<Role>, 用Set排除重复数据.
	 */
	public List<User> getAllUserWithRoleByHqlDistinctBySet() {
		return distinct(find("select u from User u left join fetch u.roles order by u.id"));
	}

	/**
	 * 使用Criteria 预加载lazy init的List<Role>, 用DISTINCE_ROOT_ENTITY排除重复数据.
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUserWithRolesByDistinctCriteria() {
		return distinct(createCriteria().setFetchMode("roles", FetchMode.JOIN)).list();
	}
}
