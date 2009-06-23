#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.entity.user.User;
import org.springside.modules.orm.hibernate.DefaultEntityManager;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 演示没有DAO层子类的模式,通过默认的DAO基类完成数据访问,HQL语句统一定义于领域对象.
 * 
 * 通过泛型声明继承DefaultEntityManager,默认拥有CRUD管理函数及HibernateDao<User,Long> entityDao成员变量.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends DefaultEntityManager<User, Long> {

	/**
	 * 验证用户名密码. 
	 * 
	 * @return 验证通过时返回true.用户名或密码错误时返回false.
	 */
	@Transactional(readOnly = true)
	public boolean authenticate(String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password))
			return false;
		return (entityDao.findLong(User.AUTH_HQL, loginName, password) == 1);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUserWithRoles() {
		List<User> users = getAll();

		for (User user : users) {
			entityDao.initCollection(user.getRoles());
		}
		
		return users;
	}

}
