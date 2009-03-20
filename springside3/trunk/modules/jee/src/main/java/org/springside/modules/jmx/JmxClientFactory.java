package org.springside.modules.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.Attribute;
import javax.management.JMException;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * JMX客户端工厂.
 * 
 * 职责有：
 * 1.负责连接和关闭远程JMX服务
 * 2.创建可操作远程MBean的本地MBean代理.
 * 3.直接读取或设置远程MBean属性(无MBean的Class文件时使用).
 * 
 * @author ben
 * @author calvin
 */
public class JmxClientFactory {

	private JMXConnector connector;
	private MBeanServerConnection mbsc;

	public JmxClientFactory(final String serviceUrl) throws IOException {
		initConnector(serviceUrl, null, null);
	}

	public JmxClientFactory(final String serviceUrl, final String userName, final String passwd) throws IOException {
		initConnector(serviceUrl, userName, passwd);
	}

	/**
	 * 连接远程JMX服务.
	 */
	@SuppressWarnings("unchecked")
	private void initConnector(final String serviceUrl, final String userName, final String passwd) throws IOException {
		JMXServiceURL url = new JMXServiceURL(serviceUrl);

		boolean hasCredentlals = StringUtils.isNotBlank(userName);
		if (!hasCredentlals) {
			connector = JMXConnectorFactory.connect(url);
		} else {
			Map environment = new HashMap();
			environment.put(JMXConnector.CREDENTIALS, new String[] { userName, passwd });
			connector = JMXConnectorFactory.connect(url, environment);
		}

		mbsc = connector.getMBeanServerConnection();
	}

	/**
	 * 断开JMX连接.
	 */
	public void close() throws IOException {
		connector.close();
	}

	/**
	 * 创建MBean代理. 
	 */
	public <T> T getMBeanProxy(final String mbeanName, final Class<T> mBeanInterface) throws IOException {
		Assert.hasText(mbeanName, "mbeanName为空");
		try {
			ObjectName objectName = new ObjectName(mbeanName);
			return JMX.newMBeanProxy(mbsc, objectName, mBeanInterface);
		} catch (MalformedObjectNameException e) {
			throw new IllegalArgumentException("mbeanName:" + mbeanName + "不正确", e);
		}
	}

	/**
	 * 创建MXBean代理. 
	 */
	public <T> T getMXBeanProxy(final String mbeanName, final Class<T> mBeanInterface) throws IOException {
		Assert.hasText(mbeanName, "mbeanName为空");
		try {
			ObjectName objectName = new ObjectName(mbeanName);
			return JMX.newMXBeanProxy(mbsc, objectName, mBeanInterface);
		} catch (MalformedObjectNameException e) {
			throw new IllegalArgumentException("mbeanName:" + mbeanName + "不正确", e);
		}
	}

	/**
	 * 直接读取MBean属性(无MBean的Class文件时使用).
	 */
	public Object getAttribute(final String mbeanName, final String attributeName) {
		Assert.hasText(mbeanName, "mbeanName为空");
		Assert.hasText(attributeName, "attributeName为空");

		try {
			ObjectName objectName = new ObjectName(mbeanName);
			return mbsc.getAttribute(objectName, attributeName);
		} catch (JMException e) {
			throw new IllegalArgumentException("参数不正确", e);
		} catch (IOException e) {
			throw new RuntimeException("连接出错", e);
		}
	}

	/**
	 * 直接设置MBean属性(无MBean的Class文件时使用).
	 */
	public void setAttribute(final String mbeanName, final String attributeName, final Object value) {
		Assert.hasText(mbeanName, "mbeanName为空");
		Assert.hasText(attributeName, "attributeName为空");

		try {
			ObjectName objectName = new ObjectName(mbeanName);
			Attribute attribute = new Attribute(attributeName, value);
			mbsc.setAttribute(objectName, attribute);
		} catch (JMException e) {
			throw new IllegalArgumentException("参数不正确", e);
		} catch (IOException e) {
			throw new RuntimeException("连接出错", e);
		}
	}
}
