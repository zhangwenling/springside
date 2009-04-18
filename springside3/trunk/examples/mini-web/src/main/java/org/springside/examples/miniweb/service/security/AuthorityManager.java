package org.springside.examples.miniweb.service.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.modules.orm.hibernate.DefaultEntityManager;

/**
 * 授权管理类.
 * 
 * @author calvin
 */
@Service
@Transactional
public class AuthorityManager extends DefaultEntityManager<Authority, Long> {
}
