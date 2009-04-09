#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.user.UserDao;
import ${package}.entity.user.User;
import ${package}.service.ServiceException;
import org.springside.modules.orm.hibernate.EntityManager;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 通过范型声明继承EntityManager,默认拥有CRUD管理方法,通过注入的UserDao访问数据库.
 * 演示使用派生DAO子类的模式,数据库访问由UserDao封装.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends EntityManager<User, Long> {
	@Autowired
	private UserDao userDao;

	@Override
	protected UserDao getEntityDao() {
		return userDao;
	}

	@Override
	public void delete(Long id) {

		//为演示异常处理及用户行为日志而抛出的异常.
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}

		userDao.delete(id);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于orgLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String orgLoginName) {
		return userDao.isPropertyUnique("loginName", loginName, orgLoginName);
	}

	/**
	 * 查找拥有指定角色的用户.
	 */
	public List<User> getUsersByRole(String roleName) {
		return userDao.getUsersByRole(roleName);
	}
}
