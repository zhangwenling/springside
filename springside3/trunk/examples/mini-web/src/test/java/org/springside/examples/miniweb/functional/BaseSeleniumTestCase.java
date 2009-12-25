package org.springside.examples.miniweb.functional;

import org.junit.BeforeClass;
import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目相关的SeleniumTestCase基类,定义服务器地址,浏览器类型及登陆函数. 
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {
	
	public static final String LOGIN_BUTTON = "//input[@value='登录']";
	public static final String SUBMIT_BUTTON = "//input[@value='提交']";
	public static final String SEARCH_BUTTON = "//input[@value='搜索']";

	/**
	 * 登录管理员角色.
	 */
	@BeforeClass
	public static void loginAsAdmin() {
		selenium.open("/mini-web/login.action");
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.click(LOGIN_BUTTON);
		waitPageLoad();
		assertTrue(selenium.isTextPresent("你好, admin."));
	}

	/**
	 * 点击菜单.
	 */
	protected void clickMenu(String menuName) {
		selenium.open("/mini-web");
		waitPageLoad();
		clickLink(menuName);
	}

	/**
	 * 点击链接.
	 */
	protected void clickLink(String linkText) {
		selenium.click("link=" + linkText);
		waitPageLoad();
	}
}
