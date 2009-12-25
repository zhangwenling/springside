package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.data.DataUtils;
import org.springside.examples.miniweb.data.SecurityEntityData;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;

/**
 * 角色管理的功能测试,测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class RoleManagerTest extends BaseSeleniumTestCase {

	private static String commonRoleName = null;

	/**
	 * 创建公共测试角色.
	 */
	@Test
	public void createRole() {
		openOverviewPage();
		clickLink("增加新角色");

		Role role = SecurityEntityData.getRandomRole();

		selenium.type("name", role.getName());
		selenium.click("checkedAuthIds-1");
		selenium.click("checkedAuthIds-3");

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(role.getName(), selenium.getTable("contentTable.3.0"));
		assertEquals("浏览用户, 浏览角色", selenium.getTable("contentTable.3.1"));

		commonRoleName = role.getName();
	}

	/**
	 * 修改公共测试角色.
	 */
	@Test
	public void editRole() {
		assertCommonRoleExist();

		openOverviewPage();
		selenium.click("//table[@id='contentTable']/tbody/tr[4]/td[3]/a[1]");
		waitPageLoad();

		String newRoleName = DataUtils.random("Role");
		selenium.type("name", newRoleName);
		selenium.click("checkedAuthIds-2");
		selenium.click("checkedAuthIds-3");

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(newRoleName, selenium.getTable("contentTable.3.0"));
		assertEquals("浏览用户, 修改用户", selenium.getTable("contentTable.3.1"));
	}

	/**
	 * 根据用户名删除对象.
	 */
	@Test
	public void deleteRole() {
		assertCommonRoleExist();

		openOverviewPage();
		selenium.click("//table[@id='contentTable']/tbody/tr[4]/td[3]/a[2]");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除角色成功"));
		assertFalse(selenium.isTextPresent(commonRoleName));

		commonRoleName = null;
	}

	private void openOverviewPage() {
		clickMenu("角色列表");
	}

	/**
	 * 确保公共测试角色已初始化的工具函数.
	 */
	private void assertCommonRoleExist() {
		if (commonRoleName == null) {
			createRole();
		}
	}
}
