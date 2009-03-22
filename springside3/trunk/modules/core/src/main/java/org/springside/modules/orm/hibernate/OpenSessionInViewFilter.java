package org.springside.modules.orm.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 加强过滤控制的OpenSessionInViewFilter.
 * 
 * 对css、js、图片等静态内容请求不创建Session连接.
 * 在web.xml中可配置excludeSuffixs参数,多个后缀名以','分割.
 * 
 * @author calvin
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

	private static final String DEFAULT_EXCLUDE_SUFFIXS = "js,css,jpg,gif";
	private static final List<String> excludeSuffixList = new ArrayList<String>();

	/**
	 * 重载过滤控制函数,过滤后缀名在excludeSuffixs中的请求.
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();

		for (String suffix : excludeSuffixList) {
			if (path.endsWith(suffix))
				return true;
		}

		return false;
	}

	/**
	 * 初始化excludeSuffixs参数
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		String excludeSuffixStr = DEFAULT_EXCLUDE_SUFFIXS;
		if (StringUtils.isNotBlank(getFilterConfig().getInitParameter("excludeSuffixs"))) {
			excludeSuffixStr = getFilterConfig().getInitParameter("excludeSuffixs");
		}

		for (String suffix : excludeSuffixStr.split(",")) {
			excludeSuffixList.add("." + suffix);
		}
	}
}
