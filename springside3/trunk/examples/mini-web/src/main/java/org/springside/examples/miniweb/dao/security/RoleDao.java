package org.springside.examples.miniweb.dao.security;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {
	
	/**
	 * 重载delte，在删除角色时删除与用户关联的中间表.
	 */
	@Override
	public void delete(Long id) {
		Role role = this.get(id);
		for (User user : role.getUsers()) {
			user.getRoles().remove(role);
		}
		super.delete(role);			
	}
}
