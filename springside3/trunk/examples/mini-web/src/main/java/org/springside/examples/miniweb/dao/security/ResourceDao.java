package org.springside.examples.miniweb.dao.security;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {
	public static final String HQL = "FROM Resources WHERE resourceType=? ORDER BY orderNum ASC";

	public List<Resource> findResource(String resourceType) {
		return this.find(HQL, resourceType);
	}
}
