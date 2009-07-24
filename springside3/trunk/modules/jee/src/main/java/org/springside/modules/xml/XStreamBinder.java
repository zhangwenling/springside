/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.xml;

import com.thoughtworks.xstream.XStream;

/**
 * 使用XStream持久化XML的Binder.
 * 
 * @author calvin
 */

@SuppressWarnings("unchecked")
public class XStreamBinder implements XmlBinder {

	private XStream xstream = new XStream();

	/**
	 * @param types 带XStream Annotation的class.
	 */
	public XStreamBinder(Class<?>... types) {
		xstream.processAnnotations(types);
	}

	/**
	 * 带泛型自动转换的XML转换Java对象.
	 */
	public <T> T fromXml(String xml) {
		return (T) xstream.fromXML(xml);
	}

	/**
	 * 将Java对象转换为xml.
	 */
	public String toXml(Object object) {
		return xstream.toXML(object);
	}

	/**
	 * 返回XStream对象.
	 */
	public XStream getStream() {
		return xstream;
	}
}
