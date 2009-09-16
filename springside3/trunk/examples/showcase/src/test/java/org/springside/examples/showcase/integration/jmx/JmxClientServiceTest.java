package org.springside.examples.showcase.integration.jmx;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.client.service.JmxClientService;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * JMXClientService的测试用例.
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml", "/jmx/applicationContext-jmx-client.xml" })
public class JmxClientServiceTest extends SpringContextTestCase {

	@Autowired
	private JmxClientService jmxClientService;

	@Test
	public void test() {
		jmxClientService.setNodeName("node1");
		assertEquals("node1", jmxClientService.getNodeName());

		jmxClientService.setNotificationMailEnabled(false);
		assertEquals(false, jmxClientService.isNotificationMailEnabled());

		assertEquals(0L, jmxClientService.getHibernateStatistics().getSessionOpenCount());
		assertEquals(0L, jmxClientService.getHibernateStatistics().getSessionCloseCount());
		jmxClientService.logSummary();
	}
}
