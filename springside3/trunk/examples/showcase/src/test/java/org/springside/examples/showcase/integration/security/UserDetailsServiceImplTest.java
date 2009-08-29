package org.springside.examples.showcase.integration.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.security.Operator;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * serDetailsServiceImpl的集成测试用例, 测试Service层的业务逻辑.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/security/applicationContext-security.xml" })
public class UserDetailsServiceImplTest extends SpringTxTestCase {
	@Autowired
	private UserDetailsService userDetailsService;

	@Test
	public void loadUserByUserName() {
		Operator operator = (Operator) userDetailsService.loadUserByUsername("admin");
		assertEquals("admin", operator.getUsername());
		assertEquals(new ShaPasswordEncoder().encodePassword("admin", null), operator.getPassword());
		assertEquals(2, operator.getAuthorities().length);
		assertNotNull(operator.getLoginTime());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByWrongUserName() {
		assertNull(userDetailsService.loadUserByUsername("foo"));
	}
}
