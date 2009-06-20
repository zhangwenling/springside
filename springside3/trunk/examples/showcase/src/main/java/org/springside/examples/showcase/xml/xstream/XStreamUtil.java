package org.springside.examples.showcase.xml.xstream;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("unchecked")
public class XStreamUtil {
	public static XStream xstream = new XStream();

	/**
	 * 注册带XStream Annotation的Class
	 */
	public static void register(Class<?>... types) {
		xstream.processAnnotations(types);
	}

	/**
	 * 带泛型自动转换的XML转换Java对象.
	 */
	public static <T> T fromXml(String xml) {
		return (T) xstream.fromXML(xml);
	}

	/**
	 * 将Java对象转换为xml.
	 */
	public static String toXml(Object object) {
		return xstream.toXML(object);
	}

	}
