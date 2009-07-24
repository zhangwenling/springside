package org.springside.examples.miniservice.unit.ws.user;

import org.apache.commons.lang.RandomStringUtils;
import org.springside.examples.miniservice.entity.user.Role;
import org.springside.examples.miniservice.entity.user.User;

/**
 * 测试用户数据生成
 * 
 * @author calvin
 *
 */
public class UserData {

	public static User getRandomUser() {
		String userName = "user" + RandomStringUtils.randomAlphanumeric(5);
	
		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("passwd");
		user.setEmail("foo@bar.com");

		return user;
	}

	public static Role getRandomRole() {
		String roleName = "role" + RandomStringUtils.randomAlphanumeric(5);

		Role role = new Role();
		role.setName(roleName);

		return role;
	}
}
