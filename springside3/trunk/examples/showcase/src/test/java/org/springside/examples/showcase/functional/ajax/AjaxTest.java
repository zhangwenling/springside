package org.springside.examples.showcase.functional.ajax;

import org.junit.Test;
import org.springside.examples.showcase.functional.BaseSeleniumTestCase;

/**
 * 
 * 演示selenium.waitForConditon()测试Ajax.
 */
public class AjaxTest extends BaseSeleniumTestCase {

	@Test
	public void mashup() {
		clickMenu("Ajax演示");
		selenium.click("link=跨域Mashup演示");
		waitPageLoad();
		selenium.click("//input[@value='获取内容']");
		selenium.waitForCondition("selenium.isTextPresent('Hello World!')==true", "5000");
		assertTrue(selenium.isTextPresent("Hello World!"));
	}
}
