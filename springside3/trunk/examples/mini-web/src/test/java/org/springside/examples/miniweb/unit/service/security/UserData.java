package org.springside.examples.miniweb.unit.service.security;

import org.apache.commons.lang.RandomStringUtils;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;

/**
 * 测试用户数据生成.
 * 
 * @author calvin
 */
public class UserData {

	public static String random() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	public static User getRandomUser() {
		String userName = "User" + random();

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("passwd");
		user.setEmail("foo@bar.com");

		return user;
	}

	public static Role getRandomRole() {
		Role role = new Role();
		role.setName("Role" + random());

		return role;
	}

	public static Authority getRandomAuthority() {
		Authority authority = new Authority();
		String authName = "Authority" + random();
		authority.setName(authName);
		authority.setDisplayName(authName);

		return authority;
	}

	public static Resource getRandomResource() {
		Resource resource = new Resource();
		resource.setValue("Resource" + random());
		resource.setResourceType(Resource.URL_TYPE);
		resource.setPosition(100);

		return resource;
	}
}
