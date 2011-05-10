package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.UserColumn;
import org.springside.modules.test.groups.Groups;

/**
 * 系统安全控制的功能测试, 测试主要用户故事.
 * 
 * @author calvin
 */
public class SecurityTest extends BaseFunctionalTestCase {

	/**
	 * 测试匿名用户访问系统时的行为.
	 */
	@Test
	@Groups(NIGHTLY)
	public void checkAnonymous() {
		//访问退出登录页面,退出之前的登录
		selenium.open(BASE_URL + "/logout.action");
		assertEquals("Mini-Web 登录页", selenium.getTitle());

		//访问任意页面会跳转到登录界面
		selenium.open(BASE_URL + "/account/user.action");
		assertEquals("Mini-Web 登录页", selenium.getTitle());
	}

	/**
	 * 只有用户权限组的操作员访问系统时的受限行为.
	 */
	@Test
	@Groups(NIGHTLY)
	public void checkUserPermission() {
		//访问退出登录页面,退出之前的登录
		selenium.open(BASE_URL + "/logout.action");
		assertEquals("Mini-Web 登录页", selenium.getTitle());

		//登录普通用户
		selenium.type(By.name("username"), "user");
		selenium.type(By.name("password"), "user");
		selenium.clickTo(By.xpath(Gui.BUTTON_LOGIN));

		//校验用户权限组的操作单元格为空
		selenium.clickTo(By.linkText(Gui.MENU_USER));
		assertEquals("查看",
				selenium.getTable(By.xpath("//table[@id='contentTable']"), 1, UserColumn.OPERATIONS.ordinal()));
	}
}
