package org.springside.modules.test.selenium;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
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

	public static final String PROPERTY_FILE = "application.test.properties";
	public static final String PROPERTY_BROWSER_NAME = "selenium.browser";
	public static final String PROPERTY_URL_NAME = "selenium.url";
	public static final String PROPERTY_SELENIUM_HOST_NAME = "selenium.host";

	protected static Selenium selenium;

	/**
	 * 初始化默认的selenium变量.
	 * 从application.test.properties中获取selenium连接参数,否则使用默认配置.
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		Properties p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
		String browser = p.getProperty(PROPERTY_BROWSER_NAME, DEFAULT_BROWSER);
		String url = p.getProperty(PROPERTY_URL_NAME, DEFAULT_URL);
		String host = p.getProperty(PROPERTY_SELENIUM_HOST_NAME, DEFAULT_SELENIUM_HOST);

		selenium = new DefaultSelenium(host, 4444, browser, url);
		selenium.start();
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
	 * 产生包含数字和字母的随机字符串.
	 * 
	 * @param length 产生字符串长度
	 */
	protected static String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
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

	protected static void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}

	protected static void assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
	}
}
