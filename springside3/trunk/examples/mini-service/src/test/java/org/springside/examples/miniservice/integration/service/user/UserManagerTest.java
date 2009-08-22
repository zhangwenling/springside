package org.springside.examples.miniservice.integration.service.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.modules.test.spring.SpringTxTestCase;

public class UserManagerTest extends SpringTxTestCase {

	@Autowired
	private UserManager userManager;

	/**
	 * 用户获取测试.
	 * 
	 * 将用户从session中断开,特别测试用户及其关联角色的初始化情况.
	 */
	@Test
	public void getAllUser() {
		List<User> userList = userManager.getAllUser();
		assertEquals(6, userList.size());
		
		evict(userList.get(0));
		assertTrue(userList.get(0).getRoles().size() > 0);
	}
	
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
