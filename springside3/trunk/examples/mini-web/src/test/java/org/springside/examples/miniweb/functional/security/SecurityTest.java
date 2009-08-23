package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;

public class SecurityTest extends BaseSeleniumTestCase {

	@Test
	public void checkAnonymous() {
		//访问退出登录页面
		selenium.open("/mini-web/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//访问任意页面会跳转到登录界面
		selenium.open("/mini-web/security/user.action");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));
	}

	@Test
	public void checkUserRole() {
		//访问退出登录页面
		selenium.open("/mini-web/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//登录普通用户
		selenium.type("j_username", "user");
		selenium.type("j_password", "user");
		selenium.click("//input[@value='登录']");
		waitPageLoad();

		//校验用户角色的操作单元格为空
		clickMenu("帐号列表");
		waitPageLoad();
		assertEquals("查看", selenium.getTable("listTable.1.4"));

		clickMenu("角色列表");
		waitPageLoad();
		assertEquals("查看", selenium.getTable("listTable.1.2"));
	}
}
