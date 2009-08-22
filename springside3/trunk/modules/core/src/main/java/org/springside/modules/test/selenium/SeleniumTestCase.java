/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.selenium;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Selenium Web集成测试用例基类.
 * 
 * @author calvin
 */
@RunWith(SeleniumTestRunner.class)
public class SeleniumTestCase {

	public static final String DEFAULT_URL = "http://localhost:8080";
	public static final String DEFAULT_BROWSER = "*chrome";
	public static final String DEFAULT_SELENIUM_HOST = "localhost";
	public static final String DEFAULT_SELENIUM_PORT = "4444";

	public static final String PROPERTY_FILE = "application.test.properties";
	public static final String PROPERTY_URL_NAME = "selenium.url";
	public static final String PROPERTY_BROWSER_NAME = "selenium.browser";
	public static final String PROPERTY_SELENIUM_HOST_NAME = "selenium.host";
	public static final String PROPERTY_SELENIUM_PORT_NAME = "selenium.port";

	public static final String WAIT_FOR_PAGE = "30000";

	protected static Selenium selenium;

	/**
	 * 初始化selenium client.
	 * 从application.test.properties中获取selenium连接参数,否则使用默认配置.
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		Properties p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
		String browser = p.getProperty(PROPERTY_BROWSER_NAME, DEFAULT_BROWSER);
		String url = p.getProperty(PROPERTY_URL_NAME, DEFAULT_URL);
		String host = p.getProperty(PROPERTY_SELENIUM_HOST_NAME, DEFAULT_SELENIUM_HOST);
		int port = Integer.valueOf(p.getProperty(PROPERTY_SELENIUM_PORT_NAME, DEFAULT_SELENIUM_PORT));

		selenium = new DefaultSelenium(host, port, browser, url);
		selenium.start();
		selenium.windowFocus();
		selenium.windowMaximize();
	}

	/**
	 * 关闭selenium变量.
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		selenium.stop();
	}

	/**
	 * 取得Selenium变量.
	 */
	public static Selenium getSelenium() {
		return selenium;
	}

	/**
	 * 等待页面载入.
	 */
	public static void waitPageLoad() {
		selenium.waitForPageToLoad(WAIT_FOR_PAGE);
	}

	// Assert 函数 //

	protected static void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	protected static void assertEquals(String message, Object expected, Object actual) {
		Assert.assertEquals(message, expected, actual);
	}

	protected static void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected static void assertTrue(String message, boolean condition) {
		Assert.assertTrue(message, condition);
	}

	protected static void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}

	protected static void assertFalse(String message, boolean condition) {
		Assert.assertFalse(message, condition);
	}

	protected static void assertNull(Object object) {
		Assert.assertNull(object);
	}

	protected static void assertNull(String message, Object object) {
		Assert.assertNull(message, object);
	}

	protected static void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}

	protected static void assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
	}
}
