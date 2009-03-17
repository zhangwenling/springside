package org.springside.examples.miniweb.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.user.UserDao;
import org.springside.examples.miniweb.entity.user.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.hibernate.EntityManager;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 演示派生DAO层子类的模式,由注入的UserDao封装数据库访问.
 *  
 * 通过范型声明继承EntityManager,默认拥有CRUD管理方法.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends EntityManager<User, Long> {
	@Autowired
	private UserDao userDao;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected UserDao getEntityDao() {
		return userDao;
	}

	/**
	 * 重载delte函数,演示异常处理及用户行为日志.
	 */
	@Override
	public void delete(Long id) {
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
	@Transactional(readOnly = true)
	public List<User> getUsersByRole(String roleName) {
		return userDao.getUsersByRole(roleName);
	}
}
