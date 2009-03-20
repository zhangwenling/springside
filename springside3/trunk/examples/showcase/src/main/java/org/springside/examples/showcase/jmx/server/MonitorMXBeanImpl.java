package org.springside.examples.showcase.jmx.server;

/**
 * 监控其运行状态的MBean实现,演示MXBean的使用.
 *  
 * @author calvin
 */
public class MonitorMXBeanImpl implements MonitorMXBean {

	private ServerStatistics serverStatistics;

	public void setServerStatistics(ServerStatistics serverStatistics) {
		this.serverStatistics = serverStatistics;
	}

	/**
	 * 获取统计数据,ServerStatistics将被转化为CompositeData.
	 */
	public ServerStatistics getServerStatistics() {
		return serverStatistics;
	}
}
