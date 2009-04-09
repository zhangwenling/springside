package org.springside.examples.showcase.intergration.jmx;

import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.server.ConfiguratorMBean;
import org.springside.modules.jmx.MBeanClientFactory;
import org.springside.modules.test.junit38.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
//载入/jmx/applicationContext-jmx-server.xml和父类中定义的applicationContext.xml.
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class JMXClientFactoryTest extends SpringContextTestCase {

	private MBeanClientFactory jmxClientFactory;

	private ConfiguratorMBean configuratorMbean;

	@Override
	public void setUp() throws Exception {
		jmxClientFactory = new MBeanClientFactory("service:jmx:rmi:///jndi/rmi://localhost:1099/showcase");
		configuratorMbean = jmxClientFactory.getMBeanProxy("org.springside.showcase:type=Configurator",
				ConfiguratorMBean.class);
	}

	@Override
	public void tearDown() throws Exception {
		jmxClientFactory.close();
	}

	public void testGetMBeanAttribute() throws Exception {
		assertEquals("node1", configuratorMbean.getNodeName());
	}

	public void testSetMBeanAttribute() throws Exception {
		configuratorMbean.setNodeName("foo");
		assertEquals("foo", configuratorMbean.getNodeName());
	}

	public void testGetMBeanAttributeByReflection() throws Exception {
		assertEquals(0L, jmxClientFactory.getAttribute("org.hibernate:type=Statistics", "SessionOpenCount"));
	}

	public void testInvokeMBeanMethodByReflection() throws Exception {
		jmxClientFactory.inoke("org.hibernate:type=Statistics", "logSummary");
	}
}
