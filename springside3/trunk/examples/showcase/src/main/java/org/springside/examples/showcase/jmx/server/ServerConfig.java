package org.springside.examples.showcase.jmx.server;

/**
 * 系统属性配置.
 * 
 * @author calvin
 */
public class ServerConfig implements ConfiguratorMBean {

	private String nodeName;//服务器节点名
	private boolean statisticsEnabled = false;//是否统计服务运行信息

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean isStatisticsEnabled() {
		return statisticsEnabled;
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		this.statisticsEnabled = statisticsEnabled;
	}
}
