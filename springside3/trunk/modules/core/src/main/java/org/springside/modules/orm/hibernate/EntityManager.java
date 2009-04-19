package org.springside.modules.orm.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 默认的领域对象业务管理类基类,提供默认的泛型DAO成员变量.
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
@Transactional
public class DefaultEntityManager<T, PK extends Serializable> {

	protected HibernateDao<T, PK> entityDao;//默认的泛型DAO成员变量.

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	@SuppressWarnings("unchecked")
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		Class<T> entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		entityDao = new HibernateDao<T, PK>(sessionFactory, entityClass);
	}

	protected HibernateDao<T, PK> getEntityDao() {
		return entityDao;
	}

	// CRUD函数 //

	@Transactional(readOnly = true)
	public T get(final PK id) {
		return getEntityDao().get(id);
	}

	@Transactional(readOnly = true)
	public Page<T> getAll(final Page<T> page) {
		return getEntityDao().getAll(page);
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return getEntityDao().getAll();
	}

	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) {
		return getEntityDao().findByFilters(page, filters);
	}

	public void save(final T entity) {
		getEntityDao().save(entity);
	}

	public void delete(final PK id) {
		getEntityDao().delete(id);
	}
}
