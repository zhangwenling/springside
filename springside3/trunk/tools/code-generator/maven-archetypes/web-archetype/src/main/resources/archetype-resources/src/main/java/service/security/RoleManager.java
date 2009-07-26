#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.security.RoleDao;
import ${package}.entity.security.Role;
import ${package}.service.EntityManager;
import ${package}.service.ServiceException;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 角色管理类.
 * 
 * @author calvin
 */
@Service
@Transactional
public class RoleManager extends EntityManager<Role, Long> {

	@Autowired
	private RoleDao roleDao;

	@Override
	protected RoleDao getEntityDao() {
		return roleDao;
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

		roleDao.delete(id);
	}
}
