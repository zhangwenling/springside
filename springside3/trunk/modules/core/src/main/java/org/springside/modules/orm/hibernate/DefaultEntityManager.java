package org.springside.modules.orm.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 默认的领域对象业务管理类基类,提供默认的范型DAO成员变量.
 * 
 * @param <T> 领域对象类型
 * @param <PK> 领域对象的主键类型
 * 
 * @author calvin
 */
@Transactional
public class DefaultEntityManager<T, PK extends Serializable> extends EntityManager<T, PK> {

	protected HibernateDao<T, PK> entityDao;//Default DAO.

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected HibernateDao<T, PK> getEntityDao() {
		return entityDao;
	}

	/**
	 * 通过注入的sessionFactory初始化Default DAO.
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		entityDao = new HibernateDao<T, PK>(sessionFactory, entityClass);
	}
}
