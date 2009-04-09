package org.springside.examples.showcase.jmx.server;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 系统属性配置.
 * 
 * @author calvin
 */
public class ServerConfig implements ConfiguratorMBean {

	private String nodeName;//服务器节点名
	private AtomicBoolean notificationMailEnabled = new AtomicBoolean(true);//是否发送通知邮件

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean isNotificationMailEnabled() {
		return notificationMailEnabled.get();
	}

	public void setNotificationMailEnabled(boolean notificationMailEnabled) {
		this.notificationMailEnabled.set(notificationMailEnabled);
	}
}
