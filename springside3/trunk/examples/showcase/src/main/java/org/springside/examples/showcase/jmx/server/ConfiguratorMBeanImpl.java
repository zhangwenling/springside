package org.springside.examples.showcase.jmx.server;

/**
 * 配置系统属性的标准MBean实现,演示标准MBean的使用.
 *  
 * @author calvin
 */
public class ConfiguratorMBeanImpl implements ConfiguratorMBean {

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
}
