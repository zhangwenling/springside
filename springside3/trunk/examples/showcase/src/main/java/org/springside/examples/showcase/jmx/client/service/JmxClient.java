package org.springside.examples.showcase.jmx.client.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.examples.showcase.jmx.server.ServerMBean;
import org.springside.modules.jmx.ClientMBeanProxyFactory;

/**
 * JMX 客户端的封装.
 * 
 * @author ben
 * @author calvin
 */
public class JmxClient {

	private static Logger logger = LoggerFactory.getLogger(JmxClient.class);

	private ClientMBeanProxyFactory mbeanFactory;
	private ServerMBean mbean;

	// 可注入的连接参数
	private String ip;
	private String port;
	private String userName;
	private String passwd;

	/**
	 * 连接JMX Server 并创建本地的MBean Proxy.
	 */
	//Spring在所有属性注入后自动执行的函数.
	@PostConstruct
	public void init() {
		Assert.hasText(ip);
		Assert.hasText(port);
		try {
			String serviceUrl = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/showcase";
			mbeanFactory = new ClientMBeanProxyFactory(serviceUrl, userName, passwd);

			String serverMBeanName = "org.springside.showcase:name=Server";
			mbean = mbeanFactory.getMBeanProxy(serverMBeanName, ServerMBean.class);
		} catch (Exception e) {
			logger.error("连接Jmx Server 或 创建Mbean Proxy时失败", e);
		}
	}

	/**
	 * 断开JMX连接.
	 */
	//Spring在销毁对象时自动执行的函数.
	@PreDestroy
	public void close() throws IOException {
		mbeanFactory.close();
	}

	// JMX操作函数

	public String getNodeName() {
		return mbean.getNodeName();
	}

	public void setNodeName(String nodeName) {
		mbean.setNodeName(nodeName);
	}

	public boolean isStatisticsEnabled() {
		return mbean.isStatisticsEnabled();
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		mbean.setStatisticsEnabled(statisticsEnabled);
	}

	public int getQueryAllUsersCount() {
		return mbean.getQueryAllUsersCount();
	}

	// 参数设定函数

	public void setIp(String ip) {
		this.ip = ip;
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
}
