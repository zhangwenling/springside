package org.springside.examples.miniweb.service.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.hibernate.DefaultEntityManager;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 演示派生DAO层子类的模式,由注入的UserDao封装数据库访问.
 *  
 * 通过泛型声明继承EntityManager,默认拥有CRUD管理方法.
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
	 * 重载delte函数,演示异常处理及用户行为日志.
	 */
	@Override
	public void delete(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}

		entityDao.delete(id);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于orgLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String orgLoginName) {
		return entityDao.isPropertyUnique("loginName", loginName, orgLoginName);
	}
}
