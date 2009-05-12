package org.springside.examples.miniweb.functional.security;

import org.springside.examples.miniweb.functional.SeleniumBaseTestCase;


public class UserManagerTest extends SeleniumBaseTestCase {
	private String loginName = "testUser1";
	private String newUserName = "newUserName";

	public void testCreateUser() {
		selenium.open("/mini-web/security/user.action");
		selenium.click("link=增加新用户");
		selenium.waitForPageToLoad("30000");

		selenium.type("loginName", loginName);
		selenium.type("name", loginName);
		selenium.type("password", "tester");
		selenium.type("passwordConfirm", "tester");
		selenium.click("checkedRoleIds-2");
		selenium.click("//input[@value='提交']");
		selenium.waitForPageToLoad("30000");

		assertTrue(selenium.isTextPresent("保存用户成功"));
	}

	public void testEditUser() {
		selenium.open("/mini-web/security/user.action");
		findTestUser();

		selenium.click("link=修改");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("name", newUserName);
		selenium.click("//input[@value='提交']");
		selenium.waitForPageToLoad("30000");

		assertTrue(selenium.isTextPresent("保存用户成功"));
		findTestUser();
		assertEquals(newUserName, selenium.getTable("//div[@id='listContent']/table.1.1"));
	}

	public void testDeleteUser() {
		selenium.open("/mini-web/security/user.action");
		findTestUser();

		selenium.click("link=删除");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("删除用户成功"));
		assertTrue(selenium.isTextPresent("共0页"));
	}

	private void findTestUser() {
		selenium.type("filter_EQ_loginName", loginName);
		selenium.click("//input[@value='搜索']");
		selenium.waitForPageToLoad("30000");
	}
}
