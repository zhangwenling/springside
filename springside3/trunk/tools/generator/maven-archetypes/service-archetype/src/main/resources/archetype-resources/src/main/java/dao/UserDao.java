#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import ${package}.entity.user.User;
import org.springside.modules.orm.hibernate.HibernateDao;

public class UserDao extends HibernateDao<User, Long> {
	// 统一定义所有以用户为主体的HQL.
	public static final String AUTH_HQL = "select count(u) from User u where u.loginName=? and u.password=?";
}
