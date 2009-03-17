package org.springside.examples.showcase.jmx.server;

/**
 * 配置系统属性,并监控其运行状态的MBean实现.
 *  
 * @author calvin
 */
public class ServerMBeanImpl implements ServerMBean {

	private ServerConfig serverConfig;

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	// 配置系统属性 //

	public String getNodeName() {
		return serverConfig.getNodeName();
	}

	public boolean isStatisticsEnabled() {
		return serverConfig.isStatisticsEnabled();
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		serverConfig.setStatisticsEnabled(statisticsEnabled);
	}

	public void setNodeName(String nodeName) {
		serverConfig.setNodeName(nodeName);
	}

	// 监控系统运行状态 //

	/**
	 * 获取系统执行查询用户列表的次数.
	 */
	public int getQueryAllUsersCount() {
		return Statistics.getQueryAllUsersCount();
	}
}
