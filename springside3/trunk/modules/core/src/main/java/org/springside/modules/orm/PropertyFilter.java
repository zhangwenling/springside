/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.orm;

import org.apache.commons.lang.StringUtils;

/**
 * 与具体ORM实现无关的属性过滤条件封装类.
 * 
 * PropertyFilter主要记录页面中简单的搜索过滤条件,比Hibernate的Criterion要简单.
 * 
 * TODO:扩展其他对比方式如大于、小于及其他数据类型如数字和日期.
 * 
 * @author calvin
 */
public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	private static final String OR_SEPARATOR = "_OR_";

	/**
	 * 属性比较类型.
	 */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	private String[] propertyNames = null;
	private Object value;
	private MatchType matchType = MatchType.EQ;

	public PropertyFilter() {
	}

	/**
	 * @param filterName 比较属性字符串,含待比较的属性列表及比较类型. 
	 *                   eg LIKE_NAME_OR_LOGIN_NAME
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final Object value) {

		String matchTypeCode = StringUtils.substringBefore(filterName, "_");
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性比较类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		propertyNames = StringUtils.split(propertyNameStr, PropertyFilter.OR_SEPARATOR);

		this.value = value;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的属性名称.
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1)
			throw new IllegalArgumentException("There are not only one property");
		return propertyNames[0];
	}

	/**
	 * 获取比较值.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 获取比较类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 是否有多个属性.
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}
}
