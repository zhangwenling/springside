package org.springside.examples.miniservice.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * Service层领域对象业务管理类基类.
 * 使用HibernateDao<T,PK>进行业务对象的CRUD操作,子类需重载getEntityDao()函数提供该DAO.
 * 
 * @param <T> 领域对象类型
 * @param <PK> 领域对象的主键类型
 * 
 * eg.
 * public class UserManager extends EntityManager<User, Long>{
 * }
 * 
 * @author calvin
 */
@Transactional
public abstract class EntityManager<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 在子类实现此函数,为下面的CRUD操作提供DAO.
	 */
	protected abstract HibernateDao<T, PK> getEntityDao();

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
		return getEntityDao().find(page, filters);
	}

	public void save(final T entity) {
		getEntityDao().save(entity);
	}

	public void delete(final PK id) {
		getEntityDao().delete(id);
	}
}
