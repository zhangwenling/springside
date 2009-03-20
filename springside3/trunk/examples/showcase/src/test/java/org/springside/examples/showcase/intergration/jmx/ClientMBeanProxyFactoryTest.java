package org.springside.examples.showcase.intergration.jmx;

import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.server.ConfiguratorMBean;
import org.springside.examples.showcase.jmx.server.MonitorMXBean;
import org.springside.modules.jmx.JmxClientFactory;
import org.springside.modules.test.junit38.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
//载入/jmx/applicationContext-jmx-server.xml和父类中定义的applicationContext.xml.
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class ClientMBeanProxyFactoryTest extends SpringContextTestCase {

	private JmxClientFactory mbeanFactory;

	private ConfiguratorMBean configuratorMbean;

	private MonitorMXBean monitorMXBean;

	@Override
	public void setUp() throws Exception {
		mbeanFactory = new JmxClientFactory("service:jmx:rmi:///jndi/rmi://localhost:1099/showcase");
		configuratorMbean = mbeanFactory.getMBeanProxy("org.springside.showcase:type=Configurator",
				ConfiguratorMBean.class);
		monitorMXBean = mbeanFactory.getMXBeanProxy("org.springside.showcase:type=Monitor", MonitorMXBean.class);
	}

	@Override
	public void tearDown() throws Exception {
		mbeanFactory.close();
	}

	public void testGetNodeName() throws Exception {
		assertEquals("node1", configuratorMbean.getNodeName());
	}

	public void testSetNodeName() throws Exception {
		configuratorMbean.setNodeName("foo");
		assertEquals("foo", configuratorMbean.getNodeName());
	}

	public void testGetQueryAllUsersHitCount() throws Exception {
		assertEquals(0, monitorMXBean.getServerStatistics().getQueryUserCount());
	}
}
