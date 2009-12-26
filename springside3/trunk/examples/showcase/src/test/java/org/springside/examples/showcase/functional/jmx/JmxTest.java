package org.springside.examples.showcase.functional.jmx;

import org.junit.Test;
import org.springside.examples.showcase.functional.BaseSeleniumTestCase;

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
