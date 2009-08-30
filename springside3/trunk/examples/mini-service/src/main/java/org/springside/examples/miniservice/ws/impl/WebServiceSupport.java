package org.springside.examples.miniservice.ws.impl;

import javax.annotation.PostConstruct;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.DozerMapperSingleton;

/**
 * 可选的WebService辅助基类.
 * 
 * 提供了公共的logger和辅助DTO复制的dozer工具类.
 * 
 * @author calvin
 */
public abstract class WebServiceSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Mapper dozer;

	public void setDozer(final Mapper dozer) {
		this.dozer = dozer;
	}

	/**
	 * 保证dozer初始化, 如果Spring未注入dozer,创建无配置文件的dozer单例.
	 */
	@PostConstruct
	public void initDozer() {
		if (dozer == null) {
			logger.info("ApplicationContext中不存在dozer mapper,使用无配置文件的默认dozer.");
			dozer = DozerMapperSingleton.getInstance();
		}
	}
}
