package org.springside.examples.showcase.functional.ajax;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * 测试Ajax Mashup.
 * 
 * @calvin
 */
public class AjaxTest extends BaseFunctionalTestCase {

	@Test
	public void mashup() {
		selenium.open(BASE_URL);
		selenium.clickTo(By.linkText("Web高级演示"));
		selenium.clickTo(By.linkText("跨域名Mashup演示"));

		selenium.clickTo(By.xpath("//input[@value='获取内容']"));
		selenium.waitForDisplay(By.id("mashupContent"), 5000);
		assertEquals("Hello World!", selenium.getText(By.id("mashupContent")));
	}
}
