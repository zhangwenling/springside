package org.springside.examples.miniweb.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.ResourceDao;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.orm.hibernate.EntityManager;
import org.springside.modules.orm.hibernate.HibernateDao;

@Service
@Transactional
public class ResourceManager extends EntityManager<Resource, Long> {

	@Autowired
	ResourceDao resourceDao;

	@Override
	protected HibernateDao<Resource, Long> getEntityDao() {
		return resourceDao;
	}

}
