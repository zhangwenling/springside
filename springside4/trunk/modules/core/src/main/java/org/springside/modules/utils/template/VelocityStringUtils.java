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
import org.apache.velocity.app.Velocity;

/**
 * 使用Velocity渲染模板内容的工具类, 用于动态模板内容或字符串替换.
 * 
 * 如果是固定的模板文件请使用VelocityEngineHolder.
 * 
 * @author calvin
 */
public class VelocityStringUtils {

	static {
		Velocity.init();
	}

	/**
	 * 渲染模板内容.
	 * 
	 * @param templateContent 模板内容.
	 * @param context 变量Map.
	 */
	public static String render(String templateContent, Map<String, ?> context) {
		VelocityContext velocityContext = new VelocityContext(context);

		StringWriter result = new StringWriter();
		Velocity.evaluate(velocityContext, result, "", templateContent);
		return result.toString();
	}
}
