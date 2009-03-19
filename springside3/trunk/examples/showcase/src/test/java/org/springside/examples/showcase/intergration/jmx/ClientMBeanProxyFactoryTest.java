package org.springside.examples.showcase.intergration.jmx;

import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.server.ServerMXBean;
import org.springside.modules.jmx.ClientMBeanProxyFactory;
import org.springside.modules.test.junit38.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
//载入/jmx/applicationContext-jmx-server.xml和父类中定义的applicationContext.xml.
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml" })
public class ClientMBeanProxyFactoryTest extends SpringContextTestCase {

	private ClientMBeanProxyFactory mbeanFactory;

	private ServerMXBean mbean;

	@Override
	public void setUp() throws Exception {
		mbeanFactory = new ClientMBeanProxyFactory("service:jmx:rmi:///jndi/rmi://localhost:1099/showcase");
		mbean = mbeanFactory.getMXBeanProxy("org.springside.showcase:name=Server", ServerMXBean.class);
	}

	@Override
	public void tearDown() throws Exception {
		mbeanFactory.close();
	}

	public void testGetNodeName() throws Exception {
		assertEquals("node1", mbean.getNodeName());
	}

	public void testSetNodeName() throws Exception {
		mbean.setNodeName("foo");
		assertEquals("foo", mbean.getNodeName());
	}

	public void testGetQueryAllUsersHitCount() throws Exception {
		assertEquals(0, mbean.getServerStatistics().getQueryUsersCount());
	}
}
