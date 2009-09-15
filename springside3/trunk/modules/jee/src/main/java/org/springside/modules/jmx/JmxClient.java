/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.Attribute;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
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
 * 1.负责连接和关闭远程JMX Server,并持有连接.
 * 2.创建可操作远程MBean的本地MBean代理.
 * 3.按属性名直接读取或设置远程MBean属性(无MBean的Class文件时使用).
 * 
 * @author ben
 * @author calvin
 */
public class JmxClient {

	private JMXConnector connector;
	private MBeanServerConnection mbsc;
	private AtomicBoolean connected = new AtomicBoolean(false);

	public JmxClient(final String serviceUrl) throws IOException {
		initConnector(serviceUrl, null, null);
	}

	public JmxClient(final String serviceUrl, final String userName, final String passwd) throws IOException {
		initConnector(serviceUrl, userName, passwd);
	}

	/**
	 * 连接远程JMX Server.
	 */
	@SuppressWarnings("unchecked")
	private void initConnector(final String serviceUrl, final String userName, final String passwd) throws IOException {
		JMXServiceURL url = new JMXServiceURL(serviceUrl);

		boolean hasCredentlals = StringUtils.isNotBlank(userName);
		if (hasCredentlals) {
			Map environment = new HashMap();
			environment.put(JMXConnector.CREDENTIALS, new String[] { userName, passwd });
			connector = JMXConnectorFactory.connect(url, environment);
		} else {
			connector = JMXConnectorFactory.connect(url);
		}

		mbsc = connector.getMBeanServerConnection();
		connected.set(true);
	}

	/**
	 * 断开JMX连接.
	 */
	public void close() throws IOException {
		connector.close();
		connected.set(false);
	}

	/**
	 * 创建标准MBean代理.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMBeanProxy(final String mbeanName, final Class<T> mBeanInterface) {
		Assert.hasText(mbeanName, "mbeanName不能为空");
		assertConnected();

		ObjectName objectName = buildObjectName(mbeanName);
		return (T) MBeanServerInvocationHandler.newProxyInstance(mbsc, objectName, mBeanInterface, false);
	}

	/**
	 * 按属性名直接读取MBean属性(无MBean的Class文件时使用).
	 */
	public Object getAttribute(final String mbeanName, final String attributeName) {
		Assert.hasText(mbeanName, "mbeanName不能为空");
		Assert.hasText(attributeName, "attributeName不能为空");
		assertConnected();

		try {
			ObjectName objectName = buildObjectName(mbeanName);
			return mbsc.getAttribute(objectName, attributeName);
		} catch (JMException e) {
			throw new IllegalArgumentException("参数不正确", e);
		} catch (IOException e) {
			throw new IllegalStateException("连接出错", e);
		}
	}

	/**
	 * 按属性名直接设置MBean属性(无MBean的Class文件时使用).
	 */
	public void setAttribute(final String mbeanName, final String attributeName, final Object value) {
		Assert.hasText(mbeanName, "mbeanName不能为空");
		Assert.hasText(attributeName, "attributeName不能为空");
		assertConnected();

		try {
			ObjectName objectName = buildObjectName(mbeanName);
			Attribute attribute = new Attribute(attributeName, value);
			mbsc.setAttribute(objectName, attribute);
		} catch (JMException e) {
			throw new IllegalArgumentException("参数不正确", e);
		} catch (IOException e) {
			throw new IllegalStateException("连接出错", e);
		}
	}

	/**
	 * 按方法名直接调用MBean方法(无MBean的Class文件时使用).
	 * 
	 * 所调用方法无参数时的简写函数.
	 */
	public void inoke(final String mbeanName, final String methodName) {
		invoke(mbeanName, methodName, new Object[] {}, new String[] {});
	}

	/**
	 * 按方法名直接调用MBean方法(无MBean的Class文件时使用).
	 * 
	 * @param signature 所有参数的Class名全称的数组.
	 */
	@SuppressWarnings("unchecked")
	public <T> T invoke(final String mbeanName, final String methodName, final Object[] params, final String[] signature) {
		Assert.hasText(mbeanName, "mbeanName不能为空");
		Assert.hasText(methodName, "methodName不能为空");
		assertConnected();

		try {
			ObjectName objectName = buildObjectName(mbeanName);
			return (T) mbsc.invoke(objectName, methodName, params, signature);
		} catch (JMException e) {
			throw new IllegalArgumentException("参数不正确", e);
		} catch (IOException e) {
			throw new IllegalStateException("连接出错", e);
		}
	}

	/**
	 * 确保Connection已连接.
	 */
	private void assertConnected() {
		if (!connected.get())
			throw new IllegalStateException("connector已关闭");
	}

	/**
	 * 构造ObjectName对象,并转换其抛出的异常为unchecked exception.
	 */
	private ObjectName buildObjectName(final String mbeanName) {
		try {
			return new ObjectName(mbeanName);
		} catch (MalformedObjectNameException e) {
			throw new IllegalArgumentException("mbeanName:" + mbeanName + "不正确", e);
		}
	}
}
