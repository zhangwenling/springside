package org.springside.modules.test.selenium;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTestCase {
	protected Selenium selenium;

	@Before
	public void setUp() throws Exception {
		setUpSelenium("http://localhost:8080/", "*chrome");
	}

	@After
	public void tearDown() throws Exception {
		selenium.close();
	}

	protected void setUpSelenium(String url, String explorer) {
		selenium = new DefaultSelenium("localhost", 4444, explorer, url);
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
