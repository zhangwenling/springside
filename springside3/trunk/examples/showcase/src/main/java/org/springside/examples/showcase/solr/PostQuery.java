package org.springside.examples.showcase.solr;

import org.apache.commons.lang.StringUtils;

public class PostQuery {

	private StringBuilder builder = new StringBuilder();

	private String keywords;

	private String authordName;

	private boolean onlyTitle;
	

	public String buildQueryString() {
		//标题与内容
		if (StringUtils.isNotBlank(keywords)) {
			builder.append("title:(" + keywords + ")");

			if (!onlyTitle) {
				builder.append(" or content:(" + keywords + ")");
			}
		}

		//用户名
		if (StringUtils.isNotBlank(authordName)) {
			if (builder.length() > 0)
				builder.append(" and ");
			builder.append("authordName:(" + authordName + ")");
		}

		return builder.toString();
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setAuthordName(String authordName) {
		this.authordName = authordName;
	}

	public void setOnlyTitle(boolean onlyTitle) {
		this.onlyTitle = onlyTitle;
	}
}
