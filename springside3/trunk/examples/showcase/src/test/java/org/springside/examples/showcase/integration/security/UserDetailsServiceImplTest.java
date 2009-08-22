package org.springside.examples.showcase.integration.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.security.Operator;
import org.springside.modules.test.spring.SpringTxTestCase;

@ContextConfiguration(locations = { "/security/applicationContext-security.xml" })
public class UserDetailsServiceImplTest extends SpringTxTestCase {
	@Autowired
	private UserDetailsService userDetailsService;

	@Test
	public void loadUserByUserName() {
		Operator operator = (Operator) userDetailsService.loadUserByUsername("admin");
		assertEquals("admin", operator.getUsername());
		assertEquals(2, operator.getAuthorities().length);
		assertNotNull(operator.getLoginTime());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByWrongUserName() {
		assertNull(userDetailsService.loadUserByUsername("foo"));
	}
}
