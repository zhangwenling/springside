package org.springside.examples.miniservice.data;

import org.springside.examples.miniservice.entity.user.Role;
import org.springside.examples.miniservice.entity.user.User;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class UserData {

	public static final String DEFAULT_PASSWORD = "123456";

	public static User getRandomUser() {
		String userName = DataUtil.random("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword(DEFAULT_PASSWORD);
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithAdminRole() {
		User user = UserData.getRandomUser();
		Role adminRole = UserData.getAdminRole();
		user.getRoleList().add(adminRole);
		return user;
	}


	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}
}
