package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.Role;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 角色对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {
}
