/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Revision$
 * $Author$
 * $Date$
 */
package org.springside.examples.miniweb.functional;

import org.junit.BeforeClass;
import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目相关的SeleniumTestCase基类,定义服务器地址,浏览器类型及登陆函数. 
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	@BeforeClass
	public static void loginAsAdmin() {
		selenium.open("/mini-web/login.action");
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='登录']");
		waitPageLoad();
		assertTrue(selenium.isTextPresent("你好,admin."));
	}
}
