/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.utils.template;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springside.modules.utils.AssertUtils;

/**
 * 使用Velocity渲染模板文件的工具类.
 * 
 * @author calvin
 */
public class VelocityFileProcessor implements InitializingBean {

	private static VelocityEngine velocityEngine;

	private String resourceLoaderPath;

	public VelocityFileProcessor() {
	}

	public VelocityFileProcessor(String resourceLoaderPath) throws Exception {
		this.resourceLoaderPath = resourceLoaderPath;
		this.init();
	}

	/**
	 * 渲染模板文件.
	 * 
	 * @param templateContent 模板文件名, loader会自动在前面加上resourceLoaderPath.
	 * @param context 变量Map.
	 */
	public static String render(String templateFilePName, String encoding, Map<String, ?> context) {
		VelocityContext velocityContext = new VelocityContext(context);

		StringWriter result = new StringWriter();
		velocityEngine.mergeTemplate(templateFilePName, "UTF-8", velocityContext, result);
		return result.toString();
	}

	/**
	 * 根据resourceLoaderPath初始化VelocityEngine, 使用Spring Resource Loader作为Resource Loader.
	 */
	public void init() throws Exception {
		AssertUtils.hasText(resourceLoaderPath);

		VelocityEngineFactory factory = new VelocityEngineFactory();
		factory.setResourceLoaderPath(resourceLoaderPath);
		velocityEngine = factory.createVelocityEngine();
	}

	/**
	 * 模板文件目录, 支持Spring Resource的各种路径写法, 不能为空.
	 */
	public void setResourceLoaderPath(String resourceLoaderPath) {
		this.resourceLoaderPath = resourceLoaderPath;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
}
