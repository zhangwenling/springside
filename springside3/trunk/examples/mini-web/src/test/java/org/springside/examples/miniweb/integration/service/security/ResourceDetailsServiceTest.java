package org.springside.examples.miniweb.integration.service.security;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.security.springsecurity.ResourceDetailsService;
import org.springside.modules.test.spring.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext-security.xml" })
public class ResourceDetailsServiceTest extends SpringTxTestCase {

	@Autowired
	ResourceDetailsService service;

	@SuppressWarnings("unchecked")
	@Test
	public void getRequestMap() throws Exception {
		LinkedHashMap<String, String> map = service.getRequestMap();
		assertEquals(6, map.size());
		Object[] requests = map.entrySet().toArray();
		
		assertEquals("/security/user!save*", ((Entry<String, String>) requests[0]).getKey());
		assertEquals("A_MODIFY_USER", ((Entry<String, String>) requests[0]).getValue());

		assertEquals("/security/role*", ((Entry<String, String>) requests[5]).getKey());
		assertEquals("A_VIEW_ROLE", ((Entry<String, String>) requests[5]).getValue());
	}
}
