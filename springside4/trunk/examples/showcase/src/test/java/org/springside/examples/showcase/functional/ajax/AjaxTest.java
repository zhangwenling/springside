package org.springside.examples.showcase.functional.ajax;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.ThreadUtils;

/**
 * 测试Ajax Mashup.
 * 
 * @calvin
 */
public class AjaxTest extends BaseFunctionalTestCase {

	@Test
	public void mashup() {
		driver.get(BASE_URL);
		ThreadUtils.sleep(1000);
		driver.findElement(By.linkText("Web高级演示")).click();
		driver.findElement(By.linkText("跨域名Mashup演示")).click();

		driver.findElement(By.xpath("//input[@value='获取内容']")).click();
		SeleniumUtils.waitForDisplay(driver.findElement(By.id("mashupContent")), 5000);
		assertTrue(SeleniumUtils.isTextPresent(driver, "Hello World!"));
	}
}
