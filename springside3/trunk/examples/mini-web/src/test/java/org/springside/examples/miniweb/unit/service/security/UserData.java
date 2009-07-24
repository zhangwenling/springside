package org.springside.examples.miniweb.unit.service.security;

import org.apache.commons.lang.RandomStringUtils;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;

public class UserData {
		
	public static User getRandomUser() {
		String random = RandomStringUtils.randomAlphanumeric(5); 
		return getUser("tester" + random);
	}

	public static User getUser(String userName) {
		User user = new User();

		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("passwd");
		user.setEmail("foo@bar.com");
	
		return user;
	}
	
	public static Role getRandomRole() {
		Role role = new Role();
	
		String random = RandomStringUtils.randomAlphanumeric(5);
		role.setName("tester" + random);
	
		return role;
	}
}
