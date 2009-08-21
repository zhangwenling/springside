#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.functional.security;

import org.junit.Test;
import org.springside.modules.test.selenium.SeleniumTestCase;

public class SecurityTest extends SeleniumTestCase {

	@Test
	public void checkAnonymous() {
		//访问退出登录页面
		selenium.open("/${artifactId}/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//访问任意页面会跳转到登录界面
		selenium.open(UserManagerTest.USER_MENU);
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));
	}

	@Test
	public void checkUserRole() {
		//访问推出登录页面
		selenium.open("/${artifactId}/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//登录普通用户
		selenium.open("/${artifactId}/login.action");
		selenium.type("j_username", "user");
		selenium.type("j_password", "user");
		selenium.click("//input[@value='登录']");
		waitPageLoad();

		//校验用户角色的操作单元格为空
		selenium.open(UserManagerTest.USER_MENU);
		waitPageLoad();
		assertEquals("", selenium.getTable("listTable.1.4"));

		selenium.open(RoleManagerTest.ROLE_MENU);
		waitPageLoad();
		assertEquals("", selenium.getTable("listTable.1.2"));
	}
}
