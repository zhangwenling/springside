package org.springside.examples.showcase.functional.common;

import org.junit.Test;
import org.springside.examples.showcase.functional.BaseSeleniumTestCase;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {

	@Test
	public void manageUser() {

		clickMenu("综合演示");
		selenium.click("//table[@id='contentTable']/tbody/tr[7]/td[7]/a");
		waitPageLoad();
		//修改用户需要登录管理员权限
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		//通用验证码,见web.xml中的设置.
		selenium.type("j_captcha", "54321");
		selenium.click("//input[@value='登录']");
		waitPageLoad();
		//提交修改
		selenium.click("//input[@value='提交']");
		selenium.waitForPageToLoad("60000");
		//重新进入用户修改页面, 检查最后修改者
		selenium.click("//table[@id='contentTable']/tbody/tr[7]/td[7]/a");
		waitPageLoad();
		assertTrue(selenium.isTextPresent("最后修改: admin"));
	}
}
