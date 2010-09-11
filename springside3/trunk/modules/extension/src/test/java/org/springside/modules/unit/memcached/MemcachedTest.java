package org.springside.modules.unit.memcached;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.memcached.SpyMemcachedClient;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/applicationContext-extension-test-memcached.xml" })
public class MemcachedTest extends SpringContextTestCase {

	@Autowired
	private SpyMemcachedClient spyMemcachedClient;

	@Test
	public void normal() {

		String key = "consumer:1";
		String value = "admin";

		spyMemcachedClient.set(key, 60 * 60 * 1, value);
		String result = spyMemcachedClient.get(key);
		assertEquals(value, result);

		spyMemcachedClient.delete(key);
		result = spyMemcachedClient.get(key);
		assertNull(result);
	}

	@Test
	public void bulk() {

		String key1 = "consumer:1";
		String value1 = "admin";

		String key2 = "consumer:2";
		String value2 = "calvin";

		String key3 = "invalidKey";

		spyMemcachedClient.set(key1, 60 * 60 * 1, value1);
		spyMemcachedClient.set(key2, 60 * 60 * 1, value2);

		Map<String, String> result = spyMemcachedClient.getBulk(key1, key2);
		assertEquals(value1, result.get(key1));
		assertEquals(value2, result.get(key2));
		assertNull(result.get(key3));
	}

	@Test
	public void incr() {
		String key = "incr_key";

		long result = spyMemcachedClient.incr(key, 2, 1);
		assertEquals(1, result);
		assertEquals("1", spyMemcachedClient.get(key));

		result = spyMemcachedClient.incr(key, 2, 1);
		assertEquals(3, result);
		assertEquals("3", spyMemcachedClient.get(key));

	}

	public static class Consumer {

		private int id;
		private String name;
		private String email = "admin@springside.org.cn";

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
