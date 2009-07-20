/**
 * $Revision$
 * $Author$
 * $Date$
 */
package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;
import org.springside.modules.test.groups.Groups;

public class UserManagerTest extends BaseSeleniumTestCase {

	@Test
	public void crudUser() {
		String loginName = createUser();
		editUser(loginName);
		deleteUser(loginName);
	}

	@Test
	@Groups("extension")
	public void validateUser() {
		selenium.open("/mini-web/security/user.action");
		selenium.click("link=增加新用户");
		waitPageLoad();

		selenium.type("loginName", "admin");
		selenium.type("name", "");
		selenium.type("password", "a");
		selenium.type("passwordConfirm", "abc");
		selenium.type("email", "abc.com");
		selenium.click("//input[@value='提交']");

		assertTrue(selenium.isTextPresent("用户登录名已存在"));
		assertEquals("必选字段", selenium.getTable("//form[@id='inputForm']/table.1.1"));
		assertEquals("请输入一个长度最少是 3 的字符串", selenium.getTable("//form[@id='inputForm']/table.2.1"));
		assertEquals("输入与上面相同的密码", selenium.getTable("//form[@id='inputForm']/table.3.1"));
		assertEquals("请输入正确格式的电子邮件", selenium.getTable("//form[@id='inputForm']/table.4.1"));

	}

	private String createUser() {
		selenium.open("/mini-web/security/user.action");
		selenium.click("link=增加新用户");
		waitPageLoad();

		String loginName = "Tester" + randomString(5);
		selenium.type("loginName", loginName);
		selenium.type("name", loginName);
		selenium.type("password", "tester");
		selenium.type("passwordConfirm", "tester");
		selenium.click("checkedRoleIds-2");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存用户成功"));
		return loginName;
	}

	private void editUser(String loginName) {
		String newUserName = "newUserName";

		selenium.open("/mini-web/security/user.action");
		findUser(loginName);

		selenium.click("link=修改");
		waitPageLoad();

		selenium.type("name", newUserName);
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存用户成功"));
		findUser(loginName);
		assertEquals(newUserName, selenium.getTable("//div[@id='listContent']/table.1.1"));
	}

	private void deleteUser(String loginName) {
		selenium.open("/mini-web/security/user.action");
		findUser(loginName);

		selenium.click("link=删除");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除用户成功"));
		findUser(loginName);
		assertTrue(selenium.isTextPresent("共0页"));
	}

	private void findUser(String userName) {
		selenium.type("filter_EQ_loginName", userName);
		selenium.click("//input[@value='搜索']");
		waitPageLoad();
	}
}
