package org.springside.examples.showcase.jmx.client;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.jmx.JmxClientTemplate;

/**
 * JMX客户端服务的封装.
 * 
 * 分别演示标准MBean代理及无MBean的Class文件时直接读取属性/调用方法两种场景.
 * 
 * @author ben
 * @author calvin
 */
public class JmxClientService {

	//ObjectName定义
	public static final String CONFIG_MBEAN_NAME = "Showcase:name=serverConfig,type=ServerConfig";
	public static final String HIBERNATE_MBEAN_NAME = "Showcase:name=hibernateStatistics,type=StatisticsService";

	private static Logger logger = LoggerFactory.getLogger(JmxClientService.class);

	//jmx客户端工厂及mbean代理
	private JmxClientTemplate jmxClientTemplate;
	private ServerConfigMBean serverConfigMBean;

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
			jmxClientTemplate = new JmxClientTemplate(serviceUrl, userName, passwd);

			serverConfigMBean = jmxClientTemplate.getMBeanProxy(CONFIG_MBEAN_NAME, ServerConfigMBean.class);
		} catch (Exception e) {
			logger.error("连接Jmx Server 或 创建Mbean Proxy时失败", e);
		}
	}

	/**
	 * 断开JMX连接.
	 */
	@PreDestroy
	public void close() throws IOException {
		jmxClientTemplate.close();
	}

	// 标准MBean代理操作演示 //
	/**
	 * 获取节点名.
	 */
	public String getNodeName() {
		return serverConfigMBean.getNodeName();
	}

	/**
	 * 设置节点名.
	 */
	public void setNodeName(String nodeName) {
		serverConfigMBean.setNodeName(nodeName);
	}

	/**
	 * 获取是否发送通知邮件.
	 */
	public boolean isNotificationMailEnabled() {
		return serverConfigMBean.isNotificationMailEnabled();
	}

	/**
	 * 设置是否发送通知邮件.
	 */
	public void setNotificationMailEnabled(boolean notificationMailEnabled) {
		serverConfigMBean.setNotificationMailEnabled(notificationMailEnabled);
	}

	// 无MBean的Class文件时直接访问属性调用方法的演示 //
	/**
	 * 获取Hibernate统计数据.
	 */
	public HibernateStatistics getHibernateStatistics() {
		HibernateStatistics statistics = new HibernateStatistics();
		statistics.setSessionOpenCount((Long) jmxClientTemplate.getAttribute(HIBERNATE_MBEAN_NAME, "SessionOpenCount"));
		statistics.setSessionCloseCount((Long) jmxClientTemplate.getAttribute(HIBERNATE_MBEAN_NAME, "SessionCloseCount"));
		return statistics;
	}

	/**
	 * 调用Hibernate MBean的logSummary函数.
	 */
	public void logSummary() {
		jmxClientTemplate.inoke(HIBERNATE_MBEAN_NAME, "logSummary");
	}

	/**
	 * 封装Hibernate统计数据的本地DTO.
	 */
	public static class HibernateStatistics {
		private long sessionOpenCount;
		private long sessionCloseCount;

		public long getSessionOpenCount() {
			return sessionOpenCount;
		}

		public void setSessionOpenCount(long sessionOpenCount) {
			this.sessionOpenCount = sessionOpenCount;
		}

		public long getSessionCloseCount() {
			return sessionCloseCount;
		}

		public void setSessionCloseCount(long sessionCloseCount) {
			this.sessionCloseCount = sessionCloseCount;
		}
	}
}
