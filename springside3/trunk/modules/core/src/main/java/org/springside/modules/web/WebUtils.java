/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Web Utils函数集合.
 * 
 * @author calvin
 */
public class WebUtils {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 文件下载后的文件名.
	 */
	public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, Date lastModifiedDate) {
		String lastModifiedDateString = DATE_FORMAT.format(lastModifiedDate);
		response.setHeader("Last-Modified", lastModifiedDateString);

	}

	/**
	 * 设置304 无修改的Header.
	 */
	public static void setNotModified(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	}

	/**
	 * 如果客户端支持gzip压缩, 返回GzipOutputStream并设置Header， 否则返回普通ServletOutStream.
	 */
	public static OutputStream getZipOutputStream(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if (StringUtils.contains(acceptEncoding, "gzip")) {
			response.setHeader("Content-Encoding", "gzip");
			return new GZIPOutputStream(response.getOutputStream());
		} else {
			return response.getOutputStream();
		}
	}

	/**
	 * 根据浏览器If-Modified-Since 或  If-Unmodified-Since头, 计算文件是否已修改.
	 * 
	 * @return 如果文件在浏览器头的时间后有修改, 返回true.
	 */
	public static boolean checkIfModified(HttpServletRequest request, Date lastModifiedDate) {
		long lastModified = lastModifiedDate.getTime();

		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			return false;
		}

		long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
		if ((ifUnmodifiedSince != -1) && (lastModified >= ifUnmodifiedSince + 1000)) {
			return false;
		}

		return true;
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParametersStartingWith(HttpServletRequest request, String prefix) {
		return org.springframework.web.util.WebUtils.getParametersStartingWith(request, prefix);
	}
}
