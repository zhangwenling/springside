package org.springside.examples.showcase.jmx.server;

/**
 * 监控系统运行统计信息的MXBean实现.
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
