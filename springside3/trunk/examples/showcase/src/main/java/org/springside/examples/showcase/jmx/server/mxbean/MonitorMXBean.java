package org.springside.examples.showcase.jmx.server.mxbean;


/**
 * 监控系统运行统计信息的MXBean接口.
 * 
 * @author calvin
 */
public interface MonitorMXBean {

	/**
	 * 获取服务运行统计数据,ServerStatistics将被转化为CompositeData.
	 */
	ServerStatistics getServerStatistics();

	/**
	 * 获取Email发送统计数据.
	 */
	EmailStatistics getEmailStatistics();

	/**
	 * 重置服务计数器为0.
	 */
	boolean reset(String statisticsName);
}
