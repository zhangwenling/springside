package org.springside.modules.test.selenium;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Selenium Web集成测试用例基类.
 * 
 * @author calvin
 */
public class SeleniumTestCase {

	public static final String DEFAULT_HOST = "http://localhost:8080";
	public static final String DEFAULT_EXPLOER = "*firefox";

	public static Selenium selenium;

	/**
	 * 初始化默认的selenium变量.
	 * 连接默认的selenium server(地址为localhost:4444).
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, getExplorer(), getHost());
		selenium.start();
	}

	/**
	 * 关闭selenium变量.
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		selenium.close();
	}

	/**
	 * 获取待测Web应用的地址,默认为"http://localhost:8080", 可在子类重载.
	 */
	protected static String getHost() {
		return DEFAULT_HOST;
	}

	/**
	 * 获取测试使用的浏览器,默认为FireFox, 可在子类重载.
	 * 
	 * 可取值有*iexplore代表IE,*firefox代表Firefox.
	 */
	protected static String getExplorer() {
		return DEFAULT_EXPLOER;
	}

	/**
	 * 产生包含数字和字母的随机字符串.
	 * 
	 * @param length 产生字符串长度
	 */
	protected String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	// Assert 函数 //

	protected void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	protected void assertEquals(String message, Object expected, Object actual) {
		Assert.assertEquals(message, expected, actual);
	}

	protected void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected void assertTrue(String message, boolean condition) {
		Assert.assertTrue(message, condition);
	}

	protected void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}

	protected void assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
	}
}
