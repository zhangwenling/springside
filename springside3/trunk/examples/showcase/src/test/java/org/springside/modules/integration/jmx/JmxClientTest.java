package org.springside.modules.integration.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.client.service.JmxClientService;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClient;
import org.springside.modules.log.Log4jMBean;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * springside-jee中{@link JmxClient}的测试用例.
 * 
 * JMX相关用例使用相同的@ContextConfiguration以保证使用同一个ApplicationContext,避免JMX端口重复注册.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml", "/jmx/applicationContext-jmx-client.xml",
		"/log/applicationContext-log.xml" })
public class JmxClientTest extends SpringContextTestCase {

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
	public void accessMBeanAttribute() {
		serverConfigMbean.setNodeName("foo");
		assertEquals("foo", serverConfigMbean.getNodeName());
	}

	@Test
	public void getMBeanAttributeByReflection() {
		assertEquals(0L, jmxClient.getAttribute(JmxClientService.HIBERNATE_MBEAN_NAME, "SessionOpenCount"));
	}

	@Test
	public void invokeMBeanMethodByReflection() {
		//无参数
		jmxClient.inoke(JmxClientService.HIBERNATE_MBEAN_NAME, "logSummary");

		//以参数Class名描述函数签名		
		assertEquals("WARN", jmxClient.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerLevel",
				new String[] { "java.lang.String" }, new Object[] { "foo" }));

		//参数Class类描述函数签名
		assertEquals("WARN", jmxClient.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerLevel",
				new Class[] { String.class }, new Object[] { "foo" }));
	}
}
