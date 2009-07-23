package org.springside.modules.xml;

public interface XmlBinder {

	public <T> T fromXml(String xml);

	public String toXml(Object object);
}
