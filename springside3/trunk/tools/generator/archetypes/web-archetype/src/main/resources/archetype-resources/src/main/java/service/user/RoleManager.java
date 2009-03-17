#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.entity.user.Role;
import ${package}.service.ServiceException;
import org.springside.modules.orm.hibernate.DefaultEntityManager;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 角色管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 通过范型声明继承DefaultEntityManager,拥有默认的HibernateDao<Role,Long>及CRUD管理函数.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleManager extends DefaultEntityManager<Role, Long> {

	@Override
	public void delete(Long id) {
		//为演示异常处理及操作员行为日志而抛出的异常.
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户角色", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		}

		entityDao.delete(id);
	}
}
