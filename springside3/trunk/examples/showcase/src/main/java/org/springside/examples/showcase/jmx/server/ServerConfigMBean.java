package org.springside.examples.showcase.jmx.server;

/**
 * 系统属性配置的标准MBean接口.
 * 
 * @author calvin
 */
public interface ServerConfigMBean {

	String getNodeName();

	void setNodeName(String nodeName);

	boolean isNotificationMailEnabled();

	void setNotificationMailEnabled(boolean notificationMailEnabled);
}
