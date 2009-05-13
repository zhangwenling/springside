package org.springside.examples.miniweb.functional.security;

import org.springside.examples.miniweb.functional.SeleniumBaseTestCase;


public class UserManagerTest extends SeleniumBaseTestCase {

	public void testCRUD() {
		String loginName = createUser();
		editUser(loginName);
		deleteUser(loginName);
	}

	private String createUser() {
		selenium.open("/mini-web/security/user.action");
		selenium.click("link=增加新用户");
		selenium.waitForPageToLoad("30000");

		String loginName = "Tester" + random();
		selenium.type("loginName", loginName);
		selenium.type("name", loginName);
		selenium.type("password", "tester");
		selenium.type("passwordConfirm", "tester");
		selenium.click("checkedRoleIds-2");
		selenium.click("//input[@value='提交']");
		selenium.waitForPageToLoad("30000");

		assertTrue(selenium.isTextPresent("保存用户成功"));
		return loginName;
	}

	private void editUser(String loginName) {
		String newUserName = "newUserName";
		
		selenium.open("/mini-web/security/user.action");
		findUser(loginName);

		selenium.click("link=修改");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("name", newUserName);
		selenium.click("//input[@value='提交']");
		selenium.waitForPageToLoad("30000");

		assertTrue(selenium.isTextPresent("保存用户成功"));
		findUser(loginName);
		assertEquals(newUserName, selenium.getTable("//div[@id='listContent']/table.1.1"));
	}

	private void deleteUser(String loginName) {
		selenium.open("/mini-web/security/user.action");
		findUser(loginName);

		selenium.click("link=删除");
		selenium.waitForPageToLoad("30000");
		
		assertTrue(selenium.isTextPresent("删除用户成功"));
		findUser(loginName);
		assertTrue(selenium.isTextPresent("共0页"));
	}
		
	private void findUser(String userName) {
		selenium.type("filter_EQ_loginName", userName);
		selenium.click("//input[@value='搜索']");
		selenium.waitForPageToLoad("30000");
	}
}
