package org.springside.examples.showcase.jmx.server;

/**
 * 监控系统运行统计信息的MXBean接口.
 * 
 * @author calvin
 */
public interface MonitorMXBean {

	/**
	 * 获取统计数据,ServerStatistics将被转化为CompositeData.
	 */
	ServerStatistics getServerStatistics();

	/**
	 * 重置计数器为0.
	 */
	boolean reset(String statisticsName);
}
