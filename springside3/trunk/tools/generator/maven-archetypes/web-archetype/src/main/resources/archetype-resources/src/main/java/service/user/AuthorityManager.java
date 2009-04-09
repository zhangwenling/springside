#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.entity.user.Authority;
import org.springside.modules.orm.hibernate.DefaultEntityManager;

/**
 * 授权管理类.
 * 
 * 实现领域对象授权的所有业务管理函数.
 * 通过范型声明继承DefaultEntityManager,拥有默认的HibernateDao<Authority,Long>及CRUD管理函数.
 * @author calvin
 */
@Service
@Transactional
public class AuthorityManager extends DefaultEntityManager<Authority, Long> {
}
