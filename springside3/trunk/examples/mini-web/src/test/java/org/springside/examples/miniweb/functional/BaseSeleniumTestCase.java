package org.springside.examples.miniweb.functional;

import org.junit.Before;
import org.springside.modules.test.selenium.SeleniumTestCase;

public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		initSelenium("http://localhost:8080", "*firefox");
		login();
	}

	protected void login() {
		selenium.open("/mini-web/login.action");
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='登录']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("你好,admin."));
	}
}
