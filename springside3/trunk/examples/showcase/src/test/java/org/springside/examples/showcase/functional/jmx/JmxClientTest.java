package org.springside.examples.showcase.functional.jmx;

import org.junit.Test;
import org.springside.examples.showcase.functional.BaseSeleniumTestCase;

/**
 * JMX演示页面内容简单测试.
 * 
 * @author calvin
 */
public class JmxClientTest extends BaseSeleniumTestCase {

	@Test
	public void general() {
		clickMenu("JMX演示");
		assertEquals("default", selenium.getValue("nodeName"));
	}
}
