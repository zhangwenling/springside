package org.springside.examples.showcase.jmx.server;

/**
 * 配置系统属性,并监控其运行状态的MBean实现.
 *  
 * @author calvin
 */
public class ServerMXBeanImpl implements ServerMXBean {

	private ServerConfig serverConfig;

	private ServerStatistics serverStatistics;

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		this.serverStatistics = serverStatistics;
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
	 * 获取系统执行情况次数.
	 */
	public ServerStatistics getServerStatistics() {
		return serverStatistics;
	}

	public ServerStatistics getServerStatisticssss() {
		return serverStatistics;
	}
}
