package org.springside.examples.showcase.functional;

import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目的Selenium测试基类,提供相关相关的公共函数与设置.
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	protected void clickMenu(String menuName) {
		selenium.open("/showcase");
		waitPageLoad();
		selenium.click("link=" + menuName);
		waitPageLoad();
	}
}
