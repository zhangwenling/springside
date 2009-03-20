package org.springside.examples.showcase.jmx.client.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.jmx.client.service.JmxClientService;
import org.springside.examples.showcase.jmx.server.ServerStatistics;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * JMX客户端演示的Action.
 * 
 * @author ben
 * @author calvin
 */
@SuppressWarnings("serial")
//因为没有按Convention Plugin默认的Pacakge命名规则,因此用annotation重新指定Namespace.
@Namespace("/jmx")
public class JmxClientAction extends ActionSupport {

	private static Logger logger = LoggerFactory.getLogger(JmxClientAction.class);

	@Autowired
	private JmxClientService jmxClientService;

	// 页面属性
	private String nodeName;
	private boolean statisticsEnabled;
	private ServerStatistics serverStatistics;
	private long sessionOpenCount;

	// 属性访问函数 //

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean isStatisticsEnabled() {
		return statisticsEnabled;
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		this.statisticsEnabled = statisticsEnabled;
	}

	public ServerStatistics getServerStatistics() {
		return serverStatistics;
	}

	public long getSessionOpenCount() {
		return sessionOpenCount;
	}

	// Action 函数 //

	/**
	 * 默认函数,显示服务器配置及运行情况.
	 */
	@Override
	public String execute() {
		nodeName = jmxClientService.getNodeName();
		statisticsEnabled = jmxClientService.isStatisticsEnabled();
		serverStatistics = jmxClientService.getServerStatistics();
		sessionOpenCount = jmxClientService.getSessionOpenCount();
		return SUCCESS;
	}

	/**
	 * 修改系统配置的Ajax请求.
	 */
	public String saveConfig() {
		try {
			jmxClientService.setNodeName(nodeName);
			jmxClientService.setStatisticsEnabled(statisticsEnabled);
			Struts2Utils.renderText("保存配置成功.");
		} catch (Exception e) {
			Struts2Utils.renderText("保存配置失败.");
			logger.error(e.getMessage(), e);
		}
		//因为直接输出而不经过jsp,因此返回null.
		return null;
	}

	/**
	 * 获取最新系统配置的Ajax请求.
	 */
	@SuppressWarnings("unchecked")
	public String updateConfig() {
		Map map = new HashMap();
		try {
			nodeName = jmxClientService.getNodeName();
			statisticsEnabled = jmxClientService.isStatisticsEnabled();
			map.put("nodeName", nodeName);
			map.put("statisticsEnabled", String.valueOf(statisticsEnabled));
			map.put("message", "获取配置成功.");
		} catch (Exception e) {
			map.put("message", "获取配置失败.");
			logger.error(e.getMessage(), e);
		}

		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 获取最新系统运行统计信息的Ajax请求.
	 */
	public String updateStatics() {
		try {
			serverStatistics = jmxClientService.getServerStatistics();
			Struts2Utils.renderJson(serverStatistics);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
}
