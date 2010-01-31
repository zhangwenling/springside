package org.springside.examples.showcase.integration.common;

import org.junit.Test;
import org.springside.examples.showcase.integration.BaseSeleniumTestCase;

/**
 * 用户管理的功能测试.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {
	/** 通用验证码,见web.xml中的设置.*/
	private static final String AUTOPASS_CAPTCHA = "RPWT54321";

	@Test
	public void editUser() {

		clickLink("综合演示");
		selenium.click("editLink-2");
		waitPageLoad();

		//修改用户需要登录管理员权限
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.type("j_captcha", AUTOPASS_CAPTCHA);
		selenium.click("//input[@value='登录']");
		waitPageLoad();
		//提交修改
		selenium.click("//input[@value='提交']");
		waitPageLoad();
		//重新进入用户修改页面, 检查最后修改者
		selenium.click("editLink-2");
		waitPageLoad();
		assertTrue(selenium.isTextPresent("最后修改: admin"));
	}
}
