/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.xml;

/**
 * XML与Java 双向转换的通用接口.
 * 
 * @author calvin
 */
public interface XmlBinder {

	/**
	 * XML->Java Object转换.
	 */
	public <T> T fromXml(String xml);

	/**
	 * Java Object->XML转换.
	 */
	public String toXml(Object object);
}
