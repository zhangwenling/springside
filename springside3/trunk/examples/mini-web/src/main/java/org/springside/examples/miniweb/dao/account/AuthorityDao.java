package org.springside.examples.miniweb.dao.account;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
