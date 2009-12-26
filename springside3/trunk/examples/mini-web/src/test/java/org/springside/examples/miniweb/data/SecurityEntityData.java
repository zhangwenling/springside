package org.springside.examples.miniweb.data;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Role> defaultRoleList = new ArrayList<Role>();

	public static List<Authority> defaultAuthorityList = new ArrayList<Authority>();

	static {
		defaultRoleList.add(new Role(1L, "管理员"));
		defaultRoleList.add(new Role(2L, "用户"));
		defaultAuthorityList.add(new Authority(1L, "A_VIEW_USER", "浏览用户"));
		defaultAuthorityList.add(new Authority(2L, "A_MODIFY_USER", "修改用户"));
		defaultAuthorityList.add(new Authority(3L, "A_VIEW_ROLE", "浏览角色"));
		defaultAuthorityList.add(new Authority(4L, "A_MODIFY_ROLE", "修改角色"));
	}

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword(DEFAULT_PASSWORD);
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithRole() {
		User user = getRandomUser();
		user.getRoleList().add(getRandomDefaultRole());

		return user;
	}

	public static Role getRandomRole() {
		Role role = new Role();
		role.setName(DataUtils.randomName("Role"));

		return role;
	}

	public static Role getRandomRoleWithAuthority() {
		Role role = getRandomRole();
		role.getAuthorityList().add(getRandomDefaultAuthority());
		return role;
	}

	public static Role getRandomDefaultRole() {
		return DataUtils.randomFromList(defaultRoleList);
	}

	public static Authority getRandomAuthority() {
		String authName = DataUtils.randomName("Authority");

		Authority authority = new Authority();
		authority.setName(authName);
		authority.setDisplayName(authName);

		return authority;
	}

	public static Authority getRandomDefaultAuthority() {
		return DataUtils.randomFromList(defaultAuthorityList);
	}

	public static Resource getRandomResource() {
		Resource resource = new Resource();
		resource.setValue(DataUtils.randomName("Resource"));
		resource.setResourceType(Resource.URL_TYPE);
		resource.setPosition(100);

		return resource;
	}
}
