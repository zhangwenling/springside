package org.springside.examples.showcase.integration;

import org.junit.BeforeClass;
import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目的Selenium测试基类,提供相关相关的公共函数与设置.
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	@BeforeClass
	public static void loginAsAdmin() {
		selenium.open("/showcase");
		waitPageLoad();
	}

	protected void clickLink(String linkText) {
		selenium.click("link=" + linkText);
		waitPageLoad();
	}
}
