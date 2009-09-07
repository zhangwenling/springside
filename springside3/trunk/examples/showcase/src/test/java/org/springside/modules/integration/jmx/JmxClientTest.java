package org.springside.modules.integration.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.client.service.JmxClientService;
import org.springside.examples.showcase.jmx.server.ServerConfig;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClient;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class JmxClientTest extends SpringContextTestCase {

	@Autowired
	private ServerConfig serverConfig;

	private JmxClient jmxClient;
	private ServerConfigMBean serverConfigMbean;

	@Before
	public void setUp() throws Exception {
		jmxClient = new JmxClient("service:jmx:rmi:///jndi/rmi://localhost:1098/showcase");
		serverConfigMbean = jmxClient.getMBeanProxy(JmxClientService.CONFIG_MBEAN_NAME, ServerConfigMBean.class);
	}

	@After
	public void tearDown() throws Exception {
		jmxClient.close();
	}

	@Test
	public void getMBeanAttribute() {
		assertEquals(serverConfig.getNodeName(), serverConfigMbean.getNodeName());
	}

	@Test
	public void setMBeanAttribute() {
		serverConfigMbean.setNodeName("foo");
		assertEquals("foo", serverConfigMbean.getNodeName());
	}

	@Test
	public void getMBeanAttributeByReflection() {
		assertEquals(0L, jmxClient.getAttribute(JmxClientService.HIBERNATE_MBEAN_NAME, "SessionOpenCount"));
	}

	@Test
	public void invokeMBeanMethodByReflection() {
		jmxClient.inoke(JmxClientService.HIBERNATE_MBEAN_NAME, "logSummary");
	}
}
