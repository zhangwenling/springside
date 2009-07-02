package org.springside.examples.miniweb.dao.security;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	/**
	 * 重载函数，在删除角色时进行删除与用户多对多关联的中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Role role = get(id);
		List<User> users = createQuery("select u from User u  left join u.roles r where r.id=?", role.getId()).list();
		for (User u : users) {
			u.getRoles().remove(role);
		}
		super.delete(role);
	}
}
