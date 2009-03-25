package org.springside.examples.showcase.jmx.server;

/**
 * 监控系统运行统计信息的MXBean实现.
 *  
 * @author calvin
 */
public class ServerMonitor implements MonitorMXBean {

	private ServerStatistics serverStatistics = new ServerStatistics();

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
