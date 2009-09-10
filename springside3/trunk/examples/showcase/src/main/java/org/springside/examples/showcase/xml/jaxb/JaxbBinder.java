/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.examples.showcase.xml.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 使用Jaxb2.0持久化XML的Binder.
 * 
 * @author calvin
 */
public class JaxbBinder {
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	/**
	 * @param types 所有需要序列化的Root对象的类型.
	 */
	public JaxbBinder(Class<?>... types) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(types);
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java->Xml.
	 */
	public String toXml(Object root) {
		try {
			StringWriter writer = new StringWriter();
			marshaller.marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}
}
