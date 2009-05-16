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
		selenium = new DefaultSelenium("localhost", 4444, url, explorer);
	}

	protected void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	/**
	 * @see #randomString(int)
	 * 
	 * 长度默认为5.
	 */
	protected String randomString() {
		return randomString(5);
	}

	/**
	 * 产生包含数字和字母的随机字符串.
	 * 
	 * @param length 产生字符串长度
	 */
	protected String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
}
