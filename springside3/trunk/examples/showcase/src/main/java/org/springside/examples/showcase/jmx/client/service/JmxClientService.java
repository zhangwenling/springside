package org.springside.examples.showcase.jmx.client.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.examples.showcase.jmx.server.ConfiguratorMBean;
import org.springside.examples.showcase.jmx.server.MonitorMXBean;
import org.springside.examples.showcase.jmx.server.ServerStatistics;
import org.springside.modules.jmx.JmxClientFactory;

/**
 * JMX客户端服务的封装.
 * 
 * 分别演示了标准MBean、MXBean代理及无MBean的Class文件时直接读取属性三种场景.
 * 
 * @author ben
 * @author calvin
 */
public class JmxClientService {

	private static Logger logger = LoggerFactory.getLogger(JmxClientService.class);

	//ObjectName定义
	private static String configuratorMBeanName = "org.springside.showcase:type=Configurator";
	private static String monitorMXBeanName = "org.springside.showcase:type=Monitor";
	private static String hibernteMBeanName = "org.hibernate:type=Statistics";

	//jmx客户端工厂及mbean代理
	private JmxClientFactory clientFactory;
	private ConfiguratorMBean configuratorMBean;
	private MonitorMXBean monitorMXBean;

	//可注入的连接参数
	private String ip;
	private String port;
	private String userName;
	private String passwd;

	// 连接参数设定函数 //

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
			clientFactory = new JmxClientFactory(serviceUrl, userName, passwd);

			configuratorMBean = clientFactory.getMBeanProxy(configuratorMBeanName, ConfiguratorMBean.class);
			monitorMXBean = clientFactory.getMXBeanProxy(monitorMXBeanName, MonitorMXBean.class);
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
		clientFactory.close();
	}

	/**
	 * 读取节点名,标准MBean代理操作的演示,下同.
	 */
	public String getNodeName() {
		return configuratorMBean.getNodeName();
	}

	public void setNodeName(String nodeName) {
		configuratorMBean.setNodeName(nodeName);
	}

	public boolean isStatisticsEnabled() {
		return configuratorMBean.isStatisticsEnabled();
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		configuratorMBean.setStatisticsEnabled(statisticsEnabled);
	}

	/**
	 * 获取系统运行统计数据,MXBean代理操作的演示.
	 */
	public ServerStatistics getServerStatistics() {
		return monitorMXBean.getServerStatistics();
	}

	/**
	 * 获取Hibernate打开数据库连接数,无MBean的Class文件时直接读取属性的演示.
	 */
	public Long getSessionOpenCount() {
		return (Long) clientFactory.getAttribute(hibernteMBeanName, "SessionOpenCount");
	}
}
