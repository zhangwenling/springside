#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * Service层领域对象业务管理类基类,对所有Hibernate对象主动进行初始化.
 * 
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
		T entity = getEntityDao().get(id);
		getEntityDao().initObject(entity);
		return entity;
	}

	@Transactional(readOnly = true)
	public Page<T> getAll(final Page<T> page) {
		getEntityDao().getAll(page);
		getEntityDao().initObjects(page.getResult());
		return page;
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		List<T> list = getEntityDao().getAll();
		getEntityDao().initObjects(list);
		return list;
	}

	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) {
		getEntityDao().find(page, filters);
		getEntityDao().initObjects(page.getResult());
		return page;
	}

	public void save(final T entity) {
		getEntityDao().save(entity);
	}

	public void delete(final PK id) {
		getEntityDao().delete(id);
	}
}
