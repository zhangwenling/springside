package org.springside.examples.showcase.cache;

public interface CacheStrategy {

	public Object get(String key);
	
	public void put(String key, Object value);
}
