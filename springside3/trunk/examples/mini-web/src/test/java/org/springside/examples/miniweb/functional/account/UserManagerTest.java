package org.springside.examples.miniweb.functional.account;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Role;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.integration.account.UserManagerTest.UserColumn;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {

	private static User testUser = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		driver.findElement(By.linkText(Gui.MENU_USER)).click();

		assertEquals("admin", getContentTable(2, UserColumn.LOGIN_NAME.ordinal() + 1));
		assertEquals("管理员, 用户", getContentTable(2, UserColumn.ROLES.ordinal() + 1));
	}

	/**
	 * 创建公共测试用户.
	 */
	@Test
	public void createUser() throws InterruptedException {
		//打开新增用户页面
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		driver.findElement(By.linkText("增加新用户")).click();

		//生成待输入的测试用户数据
		User user = AccountData.getRandomUserWithRole();

		//输入数据
		driver.findElement(By.name("loginName")).sendKeys(user.getLoginName());
		driver.findElement(By.name("name")).sendKeys(user.getName());
		driver.findElement(By.name("password")).sendKeys(user.getPassword());
		driver.findElement(By.name("passwordConfirm")).sendKeys(user.getPassword());
		for (Role role : user.getRoleList()) {
			driver.findElement(By.id("checkedRoleIds-" + role.getId())).setSelected();
		}
		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();
		//校验结果
		assertTrue(StringUtils.contains(driver.getPageSource(), "保存用户成功"));

		//设置公共测试用户
		testUser = user;
	}
}
