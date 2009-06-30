package org.springside.examples.miniweb.dao.security;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	/**
	 * 重载函数，在删除角色时进行特殊处理删除与用户多对多关联的中间表.
	 */
	@Override
	public void delete(Long id) {
		Role role = get(id);
		//TODO: very slow
		for (User user : role.getUsers()) {
			user.getRoles().remove(role);
		}
		super.delete(role);
	}
}
