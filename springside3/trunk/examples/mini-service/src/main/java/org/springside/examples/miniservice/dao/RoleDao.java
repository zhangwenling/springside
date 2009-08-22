package org.springside.examples.miniservice.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniservice.entity.user.Role;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 角色对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {
}
