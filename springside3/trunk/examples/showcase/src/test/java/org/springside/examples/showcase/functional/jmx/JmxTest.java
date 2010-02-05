package org.springside.examples.showcase.functional.jmx;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * JMX演示页面内容简单测试.
 * 
 * @author calvin
 */
public class JmxTest extends BaseFunctionalTestCase {

	@Test
	public void test() throws InterruptedException {

		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(BASE_URL + "/jmx/jmx-client.action");
		Thread.sleep(100000);
		assertEquals("default", driver.findElement(By.id("nodeName")).getText());
	}
}
