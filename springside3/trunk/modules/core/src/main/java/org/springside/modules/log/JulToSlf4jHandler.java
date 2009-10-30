/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.log;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * 在Spring ApplicationContext中初始化slf4对java.util.logging的拦截.
 * 
 * @author calvin
 */
public class JulToSlf4jHandler {

	//Spring在启动时调用的函数.
	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
