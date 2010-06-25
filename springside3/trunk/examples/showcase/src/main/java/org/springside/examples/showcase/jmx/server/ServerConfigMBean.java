package org.springside.examples.showcase.jmx.server;

/**
 * 系统属性配置的MBean接口.
 * 
 * @author calvin
 */
public interface ServerConfigMBean {

	/**
	 * ServerConfigMBean自动生成的注册名称.
	 */
	public static final String SERVER_CONFIG_MBEAN_NAME = "Showcase:name=serverConfig,type=ServerConfig";

	/**
	 * 服务器节点名.
	 */
	public String getNodeName();

	public void setNodeName(String nodeName);

	/**
	 * 是否发送通知邮件.
	 */
	public boolean isNotificationMailEnabled();

	public void setNotificationMailEnabled(boolean notificationMailEnabled);
}
