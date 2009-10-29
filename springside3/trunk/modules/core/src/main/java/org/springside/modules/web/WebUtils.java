/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JdbcAppenderTask.java 353 2009-08-22 09:33:28Z calvinxiu
 */
package org.springside.modules.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {

	public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getParametersStartingWith(HttpServletRequest request, String prefix) {
		return org.springframework.web.util.WebUtils.getParametersStartingWith(request, prefix);
	}
}
