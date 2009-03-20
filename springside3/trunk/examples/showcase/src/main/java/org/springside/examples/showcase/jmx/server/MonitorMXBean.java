package org.springside.examples.showcase.jmx.server;

/**
 * 监控系统运行统计信息的MXBean接口.
 * 
 * @author calvin
 */
public interface MonitorMXBean {

	ServerStatistics getServerStatistics();
}
