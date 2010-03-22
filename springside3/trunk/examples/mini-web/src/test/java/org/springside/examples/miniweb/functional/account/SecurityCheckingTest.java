package org.springside.examples.miniweb.functional.account;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.UserColumn;

/**
 * 系统安全控制的功能测试, 测试主要用户故事.
 * 
 * @author calvin
 */
public class SecurityCheckingTest extends BaseFunctionalTestCase {

	/**
	 * 测试匿名用户访问系统时的行为.
	 */
	@Test
	public void checkAnonymous() {
		//访问退出登录页面,退出之前的登录
		driver.get(BASE_URL + "/j_spring_security_logout");
		assertEquals("Mini-Web 登录页", driver.getTitle());

		//访问任意页面会跳转到登录界面
		driver.get(BASE_URL + "/account/user.action");
		assertEquals("Mini-Web 登录页", driver.getTitle());
	}

	/**
	 * 只有用户角色的操作员访问系统时的受限行为.
	 */
	@Test
	public void checkUserRole() {
		//访问退出登录页面,退出之前的登录
		driver.get(BASE_URL + "/j_spring_security_logout");
		assertEquals("Mini-Web 登录页", driver.getTitle());

		//登录普通用户
		driver.findElement(By.name("j_username")).sendKeys("user");
		driver.findElement(By.name("j_password")).sendKeys("user");
		driver.findElement(By.xpath(Gui.BUTTON_LOGIN)).click();

		//校验用户角色的操作单元格为空
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		assertEquals("查看", getContentTable(2, UserColumn.OPERATIONS.ordinal() + 1));
	}
}
