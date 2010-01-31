package org.springside.examples.showcase.integration.jmx;

import org.junit.Test;
import org.springside.examples.showcase.integration.BaseSeleniumTestCase;

/**
 * JMX演示页面内容简单测试.
 * 
 * @author calvin
 */
public class JmxTest extends BaseSeleniumTestCase {

	@Test
	public void test() {
		clickLink("JMX演示");
		assertEquals("default", selenium.getValue("nodeName"));
	}
}
