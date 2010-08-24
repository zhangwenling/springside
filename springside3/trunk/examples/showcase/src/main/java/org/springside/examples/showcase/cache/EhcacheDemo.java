package org.springside.examples.showcase.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 本地缓存策略,使用EhCache, 支持限制总数, Idle time/LRU失效, 持久化到磁盘等功能.
 * 
 * 配置见applicationContext-ehcache.xml与ehcache.xml
 * 
 * @author calvin
 */
public class EhcacheDemo {

	private static final String CACHE_NAME = "contentInfoCache";

	private static Cache cache;

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("/cache/applicationContext-ehcache.xml");

		CacheManager ehcacheManager = context.getBean(CacheManager.class);

		cache = ehcacheManager.getCache(CACHE_NAME);

		String key = "foo";
		String value = "boo";

		put(key, value);
		Object result = get(key);
		System.out.println(result);

	}

	public static Object get(String key) {
		Element element = cache.get(key);
		return element.getObjectValue();
	}

	public static void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}
}
