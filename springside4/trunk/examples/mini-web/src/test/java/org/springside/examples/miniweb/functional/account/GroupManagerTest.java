package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.Permission;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.GroupColumn;
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 權限組管理的功能测试,测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class GroupManagerTest extends BaseFunctionalTestCase {

	private static Group testGroup = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();
		WebElement table = driver.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("管理员", SeleniumUtils.getTable(table, 1, GroupColumn.NAME.ordinal()));
		assertEquals("浏览用户, 修改用户, 浏览權限組, 修改權限組", SeleniumUtils.getTable(table, 1, GroupColumn.PERMISSIONS.ordinal()));
	}

	/**
	 * 创建公共测试權限組.
	 */
	@Test
	public void createGroup() {
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();
		driver.findElement(By.linkText("增加新權限組")).click();

		//生成测试数据
		Group group = AccountData.getRandomGroupWithPermissions();

		//输入数据
		SeleniumUtils.type(driver.findElement(By.id("name")), group.getName());
		for (String permission : group.getPermissionList()) {
			driver.findElement(By.id("checkedAuthIds-" + Permission.parse(permission).name())).setSelected();
		}
		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		//校验结果
		assertTrue(SeleniumUtils.isTextPresent(driver, "保存權限組成功"));
		verifyGroup(group);

		//设置公共测试權限組
		testGroup = group;
	}

	/**
	 * 修改公共测试權限組.
	 */
	@Test
	public void editGroup() {
		ensureTestGroupExist();
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();
		driver.findElement(By.id("editLink-" + testGroup.getName())).click();

		testGroup.setName(DataUtils.randomName("Group"));
		SeleniumUtils.type(driver.findElement(By.id("name")), testGroup.getName());

		for (String permission : testGroup.getPermissionList()) {
			SeleniumUtils.uncheck(driver.findElement(By.id("checkedAuthIds-" + authority.getId())));
		}
		testGroup.getPermissionList().clear();

		List<String> permissionList = AccountData.getRandomDefaultPermissionList();
		for (String permission : permissionList) {
			driver.findElement(By.id("checkedAuthIds-" + authority.getId())).setSelected();
		}
		testGroup.getPermissionList().addAll(permissionList);

		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, "保存權限組成功"));
		verifyGroup(testGroup);
	}

	/**
	 * 删除测试權限組.
	 */
	@Test
	public void deleteGroup() {
		ensureTestGroupExist();
		driver.findElement(By.linkText(Gui.MENU_GROUP)).click();

		driver.findElement(By.id("deleteLink-" + testGroup.getName())).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, "删除權限組成功"));
		assertFalse(SeleniumUtils.isTextPresent(driver, testGroup.getName()));

		testGroup = null;
	}

	private void verifyGroup(Group group) {
		driver.findElement(By.id("editLink-" + group.getName())).click();

		assertEquals(group.getName(), driver.findElement(By.id("name")).getValue());

		for (String permission : group.getPermissionList()) {
			assertTrue(driver.findElement(By.id("checkedAuthIds-" + authority.getId())).isSelected());
		}

		List<String> uncheckPermissionList = ListUtils.subtract(AccountData.getDefaultPermissionList(),
				group.getPermissionList());
		for (String permission : uncheckPermissionList) {
			assertFalse(driver.findElement(By.id("checkedAuthIds-" + authority.getId())).isSelected());
		}
	}

	/**
	 * 确保公共测试權限組已初始化的工具函数.
	 */
	private void ensureTestGroupExist() {
		if (testGroup == null) {
			createGroup();
		}
	}
}
