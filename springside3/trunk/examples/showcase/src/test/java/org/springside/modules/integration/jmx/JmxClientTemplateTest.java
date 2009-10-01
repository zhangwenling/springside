package org.springside.modules.integration.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.client.JmxClientService;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClientTemplate;
import org.springside.modules.log.Log4jMBean;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * springside-jee中{@link JmxClientTemplate}的测试用例.
 * 
 * JMX相关用例使用相同的@ContextConfiguration以保证使用同一个ApplicationContext,避免JMX端口重复注册.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml", "/jmx/applicationContext-jmx-client.xml",
		"/log/applicationContext-log.xml" })
public class JmxClientTemplateTest extends SpringContextTestCase {

	private JmxClientTemplate jmxClientTemplate;
	private ServerConfigMBean serverConfigMbean;

	@Before
	public void setUp() throws Exception {
		jmxClientTemplate = new JmxClientTemplate("service:jmx:rmi:///jndi/rmi://localhost:1098/showcase");
		serverConfigMbean = jmxClientTemplate.getMBeanProxy(JmxClientService.CONFIG_MBEAN_NAME, ServerConfigMBean.class);
	}

	@After
	public void tearDown() throws Exception {
		jmxClientTemplate.close();
	}

	@Test
	public void accessMBeanAttribute() {
		String oldName = serverConfigMbean.getNodeName();

		serverConfigMbean.setNodeName("foo");
		assertEquals("foo", serverConfigMbean.getNodeName());

		serverConfigMbean.setNodeName(oldName);
	}

	@Test
	public void accessMBeanAttributeByReflection() {
		String oldName = (String) jmxClientTemplate.getAttribute(JmxClientService.CONFIG_MBEAN_NAME, "NodeName");

		jmxClientTemplate.setAttribute(JmxClientService.CONFIG_MBEAN_NAME, "NodeName", "foo");
		assertEquals("foo", jmxClientTemplate.getAttribute(JmxClientService.CONFIG_MBEAN_NAME, "NodeName"));

		jmxClientTemplate.setAttribute(JmxClientService.CONFIG_MBEAN_NAME, "NodeName", oldName);
	}

	@Test
	public void invokeMBeanMethodByReflection() {
		//无参数
		jmxClientTemplate.inoke(JmxClientService.HIBERNATE_MBEAN_NAME, "logSummary");

		//以参数Class名描述函数签名		
		assertEquals("WARN", jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerLevel",
				new String[] { "java.lang.String" }, new Object[] { "foo" }));

		//参数Class类描述函数签名
		assertEquals("WARN", jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerLevel",
				new Class[] { String.class }, new Object[] { "foo" }));
	}
}
