package org.springside.modules.integration.log;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.jmx.JmxClient;
import org.springside.modules.log.Log4jMBean;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml", "/log/applicationContext-log.xml" })
public class Log4jMBeanTest extends SpringContextTestCase {

	private JmxClient jmxClient;

	@Before
	public void setUp() throws Exception {
		jmxClient = new JmxClient("service:jmx:rmi:///jndi/rmi://localhost:1098/showcase");
	}

	@Test
	public void getRootLoggerLevel() {
		assertEquals("WARN", jmxClient.getAttribute(Log4jMBean.LOG4J_MBEAN_NAME, "RootLoggerLevel"));
	}

}
