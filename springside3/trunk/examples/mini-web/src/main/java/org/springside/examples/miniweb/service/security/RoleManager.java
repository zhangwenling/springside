package org.springside.examples.miniweb.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.RoleDao;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.hibernate.EntityManager;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 角色管理类.
 * 
 * 实现领域对象角色的所有业务管理函数.
 * 通过泛型声明继承DefaultEntityManager,默认拥有CRUD管理函数及HibernateDao<Role,Long> entityDao成员变量.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
@Service
@Transactional
public class RoleManager extends EntityManager<Role, Long> {

	@Autowired
	RoleDao roldDao;

	@Override
	protected HibernateDao<Role, Long> getEntityDao() {
		return roldDao;
	}

	/**
	 * 重载delte函数,演示异常处理及用户行为日志.
	 */
	@Override
	public void delete(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户角色", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		}

		roldDao.delete(id);
	}
}
