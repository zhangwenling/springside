package org.springside.examples.miniweb.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.AuthorityDao;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.modules.orm.hibernate.EntityManager;

/**
 * 授权管理类.
 * 
 * 实现领域对象授权的所有业务管理函数.
 * 通过泛型声明继承DefaultEntityManager,默认拥有CRUD管理函数及HibernateDao<Authority,Long> entityDao成员变量.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AuthorityManager extends EntityManager<Authority, Long> {
	@Autowired
	private AuthorityDao authorityDao;

	@Override
	protected AuthorityDao getEntityDao() {
		return authorityDao;
	}
}
