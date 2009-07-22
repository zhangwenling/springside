package org.springside.examples.showcase.xml.xstream;

import com.thoughtworks.xstream.XStream;

/**
 * 使用XStream持久化XML的Util.
 * 
 * @author calvin
 */

@SuppressWarnings("unchecked")
public class XStreamUtil {
	private XStream xstream = new XStream();

	/**
	 * 初始化XStream对象.
	 * @param types 带XStream Annotation的class.
	 */
	public XStreamUtil(Class<?>... types) {
		register(types);
	}

	/**
	 * 注册带XStream Annotation的Class.
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
