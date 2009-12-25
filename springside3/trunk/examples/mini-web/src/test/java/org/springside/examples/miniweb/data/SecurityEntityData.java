package org.springside.examples.miniweb.data;

import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;

/**
 * 安全相关实体测试数据生成.
 * 
 * @author calvin
 */
public class SecurityEntityData {

	public static final String DEFAULT_PASSWORD = "123456";

	public static User getRandomUser() {
		String userName = DataUtils.random("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword(DEFAULT_PASSWORD);
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithAdminRole() {
		User user = getRandomUser();
		Role adminRole = getAdminRole();
		user.getRoleList().add(adminRole);

		return user;
	}

	public static Role getRandomRole() {
		Role role = new Role();
		role.setName(DataUtils.random("Role"));

		return role;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}

	public static Authority getRandomAuthority() {
		String authName = DataUtils.random("Authority");

		Authority authority = new Authority();
		authority.setName(authName);
		authority.setDisplayName(authName);

		return authority;
	}

	public static Resource getRandomResource() {
		Resource resource = new Resource();
		resource.setValue(DataUtils.random("Resource"));
		resource.setResourceType(Resource.URL_TYPE);
		resource.setPosition(100);

		return resource;
	}
}
