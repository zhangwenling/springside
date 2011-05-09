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
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 权限组管理的功能测试,测 试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class GroupManagerTest extends BaseFunctionalTestCase {

	/**
	 * 检验OverViewPage.
	 */
	@Groups(DAILY)
	@Test
	public void overviewPage() {
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();
		WebElement table = driver.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("管理员", SeleniumUtils.getTable(table, 1, GroupColumn.NAME.ordinal()));
		assertEquals("查看用戶,修改用户,查看权限组,修改权限组", SeleniumUtils.getTable(table, 1, GroupColumn.PERMISSIONS.ordinal()));
	}

	/**
	 * 创建公共测试权限组.
	 */
	@Groups(DAILY)
	@Test
	public void createGroup() {
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();
		driver.findElement(By.linkText("增加新权限组")).click();

		//生成测试数据
		Group group = AccountData.getRandomGroupWithPermissions();

		//输入数据
		SeleniumUtils.type(driver.findElement(By.id("name")), group.getName());
		for (String permission : group.getPermissionList()) {
			driver.findElement(By.id("checkedPermissions-" + permission)).setSelected();
		}
		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		//校验结果
		SeleniumUtils.waitForDisplay(driver.findElement(By.id("message")), 3000);
		assertTrue(SeleniumUtils.isTextPresent(driver, "保存权限组成功"));
		verifyGroup(group);

	}

	private void verifyGroup(Group group) {
		driver.findElement(By.id("editLink-" + group.getName())).click();

		assertEquals(group.getName(), driver.findElement(By.id("name")).getValue());

		for (String permission : group.getPermissionList()) {
			assertTrue(driver.findElement(By.id("checkedPermissions-" + permission)).isSelected());
		}

		List<String> uncheckPermissionList = ListUtils.subtract(AccountData.getDefaultPermissionList(),
				group.getPermissionList());
		for (String permission : uncheckPermissionList) {
			assertFalse(driver.findElement(By.id("checkedPermissions-" + permission)).isSelected());
		}
	}

}
