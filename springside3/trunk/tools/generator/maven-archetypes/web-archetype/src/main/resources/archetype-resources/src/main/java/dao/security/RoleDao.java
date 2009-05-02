#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.security;

import org.springframework.stereotype.Repository;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	/**
	 * 重载delte，在删除角色时删除与用户关联的中间表.
	 */
	@Override
	public void delete(Long id) {
		Role role = get(id);
		for (User user : role.getUsers()) {
			user.getRoles().remove(role);
		}
		super.delete(role);
	}
}
