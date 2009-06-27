package org.springside.examples.miniweb.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.AuthorityDao;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.service.EntityManager;

/**
 * 授权管理类.
 * 
 * @author calvin
 */
@Service
@Transactional
public class AuthorityManager extends EntityManager<Authority, Long> {
	@Autowired
	private AuthorityDao authorityDao;

	@Override
	protected AuthorityDao getEntityDao() {
		return authorityDao;
	}
}
