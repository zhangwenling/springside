package org.springside.examples.showcase.xml.xstream;

import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * 将key转换为entry节点属性的Converter.
 */
public class HouseMapConverter extends MapConverter {

	private static final String ENTRY_NAME = "house";

	private static final String KEY_NAME = "key";

	public HouseMapConverter(Mapper mapper) {
		super(mapper);
	}

	/**
	 * 将Map对象写成XML.
	 */
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map<?, ?> map = (Map<?, ?>) source;
		for (Entry<?, ?> entry : map.entrySet()) {
			writer.startNode(ENTRY_NAME);
			writer.addAttribute(KEY_NAME, entry.getKey().toString());
			writer.setValue(entry.getValue().toString());
			writer.endNode();
		}
	}

	/**
	 * 从XML节点组装Map对象.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Object key = reader.getAttribute(KEY_NAME);
			Object value = reader.getValue();
			reader.moveUp();
			map.put(key, value);
		}
	}
}
