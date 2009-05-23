package org.springside.modules.orm.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.ReflectionUtils;

/**
 * Service层领域对象业务管理类基类,自带泛型HibernateDAO.
 * 为创建泛型DAO,需注入并持有Hibernate的sessionFactory.
 *
 * @param <T> 领域对象类型
 * @param <PK> 领域对象的主键类型
 * 
 * eg.
 * public class UserManager extends DefaultEntityManager<User, Long>{
 * }
 * 
 * @author calvin
 */
public class DefaultEntityManager<T, PK extends Serializable> extends EntityManager<T, PK> {

	protected HibernateDao<T, PK> entityDao;

	/**
	 * 通过注入的sessionFactory初始化自带的泛型DAO成员变量.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		Class<T> entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		entityDao = new HibernateDao<T, PK>(sessionFactory, entityClass);
	}

	/**
	 * 实现基类的entityDao提供函数,将自带的泛型DAO成员变量提供给基类的CRUD函数.
	 */
	@Override
	protected HibernateDao<T, PK> getEntityDao() {
		return entityDao;
	}
}
