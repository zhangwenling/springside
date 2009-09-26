package org.springside.examples.showcase.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 为使Map<String,String> houses转化为有业务意义的xml的巨大努力,
 * 分别定义了一个List<HouseEntry> Wrapper类--HouseMap, 一个MapEntry表达类--HouseEntry和一个Adapter--HouseMapAdapter.
 * 最后的劳动成果是：
 * <houses>
 * 		<house key="bj">house1</item>
 * 		<hosue key="gz">house2</item>
 * 	</houses>
 * 
 * @author calvin
 */
@XmlType(name = "houses")
public class HouseMap {
	@XmlElement(name = "house")
	List<HouseEntry> entries = new ArrayList<HouseEntry>();

	/**
	 * House Map中的Entry.
	 */
	public static class HouseEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public String value;

		public HouseEntry() {
		}

		public HouseEntry(Map.Entry<String, String> e) {
			key = e.getKey();
			value = e.getValue();
		}
	}

	/**
	 * Map转换适配器,将Map转换为HouseMap.
	 */
	public static class HouseMapAdapter extends XmlAdapter<HouseMap, Map<String, String>> {

		@Override
		public HouseMap marshal(Map<String, String> map) throws Exception {
			HouseMap houseMap = new HouseMap();
			for (Map.Entry<String, String> e : map.entrySet()) {
				houseMap.entries.add(new HouseEntry(e));
			}
			return houseMap;
		}

		@Override
		public Map<String, String> unmarshal(HouseMap houseMap) throws Exception {
			Map<String, String> map = new HashMap<String, String>();
			for (HouseEntry e : houseMap.entries) {
				map.put(e.key, e.value);
			}
			return map;
		}
	}
}
