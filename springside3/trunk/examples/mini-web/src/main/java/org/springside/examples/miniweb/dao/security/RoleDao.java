package org.springside.examples.miniweb.dao.security;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class RoleDao extends HibernateDao<Role, Long> {
}
