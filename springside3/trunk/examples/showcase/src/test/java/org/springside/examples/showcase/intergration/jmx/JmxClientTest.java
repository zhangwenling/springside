package org.springside.examples.showcase.intergration.jmx;

import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClient;
import org.springside.modules.test.junit38.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
//载入/jmx/applicationContext-jmx-server.xml和父类中定义的applicationContext.xml.
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class JmxClientTest extends SpringContextTestCase {

	private JmxClient jmxClient;

	private ServerConfigMBean serverConfigMbean;

	@Override
	public void setUp() throws Exception {
		jmxClient = new JmxClient("service:jmx:rmi:///jndi/rmi://localhost:1099/showcase");
		serverConfigMbean = jmxClient.getMBeanProxy("org.springside.showcase:type=Configurator",
				ServerConfigMBean.class);
	}

	@Override
	public void tearDown() throws Exception {
		jmxClient.close();
	}

	public void testGetMBeanAttribute() {
		assertEquals("node1", serverConfigMbean.getNodeName());
	}

	public void testSetMBeanAttribute() {
		serverConfigMbean.setNodeName("foo");
		assertEquals("foo", serverConfigMbean.getNodeName());
	}

	public void testGetMBeanAttributeByReflection() {
		assertEquals(0L, jmxClient.getAttribute("org.hibernate:type=Statistics", "SessionOpenCount"));
	}

	public void testInvokeMBeanMethodByReflection() {
		jmxClient.inoke("org.hibernate:type=Statistics", "logSummary");
	}
}
