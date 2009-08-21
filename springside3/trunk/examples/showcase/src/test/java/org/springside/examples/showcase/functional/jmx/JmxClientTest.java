package org.springside.examples.showcase.functional.jmx;

import org.junit.Test;
import org.springside.examples.showcase.functional.BaseSeleniumTestCase;

public class JmxClientTest extends BaseSeleniumTestCase {

	@Test
	public void general() {
		clickMenu("JMX演示");
		assertEquals("node1", selenium.getValue("nodeName"));
	}
}
