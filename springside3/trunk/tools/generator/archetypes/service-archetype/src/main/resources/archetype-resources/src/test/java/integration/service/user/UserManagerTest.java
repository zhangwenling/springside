#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.user;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import ${package}.entity.user.Role;
import ${package}.entity.user.User;
import ${package}.service.user.UserManager;
import org.springside.modules.test.junit38.SpringTransactionalTestCase;

/**
 * UserManager的集成测试用例.
 * 
 * 调用实际的DAO类进行操作,默认在操作后进行回滚.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTransactionalTestCase {

	@Autowired
	private UserManager userManager;

	public void testSaveUser() {

		User entity = new User();
		// 因为LoginName要求唯一性，因此添加random字段。
		entity.setLoginName("tester" + RandomStringUtils.randomAlphabetic(5));
		entity.setName("foo");
		entity.setEmail("foo@bar.com");
		entity.setPassword("foo");
		Role role = new Role();
		role.setId(1L);
		entity.getRoles().add(role);
		userManager.save(entity);
		flush();
		assertNotNull(entity.getId());
	}

	@ExpectedException(value = org.hibernate.exception.ConstraintViolationException.class)
	public void testSavenNotUniqueUser() {
		User entity = new User();
		entity.setLoginName("admin");
		userManager.save(entity);
		flush();
	}

	public void testAuthUser() {
		assertTrue(userManager.authenticate("admin", "admin"));
		assertFalse(userManager.authenticate("admin", ""));
	}
}
