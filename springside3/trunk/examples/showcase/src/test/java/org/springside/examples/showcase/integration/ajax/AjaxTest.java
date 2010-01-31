package org.springside.examples.showcase.integration.ajax;

import org.junit.Test;
import org.springside.examples.showcase.integration.BaseSeleniumTestCase;

/**
 * 测试Ajax Mashup.
 * 
 * 演示Ajax测试常用的selenium.waitForConditon()
 * 
 * @calvin
 */
public class AjaxTest extends BaseSeleniumTestCase {

	@Test
	public void mashup() {
		clickLink("Ajax演示");
		clickLink("跨域Mashup演示");

		selenium.click("//input[@value='获取内容']");
		selenium.waitForCondition("selenium.isTextPresent('Hello World!')==true", "5000");
		assertTrue(selenium.isTextPresent("Hello World!"));
	}
}
