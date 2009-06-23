package org.springside.examples.showcase.xml.xstream;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("unchecked")
public class XStreamUtil {
	public XStream xstream = new XStream();

	public XStreamUtil(Class<?>... types) {
		register(types);
	}

	/**
	 * 注册带XStream Annotation的Class
	 */
	public void register(Class<?>... types) {
		xstream.processAnnotations(types);
	}

	/**
	 * 返回XStream对象.
	 */
	public XStream getStream() {
		return xstream;
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

}
