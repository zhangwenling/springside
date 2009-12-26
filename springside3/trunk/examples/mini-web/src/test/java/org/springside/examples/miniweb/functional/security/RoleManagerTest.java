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

	private static Role testRole = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		clickMenu(ROLE_MENU);

		assertEquals("管理员", getFromTable(1, OverviewColumn.NAME));
		assertEquals("浏览用户, 修改用户, 浏览角色, 修改角色", getFromTable(1, OverviewColumn.AUTHORITIES));
	}

	/**
	 * 创建公共测试角色.
	 */
	@Test
	public void createRole() {
		clickMenu(ROLE_MENU);
		clickLink("增加新角色");

		Role role = SecurityEntityData.getRandomRole();

		selenium.type("name", role.getName());
		//TODO
		selenium.click("checkedAuthIds-1");
		selenium.click("checkedAuthIds-3");

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(role.getName(), getFromTable(3, OverviewColumn.NAME));
		assertEquals("浏览用户, 浏览角色", getFromTable(3, OverviewColumn.AUTHORITIES));

		testRole = role;
	}

	/**
	 * 修改公共测试角色.
	 */
	@Test
	public void editRole() {
		ensureTestRoleExist();
		clickMenu(ROLE_MENU);
		//TODO
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
	 * 根据测试角色.
	 */
	@Test
	public void deleteRole() {
		ensureTestRoleExist();
		clickMenu(ROLE_MENU);

		selenium.click("//table[@id='contentTable']/tbody/tr[4]/td[3]/a[2]");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除角色成功"));
		assertFalse(selenium.isTextPresent(testRole.getName()));

		testRole = null;
	}

	enum OverviewColumn {
		NAME, AUTHORITIES
	}

	/**
	 * 取得Overview表格内容.
	 */
	private static String getFromTable(int rowIndex, OverviewColumn column) {
		return selenium.getTable("contentTable." + rowIndex + "." + column.ordinal());
	}

	/**
	 * 确保公共测试角色已初始化的工具函数.
	 */
	private void ensureTestRoleExist() {
		if (testRole == null) {
			createRole();
		}
	}
}
