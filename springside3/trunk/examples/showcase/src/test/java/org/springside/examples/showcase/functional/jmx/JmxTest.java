package org.springside.examples.showcase.functional.jmx;

import src.test.java.org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * JMX演示页面内容简单测试.
 * 
 * @author calvin
 */
public class JmxTest extends BaseFunctionalTestCase {

	@Test
	public void test() {
		
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(BASE_URL+"/jmx/jmx-client.action");
		
		assertEquals("default",driver.findElement(By.name("nodeName")).getValue());

	}
}
