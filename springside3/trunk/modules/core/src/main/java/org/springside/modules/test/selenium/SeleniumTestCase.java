package org.springside.modules.test.selenium;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Selenium Web集成测试用例基类.
 * 
 * @author calvin
 */
public class SeleniumTestCase {

	protected Selenium selenium;

	protected String getHost() {
		return "http://localhost:8080";
	}

	protected String getExplorer() {
		return "*firefox";
	}

	/**
	 * 初始化默认的selenium变量.
	 * 
	 * selenium server的地址为localhost:4444, 待测应用基础路径为"http://localhost:8080/", 浏览器为Firefox.
	 */
	@Before
	public void setUp() throws Exception {
		initSelenium(getHost(), getExplorer());
	}

	/**
	 * 关闭selenium变量.
	 */
	@After
	public void tearDown() throws Exception {
		selenium.close();
	}

	/**
	 * 初始化selenium变量.
	 * 
	 * 连接默认的selenium server(地址为localhost:4444),根据参数初始化selenium变量.
	 * 
	 * @param siteUrl 待测Web应用的基本路径, 如"http://localhost:8080/"
	 * @param browser 浏览器选定, 可取值有*iexplore代表IE,*firefox代表Firefox.
	 */
	protected void initSelenium(String siteUrl, String browser) {
		selenium = new DefaultSelenium("localhost", 4444, browser, siteUrl);
		selenium.start();
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
