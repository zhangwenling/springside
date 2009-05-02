package org.springside.modules.utils;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * 在Spring ApplicationContext中初始化Slf4对Java.util.logging的拦截.
 * 
 * @author calvin
 */
public class JulOverSlf4j {

	//Spring在所有属性注入后自动执行的函数.
	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
