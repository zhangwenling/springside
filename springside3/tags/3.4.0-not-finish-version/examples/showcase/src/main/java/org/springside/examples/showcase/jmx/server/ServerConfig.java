package org.springside.examples.showcase.jmx.server;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 系统属性配置的MBean实现.
 * 
 * @author calvin
 */
public class ServerConfig implements ServerConfigMBean {

	private String nodeName;
	private AtomicBoolean notificationMailEnabled = new AtomicBoolean(true);

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public boolean isNotificationMailEnabled() {
		return notificationMailEnabled.get();
	}

	@Override
	public void setNotificationMailEnabled(boolean notificationMailEnabled) {
		this.notificationMailEnabled.set(notificationMailEnabled);
	}
}
