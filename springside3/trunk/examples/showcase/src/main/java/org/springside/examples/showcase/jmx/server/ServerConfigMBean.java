package org.springside.examples.showcase.jmx.server;

/**
 * 系统属性配置的标准MBean接口.
 * 
 * @author calvin
 */
public interface ServerConfigMBean {

	public String getNodeName();

	public void setNodeName(String nodeName);

	public boolean isNotificationMailEnabled();

	public void setNotificationMailEnabled(boolean notificationMailEnabled);
}
