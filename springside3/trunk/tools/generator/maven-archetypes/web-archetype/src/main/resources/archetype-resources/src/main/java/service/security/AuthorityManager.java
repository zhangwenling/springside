#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.security.AuthorityDao;
import ${package}.entity.security.Authority;
import org.springside.modules.orm.hibernate.EntityManager;

/**
 * 授权管理类.
 * 
 * @author calvin
 */
@Service
@Transactional
public class AuthorityManager extends EntityManager<Authority, Long> {
	@Autowired
	AuthorityDao authorityDao;

	@Override
	protected AuthorityDao getEntityDao() {
		return authorityDao;
	}
}
