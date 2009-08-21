package org.springside.examples.showcase.functional;

import org.springside.modules.test.selenium.SeleniumTestCase;

public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	protected void clickMenu(String menuName) {
		selenium.open("/showcase");
		waitPageLoad();
		selenium.click("link=" + menuName);
		waitPageLoad();
	}
}
