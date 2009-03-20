package org.springside.examples.showcase.jmx.server;

/**
 * 监控系统运行状态的MXBean接口.
 * 
 * @author calvin
 */
public interface MonitorMXBean {

	ServerStatistics getServerStatistics();
}
