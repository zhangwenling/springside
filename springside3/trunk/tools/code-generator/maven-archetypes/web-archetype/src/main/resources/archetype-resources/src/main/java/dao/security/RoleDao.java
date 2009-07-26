#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.security;

import java.util.List;

import org.springframework.stereotype.Repository;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	/**
	 * 重载函数,因为Role中没有建立与User的主动关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
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
