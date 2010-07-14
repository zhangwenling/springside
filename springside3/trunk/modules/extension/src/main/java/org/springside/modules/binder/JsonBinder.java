package org.springside.modules.binder;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonBinder {

	private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

	private ObjectMapper mapper;

	public JsonBinder() {
		mapper = new ObjectMapper();
	}

	public JsonBinder(Inclusion inclusion) {
		this();
		setInclusion(inclusion);
	}

	public void setInclusion(Inclusion inclusion) {
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
	}

	public <T> T fromJson(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	public <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}
