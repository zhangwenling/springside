package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.data.SecurityData;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;

public class RoleManagerTest extends BaseSeleniumTestCase {
	
	private static final String ROLE_LINK = "/mini-web/security/role.action";
	
	/**
	 * 用户增删改操作查测试.
	 */
	@Test
	public void crudRole() {
		String roleName = createRole();
		editRole(roleName);
		deleteRole(roleName);
	}

	/**
	 * 创建用户,并返回创建的用户名.
	 */
	private String createRole() {
		selenium.open(ROLE_LINK);
		selenium.click("link=增加新角色");
		waitPageLoad();

		Role role = SecurityData.getRandomRole();

		selenium.type("name", role.getName());
		selenium.click("checkedAuthIds-1");
		selenium.click("checkedAuthIds-3");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(role.getName(), selenium.getTable("listTable.3.0"));
		assertEquals("浏览用户, 浏览角色", selenium.getTable("listTable.3.1"));
		return role.getName();
	}

	/**
	 * 根据用户名修改对象.
	 */
	private void editRole(String rolenName) {
		String newRoleName = "newRoleName";

		selenium.open(ROLE_LINK);
		selenium.click("//table[@id='listTable']/tbody/tr[4]/td[3]/a[1]");
		waitPageLoad();

		selenium.type("name", newRoleName);
		selenium.click("checkedAuthIds-2");
		selenium.click("checkedAuthIds-3");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(newRoleName, selenium.getTable("listTable.3.0"));
		assertEquals("浏览用户, 修改用户", selenium.getTable("listTable.3.1"));
	}

	/**
	 * 根据用户名删除对象.
	 */
	private void deleteRole(String roleName) {
		selenium.open(ROLE_LINK);
		selenium.click("//table[@id='listTable']/tbody/tr[4]/td[3]/a[2]");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除角色成功"));
	}
}
