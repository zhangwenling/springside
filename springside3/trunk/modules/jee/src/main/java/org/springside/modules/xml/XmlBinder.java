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

	public <T> T fromXml(String xml);

	public String toXml(Object object);
}
