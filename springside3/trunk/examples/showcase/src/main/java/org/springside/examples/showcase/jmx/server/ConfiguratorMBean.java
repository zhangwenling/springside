package org.springside.examples.showcase.jmx.server;

/**
 * 配置系统属性的标准MBean接口. 
 * 
 * @author calvin
 */
public interface ConfiguratorMBean {

	String getNodeName();

	void setNodeName(String nodeName);

	boolean isStatisticsEnabled();

	void setStatisticsEnabled(boolean statisticsEnabled);
}
