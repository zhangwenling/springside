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

@XmlType(name = "houses")
public class HouseMap {

	private List<HouseEntry> entries = new ArrayList<HouseEntry>();

	@XmlElement(name = "house")
	public List<HouseEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<HouseEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Map转换适配器,将Map转换为HouseMap.
	 */
	public static class MapAdapter extends XmlAdapter<HouseMap, Map<String, String>> {

		@Override
		public HouseMap marshal(Map<String, String> map) throws Exception {
			HouseMap houseMap = new HouseMap();
			for (Map.Entry<String, String> e : map.entrySet()) {
				houseMap.getEntries().add(new HouseEntry(e));
			}
			return houseMap;
		}

		@Override
		public Map<String, String> unmarshal(HouseMap houseMap) throws Exception {
			Map<String, String> map = new HashMap<String, String>();
			for (HouseEntry e : houseMap.getEntries()) {
				map.put(e.key, e.value);
			}
			return map;
		}
	}

	/**
	 * Map的可绑定数据类型.
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

}
