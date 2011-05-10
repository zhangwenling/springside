package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.GroupColumn;
import org.springside.modules.test.groups.Groups;

/**
 * 权限组管理的功能测试,测 试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class GroupManagerTest extends BaseFunctionalTestCase {

	/**
	 * 检验ListPage.
	 */
	@Groups(DAILY)
	@Test
	public void listPage() {
		selenium.clickTo(By.linkText(Gui.MENU_GROUP));
		WebElement table = selenium.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("管理员", selenium.getTable(table, 1, GroupColumn.NAME.ordinal()));
		assertEquals("查看用戶,修改用户,查看权限组,修改权限组", selenium.getTable(table, 1, GroupColumn.PERMISSIONS.ordinal()));
	}

	/**
	 * 创建公共测试权限组.
	 */
	@Groups(DAILY)
	@Test
	public void createGroup() {
		selenium.clickTo(By.linkText(Gui.MENU_GROUP));
		selenium.clickTo(By.linkText("增加新权限组"));

		//生成测试数据
		Group group = AccountData.getRandomGroupWithPermissions();

		//输入数据
		selenium.type(By.id("name"), group.getName());
		for (String permission : group.getPermissionList()) {
			selenium.check(By.id("checkedPermissions-" + permission));
		}
		selenium.clickTo(By.xpath(Gui.BUTTON_SUBMIT));

		//校验结果
		assertTrue(selenium.isTextPresent("保存权限组成功"));
		verifyGroup(group);
	}

	private void verifyGroup(Group group) {
		selenium.clickTo(By.linkText(Gui.MENU_GROUP));
		selenium.clickTo(By.id("editLink-" + group.getName()));

		assertEquals(group.getName(), selenium.getValue(By.id("name")));

		for (String permission : group.getPermissionList()) {
			assertTrue(selenium.isChecked(By.id("checkedPermissions-" + permission)));
		}

		List<String> uncheckPermissionList = ListUtils.subtract(AccountData.getDefaultPermissionList(),
				group.getPermissionList());
		for (String permission : uncheckPermissionList) {
			assertFalse(selenium.isChecked(By.id("checkedPermissions-" + permission)));
		}
	}
}
