package org.springside.examples.showcase.jmx.server;

/**
 * 可配置系统属性,并监控其运行状态的MBean接口. 
 * 
 * @author calvin
 */
public interface ServerMXBean {

	// 系统属性配置 //

	String getNodeName();

	void setNodeName(String nodeName);

	boolean isStatisticsEnabled();

	void setStatisticsEnabled(boolean statisticsEnabled);

	// 监控系统运行状态 //

	ServerStatistics getServerStatistics();
}
