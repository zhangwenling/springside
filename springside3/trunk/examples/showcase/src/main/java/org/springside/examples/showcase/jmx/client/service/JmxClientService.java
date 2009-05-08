package org.springside.examples.showcase.jmx.client.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.examples.showcase.jmx.server.ConfiguratorMBean;
import org.springside.modules.jmx.JmxClient;

/**
 * JMX客户端服务的封装.
 * 
 * 分别演示了标准MBean、MXBean代理及无MBean的Class文件时直接读取属性三种场景.
 * 
 * @author ben
 * @author calvin
 */
public class JmxClientService {

	protected static Logger logger = LoggerFactory.getLogger(JmxClientService.class);

	//ObjectName定义
	private static String configuratorMBeanName = "org.springside.showcase:type=Configurator";
	private static String hibernteMBeanName = "org.hibernate:type=Statistics";

	//jmx客户端工厂及mbean代理
	private JmxClient jmxClient;
	private ConfiguratorMBean configuratorMBean;

	//可注入的连接参数
	private String host;
	private String port;
	private String userName;
	private String passwd;

	// 连接参数设定函数 //

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	// 连接与断开远程连接 //
	/**
	 * 连接JMX Server并创建本地的MBean代理.
	 */
	@PostConstruct
	public void init() {
		Assert.hasText(host, "host参数不能为空");
		Assert.hasText(port, "port参数不能为空");

		try {
			String serviceUrl = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/showcase";
			jmxClient = new JmxClient(serviceUrl, userName, passwd);

			configuratorMBean = jmxClient.getMBeanProxy(configuratorMBeanName, ConfiguratorMBean.class);
		} catch (Exception e) {
			logger.error("连接Jmx Server 或 创建Mbean Proxy时失败", e);
		}
	}

	/**
	 * 断开JMX连接.
	 */
	@PreDestroy
	public void close() throws IOException {
		jmxClient.close();
	}

	// 标准MBean代理操作演示 、、

	public String getNodeName() {
		return configuratorMBean.getNodeName();
	}

	public void setNodeName(String nodeName) {
		configuratorMBean.setNodeName(nodeName);
	}

	public boolean isNotificationMailEnabled() {
		return configuratorMBean.isNotificationMailEnabled();
	}

	public void setNotificationMailEnabled(boolean notificationMailEnabled) {
		configuratorMBean.setNotificationMailEnabled(notificationMailEnabled);
	}

	// 无MBean的Class文件时直接访问属性调用方法的演示 //

	/**
	 * 获取Hibernate统计数据.
	 */
	public HibernateStatistics getHibernateStatistics() {
		HibernateStatistics statistics = new HibernateStatistics();
		statistics.setSessionOpenCount((Long) jmxClient.getAttribute(hibernteMBeanName, "SessionOpenCount"));
		statistics.setSessionCloseCount((Long) jmxClient.getAttribute(hibernteMBeanName, "SessionCloseCount"));
		return statistics;
	}

	/**
	 * 调用Hibernate MBean的logSummary函数.
	 */
	public void logSummary() {
		jmxClient.inoke(hibernteMBeanName, "logSummary");
	}
}
