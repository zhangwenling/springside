package org.springside.examples.miniweb.dao.security;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {
	public static final String QUERY_BY_URL_TYPE = "FROM Resource WHERE resourceType=? ORDER BY orderNum ASC";
}
