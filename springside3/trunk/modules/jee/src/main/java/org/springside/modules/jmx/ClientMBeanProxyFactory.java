package org.springside.modules.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.StringUtils;

/**
 * JMX MBean客户端的代理工厂.
 * 
 * 负责连接远程JMX服务,并创建可操作远程MBean的本地MBean代理.
 * 因为Spring提供的JMX Client FactoryBean只能在applcationContext.xml中配置参数及初始化连接.
 * 所以重新进行了封装.
 * 
 * @author ben
 * @author calvin
 */
public class ClientMBeanProxyFactory {

	private JMXConnector connector;

	public ClientMBeanProxyFactory(final String serviceUrl) throws IOException {
		initConnector(serviceUrl, null, null);
	}

	public ClientMBeanProxyFactory(final String serviceUrl, final String userName, final String passwd)
			throws IOException {
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
	}

	/**
	 * 断开连接.
	 */
	public void close() throws IOException {
		connector.close();
	}

	/**
	 * 创建MBean代理. 
	 */
	public <T> T getMBeanProxy(final String mbeanName, final Class<T> mBeanInterface) throws IOException {
		try {
			ObjectName objectName = new ObjectName(mbeanName);
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			return JMX.newMBeanProxy(connection, objectName, mBeanInterface);
		} catch (MalformedObjectNameException e) {
			new IllegalArgumentException("mbeanName:" + mbeanName + "不正确", e);
		} catch (NullPointerException e) {
			new IllegalArgumentException("mbeanName为空");
		}
		return null;
	}
	
	/**
	 * 创建MXBean代理. 
	 */
	public <T> T getMXBeanProxy(final String mbeanName, final Class<T> mBeanInterface) throws IOException {
		try {
			ObjectName objectName = new ObjectName(mbeanName);
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			return JMX.newMXBeanProxy(connection, objectName, mBeanInterface);
		} catch (MalformedObjectNameException e) {
			new IllegalArgumentException("mbeanName:" + mbeanName + "不正确", e);
		} catch (NullPointerException e) {
			new IllegalArgumentException("mbeanName为空");
		}
		return null;
	}
}
