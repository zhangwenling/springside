package org.springside.modules.orm.hibernate;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;

/**
 * 领域对象的业务管理类基类.
 * 
 * 提供了领域对象的简单CRUD方法.
 *
 * @param <T> 领域对象类型
 * @param <PK> 领域对象的主键类型
 * 
 * @author calvin
 */
@Transactional
public abstract class EntityManager<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 在子类实现的回调函数,为EntityManager提供默认CRUD操作所需的DAO.
	 */
	protected abstract HibernateDao<T, PK> getEntityDao();

	// CRUD函数 //
	
	@Transactional(readOnly = true)
	public T get(PK id) {
		return getEntityDao().get(id);
	}

	@Transactional(readOnly = true)
	public Page<T> getAll(Page<T> page) {
		return getEntityDao().getAll(page);
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return getEntityDao().getAll();
	}

	@Transactional(readOnly = true)
	public Page<T> search(Page<T> page, List<PropertyFilter> filters) {
		return getEntityDao().findByFilters(page, filters);
	}

	public void save(T entity) {
		getEntityDao().save(entity);
	}

	public void delete(PK id) {
		getEntityDao().delete(id);
	}
}
