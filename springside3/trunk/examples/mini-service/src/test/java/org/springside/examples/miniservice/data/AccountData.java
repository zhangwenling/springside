package org.springside.examples.miniservice.data;

import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.test.utils.DataUtils;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class AccountData {

	public static Department getRandomDepartment() {
		String departmentName = DataUtils.randomName("Department");

		Department department = new Department();
		department.setName(departmentName);
		
		User manager = getRandomUser();
		department.setManager(manager);
		department.getUserList().add(manager);

		return department;
	}

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("123456");
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}
}
