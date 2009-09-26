package org.springside.examples.showcase.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

public class ListWrapper {
	@XmlAnyElement
	private List<Object> value = new ArrayList<Object>();

	public List<Object> getValue() {
		return value;
	}
}