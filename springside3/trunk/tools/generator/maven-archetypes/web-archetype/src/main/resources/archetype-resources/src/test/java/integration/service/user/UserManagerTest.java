#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.user;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import ${package}.service.security.UserManager;
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

	public void testUserCRUD() {
		//保存用户并验证.
		User entity = new User();
		// 因为LoginName要求唯一性，因此添加random字段。
		entity.setLoginName("tester" + RandomStringUtils.randomAlphabetic(5));
		entity.setName("foo");
		entity.setEmail("foo@bar.com");
		entity.setPassword("foo");
		userManager.save(entity);
		flush();
		assertNotNull(entity.getId());

		//删除用户并验证
		userManager.delete(entity.getId());
		flush();
	}

	public void testUserAndRole() {
		//保存带角色的用户并验证
		User entity = new User();
		entity.setLoginName("tester" + RandomStringUtils.randomAlphabetic(5));

		Role role = new Role();
		role.setId(1L);
		entity.getRoles().add(role);
		userManager.save(entity);
		flush();
		entity = userManager.get(entity.getId());
		assertEquals(1, entity.getRoles().size());

		//删除用户的角色并验证
		entity.getRoles().remove(role);
		flush();
		entity = userManager.get(entity.getId());
		assertEquals(0, entity.getRoles().size());
	}

	//期望抛出ConstraintViolationException的异常.
	@ExpectedException(value = org.hibernate.exception.ConstraintViolationException.class)
	public void testSavenEntityNotUnique() {
		User entity = new User();
		entity.setLoginName("admin");
		userManager.save(entity);
		flush();
	}
}