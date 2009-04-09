#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;
import ${package}.entity.user.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 继承于HibernateDao的范型DAO子类.
 * 
 * 用于集中定义HQL,封装DAO细节,在Service间解耦并共享DAO操作.
 * 
 * @author calvin
 */
//Spring DAO Bean的标识
@Repository
public class UserDao extends HibernateDao<User, Long> {

	// 统一定义所有用户的HQL.
	private static final String QUERY_BY_ROLE_HQL = "select user from User user join user.roles as role where role.name=?";

	public User loadByLoginName(String userName) {
		return findUniqueByProperty("loginName", userName);
	}

	/**
	 * 查找拥有指定角色的用户.
	 */
	public List<User> getUsersByRole(String roleName) {
		return find(QUERY_BY_ROLE_HQL, roleName);
	}
}
