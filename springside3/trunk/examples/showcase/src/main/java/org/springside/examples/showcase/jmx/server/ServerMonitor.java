package org.springside.examples.showcase.jmx.server;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监控系统运行统计信息的MXBean实现.
 *  
 * @author calvin
 */
public class ServerMonitor implements MonitorMXBean {

	Logger logger = LoggerFactory.getLogger(ServerMonitor.class);

	private ServerStatistics serverStatistics = new ServerStatistics();
	private EmailStatistics emailStatistics = new EmailStatistics();

	public void setServerStatistics(ServerStatistics serverStatistics) {
		this.serverStatistics = serverStatistics;
	}

	/**
	 * 获取服务统计数据,ServerStatistics将被转化为CompositeData.
	 */
	public ServerStatistics getServerStatistics() {
		return serverStatistics;
	}

	/**
	 * 获取Email发送统计数据.
	 */
	public EmailStatistics getEmailStatistics() {
		return emailStatistics;
	}

	/**
	 * 重置服务计数器为0.
	 */
	public boolean reset(String statisticsName) {
		try {
			String methodName = "reset" + StringUtils.capitalize(statisticsName);
			MethodUtils.invokeMethod(serverStatistics, methodName, null);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
