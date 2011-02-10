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
 * 使用Velocity根据模板生成内容的工具类.
 * 
 * @author calvin
 */
public class VelocityUtils {

	static {
		try {
			Velocity.init();
		} catch (Exception e) {
			throw new RuntimeException("Exception occurs while initialize the velociy.", e);
		}
	}

	/**
	 * 渲染模板内容.
	 * 
	 * @param template 模板内容.
	 * @param model 变量Map.
	 */
	public static String render(String template, Map<String, ?> model) {
		try {
			VelocityContext velocityContext = new VelocityContext(model);
			StringWriter result = new StringWriter();
			Velocity.evaluate(velocityContext, result, "", template);
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException("Parse template failed.", e);
		}
	}
}
