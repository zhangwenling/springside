package org.springside.examples.showcase.solr;

import org.apache.commons.lang.StringUtils;

public class PostQueryBuilder {

	public enum TimeScope {
		ALL, ONE_MONTH, ONE_YEAR
	}

	private StringBuilder builder;

	private String keywords;

	private boolean includeContent;

	private String authorName;

	private TimeScope timeScope;

	public String buildQueryString() {

		builder = new StringBuilder();
		//标题与内容
		if (StringUtils.isNotBlank(keywords)) {
			builder.append("title:(" + keywords + ")");

			if (includeContent) {
				builder.append(" OR content:(" + keywords + ")");
			}
		}

		//用户名
		if (StringUtils.isNotBlank(authorName)) {
			appendAnd();
			builder.append("authorName:(" + authorName + ")");
		}

		//时间范围
		if (timeScope != null && timeScope != TimeScope.ALL) {
			appendAnd();
			switch (timeScope) {
			case ONE_MONTH:
				builder.append("modifyTime:[NOW-1MONTH TO *]");
				break;
			case ONE_YEAR:
				builder.append("modifyTime:[NOW-1YEAR TO *]");
				break;
			}
		}

		return builder.toString();
	}

	private void appendAnd() {
		if (builder.length() > 0) {
			builder.append(" AND ");
		}
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setIncludeContent(boolean includeContent) {
		this.includeContent = includeContent;
	}

	public void setTimeScope(TimeScope timeScope) {
		this.timeScope = timeScope;
	}
}
