#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.service.user.UserManager;
import org.springside.modules.test.spring.SpringTxTestCase;

public class UserManagerTest extends SpringTxTestCase {

	@Autowired
	private UserManager userManager;

	/**
	 * 用户认证测试.
	 * 分别测试正确的用户与正确,空,错误的密码三种情况.
	 */
	@Test
	public void authUser() {
		assertEquals(true, userManager.authenticate("admin", "admin"));
		assertEquals(false, userManager.authenticate("admin", ""));
		assertEquals(false, userManager.authenticate("admin", "errorPasswd"));
	}
}
