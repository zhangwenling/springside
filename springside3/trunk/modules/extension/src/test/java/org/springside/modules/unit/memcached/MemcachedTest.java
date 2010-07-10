package org.springside.modules.unit.memcached;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.memcached.SpyMemcachedClientWrapper;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/applicationContext-extension-test-memcached.xml" })
public class MemcachedTest extends SpringContextTestCase {

	@Autowired
	private SpyMemcachedClientWrapper spyClient;

	@Test
	public void normalCase() {
		String key = "key:1";
		String value = "value:hello";

		spyClient.set(key, 60 * 60 * 1, value);
		String result = spyClient.get(key);
		assertEquals(value, result);

		spyClient.delete(key);
		result = spyClient.get(key);
		assertNull(result);
	}

	@Test
	public void jsonCase() {
		String key = "key:1";
		Consumer value = new Consumer();
		value.setId(1);
		value.setName("admin");

		spyClient.setToJson(key, 60 * 60 * 1, value);
		Consumer result = spyClient.getFromJson(key, Consumer.class);
		assertEquals(value, result);
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Consumer other = (Consumer) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}
}
